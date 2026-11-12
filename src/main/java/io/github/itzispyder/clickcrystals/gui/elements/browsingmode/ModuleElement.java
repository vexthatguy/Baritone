package io.github.itzispyder.clickcrystals.gui.elements.browsingmode;

import com.vex.baritone.config.ui.RenderKit;
import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.modules.Module;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (ModuleElement.java) with module-edit/script
 * dependencies removed; click toggles the module.
 */
public class ModuleElement extends GuiElement {
    private final Module module;

    public ModuleElement(Module module) {
        super(0, 0, 280, 30);
        this.module = module;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        boolean hover = this.isHovered(mouseX, mouseY);
        RenderKit.fillRect(ctx, this.x, this.y, this.width, this.height, hover ? 0xB01A1A22 : 0xA0101015);
        if (hover) {
            RenderKit.fillRect(ctx, this.x, this.y, 3, this.height, -16730113);
        }
        RenderKit.drawText(ctx, this.module.getName(), this.x + 12, this.y + 5, false);
        RenderKit.drawText(ctx, "§7- " + this.module.getDescription(), this.x + 12, this.y + 16, 0.7F, false);
        this.renderToggle(ctx, this.x + this.width - 34, this.y + (this.height - 8) / 2, this.module.isEnabled());
    }

    private void renderToggle(class_332 ctx, int tx, int ty, boolean on) {
        RenderKit.fillRoundHoriLine(ctx, tx, ty, 24, 8, on ? -16741056 : -11184811);
        int kx = on ? tx + 17 : tx + 2;
        RenderKit.fillCircle(ctx, kx + 3, ty + 4, 5, on ? -9977345 : -7829368);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHovered((int) mouseX, (int) mouseY)) {
            this.module.toggle();
        }
    }
}
