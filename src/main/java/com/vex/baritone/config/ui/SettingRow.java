package com.vex.baritone.config.ui;

import com.vex.baritone.api.Settings;
import net.minecraft.class_332;

/**
 * One setting: label, short description and a compact switch tied to a live
 * Baritone boolean setting.
 *
 * <p>The row is a plain 34px strip with a full-width hairline under it. Hover
 * adds a flat wash, the knob glides, and rows stagger in when a section
 * changes.</p>
 */
public class SettingRow extends GuiElement {
    public static final int ROW_HEIGHT = 34;
    public static final int ROW_GAP = 0;

    private static final int TRACK_WIDTH = 28;
    private static final int TRACK_HEIGHT = 14;
    private static final int KNOB_SIZE = 10;

    private final String label;
    private final String description;
    private final Settings.Setting<Boolean> setting;
    private final Runnable onToggle;
    private final PollingAnimator animator;

    private double hover;
    private double appear = 1.0;
    private long appearStart;
    private long appearDelayMs;
    private long lastFrame = System.nanoTime();
    private boolean interactive = true;

    public SettingRow(String label, String description, Settings.Setting<Boolean> setting, Runnable onToggle) {
        super(0, 0, 280, ROW_HEIGHT);
        this.label = label;
        this.description = description;
        this.setting = setting;
        this.onToggle = onToggle;
        this.animator = new PollingAnimator(170, () -> this.setting.value, Animations.EASE_OUT);
    }

    public void toggle() {
        if (!this.interactive) {
            return;
        }
        this.setting.value = !this.setting.value;
        this.onToggle.run();
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    /** Restarts the staggered fade-in, delayed by {@code delayMs}. */
    public void appear(int delayMs) {
        this.appearStart = System.currentTimeMillis();
        this.appearDelayMs = delayMs;
        this.appear = 0.0;
        this.hover = 0.0;
    }

    @Override
    public void onRender(class_332 ctx, int mouseX, int mouseY) {
        renderFaded(ctx, mouseX, mouseY, 1.0);
    }

    public void renderFaded(class_332 ctx, int mouseX, int mouseY, double fade) {
        long now = System.nanoTime();
        double dt = Motion.delta(this.lastFrame);
        this.lastFrame = now;

        boolean hovered = this.interactive && this.isHovered(mouseX, mouseY);
        this.hover = Motion.approach(this.hover, hovered ? 1.0 : 0.0, 18.0, dt);

        if (this.appear < 1.0) {
            long elapsed = System.currentTimeMillis() - this.appearStart - this.appearDelayMs;
            this.appear = Motion.clamp01(elapsed / 190.0);
            this.appear = Motion.easeOutCubic(this.appear);
        }

        double alpha = fade * this.appear;
        if (alpha <= 0.01) {
            return;
        }

        if (this.hover > 0.01) {
            RenderKit.fillRect(ctx, this.x, this.y, this.width, this.height,
                RenderKit.alpha(Theme.ROW_HOVER, alpha * this.hover));
        }

        int textLeft = this.x + 14;
        int textMax = Math.max(24, this.width - 14 - 14 - TRACK_WIDTH - 16);
        RenderKit.drawText(ctx, RenderKit.ellipsize(this.label, 1.0F, textMax),
            textLeft, this.y + 8, 1.0F, false, RenderKit.alpha(Theme.TEXT, alpha));
        if (this.width >= 170) {
            RenderKit.drawText(ctx, RenderKit.ellipsize(this.description, 1.0F, textMax),
                textLeft, this.y + 19, 1.0F, false, RenderKit.alpha(Theme.TEXT_FAINT, alpha));
        }

        RenderKit.drawHorLine(ctx, this.x, this.y + this.height - 1, this.width,
            RenderKit.alpha(Theme.ROW_SEPARATOR, alpha * (1.0 - this.hover * 0.55)));

        renderToggle(ctx, alpha);
    }

    private void renderToggle(class_332 ctx, double fade) {
        double progress = this.animator.getAnimation();
        int trackX = this.x + this.width - 14 - TRACK_WIDTH;
        int trackY = this.y + (this.height - TRACK_HEIGHT) / 2;

        int trackColor = RenderKit.lerp(Theme.TRACK_OFF, Theme.ACCENT, progress);
        if (this.hover > 0.0) {
            trackColor = RenderKit.lerp(trackColor, Theme.ACCENT_DEEP, this.hover * 0.35 * (1.0 - progress));
        }
        RenderKit.fillRoundRect(ctx, trackX, trackY, TRACK_WIDTH, TRACK_HEIGHT, 2,
            RenderKit.alpha(trackColor, fade));

        int travel = TRACK_WIDTH - KNOB_SIZE - 4;
        int knobX = trackX + 2 + (int) Math.round(travel * progress);
        int knobY = trackY + (TRACK_HEIGHT - KNOB_SIZE) / 2;

        RenderKit.fillRoundRect(ctx, knobX, knobY, KNOB_SIZE, KNOB_SIZE, 1,
            RenderKit.alpha(Theme.KNOB, fade));
    }
}
