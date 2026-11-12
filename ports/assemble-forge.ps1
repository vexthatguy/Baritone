param(
    [Parameter(Mandatory = $true)]
    [string]$Version,
    [Parameter(Mandatory = $true)]
    [ValidateSet("forge", "neoforge")]
    [string]$Loader
)

$ErrorActionPreference = "Stop"
$env:JAVA_HOME = if ($env:JAVA_HOME) { $env:JAVA_HOME } else { "C:\Users\johnp\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot" }
Set-Location "C:\Users\johnp\Downloads\baritone"

$env:MC_VERSION = $Version
$env:MC_STOCK_BARITONE_JAR = "libs/stock-baritone-$Loader-$Version.jar"
$env:MC_INTERMEDIARY_JAR = "ports/cache/$Version/minecraft-merged-intermediary.jar"
$env:MC_MODMENU_JAR = "libs/modmenu-17.0.1.jar"

if (-not (Test-Path $env:MC_STOCK_BARITONE_JAR)) {
    .\ports\get-stock.ps1 -Version $Version -Loader $Loader | Out-Null
}

# ensure intermediary build + remapped UI exist
$uiJar = "build/forge-ui/ui-remapped.jar"
if (-not (Test-Path $uiJar)) {
    Write-Host "run remapUi first (needs yarn.tiny)"
    exit 2
}

# Forge jars are Mojang-official-named; give the patcher the official merged
# jar as classpath instead of the intermediary one.
$env:MC_INTERMEDIARY_JAR = "ports/cache/$Version/merged.jar"

.\gradlew.bat renameJar patchBaritone --console=plain 2>&1 | Out-Null

$out = "ports/cache/$Version/baritone-remastered-$Loader-$Version.jar"
Copy-Item "build/libs/baritone-patched.jar" $out -Force

# overlay remapped UI classes
$tmp = "ports/cache/$Version/_overlay"
Remove-Item $tmp -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force $tmp | Out-Null
Push-Location $tmp
& "$env:JAVA_HOME\bin\jar.exe" xf "C:\Users\johnp\Downloads\baritone\$uiJar"
Pop-Location
& "$env:JAVA_HOME\bin\jar.exe" uf $out -C $tmp .
Remove-Item $tmp -Recurse -Force
Write-Host "wrote $out"
