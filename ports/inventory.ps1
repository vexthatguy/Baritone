param()

$ErrorActionPreference = "Continue"
Write-Host "Baritone Remastered artifact inventory"
Write-Host "======================================"
Get-ChildItem "build/libs" -Filter "baritone-*-SNAPSHOT.*.jar" -ErrorAction SilentlyContinue | Sort-Object Name | ForEach-Object { Write-Host ("FABRIC  {0}  {1:N0} bytes" -f $_.Name, $_.Length) }
Get-ChildItem "ports/cache" -Recurse -Filter "baritone-remastered-*-*.jar" -ErrorAction SilentlyContinue | Sort-Object FullName | ForEach-Object {
    $rel = $_.FullName.Replace("C:\Users\johnp\Downloads\baritone\", "")
    $loader = if ($_.Name -match "forge") { "FORGE" } else { "NEOFORGE" }
    Write-Host ("{0,-8} {1}  {2:N0} bytes" -f $loader, $rel, $_.Length)
}
Write-Host ""
Write-Host "Run ./ports/port-all-loaders.ps1 -Versions 1.21.7,1.21.8 to rebuild any version/loader."
