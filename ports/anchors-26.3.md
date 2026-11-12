# 26.3 port diff (LinkCheck)

Status: experimental 26.3 builds exist for fabric/forge/neoforge, but our UI
classes are compiled against 1.21.11 yarn names. The 26.x runtime ships its
own named API, so classes/methods moved. This is the concrete list of missing
references discovered by LinkCheck against the 26.3 merged runtime.

Result: 56 UI classes checked, 70 missing references.

```
indexed minecraft-merged-26.3.jar (11387 types)
indexed baritone-remastered-EXPERIMENTAL-fabric-26.3.jar (11912 types)
indexed modmenu-17.0.1.jar (11986 types)
LinkCheck checked 56 classes; missing references: 70
  MISSING: com/vex/baritone/config/BaritoneConfigScreen.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/BaritoneConfigScreen.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/registry/DynamicRegistryManager
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/registry/Registry
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/registry/RegistryKey
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/registry/entry/RegistryEntry
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/util/math/BlockPos
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/world/World
  MISSING: com/vex/baritone/config/BiomeCommand.class -> net/minecraft/world/biome/Biome
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/client/gui/Element
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/client/gui/widget/ButtonWidget
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/client/gui/widget/ButtonWidget$Builder
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/client/gui/widget/ButtonWidget$PressAction
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/text/MutableText
  MISSING: com/vex/baritone/config/CreditsToggle.class -> net/minecraft/text/Text
  MISSING: com/vex/baritone/config/FeatureConfig.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/HomeCommand.class -> net/minecraft/util/math/BlockPos
  MISSING: com/vex/baritone/config/ModMenuIntegration.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/NoopBaritoneProvider.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/NoopBaritoneProvider.class -> net/minecraft/client/network/ClientPlayNetworkHandler
  MISSING: com/vex/baritone/config/NoopBaritoneProvider.class -> net/minecraft/client/network/ClientPlayerEntity
  MISSING: com/vex/baritone/config/ScriptCommand.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/ScriptCommand.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/font/TextRenderer
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/gui/Element
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/client/gui/widget/TextFieldWidget
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/text/MutableText
  MISSING: com/vex/baritone/config/ScriptCreateScreen.class -> net/minecraft/text/Text
  MISSING: com/vex/baritone/config/ScriptManager.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/WorldKey.class -> net/minecraft/client/network/ServerInfo
  MISSING: com/vex/baritone/config/mixin/ScreenRenderMixin.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/mixin/ScreenRenderMixin.class -> net/minecraft/client/gui/screen/option/CreditsAndAttributionScreen
  MISSING: com/vex/baritone/config/ui/AbstractElement$Builder.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/AbstractElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/ButtonElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/CategoryElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/CategoryElement.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/ui/ConfigBase.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/ConfigBase.class -> net/minecraft/text/MutableText
  MISSING: com/vex/baritone/config/ui/ConfigBase.class -> net/minecraft/text/Text
  MISSING: com/vex/baritone/config/ui/ConfigBase.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/ui/DrawSupport.class -> net/minecraft/client/gl/RenderPipelines
  MISSING: com/vex/baritone/config/ui/DrawSupport.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/DrawSupport.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/ui/GuiElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/ModuleElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/ModuleElement.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/ui/NavItem.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/RemasteredUiBase.class -> net/minecraft/client/gui/Click
  MISSING: com/vex/baritone/config/ui/RemasteredUiBase.class -> net/minecraft/client/gui/screen/Screen
  MISSING: com/vex/baritone/config/ui/RemasteredUiBase.class -> net/minecraft/client/gui/widget/TextFieldWidget
  MISSING: com/vex/baritone/config/ui/RemasteredUiBase.class -> net/minecraft/client/input/MouseInput
  MISSING: com/vex/baritone/config/ui/RemasteredUiBase.class -> net/minecraft/text/Text
  MISSING: com/vex/baritone/config/ui/RenderAction.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/client/MinecraftClient
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/client/font/TextRenderer
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/client/util/Window
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/text/MutableText
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/text/StringVisitable
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/text/Text
  MISSING: com/vex/baritone/config/ui/RenderKit.class -> net/minecraft/util/Identifier
  MISSING: com/vex/baritone/config/ui/ScrollPanelElement.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/SettingRow.class -> net/minecraft/client/gui/DrawContext
  MISSING: com/vex/baritone/config/ui/ToggleRow.class -> net/minecraft/client/gui/DrawContext

```

## How to use

- Add src/version/26.3/java overlays that adapt the renamed references.
- Re-run LinkCheck until zero missing.
- Re-run ssemble-experimental.ps1 -Version 26.3 -Loader <loader>.
