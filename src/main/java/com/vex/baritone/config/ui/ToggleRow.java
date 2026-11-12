package com.vex.baritone.config.ui;

import com.vex.baritone.api.Settings;
import java.util.function.Consumer;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (BooleanSettingElement.java), rebound to Baritone's
 * Settings.Setting<Boolean>. Pill-style toggle with hover highlight.
 */
public class ToggleRow extends GuiElement {
    private final Settings.Setting<Boolean> setting;
    private final String label;
    private final String desc;
    private final Consumer<Boolean> onToggle;

    public ToggleRow(String label, String desc, Settings.Setting<Boolean> setting, Consumer<Boolean> onToggle) {
        super(0, 0, 300, 30);
        this.label = label;
        this.desc = desc;
        this.setting = setting;
        this.onToggle = onToggle;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        boolean hover = this.isHovered(mouseX, mouseY);
        RenderKit.fillRect(ctx, this.x, this.y, this.width, this.height, hover ? 0xB01A1A22 : 0xA0101015);
        if (hover) {
            RenderKit.fillRect(ctx, this.x, this.y, 3, this.height, -16730113);
        }
        RenderKit.drawText(ctx, this.label, this.x + 12, this.y + 5, false);
        RenderKit.drawText(ctx, "§7- " + this.desc, this.x + 12, this.y + 16, 0.7F, false);
        this.renderToggle(ctx, this.x + this.width - 34, this.y + (this.height - 8) / 2, this.setting.value);
    }

    private void renderToggle(class_332 ctx, int tx, int ty, boolean on) {
        RenderKit.fillRoundHoriLine(ctx, tx, ty, 24, 8, on ? -16741056 : -11184811);
        int kx = on ? tx + 17 : tx + 2;
        RenderKit.fillCircle(ctx, kx + 3, ty + 4, 5, on ? -9977345 : -7829368);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHovered((int) mouseX, (int) mouseY)) {
            boolean next = !this.setting.value;
            this.setting.value = next;
            this.onToggle.accept(next);
        }
    }
}
