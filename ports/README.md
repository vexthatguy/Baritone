# Port tooling

This folder is the home of the multi-version port.

## Current status

| Target | Status |
| --- | --- |
| 1.21.11 (Fabric) | ✅ builds, 42/42 anchors verified |
| 1.21.10/1.21.9/1.21.8/1.21.7 (Fabric) | ✅ builds, 42/42 anchors verified |
| 1.21.6/1.21.5/1.21.4/1.21.3/1.21.2/1.21.1/1.21 (Fabric) | ✅ builds, 42/42 anchors verified |
| 1.21.x Forge + NeoForge (1.21 → 1.21.10) | ✅ assembled (renamed stock + official-named remapped UI) |
| 26.1/26.2/26.3 (all loaders) | 🚧 blocked: Fabric has not published intermediary mappings for 26.x |
| Forge / NeoForge (1.21.11) | 🚧 no stock 1.21.11 Forge/NeoForge jar upstream |

## How a port works

The pipeline is version-parameterized:

```
gradlew build \
  -Pmc_version=1.21 \
  -Pstock_baritone_jar=libs/baritone-unoptimized-fabric-1.21.jar \
  -Pmc_intermediary_jar=<merged-intermediary jar for 1.21> \
  -Pmodmenu_jar=libs/modmenu-17.0.1.jar
```

1. **Get the stock Baritone jar for the target version** (`libs/`).
2. **Get a merged intermediary Minecraft jar for the target version**:
   - If you already have a Fabric-Loom cache for it, point
     `mc_intermediary_jar` at the `minecraft-merged-intermediary` jar.
   - Otherwise use `fetch-deps.ps1` + `makeIntermediary` (see below).
3. **Run `verifyAnchors`** — it lists every anchor that needs updating in
   `PatchBaritone.java` / UI sources for the new version.

## Fetching Minecraft + mappings for a new version

```
.\ports\fetch-deps.ps1 -Version 1.21 -OutDir ports\cache\1.21
```

Downloads (requires internet):
- Mojang client+server jars for `-Version`
- yarn mappings (`yarn-<version>-v2.jar`, `mappings.tiny` inside)
- intermediary v2 mappings (`intermediary-<version>-v2.jar`)

Then produce the merged intermediary jar:

```
gradlew makeIntermediary \
  -Pport_version=1.21 \
  -Pport_client=ports/cache/1.21/client.jar \
  -Pport_server=ports/cache/1.21/server.jar \
  -Pport_mappings=ports/cache/1.21/mappings.tiny \
  -Pport_out=ports/cache/1.21/minecraft-merged-intermediary.jar
```

The tool merges client+server (client wins on duplicates), remaps the
combined jar from yarn names to intermediary names using tiny-remapper, and
writes a jar shaped like Loom's `minecraft-merged-intermediary`.

## Anchor inventory

- `anchors-1.21.11.md` — every runtime anchor the pipeline depends on for
  the current line, with what each one is for.
- `verifyAnchors` (Gradle task, runs against the final stamped jar) checks
  them all and fails the build listing anything missing. On a new version,
  run it immediately and fix failures one by one.

## Forge / NeoForge notes

- Fabric-specific files: `fabric.mod.json`, `ModMenuIntegration`,
  `ModMenuVisibility`, `mixins.baritone-visibility.json`, and the mixins in
  `src/main/java/com/vex/baritone/config/mixin/`.
- Forge equivalents: `META-INF/mods.toml`, a `@Mod` entry class, and a
  Forge mixin config (or coremod/ASM). The commands, GUI, FeatureConfig and
  ScriptCreateScreen are loader-agnostic and should carry over unchanged.
