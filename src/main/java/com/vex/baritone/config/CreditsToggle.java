package com.vex.baritone.config;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.class_2561;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

/**
 * The single "Baritone: ON / OFF" button shown in the top-left of the vanilla
 * Credits &amp; Attribution screen.
 *
 * <p>It is added through the vanilla screen API (reflectively, to avoid
 * mapping-sensitive shadow lookups) and re-added if the screen rebuilds its
 * widget list. This button is deliberately the only thing that keeps working
 * while the mod is disabled.</p>
 */
public final class CreditsToggle {
    private static final int X = 10;
    private static final int Y = 8;
    private static final int W = 112;
    private static final int H = 20;

    private static final Map<Object, class_4185> BUTTONS = new WeakHashMap<>();
    private static Method addMethod;

    private CreditsToggle() {
    }

    public static void ensure(Object screen) {
        if (!(screen instanceof class_437 parent)) {
            return;
        }
        VisibilityConfig.load();

        class_4185 existing = BUTTONS.get(screen);
        if (existing != null && parent.method_25396().contains(existing)) {
            return;
        }

        class_4185 button = class_4185.method_46430(
                label(),
                btn -> {
                    try {
                        VisibilityConfig.setEnabled(!VisibilityConfig.isEnabled());
                        ModMenuVisibility.sync();
                    } catch (Throwable ignored) {
                        // A toggle must never take the game down with it.
                    }
                    btn.method_25355(label());
                })
            .method_46434(X, Y, W, H)
            .method_46431();

        try {
            add(screen, button);
            BUTTONS.put(screen, button);
        } catch (Throwable ignored) {
            // If the API ever changes shape the rest of the mod still works.
        }
    }

    private static class_2561 label() {
        return class_2561.method_43470(
            "Baritone: " + (VisibilityConfig.isEnabled() ? "ON" : "OFF"));
    }

    private static synchronized void add(Object screen, class_364 widget) throws Exception {
        if (addMethod == null) {
            Method method = class_437.class.getDeclaredMethod("method_37063", class_364.class);
            method.setAccessible(true);
            addMethod = method;
        }
        addMethod.invoke(screen, widget);
    }
}
