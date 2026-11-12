package com.vex.baritone.config.ui;

import net.minecraft.class_2960;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (CategoryElement.java): a nav-column item with
 * hover highlight, used for the left categories.
 */
public class CategoryElement extends GuiElement {
    private final String title;
    private final class_2960 icon;
    private final Runnable onClick;
    private boolean selected;

    public CategoryElement(String title, class_2960 icon, Runnable onClick) {
        super(0, 0, 90, 14);
        this.title = title;
        this.icon = icon;
        this.onClick = onClick;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        boolean hover = this.isHovered(mouseX, mouseY);
        if (hover || this.selected) {
            RenderKit.fillRoundHoriLine(ctx, this.x + 2, this.y, this.width - 4, this.height, this.selected ? -16741056 : -7829368);
        }
        if (this.icon != null) {
            RenderKit.drawTexture(ctx, this.icon, this.x + 4, this.y + 2, 10, 10);
            RenderKit.drawText(ctx, this.title, this.x + 18, this.y + 3, 0.7F, false);
        } else {
            RenderKit.drawText(ctx, this.title, this.x + 8, this.y + 3, 0.7F, false);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHovered((int) mouseX, (int) mouseY)) {
            this.onClick.run();
        }
    }
}
