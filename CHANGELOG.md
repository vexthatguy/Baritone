# Changelog

All notable changes are tracked here. Format based on
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [1.21.11-SNAPSHOT.111] — 2026-11-13

### Added
- Master kill-switch ("Baritone: ON/OFF") in Credits & Attribution; OFF
  disables all pathing, input, processes, goals, selections, the `#` command
  prefix, and removes the mod from the ModMenu list.
- Flat config GUI (Xaero-style): navigation tabs, compact toggles, animations.
- Named homes: `#sethome <name>`, `#home <name>`, `#homes`, `#delhome`.
- Death waypoints with death coordinates in chat.
- Per-world / per-server waypoint storage.
- `#biome` command (current biome, nearest match, `#biome list`).
- Farm fixes: hard 12-chunk scan cap, `#farm` range clamp, nether-wart
  replanting enabled by default.
- Script system: `#script create/edit/list/run/delete/stop` with editor GUI
  and tick-based execution.
- `#version` reads the real mod version; `#help` is alphabetical + paginated.

### Fixed
- `#home <name>` previously failed with a bogus "Expected Integer" error.
- Command prefix/tab-complete still active when the mod was toggled off.
- Version command reported "Null version" on Fabric.
- Nether-wart farming (harvest/replant) misbehaved.

### Infrastructure
- Parameterized multi-version build (`-Pmc_version=...` / env vars).
- Anchor verifier (`verifyAnchors`) and static linker (`LinkCheck`).
- CI builds the full 1.21.x Fabric matrix plus Forge/NeoForge assembly.

## [Unreleased] — 26.3 (experimental)
- Experimental builds for Fabric/Forge/NeoForge on 26.3.
- Named-source port in progress (`src/named-port/26.3/`); GUI layer being
  ported to the new 26.3 rendering API.
