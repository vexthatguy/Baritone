package com.vex.baritone.config;

import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.util.mod.Mod;

/**
 * Makes this mod genuinely absent from ModMenu while the master switch is off.
 *
 * <p>ModMenu draws its list, its search results and its mod count from the
 * static {@code ModMenu.MODS} / {@code ModMenu.ROOT_MODS} maps. Simply deleting
 * rows from the widget is fragile: the list is rebuilt whenever the screen is
 * resized or the search box changes. So instead the entry is taken out of those
 * maps before the list is built, and put back (same {@link Mod} instance) when
 * the switch is turned on again. No ModMenu fork, no custom jar.</p>
 */
public final class ModMenuVisibility {
    private static final String MOD_ID = "baritone-meteor";

    /** The real ModMenu entry, kept so re-enabling is instant and lossless. */
    private static Mod parked;

    private ModMenuVisibility() {
    }

    /** Aligns the ModMenu registry with the current master switch state. */
    public static void sync() {
        if (VisibilityConfig.isModMenuHidden()) {
            hide();
        } else {
            restore();
        }
    }

    private static void hide() {
        try {
            boolean changed = false;
            Mod mod = ModMenu.MODS.remove(MOD_ID);
            if (mod == null) {
                mod = ModMenu.ROOT_MODS.remove(MOD_ID);
            } else {
                ModMenu.ROOT_MODS.remove(MOD_ID);
                changed = true;
            }
            if (mod != null) {
                parked = mod;
                changed = true;
            }
            if (changed) {
                ModMenu.clearModCountCache();
            }
        } catch (Throwable ignored) {
        }
    }

    private static void restore() {
        Mod mod = parked;
        if (mod == null) {
            return;
        }
        try {
            boolean changed = ModMenu.MODS.putIfAbsent(MOD_ID, mod) == null;
            changed |= ModMenu.ROOT_MODS.putIfAbsent(MOD_ID, mod) == null;
            if (changed) {
                ModMenu.clearModCountCache();
            }
            parked = null;
        } catch (Throwable ignored) {
        }
    }
}
