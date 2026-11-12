param(
    [Parameter(Mandatory = $true)]
    [string]$Version,
    [string]$OutDir = "ports\cache\$Version"
)

$ErrorActionPreference = "Stop"
New-Item -ItemType Directory -Force $OutDir | Out-Null
$OutDir = (Get-Item $OutDir).FullName

# ---- Mojang version manifest ------------------------------------------------
Write-Host "Fetching Mojang version manifest..."
$manifest = Invoke-RestMethod "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"
$ver = $manifest.versions | Where-Object { $_.id -eq $Version } | Select-Object -First 1
if (-not $ver) { throw "Minecraft version $Version not found in manifest" }
$meta = Invoke-RestMethod $ver.url

$clientUrl = $meta.downloads.client.url
$serverUrl = $meta.downloads.server.url

Write-Host "Downloading client jar..."
Invoke-WebRequest $clientUrl -OutFile "$OutDir\client.jar"
Write-Host "Downloading server jar..."
Invoke-WebRequest $serverUrl -OutFile "$OutDir\server.jar"

# ---- Yarn mappings (optional; used only for named output) -------------------
try {
    Write-Host "Resolving yarn build for 1.21..."
    $yarnMeta = Invoke-RestMethod "https://maven.fabricmc.net/net/fabricmc/yarn/maven-metadata.xml"
    $yarnVersions = @($yarnMeta.versioning.versions.version) | Where-Object { $_ -like "$Version+build.*" }
    if ($yarnVersions.Count -gt 0) {
        $yarnVer = $yarnVersions | Sort-Object { [int]($_ -replace ".*\+build\.", "") } -Descending | Select-Object -First 1
        $yarnUrl = "https://maven.fabricmc.net/net/fabricmc/yarn/$yarnVer/yarn-$yarnVer-v2.jar"
        Write-Host "Downloading yarn $yarnVer"
        Invoke-WebRequest $yarnUrl -OutFile "$OutDir\yarn.jar"
        $tmpY = "$OutDir\_yarn_extract"
        Remove-Item $tmpY -Recurse -Force -ErrorAction SilentlyContinue
        New-Item -ItemType Directory -Force $tmpY | Out-Null
        Push-Location $tmpY
        & "$env:JAVA_HOME\bin\jar.exe" xf "$OutDir\yarn.jar" mappings.tiny
        Pop-Location
        Move-Item -Force "$tmpY\mappings.tiny" "$OutDir\yarn.tiny"
        Remove-Item $tmpY -Recurse -Force
    } else {
        Write-Host "No yarn build found for $Version (skipping, not needed for intermediary)"
    }
} catch {
    Write-Host "Yarn lookup skipped: $($_.Exception.Message)"
}

# ---- Intermediary v2 mappings (official -> intermediary) --------------------
$medUrl = "https://maven.fabricmc.net/net/fabricmc/intermediary/$Version/intermediary-$Version-v2.jar"
Write-Host "Downloading intermediary $Version"
try {
    Invoke-WebRequest $medUrl -OutFile "$OutDir\intermediary.jar"
} catch {
    throw "Fabric has not published intermediary mappings for $Version yet (latest on maven.fabricmc.net is usually the newest Fabric-supported release). The port pipeline is blocked until it does."
}

Write-Host "Extracting mappings/mappings.tiny from intermediary"
$tmp2 = "$OutDir\_med_extract"
Remove-Item $tmp2 -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force $tmp2 | Out-Null
Push-Location $tmp2
& "$env:JAVA_HOME\bin\jar.exe" xf "$OutDir\intermediary.jar" mappings/mappings.tiny
Pop-Location
Move-Item -Force "$tmp2\mappings\mappings.tiny" "$OutDir\intermediary.tiny"
Remove-Item $tmp2 -Recurse -Force

Write-Host ""
Write-Host "Done. Files in $OutDir :"
Get-ChildItem $OutDir | Select-Object Name, Length

Write-Host ""
Write-Host "Next:"
Write-Host "  gradlew makeIntermediary -Pport_version=$Version -Pport_client=$OutDir/client.jar -Pport_server=$OutDir/server.jar -Pport_mappings=$OutDir/intermediary.tiny -Pport_out=$OutDir/minecraft-merged-intermediary.jar"
