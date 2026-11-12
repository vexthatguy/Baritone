param(
    [Parameter(Mandatory = $true)]
    [string[]]$Versions
)

$ErrorActionPreference = "Continue"
$env:JAVA_HOME = if ($env:JAVA_HOME) { $env:JAVA_HOME } else { "C:\Users\johnp\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot" }
Set-Location "C:\Users\johnp\Downloads\baritone"

$relJson = "ports/cache/releases.json"
if (-not (Test-Path $relJson)) {
    $all = @()
    for ($page = 1; $page -le 6; $page++) {
        $r = Invoke-RestMethod "https://api.github.com/repos/cabaletta/baritone/releases?per_page=100&page=$page"
        if ($r.Count -eq 0) { break }
        $all += $r
    }
    $all | ConvertTo-Json -Depth 4 | Set-Content $relJson
}
$releases = Get-Content $relJson -Raw | ConvertFrom-Json

function Resolve-Stock($mc, $loader) {
    foreach ($rel in $releases) {
        if ("$($rel.body)" -match "For Minecraft .*$([regex]::Escape($mc))") {
            $asset = $rel.assets | Where-Object { $_.name -eq "baritone-unoptimized-$loader-$($rel.tag_name).jar" } | Select-Object -First 1
            if (-not $asset) { $asset = $rel.assets | Where-Object { $_.name -match "unoptimized-$loader" } | Select-Object -First 1 }
            if ($asset) {
                $out = "libs/stock-baritone-$loader-$mc.jar"
                if (-not (Test-Path $out)) {
                    Invoke-WebRequest $asset.browser_download_url -OutFile $out
                }
                return $out
            }
        }
    }
    return $null
}

foreach ($v in $Versions) {
    Write-Host "==================== $v ===================="
    foreach ($loader in @("fabric", "forge", "neoforge")) {
        $s = Resolve-Stock $v $loader
        Write-Host "  stock[$loader]: $s"
    }
    if (-not (Test-Path "ports/cache/$v/intermediary.tiny")) {
        .\ports\fetch-deps.ps1 -Version $v -OutDir "ports/cache/$v" | Out-Null
    }
    if (-not (Test-Path "ports/cache/$v/yarn.tiny")) {
        try {
            $yarnXml = (Invoke-WebRequest "https://maven.fabricmc.net/net/fabricmc/yarn/maven-metadata.xml" -UseBasicParsing -TimeoutSec 30).Content
            $yarnVersions = [regex]::Matches($yarnXml, '<version>([^<]+)</version>') | ForEach-Object { $_.Groups[1].Value } |
                Where-Object { $_ -like "$v+build.*" }
            if ($yarnVersions.Count -gt 0) {
                $yarnVer = $yarnVersions | Sort-Object { [int]($_ -replace ".*\+build\.", "") } -Descending | Select-Object -First 1
                Invoke-WebRequest "https://maven.fabricmc.net/net/fabricmc/yarn/$yarnVer/yarn-$yarnVer-v2.jar" -OutFile "ports/cache/$v/yarn.jar"
                $tmpY = "ports/cache/$v/_yarn_extract"
                Remove-Item $tmpY -Recurse -Force -ErrorAction SilentlyContinue
                New-Item -ItemType Directory -Force $tmpY | Out-Null
                Push-Location $tmpY
                & "$env:JAVA_HOME\bin\jar.exe" xf "C:\Users\johnp\Downloads\baritone\ports\cache\$v\yarn.jar" mappings/mappings.tiny
                Pop-Location
                Move-Item -Force "$tmpY\mappings\mappings.tiny" "ports/cache/$v/yarn.tiny"
                Remove-Item $tmpY -Recurse -Force
                Write-Host "  yarn: $yarnVer"
            }
        } catch { Write-Host "  (yarn skipped)" }
    }
    if (-not (Test-Path "ports/cache/$v/minecraft-merged-intermediary.jar")) {
        $orig = Get-Content gradle.properties -Raw
        $over = @"
org.gradle.jvmargs=-Xmx4G
port_version=$v
port_client=C:/Users/johnp/Downloads/baritone/ports/cache/$v/client.jar
port_server=C:/Users/johnp/Downloads/baritone/ports/cache/$v/server.jar
port_mappings=C:/Users/johnp/Downloads/baritone/ports/cache/$v/intermediary.tiny
port_out=C:/Users/johnp/Downloads/baritone/ports/cache/$v/minecraft-merged-intermediary.jar
"@
        Add-Content -Path gradle.properties -Value $over
        .\gradlew.bat makeIntermediary --console=plain | Out-Null
        Set-Content -Path gradle.properties -Value $orig
    }

    $env:MC_VERSION = $v
    $env:MC_STOCK_BARITONE_JAR = "libs/stock-baritone-fabric-$v.jar"
    $env:MC_INTERMEDIARY_JAR = "ports/cache/$v/minecraft-merged-intermediary.jar"
    $env:MC_MODMENU_JAR = "libs/modmenu-17.0.1.jar"

    # Fabric build + verify (retry once without frame computation if it crashes)
    .\gradlew.bat build verifyAnchors --console=plain -x test 2>&1 |
        Select-String -Pattern "checks:|FAIL:|BUILD (SUCCESSFUL|FAILED)" | ForEach-Object { Write-Host "  fabric: $($_.Line)" }
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  fabric retry (no-frames)"
        $env:MC_NO_FRAMES = "1"
        .\gradlew.bat build verifyAnchors --console=plain -x test 2>&1 |
            Select-String -Pattern "checks:|FAIL:|BUILD (SUCCESSFUL|FAILED)" | ForEach-Object { Write-Host "  fabric: $($_.Line)" }
        Remove-Item Env:MC_NO_FRAMES -ErrorAction SilentlyContinue
    }

    # UI remap (yarn) for forge/neoforge - only when compileUi succeeded
    .\gradlew.bat compileUi --console=plain 2>&1 | Out-Null
    if ($LASTEXITCODE -eq 0 -and (Test-Path "ports/cache/$v/yarn.tiny")) {
        .\gradlew.bat remapUi --console=plain 2>&1 | Out-Null
        $env:MC_NO_FRAMES = "1"
        foreach ($loader in @("forge", "neoforge")) {
            if (Test-Path "libs/stock-baritone-$loader-$v.jar") {
                .\ports\assemble-forge.ps1 -Version $v -Loader $loader 2>&1 |
                    Select-String -Pattern "wrote" | ForEach-Object { Write-Host "  ${loader}: $($_.Line)" }
            }
        }
        Remove-Item Env:MC_NO_FRAMES -ErrorAction SilentlyContinue
    } else {
        Write-Host "  (no yarn for $v; forge/neoforge skipped)"
    }
}
