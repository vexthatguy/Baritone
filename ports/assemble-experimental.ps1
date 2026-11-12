param(
    [Parameter(Mandatory = $true)]
    [string]$Version,
    [Parameter(Mandatory = $true)]
    [ValidateSet("fabric", "forge", "neoforge")]
    [string]$Loader
)

$ErrorActionPreference = "Stop"
$env:JAVA_HOME = if ($env:JAVA_HOME) { $env:JAVA_HOME } else { "C:\Users\johnp\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot" }
Set-Location "C:\Users\johnp\Downloads\baritone"

$uiJar = "build/forge-ui/ui-remapped.jar"
if (-not (Test-Path $uiJar)) {
    Write-Host "run remapUi first"
    exit 2
}
$stock = "libs/stock-baritone-$Loader-$Version.jar"
if (-not (Test-Path $stock)) {
    .\ports\get-stock.ps1 -Version $Version -Loader $Loader | Out-Null
}

$env:MC_VERSION = $Version
$env:MC_STOCK_BARITONE_JAR = $stock
$env:MC_INTERMEDIARY_JAR = "ports/cache/$Version/minecraft-merged-$Version.jar"
$env:MC_MODMENU_JAR = "libs/modmenu-17.0.1.jar"

.\gradlew.bat renameJar --console=plain 2>&1 | Out-Null

$out = "ports/cache/$Version/baritone-remastered-EXPERIMENTAL-$Loader-$Version.jar"
Copy-Item "build/libs/renamed-baritone-$Version-SNAPSHOT.jar" $out -Force

$tmp = "ports/cache/$Version/_overlay_exp"
Remove-Item $tmp -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force $tmp | Out-Null
Push-Location $tmp
& "$env:JAVA_HOME\bin\jar.exe" xf "C:\Users\johnp\Downloads\baritone\$uiJar"
Pop-Location
& "$env:JAVA_HOME\bin\jar.exe" uf $out -C $tmp .
Remove-Item $tmp -Recurse -Force
Write-Host "wrote $out"
