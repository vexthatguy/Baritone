package com.vex.baritone.config.ui;

import net.minecraft.class_11909;
import net.minecraft.class_11909;
import net.minecraft.class_11910;
import net.minecraft.class_2561;
import net.minecraft.class_342;
import net.minecraft.class_437;

/**
 * Screen base for MC 1.21.11: click events arrive as a class_11909 record.
 */
public abstract class RemasteredUiBase extends class_437 {

    protected RemasteredUiBase(class_2561 title) {
        super(title);
    }

    protected abstract boolean handleMouse(double x, double y, int button);

    /** Focuses a text field when it is clicked, per-version click plumbing. */
    protected void focusTextField(class_342 field, int x, int y) {
        field.method_25348(new class_11909(x, y, new class_11910(0, 0)), false);
    }
}
