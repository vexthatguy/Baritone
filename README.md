# Baritone Remastered

A polished continuation of Baritone — the classic Minecraft pathfinding mod —
with a modern config GUI, a hard kill-switch, and fixes for the rough edges
that annoyed everyone. Built for Minecraft 1.21.x (Fabric, Forge, NeoForge)
with experimental 26.3 builds.

![icon](assets/baritone/icon.png)

## Highlights

- **Master toggle** — "Baritone: ON/OFF" lives in Credits & Attribution.
  Switching it off kills every bot feature *and* the `#` command prefix, and
  even hides the mod from ModMenu. One click, fully gone.
- **Config GUI** — flat, dark, Xaero-inspired. Navigation tabs, compact
  toggles, smooth animations, crisp text. Everything you'd tweak is a click
  away.
- **Named homes** — `#sethome base`, `#home base`. No more guessing.
- **Death waypoints** — auto-saved, with your death coordinates posted to
  chat.
- **Per-world/per-server waypoints** — homes never leak between worlds.
- **`#biome`** — report your biome or path to the nearest matching one.
- **Farming that actually works** — capped at a sane 12-chunk radius,
  `#farm` range clamped, nether wart replanting on by default.
- **Scripts** — `#script create <name>` opens an editor, `#script run <name>`
  executes your command list one per tick.
- **`#version`** prints the real build, and `#help` is sorted and paginated.

## Commands

```
#goto <x> <y> <z>      path to coordinates
#follow player [name]   follow a player
#home [name]            path to a named home
#sethome [name]         set a home at your feet
#homes [filter]         list homes
#delhome [name]         delete home(s)
#biome [name]           report / path to biome
#biome list [filter]    list biome ids
#farm [range]           farm crops (≤192 blocks)
#script create <name>    open script editor
#script run <name>       run a script
#version                print version
#help [command]         help, paginated
```

## Install

Drop the matching jar into your instance's `mods/` folder. Make sure the jar
matches your Minecraft version and loader (Fabric / Forge / NeoForge), and
remove any older Baritone jars first.

## Building

Requires JDK 25.

```
gradlew build -x test
```

The build is parameterized per MC version — see `ports/README.md` for the
full matrix and per-version commands. `verifyAnchors` runs automatically as
part of the build chain and fails with a report if any anchor drifts.

## Versions

| Minecraft | Fabric | Forge | NeoForge |
|---|---|---|---|
| 1.21 → 1.21.10 | ✅ | ✅ | ✅ |
| 1.21.11 | ✅ | — | — |
| 26.3 | ⚠️ experimental | ⚠️ experimental | ⚠️ experimental |

All 1.21.x builds pass the full anchor suite. The 26.3 line is early-access:
the build is produced but the GUI layer is still being ported to the new
26.3 rendering API and hasn't been play-tested yet.

## Acknowledgements

Built on the shoulders of the Baritone project (LGPLv3) and its community.
This fork aims to keep that legacy alive on modern Minecraft.
