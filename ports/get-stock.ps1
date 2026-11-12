param(
    [Parameter(Mandatory = $true)]
    [string]$Version,
    [string]$Loader = "fabric",
    [string]$OutDir = "libs"
)

$ErrorActionPreference = "Stop"
$token = $env:GITHUB_TOKEN
$headers = @{}
if ($token) { $headers.Authorization = "Bearer $token" }

# Resolve release tag for this MC version from release bodies.
$page = 1
$found = $null
while ($page -le 10 -and -not $found) {
    $releases = Invoke-RestMethod -Headers $headers "https://api.github.com/repos/cabaletta/baritone/releases?per_page=100&page=$page"
    if ($releases.Count -eq 0) { break }
    foreach ($rel in $releases) {
        $body = "$($rel.body)"
        if ($body -match "For Minecraft [\d.]+ and [\d.]+" -or $body -match "For Minecraft $Version") {
            if ($body -match "For Minecraft .*$([regex]::Escape($Version))") {
                $found = $rel
                break
            }
        }
    }
    $page++
}
if (-not $found) {
    throw "No Baritone release found mentioning Minecraft $Version"
}
Write-Host "release: $($found.tag_name) (MC $Version)"

$assetName = "baritone-unoptimized-$Loader-$($found.tag_name).jar"
$asset = $found.assets | Where-Object { $_.name -eq $assetName } | Select-Object -First 1
if (-not $asset) {
    # fallback: any asset with that loader in name
    $asset = $found.assets | Where-Object { $_.name -match "unoptimized-$Loader" } | Select-Object -First 1
}
if (-not $asset) {
    throw "No $Loader asset in $($found.tag_name)"
}

$out = Join-Path $OutDir "stock-baritone-$Loader-$Version.jar"
Write-Host "downloading $($asset.name) -> $out"
Invoke-WebRequest -Headers $headers $asset.browser_download_url -OutFile $out
Write-Host $out
