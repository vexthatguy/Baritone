package com.vex.baritone.config.ui;

import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (ButtonElement.java) with animation and interaction
 * utils stripped; hover is instant.
 */
public class ButtonElement extends GuiElement {
    private PressAction<ButtonElement> clickCallback;
    private String text;
    private int borderRadius;
    private int fillColor;
    private int hoverColor;

    public ButtonElement(String text, int x, int y, int w, int h, int r, int fillColor, int hoverColor,
                         PressAction<ButtonElement> onClick) {
        super(x, y, w, h);
        this.text = text;
        this.borderRadius = r;
        this.clickCallback = onClick;
        this.fillColor = fillColor;
        this.hoverColor = hoverColor;
    }

    public ButtonElement(String text, int x, int y, int w, int h, int r, PressAction<ButtonElement> onClick) {
        this(text, x, y, w, h, r, -11184811, -9186049, onClick);
    }

    public ButtonElement(String text, int x, int y, int w, int h, PressAction<ButtonElement> onClick) {
        this(text, x, y, w, h, h / 2, onClick);
    }

    @Override
    public void onRender(class_332 context, int mouseX, int mouseY) {
        int color = this.isHovered(mouseX, mouseY) ? this.hoverColor : this.fillColor;
        RenderKit.fillRoundRect(context, this.x, this.y, this.width, this.height, this.borderRadius, color);
        RenderKit.drawCenteredText(context, this.text, this.x + this.width / 2, this.y + (this.height - 9) / 2, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered((int) mouseX, (int) mouseY)) {
            this.clickCallback.onPress(this);
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getHoverColor() {
        return this.hoverColor;
    }

    public void setHoverColor(int hoverColor) {
        this.hoverColor = hoverColor;
    }
}
