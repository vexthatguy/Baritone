package com.vex.baritone.config.ui;

/**
 * Ported from ClickCrystals (PressAction.java).
 */
@FunctionalInterface
public interface PressAction<T extends GuiElement> {
    void onPress(T element);
}
