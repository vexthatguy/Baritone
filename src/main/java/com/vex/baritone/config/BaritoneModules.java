package com.vex.baritone.config;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Registry;

/**
 * Builds the ClickCrystals module tree from Baritone's settings.
 */
public final class BaritoneModules {
    private static boolean registered = false;

    private BaritoneModules() {
    }

    public static synchronized void ensureRegistered() {
        if (registered) {
            return;
        }
        registered = true;
        Settings s = BaritoneAPI.getSettings();
        new BaritoneModule(Categories.MOVEMENT, "Sprint", "Sprint when moving forward", s.allowSprint);
        new BaritoneModule(Categories.MOVEMENT, "Parkour", "Allow parkour jumps", s.allowParkour);
        new BaritoneModule(Categories.MOVEMENT, "Parkour ascend", "Allow parkour upward jumps", s.allowParkourAscend);
        new BaritoneModule(Categories.MOVEMENT, "Parkour place", "Place blocks during parkour", s.allowParkourPlace);
        new BaritoneModule(Categories.MOVEMENT, "Diagonal ascend", "Allow diagonal upward movement", s.allowDiagonalAscend);
        new BaritoneModule(Categories.MOVEMENT, "Diagonal descend", "Allow diagonal downward movement", s.allowDiagonalDescend);
        new BaritoneModule(Categories.MOVEMENT, "Downward", "Allow going straight down", s.allowDownward);
        new BaritoneModule(Categories.MOVEMENT, "Sprint ascends", "Sprint when ascending", s.sprintAscends);
        new BaritoneModule(Categories.MOVEMENT, "Assume step", "Assume one-block steps", s.assumeStep);
        new BaritoneModule(Categories.MOVEMENT, "Assume safe walk", "Assume safe walking surfaces", s.assumeSafeWalk);
        new BaritoneModule(Categories.MOVEMENT, "Jump at build limit", "Allow jumping at build limit", s.allowJumpAtBuildLimit);

        new BaritoneModule(Categories.MINING, "Allow break", "Allow breaking blocks", s.allowBreak);
        new BaritoneModule(Categories.MINING, "Allow place", "Allow placing blocks", s.allowPlace);
        new BaritoneModule(Categories.MINING, "Auto tool", "Automatically select tools", s.autoTool);
        new BaritoneModule(Categories.MINING, "Assume external auto-tool", "Assume external tool switching", s.assumeExternalAutoTool);
        new BaritoneModule(Categories.MINING, "Pause mining for falling blocks", "Stop mining when blocks fall", s.pauseMiningForFallingBlocks);
        new BaritoneModule(Categories.MINING, "Avoid falling-block updates", "Avoid updating falling blocks", s.avoidUpdatingFallingBlocks);
        new BaritoneModule(Categories.MINING, "Break vines", "Allow breaking vines", s.allowVines);
        new BaritoneModule(Categories.MINING, "Inventory", "Allow inventory manipulation", s.allowInventory);

        new BaritoneModule(Categories.SAFETY, "Avoidance", "Avoid mobs and danger", s.avoidance);
        new BaritoneModule(Categories.SAFETY, "Strict liquid check", "Treat liquids strictly", s.strictLiquidCheck);
        new BaritoneModule(Categories.SAFETY, "Water bucket fall", "Use water buckets to survive falls", s.allowWaterBucketFall);
        new BaritoneModule(Categories.SAFETY, "Walk on bottom slab", "Walk on bottom slabs", s.allowWalkOnBottomSlab);
        new BaritoneModule(Categories.SAFETY, "Consider potion effects", "Factor in potion effects", s.considerPotionEffects);
        new BaritoneModule(Categories.SAFETY, "Build ignore existing", "Ignore existing blocks when building", s.buildIgnoreExisting);
        new BaritoneModule(Categories.SAFETY, "Build ignore direction", "Ignore build direction", s.buildIgnoreDirection);

        new BaritoneModule(Categories.ADVANCED, "Overshoot traverse", "Overshoot when traversing", s.overshootTraverse);
        new BaritoneModule(Categories.ADVANCED, "Avoid place in fluid source", "Don't place in fluid source blocks", s.allowPlaceInFluidsSource);
        new BaritoneModule(Categories.ADVANCED, "Avoid place in flowing fluid", "Don't place in flowing fluids", s.allowPlaceInFluidsFlow);
    }
}
