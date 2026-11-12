package com.vex.baritone.config.ui;

import net.minecraft.class_332;

/**
 * Left-hand navigation entry: a plain text row with a flat hover wash. The
 * selected fill and accent bar are drawn by {@link ConfigBase} so they can
 * glide between tabs.
 */
public class NavItem extends GuiElement {
    private final String label;
    private final Runnable action;
    private boolean selected;
    private double hover;
    private double selectAmount;
    private long lastFrame = System.nanoTime();

    public NavItem(String label, Runnable action) {
        super(0, 0, 100, 24);
        this.label = label;
        this.action = action;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void activate() {
        this.action.run();
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        renderFaded(ctx, mouseX, mouseY, 1.0);
    }

    public void renderFaded(class_332 ctx, int mouseX, int mouseY, double fade) {
        double dt = Motion.delta(this.lastFrame);
        this.lastFrame = System.nanoTime();

        boolean hovered = this.isHovered(mouseX, mouseY);
        this.hover = Motion.approach(this.hover, hovered ? 1.0 : 0.0, 18.0, dt);
        this.selectAmount = Motion.approach(this.selectAmount, this.selected ? 1.0 : 0.0, 16.0, dt);

        if (this.hover > 0.01 && this.selectAmount < 0.9) {
            RenderKit.fillRect(ctx, this.x + 1, this.y, this.width - 2, this.height,
                RenderKit.alpha(Theme.NAV_HOVER, fade * this.hover * (1.0 - this.selectAmount)));
        }

        int color = RenderKit.lerp(Theme.TEXT_DIM, Theme.TEXT, Math.max(this.selectAmount, this.hover * 0.7));
        RenderKit.drawText(ctx, this.label, this.x + 14, this.y + (this.height - 9) / 2, 1.0F,
            false, RenderKit.alpha(color, fade));
    }
}
