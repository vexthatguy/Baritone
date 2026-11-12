package io.github.itzispyder.clickcrystals.gui.elements.common;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import net.minecraft.class_332;

@FunctionalInterface
public interface RenderAction<T extends GuiElement> {
   void onRender(class_332 var1, int var2, int var3, T var4);
}
