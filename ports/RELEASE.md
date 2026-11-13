# Release guide

Everything needed to publish a new build.

## Build

```
# default 1.21.11
gradlew build -x test

# any other version
gradlew build -x test -Pmc_version=1.21.4 \
  -Pstock_baritone_jar=libs/stock-baritone-fabric-1.21.4.jar \
  -Pmc_intermediary_jar=ports/cache/1.21.4/minecraft-merged-intermediary.jar
```

Build output: `build/libs/baritone-<mc>-SNAPSHOT.<n>.jar`.

## Verify

- `verifyAnchors` runs as part of `build`; it must exit 0.
- `ports/inventory.ps1` prints every artifact with its status.
- For loader assemblies: `ports/assemble-forge.ps1 -Version X -Loader forge|neoforge`.

## Publish

1. Run the GitHub Actions workflow (pushes to `main` build the full matrix
   and upload artifacts).
2. Create a GitHub release from the desired tag with the matching jars
   attached (Fabric / Forge / NeoForge per MC version).
3. On Modrinth: create versions per MC version/loader using the same jars;
   use the icon in `assets/baritone/icon.png` and this repo as source link.

## Versioning

- Tags follow `<mc>-SNAPSHOT.<build>` (e.g. `v1.21.4-SNAPSHOT.112`).
- The build number is auto-incremented in `build/version.properties`.
- `#version` reads `Implementation-Version` from the jar manifest.
