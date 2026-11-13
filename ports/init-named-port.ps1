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

# Verified package renames (applied after token replacement), order matters.
$renames = @(
    @('net.minecraft.client.gui.screen.Screen', 'net.minecraft.client.gui.screens.Screen'),
    @('net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen', 'net.minecraft.client.gui.screens.CreditsAndAttributionScreen'),
    @('net.minecraft.util.Identifier', 'net.minecraft.resources.Identifier'),
    @('net.minecraft.util.math.BlockPos', 'net.minecraft.core.BlockPos'),
    @('net.minecraft.world.biome.Biome', 'net.minecraft.world.level.biome.Biome'),
    @('net.minecraft.client.network.ServerInfo', 'net.minecraft.server.ServerInfo'),
    @('net.minecraft.registry.Registry', 'net.minecraft.core.Registry'),
    @('net.minecraft.registry.DynamicRegistryManager', 'net.minecraft.core.RegistryAccess'),
    @('net.minecraft.registry.RegistryKey', 'net.minecraft.resources.ResourceKey'),
    @('net.minecraft.registry.entry.RegistryEntry', 'net.minecraft.core.RegistryAccess$RegistryEntry'),
    @('net.minecraft.text.Text', 'net.minecraft.network.chat.Component'),
    @('net.minecraft.text.MutableText', 'net.minecraft.network.chat.MutableComponent'),
    @('net.minecraft.client.MinecraftClient', 'net.minecraft.client.Minecraft'),
    @('net.minecraft.client.font.TextRenderer', 'net.minecraft.client.gui.Font'),
    @('net.minecraft.client.gui.widget.TextFieldWidget', 'net.minecraft.client.gui.components.EditBox'),
    @('net.minecraft.client.gui.widget.ButtonWidget', 'net.minecraft.client.gui.components.Button'),
    @('net.minecraft.client.gui.widget.ButtonWidget$Builder', 'net.minecraft.client.gui.components.Button$Builder'),
    @('net.minecraft.client.gui.widget.ButtonWidget$PressAction', 'net.minecraft.client.gui.components.Button$OnPress'),
    @('net.minecraft.client.gui.Element', 'net.minecraft.client.gui.components.events.GuiEventListener'),
    @('net.minecraft.client.network.ClientPlayNetworkHandler', 'net.minecraft.client.multiplayer.ClientPacketListener'),
    @('net.minecraft.client.network.ClientPlayerEntity', 'net.minecraft.client.player.LocalPlayer'),
    @('net.minecraft.world.World', 'net.minecraft.world.level.Level'),
    @('net.minecraft.client.util.Window', 'com.mojang.blaze3d.platform.Window'),
    @('net.minecraft.client.gui.DrawContext', 'net.minecraft.client.gui.GuiGraphicsExtractor')
)

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
    foreach ($r in $renames) {
        $content = $content.Replace($r[0], $r[1])
    }
    Set-Content $_.FullName $content -NoNewline
}

# overlay hand-authored named-port overrides for this version
$hand = "src/named-port/$Version/java"
if (Test-Path $hand) {
    Copy-Item "$hand\com\vex\baritone\*" $dst -Recurse -Force
    Write-Host "overlaid hand-authored overrides from $hand"
}

# 26.3 screen-plumbing renames (post-overlay)
Get-ChildItem $dst -Recurse -Filter "*.java" | ForEach-Object {
    $c = Get-Content $_.FullName -Raw
    $c = $c.Replace('public void method_25394(net.minecraft.client.gui.GuiGraphicsExtractor', 'public void extractRenderState(net.minecraft.client.gui.GuiGraphicsExtractor')
    $c = $c.Replace('super.method_25394(', 'super.extractRenderState(')
    $c = $c.Replace('method_25419()', 'onClose()')
    $c = $c.Replace('.method_60655(', '.of(')
    $c = $c.Replace('method_43470(', 'literal(')
    $c = $c.Replace('.of("baritone", "textures/icon.png")', '.fromNamespaceAndPath("baritone", "textures/icon.png")')
    $c = $c.Replace('.field_1697.toPath()', '.gameDirectory.toPath()')
    $c = $c.Replace('public boolean method_25401(', 'public boolean mouseScrolled(')
    $c = $c.Replace('super.method_25401(', 'super.mouseScrolled(')
    Set-Content $_.FullName $c -NoNewline
}

# modmenu integration is loader-specific and cannot link against 26.3 UI; drop it
Remove-Item (Join-Path $dst "ModMenuIntegration.java") -Force -ErrorAction SilentlyContinue
Remove-Item (Join-Path $dst "ModMenuVisibility.java") -Force -ErrorAction SilentlyContinue

Write-Host "named source scaffold ready at $OutDir"
