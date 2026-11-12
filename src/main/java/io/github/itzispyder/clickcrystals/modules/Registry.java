package io.github.itzispyder.clickcrystals.modules;

import java.util.ArrayList;
import java.util.List;

/**
 * Static module registry (port of ClickCrystals' Registry).
 */
public final class Registry {
    private static final List<Module> MODULES = new ArrayList<>();

    private Registry() {
    }

    public static void register(Module module) {
        if (!MODULES.contains(module)) {
            MODULES.add(module);
        }
    }

    public static List<Module> getModules() {
        return MODULES;
    }
}
