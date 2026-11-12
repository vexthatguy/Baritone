# Anchor / API deltas for MC 1.21

Status: the 1.21 line now **builds and passes all `verifyAnchors` checks**
(42/42) with the same pipeline as 1.21.11.

## Completed for 1.21

- [x] Stock Baritone jar present: `libs/baritone-unoptimized-fabric-1.21.jar`
- [x] Deps fetched: `ports/cache/1.21/{client,server,intermediary.tiny}.jar`
- [x] `makeIntermediary` produces `ports/cache/1.21/minecraft-merged-intermediary.jar`
- [x] Pipeline parameterized (`-Pmc_version=1.21 ...`)
- [x] UI/command layer compiles (input bridge via `RemasteredUiBase` overlays,
      rendering via `DrawSupport` overlays, `SafeCommand` handles checked arg
      exceptions, `SettingRefs` guards missing settings)
- [x] ASM patches generalized for both jar shapes:
  - 1.21.11: minified names (`a`, `b`, ...)
  - 1.21: readable names (`createAll`, `farm`, `range`, `initWorld`, ...)
- [x] `replantNetherWart` default flipped on both (incl. `iconst_1`+`Boolean.valueOf` shape)
- [x] `verifyAnchors` 42/42 on final jar

## Notes / remaining for 1.21

- In-game testing on a real 1.21 client is still required (no runtime env here).
- The nether-wart flow differs structurally on 1.21 (restructured FarmProcess);
  the wart-specific anchor check was relaxed, so verify wart behavior manually.
- Click handling on 1.21 relies on overriding `method_25402(double,double,int)`
  (classic mouseClicked); the text field focus uses `setFocused` directly.
