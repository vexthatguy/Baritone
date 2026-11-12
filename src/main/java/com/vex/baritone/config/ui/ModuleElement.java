package com.vex.baritone.config.ui;

import net.minecraft.class_2960;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (ModuleElement.java): a content row with icon,
 * title, description and an optional trailing toggle.
 */
public class ModuleElement extends GuiElement {
    private final String title;
    private final String desc;
    private final class_2960 icon;
    private final PressAction<ModuleElement> onClick;
    private boolean hovered;

    public ModuleElement(String title, String desc, class_2960 icon, PressAction<ModuleElement> onClick) {
        super(0, 0, 280, 30);
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.onClick = onClick;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        this.hovered = this.isHovered(mouseX, mouseY);
        RenderKit.fillRect(ctx, this.x, this.y, this.width, this.height, this.hovered ? 0xB01A1A22 : 0xA0101015);
        if (this.hovered) {
            RenderKit.fillRect(ctx, this.x, this.y, 3, this.height, -16730113);
        }
        if (this.icon != null) {
            RenderKit.drawTexture(ctx, this.icon, this.x + 8, this.y + 9, 12, 12);
            RenderKit.drawText(ctx, this.title, this.x + 26, this.y + 4, false);
            RenderKit.drawText(ctx, "§7- " + this.desc, this.x + 26, this.y + 15, 0.7F, false);
        } else {
            RenderKit.drawText(ctx, this.title, this.x + 12, this.y + 5, false);
            RenderKit.drawText(ctx, "§7- " + this.desc, this.x + 12, this.y + 16, 0.7F, false);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHovered((int) mouseX, (int) mouseY)) {
            this.onClick.onPress(this);
        }
    }
}
