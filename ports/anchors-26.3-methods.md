# 26.3 method/event deltas (probed)

## EditBox (form field)
- focus/click: method_25348(Click, Z) -> onClick(MouseButtonEvent, boolean)
- render: method_48579(DrawContext,IIF) -> extractWidgetRenderState(GuiGraphicsExtractor,IIF)
- key/char events: net.minecraft.client.input.KeyEvent / CharacterEvent / PreeditEvent
## DrawContext
- removed entirely -> GuiGraphicsExtractor (+ widget render-state extraction)
- RenderKit/DrawSupport/ScrollPanelElement must be rewritten against this API
## Level
- no direct getBiome found in probes; BiomeManager holds biome data (verify)
## Button
- builder(Component, OnPress) exists; setMessage unresolved -> check AbstractButton
## ServerInfo
- isRealm() not found; verify fields (maybe removed in 26.x)
## Font
- width via StringVisitable not found; likely width(Component)
## RegistryAccess
- not a real type; suggest net.minecraft.core.Holder (verify)
