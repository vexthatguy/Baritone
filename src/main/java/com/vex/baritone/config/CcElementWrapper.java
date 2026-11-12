package com.vex.baritone.config;

import com.vex.baritone.config.ui.GuiElement;
import net.minecraft.class_332;

/**
 * Adapts a ClickCrystals GuiElement into our ConfigBase element type.
 */
public class CcElementWrapper extends GuiElement {
    private final io.github.itzispyder.clickcrystals.gui.GuiElement delegate;

    public CcElementWrapper(io.github.itzispyder.clickcrystals.gui.GuiElement delegate) {
        super(0, 0, delegate.getWidth(), delegate.getHeight());
        this.delegate = delegate;
    }

    public io.github.itzispyder.clickcrystals.gui.GuiElement getDelegate() {
        return this.delegate;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        this.delegate.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        this.delegate.setY(y);
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        this.delegate.render(ctx, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        this.delegate.mouseClicked(mouseX, mouseY, button);
    }
}
