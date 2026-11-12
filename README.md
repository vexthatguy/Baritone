# Baritone Remastered

A maintained fork of Baritone for Minecraft **1.21.11** (Fabric), with a flat
Xaero-style config GUI, a master kill-switch, and a set of quality-of-life
fixes and additions.

![icon](assets/baritone/icon.png)

## Features

- **Master toggle** — "Baritone: ON/OFF" in Credits & Attribution. OFF
  disables all pathing, input, processes, goals, selections, the `#` command
  prefix, and removes the mod from the ModMenu list (restored on enable).
- **Config GUI** — flat dark panel, 1px borders, blue section headers,
  compact rows, small toggles, left tabs, logo in the header, animations.
- **Named homes** — `#sethome <name>`, `#home <name>`, `#homes`,
  `#delhome [name]`.
- **Death waypoints** — auto-saved on death (stock behavior) with death
  coordinates printed to chat.
- **Per-world/per-server waypoints** — waypoint folders are keyed by world
  or server address.
- **`#biome`** — report current biome, path to the nearest matching biome,
  `#biome list [filter]`.
- **Farm fixes** — scan radius hard-capped at 12 chunks, `#farm <range>`
  clamped to 192 blocks, nether-wart replanting enabled by default.
- **`#version`** — prints the real mod version (read from `fabric.mod.json`).
- **Scripts** — `#script create <name>` opens an editor GUI; run with
  `#script run <name>`; also `#script list|delete|stop`.
- **`#help`** — alphabetical listing, paginated.
- ModMenu entry, metadata (author `vexthatguy`, Modrinth homepage/sources).

## Commands

| Command | What it does |
| --- | --- |
| `#goto <x y z>` | path to coordinates |
| `#follow player [name]` | follow a player |
| `#home [name]` | path to home |
| `#sethome [name]` | set home at feet |
| `#homes [filter]` | list homes |
| `#delhome [name]` | delete home(s) |
| `#biome [name]` | report/path to biome |
| `#biome list [filter]` | list biomes |
| `#farm [range]` | farm crops (≤192 blocks) |
| `#script create <name>` | open script editor |
| `#script run <name>` | run a script |
| `#version` | print version |
| `#help [command]` | help / pages |

## Building

Requires a JDK (built with JDK 25) and network access to the Gradle plugin
portal.

```
gradlew.bat build -x test
```

Output: `build/libs/baritone-1.21.11-SNAPSHOT.<n>.jar`

The build chain: `renameJar` (package rename `baritone` → `com.vex.baritone`)
→ `compileUi` (config GUI/commands) → `patchBaritone` (ASM patches) →
`stampJar` (version + manifest + injected classes + modmenu entrypoint).

## Install

Drop the jar into your instance's `mods/` folder (Fabric, MC 1.21.11).
Remove other Baritone jars first.

## Version support / roadmap

See [ROADMAP.md](ROADMAP.md).
