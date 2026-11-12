param(
    [Parameter(Mandatory = $true)]
    [string[]]$Versions,
    [string]$OutRoot = "ports/cache",
    [string]$StockRoot = "libs"
)

$ErrorActionPreference = "Stop"
$env:JAVA_HOME = if ($env:JAVA_HOME) { $env:JAVA_HOME } else { "C:\Users\johnp\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot" }
Set-Location "C:\Users\johnp\Downloads\baritone"

# ---- cache release metadata (limited API calls) ----------------------------
$relJson = Join-Path $OutRoot "releases.json"
if (-not (Test-Path $relJson)) {
    $all = @()
    for ($page = 1; $page -le 4; $page++) {
        $r = Invoke-RestMethod "https://api.github.com/repos/cabaletta/baritone/releases?per_page=100&page=$page"
        if ($r.Count -eq 0) { break }
        $all += $r
    }
    $all | ConvertTo-Json -Depth 4 | Set-Content $relJson
}
$releases = Get-Content $relJson -Raw | ConvertFrom-Json

function Resolve-Stock($mc, $loader) {
    foreach ($rel in $releases) {
        $body = "$($rel.body)"
        if ($body -match "For Minecraft .*$([regex]::Escape($mc))") {
            $asset = $rel.assets | Where-Object { $_.name -eq "baritone-unoptimized-$loader-$($rel.tag_name).jar" } | Select-Object -First 1
            if (-not $asset) { $asset = $rel.assets | Where-Object { $_.name -match "unoptimized-$loader" } | Select-Object -First 1 }
            if ($asset) {
                $out = Join-Path $StockRoot "stock-baritone-$loader-$mc.jar"
                Invoke-WebRequest $asset.browser_download_url -OutFile $out
                return $out
            }
        }
    }
    return $null
}

foreach ($v in $Versions) {
    Write-Host "==================== $v ===================="
    $stock = Resolve-Stock $v "fabric"
    if (-not $stock) { Write-Host "NO STOCK JAR for $v"; continue }

    .\ports\fetch-deps.ps1 -Version $v -OutDir (Join-Path $OutRoot $v) | Out-Null

    # makeIntermediary via gradle.properties injection
    $orig = Get-Content gradle.properties -Raw
    $over = @"
org.gradle.jvmargs=-Xmx4G
port_version=$v
port_client=C:/Users/johnp/Downloads/baritone/$OutRoot/$v/client.jar
port_server=C:/Users/johnp/Downloads/baritone/$OutRoot/$v/server.jar
port_mappings=C:/Users/johnp/Downloads/baritone/$OutRoot/$v/intermediary.tiny
port_out=C:/Users/johnp/Downloads/baritone/$OutRoot/$v/minecraft-merged-intermediary.jar
"@
    Add-Content -Path gradle.properties -Value $over
    .\gradlew.bat makeIntermediary --console=plain | Select-String -Pattern "wrote|Exception" | Select-Object -Last 1 | ForEach-Object { Write-Host "  $($_.Line)" }
    Set-Content -Path gradle.properties -Value $orig

    $env:MC_VERSION = $v
    $env:MC_STOCK_BARITONE_JAR = $stock.Replace('\', '/')
    $env:MC_INTERMEDIARY_JAR = "$OutRoot/$v/minecraft-merged-intermediary.jar"
    $env:MC_MODMENU_JAR = "libs/modmenu-17.0.1.jar"

    $out = .\gradlew.bat build verifyAnchors --console=plain -x test 2>&1 |
        Select-String -Pattern "checks:|FAIL:|BUILD (SUCCESSFUL|FAILED)|error:" | ForEach-Object { $_.Line }
    $out | Select-Object -First 15
}
