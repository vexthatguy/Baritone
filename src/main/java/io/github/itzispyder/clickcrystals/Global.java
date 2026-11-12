package io.github.itzispyder.clickcrystals;

import io.github.itzispyder.clickcrystals.client.system.ClickCrystalsSystem;
import net.minecraft.class_310;

/**
 * Shim for ClickCrystals' Global interface, adapted for Baritone.
 */
public interface Global {
    class_310 mc = class_310.method_1551();
    ClickCrystalsSystem system = ClickCrystalsSystem.getInstance();
}
