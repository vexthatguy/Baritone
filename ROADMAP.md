# Roadmap

## Branches / tags

- `main` / `1.21` — current line: Minecraft 1.21.11, Fabric.
- Releases are tagged `v<mod-version>` (e.g. `v1.21.11-SNAPSHOT.84`).

## Status

| Target | Status |
| --- | --- |
| MC 1.21.11 (Fabric) | ✅ builds, 42/42 anchors verified |
| MC 1.21 (Fabric) | ✅ builds, 42/42 anchors verified (in-game test pending) |
| MC 26.3 / 26.4 snapshots (Fabric) | 🚧 blocked on a stock Baritone jar for 26.x |
| Forge / NeoForge | 🚧 planned |

## Why 26.x is blocked (for now)

The current Minecraft versioning (2026) is `26.3` (release) and
`26.4-snapshot-N`. The port pipeline can fetch all Mojang/Fabric assets for
those versions already (`fetch-deps.ps1 -Version 26.3`), but the pipeline
needs a **stock Baritone jar built for that version** to rename, patch and
extend. There is no public stock Baritone for 26.x yet; the moment one
exists (or is built from source by the Baritone community), point
`MC_STOCK_BARITONE_JAR` at it and run the same commands.

## How the port will be done

The codebase is deliberately split so most logic is loader-agnostic:

- `src/main/java/com/vex/baritone/config/**` — commands, GUI, feature config.
  Pure Java + intermediary-mapped Minecraft APIs; recompiles per version.
- `src/tools/java/**` — ASM patchers (`RenameJar`, `PatchBaritone`,
  `StampVersion`) that transform the stock Baritone jar. These are per-version
  because they target obfuscated field/method names.
- `libs/original-baritone-meteor-1.21.11.jar` — the stock jar for 1.21.11;
  replace with the matching stock jar for other versions.

### Steps for a new Minecraft version

1. Obtain the matching stock Baritone-meteor jar for the new version and put
   it in `libs/`.
2. Update `gradle.properties` (`minecraft_version`) and the intermediary jar
   path in `build.gradle`.
3. Re-verify every ASM anchor in `PatchBaritone.java` (field/method names
   change between versions) — see the `javap` verification notes in the
   source comments.
4. Adjust any changed Minecraft APIs used by the config/command classes.
5. Build, install, and test the command suite:
   `#home`, `#biome`, `#farm`, `#script`, `#version`, `#help`, toggle OFF.

### Forge / NeoForge

Fabric-specific parts are limited to:

- `fabric.mod.json` entrypoints + ModMenu integration (`ModMenuIntegration`,
  `ModMenuVisibility`).
- Mixin config `mixins.baritone-visibility.json` and the mixins in
  `src/main/java/com/vex/baritone/config/mixin/`.

For Forge these become:

- `META-INF/mods.toml` + coremod/ASM patches (or Mixin via the Forge
  mixin support).
- A `@Mod` entry class replacing the Fabric entrypoint, reusing the same
  command/GUI classes unchanged.
