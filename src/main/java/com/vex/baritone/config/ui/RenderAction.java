package com.vex.baritone.config.ui;

import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (RenderAction.java).
 */
@FunctionalInterface
public interface RenderAction<T extends GuiElement> {
    void onRender(class_332 context, int mouseX, int mouseY, T element);
}
