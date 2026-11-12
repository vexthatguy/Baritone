package com.vex.baritone.config;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;

/**
 * Registers Baritone settings as ClickCrystals modules when ClickCrystals is
 * present at runtime. The dependency is resolved reflectively so this mod
 * compiles and runs with or without ClickCrystals installed.
 */
public final class BaritoneModules {

    private static boolean attempted = false;

    private BaritoneModules() {
    }

    public static synchronized void ensureRegistered() {
        if (attempted) {
            return;
        }
        attempted = true;
        Settings s;
        try {
            s = BaritoneAPI.getSettings();
            Class<?> moduleClass = Class.forName("io.github.itzispyder.clickcrystals.modules.Module");
            Class<?> categoryClass = Class.forName("io.github.itzispyder.clickcrystals.modules.Category");
            Class<?> categoriesClass = Class.forName("io.github.itzispyder.clickcrystals.modules.Categories");
            var ctor = moduleClass.getConstructor(String.class, String.class, categoryClass);

            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Sprint", "Sprint when moving forward", s, "allowSprint");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Parkour", "Allow parkour jumps", s, "allowParkour");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Parkour ascend", "Allow parkour upward jumps", s, "allowParkourAscend");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Parkour place", "Place blocks during parkour", s, "allowParkourPlace");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Diagonal ascend", "Allow diagonal upward movement", s, "allowDiagonalAscend");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Diagonal descend", "Allow diagonal downward movement", s, "allowDiagonalDescend");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Downward", "Allow going straight down", s, "allowDownward");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Sprint ascends", "Sprint when ascending", s, "sprintAscends");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Assume step", "Assume one-block steps", s, "assumeStep");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Assume safe walk", "Assume safe walking surfaces", s, "assumeSafeWalk");
            register(ctor, categoriesClass, moduleClass, "MOVEMENT", "Jump at build limit", "Allow jumping at build limit", s, "allowJumpAtBuildLimit");

            register(ctor, categoriesClass, moduleClass, "MINING", "Allow break", "Allow breaking blocks", s, "allowBreak");
            register(ctor, categoriesClass, moduleClass, "MINING", "Allow place", "Allow placing blocks", s, "allowPlace");
            register(ctor, categoriesClass, moduleClass, "MINING", "Auto tool", "Automatically select tools", s, "autoTool");
            register(ctor, categoriesClass, moduleClass, "MINING", "Assume external auto-tool", "Assume external tool switching", s, "assumeExternalAutoTool");
            register(ctor, categoriesClass, moduleClass, "MINING", "Pause mining for falling blocks", "Stop mining when blocks fall", s, "pauseMiningForFallingBlocks");
            register(ctor, categoriesClass, moduleClass, "MINING", "Avoid falling-block updates", "Avoid updating falling blocks", s, "avoidUpdatingFallingBlocks");
            register(ctor, categoriesClass, moduleClass, "MINING", "Break vines", "Allow breaking vines", s, "allowVines");
            register(ctor, categoriesClass, moduleClass, "MINING", "Inventory", "Allow inventory manipulation", s, "allowInventory");

            register(ctor, categoriesClass, moduleClass, "SAFETY", "Avoidance", "Avoid mobs and danger", s, "avoidance");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Strict liquid check", "Treat liquids strictly", s, "strictLiquidCheck");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Water bucket fall", "Use water buckets to survive falls", s, "allowWaterBucketFall");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Walk on bottom slab", "Walk on bottom slabs", s, "allowWalkOnBottomSlab");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Consider potion effects", "Factor in potion effects", s, "considerPotionEffects");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Build ignore existing", "Ignore existing blocks when building", s, "buildIgnoreExisting");
            register(ctor, categoriesClass, moduleClass, "SAFETY", "Build ignore direction", "Ignore build direction", s, "buildIgnoreDirection");

            register(ctor, categoriesClass, moduleClass, "ADVANCED", "Overshoot traverse", "Overshoot when traversing", s, "overshootTraverse");
            register(ctor, categoriesClass, moduleClass, "ADVANCED", "Avoid place in fluid source", "Don't place in fluid source blocks", s, "allowPlaceInFluidsSource");
            register(ctor, categoriesClass, moduleClass, "ADVANCED", "Avoid place in flowing fluid", "Don't place in flowing fluids", s, "allowPlaceInFluidsFlow");
        } catch (Throwable t) {
            // ClickCrystals not present or incompatible: silently skip
        }
    }

    private static void register(java.lang.reflect.Constructor<?> ctor,
                                 Class<?> categoriesClass,
                                 Class<?> moduleClass,
                                 String categoryField,
                                 String name,
                                 String description,
                                 Settings settings,
                                 String settingName) throws Exception {
        Settings.Setting<Boolean> setting = SettingRefs.boolSetting(settings, settingName);
        if (setting == null) {
            return;
        }
        Object category = categoriesClass.getField(categoryField).get(null);
        Object module = ctor.newInstance(name, description, category);
        moduleClass.getMethod("setEnabled", boolean.class).invoke(module, setting.value);
    }
}
