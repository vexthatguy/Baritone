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

## GuiGraphicsExtractor API (26.3) - rewrite target for RenderKit/DrawSupport
- fill(x1,y1,x2,y2,color) | fillGradient(x1,y1,x2,y2,c1,c2)
- horizontalLine(x1,x2,y,color) | verticalLine(x,y1,y2,color) | outline(x,y,w,h,color)
- enableScissor(x,y,w,h) / disableScissor()
- text(Font, String|Component|FormattedCharSequence, x, y, color [, shadow])
- centeredText(Font, text, x, y, color)
- blit(Identifier, x, y, w, h, u0, v0, u1, v1)  [no-pipeline convenience overload]
- pose() -> Matrix3x2fStack (push/pop/scale available)
- Screen render hook: extractRenderState(GuiGraphicsExtractor, int, int, float)

