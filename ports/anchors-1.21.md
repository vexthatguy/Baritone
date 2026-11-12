# Anchor / API deltas for MC 1.21

Status of the 1.21 port as of this snapshot. Dependencies are fetched and the
merged intermediary jar builds; the command layer compiles; the config GUI
still needs the per-version input/rendering work listed below.

## Completed for 1.21

- [x] Stock Baritone jar present: `libs/baritone-unoptimized-fabric-1.21.jar`
- [x] Deps fetched: `ports/cache/1.21/{client,server,intermediary.tiny}.jar`
- [x] `makeIntermediary` produces `ports/cache/1.21/minecraft-merged-intermediary.jar` (8273 remapped classes)
- [x] Build pipeline parameterized (`-Pmc_version=1.21 ...`)
- [x] Command layer compiles for 1.21 (commands, managers, config, script storage)
- [x] `Command.baritone` field is `protected` in 1.21 -> `CommandBootstrap` reads it reflectively
- [x] Settings missing in 1.21 (`allowPlaceInFluidsSource/Flow`, `allowJumpAtBuildLimit`) -> guarded via `SettingRefs`
- [x] Legacy ClickCrystals source excluded from build; `BaritoneModules` now reflective/no-op when ClickCrystals absent

## Remaining for 1.21 (GUI layer)

1. **Screen mouse input** — 1.21.11 uses `method_25402(class_11909, boolean)`;
   1.21 has no equivalent override hook (widgets only). ConfigBase and
   ScriptCreateScreen override the click handler directly. Plan:
   - introduce `src/version/<mc>/java/com/vex/baritone/config/ui/RemasteredUiBase`
     per version, bridging to a protected `handleMouse(double,double,int)`
   - 1.21.11 impl overrides `method_25402(class_11909, boolean)`
   - 1.21 impl needs a verified click entrypoint (TBD from 1.21 Screen source)
2. **RenderKit texture/matrix APIs** — 1.21.11: `ctx.method_25290/25302(class_10799.field_56883, ...)` and `ctx.method_51448().pushMatrix()/popMatrix()`.
   1.21 DrawContext (`class_332`) exposes `method_51448()` returning `class_4587`
   (MatrixStack) and has no matching `drawTexture`; needs a per-version
   `DrawSupport` bridge.
3. **TextRenderer accessor** — `Screen.method_64506()` used by ScriptCreateScreen
   does not exist on 1.21 Screen; use `field_22787.method_...` equivalent per version.
4. **Button/text field parity** — `class_342` ctor signature matches in 1.21;
   ButtonWidget presence verified. Only consumer updates needed once (1)-(3) land.

## Re-verify after GUI work

```
gradlew build -Pmc_version=1.21 -Pstock_baritone_jar=libs/baritone-unoptimized-fabric-1.21.jar -Pmc_intermediary_jar=ports/cache/1.21/minecraft-merged-intermediary.jar
gradlew verifyAnchors ...
```
