package com.vex.baritone.config.ui;

import net.minecraft.class_2561;
import net.minecraft.class_342;
import net.minecraft.class_437;

/**
 * Screen base for MC 1.21: click events arrive as the classic
 * mouseClicked(double, double, int) override.
 */
public abstract class RemasteredUiBase extends class_437 {

    protected RemasteredUiBase(class_2561 title) {
        super(title);
    }

    protected abstract boolean handleMouse(double x, double y, int button);

    /** Focuses a text field when it is clicked, per-version click plumbing. */
    protected void focusTextField(class_342 field, int x, int y) {
        field.method_1858(true);
    }

    @Override
    public boolean method_25402(double x, double y, int button) {
        return handleMouse(x, y, button);
    }
}
