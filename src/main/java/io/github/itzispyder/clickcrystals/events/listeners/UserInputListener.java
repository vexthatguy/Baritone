package io.github.itzispyder.clickcrystals.events.listeners;

import com.vex.baritone.config.BaritoneConfigScreen;
import net.minecraft.class_310;

/**
 * Stub that routes the ClickCrystals "modules screen" request to our Baritone
 * config screen.
 */
public final class UserInputListener {
    private UserInputListener() {
    }

    public static void openModulesScreen() {
        class_310.method_1551().method_1507(new BaritoneConfigScreen());
    }
}
