package com.vex.baritone.config.ui;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

/**
 * Screen base for MC 26.3: input arrives via GuiEventListener events
 * (MouseButtonEvent), rendering via render-state extraction.
 */
public abstract class RemasteredUiBase extends Screen {

    protected RemasteredUiBase(Component title) {
        super(title);
    }

    protected abstract boolean handleMouse(double x, double y, int button);

    /** Focuses a text field when it is clicked (26.3 API). */
    protected void focusTextField(EditBox field, int x, int y) {
        field.setFocused(true);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubled) {
        return handleMouse(event.x(), event.y(), event.button());
    }
}
