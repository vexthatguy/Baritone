package io.github.itzispyder.clickcrystals.gui.elements.common.interactive;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import com.vex.baritone.config.ui.RenderKit;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (ScrollPanelElement.java), dependency-trimmed.
 */
public class ScrollPanelElement extends GuiElement {
    private final List<GuiElement> children = new ArrayList<>();
    private int scroll;
    private final int maxScroll;

    public ScrollPanelElement(int x, int y, int width, int height, int maxScroll) {
        super(x, y, width, height);
        this.maxScroll = maxScroll;
    }

    @Override
    public void addChild(GuiElement child) {
        this.children.add(child);
    }

    public void setScroll(int amount) {
        this.scroll = Math.max(0, Math.min(this.maxScroll, amount));
    }

    public void scroll(double amount) {
        this.setScroll(this.scroll + (int) amount);
    }

    @Override
    public void render(class_332 ctx, int mouseX, int mouseY) {
        if (!this.canRender()) {
            return;
        }
        ctx.method_44379(this.x, this.y, this.x + this.width, this.y + this.height);
        for (GuiElement child : this.children) {
            int baseY = child.getY() - this.scroll;
            if (baseY + child.getHeight() < this.y || baseY > this.y + this.height) {
                continue;
            }
            child.setY(baseY);
            child.render(ctx, mouseX, mouseY);
        }
        ctx.method_44380();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isHovered((int) mouseX, (int) mouseY)) {
            return;
        }
        for (int i = this.children.size() - 1; i >= 0; i--) {
            this.children.get(i).mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
    }
}
