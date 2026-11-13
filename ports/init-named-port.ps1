param(
    [Parameter(Mandatory = $true)]
    [string]$Version,
    [string]$OutDir = "src/named/$Version/java"
)

$ErrorActionPreference = "Stop"
Set-Location "C:\Users\johnp\Downloads\baritone"

$src = "src/main/java/com/vex/baritone"
$dst = "$OutDir/com/vex/baritone"
New-Item -ItemType Directory -Force $dst | Out-Null
Copy-Item "$src\*" $dst -Recurse -Force

# Build intermediary->named class map from yarn 1.21.11 tiny.
$yarn = "ports/cache/1.21.11/yarn.tiny"
$classMap = @{}
foreach ($line in [System.IO.File]::ReadLines($yarn)) {
    if ($line.StartsWith("c`t")) {
        $parts = $line.Split("`t")
        if ($parts.Count -ge 3) { $classMap[$parts[1]] = $parts[2] }
    }
}
Write-Host "yarn class entries: $($classMap.Count)"

# Verified package renames from GuessMappings (applied after token replacement).
$table = @{
    "net.minecraft.client.gui.screen.Screen" = "net.minecraft.client.gui.screens.Screen"
    "net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen" = "net.minecraft.client.gui.screens.CreditsAndAttributionScreen"
    "net.minecraft.util.Identifier" = "net.minecraft.resources.Identifier"
    "net.minecraft.util.math.BlockPos" = "net.minecraft.core.BlockPos"
    "net.minecraft.world.biome.Biome" = "net.minecraft.world.level.biome.Biome"
    "net.minecraft.client.network.ServerInfo" = "net.minecraft.server.ServerInfo"
    "net.minecraft.registry.Registry" = "net.minecraft.core.Registry"
    "net.minecraft.registry.DynamicRegistryManager" = "net.minecraft.core.RegistryAccess"
    "net.minecraft.registry.RegistryKey" = "net.minecraft.resources.ResourceKey"
    "net.minecraft.registry.entry.RegistryEntry" = "net.minecraft.core.RegistryAccess\$RegistryEntry"
}

Get-ChildItem $dst -Recurse -Filter "*.java" | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    $content = [regex]::Replace($content, '\bclass_\d+\b', {
        param($m)
        $t = $m.Value
        $key = "net/minecraft/$t"
        if ($classMap.ContainsKey($key)) { return $classMap[$key] }
        return $t
    })
    # normalize fused package prefixes left by the token replacement
    $content = [regex]::Replace($content, 'net\.minecraft\.net/minecraft/([A-Za-z0-9_/$]+)', {
        param($m)
        'net.minecraft.' + ($m.Groups[1].Value -replace '/', '.')
    })
    # convert remaining slash-paths to dotted FQNs (type usages)
    $content = [regex]::Replace($content, '\bnet/minecraft/[A-Za-z0-9_/$]+', {
        param($m)
        $m.Value -replace '/', '.'
    })
    foreach ($k in $table.Keys) {
        $content = $content.Replace($k, $table[$k])
    }
    Set-Content $_.FullName $content -NoNewline
}

Write-Host "named source scaffold ready at $OutDir"
