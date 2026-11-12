package io.github.itzispyder.clickcrystals.gui.elements.browsingmode;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.modules.Category;
import com.vex.baritone.config.ui.RenderKit;
import java.util.function.Consumer;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (CategoryElement.java) with screen-opening moved
 * to a callback.
 */
public class CategoryElement extends GuiElement {
    private final Category category;
    private final Consumer<Category> onClick;
    private boolean selected;

    public CategoryElement(Category category, Consumer<Category> onClick) {
        super(0, 0, 90, 14);
        this.category = category;
        this.onClick = onClick;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        boolean hover = this.isHovered(mouseX, mouseY);
        if (hover || this.selected) {
            RenderKit.fillRoundHoriLine(ctx, this.x + 2, this.y, this.width - 4, this.height, this.selected ? -16741056 : -7829368);
        }
        RenderKit.drawText(ctx, this.category.getName(), this.x + 8, this.y + 3, 0.7F, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHovered((int) mouseX, (int) mouseY)) {
            this.onClick.accept(this.category);
        }
    }
}
