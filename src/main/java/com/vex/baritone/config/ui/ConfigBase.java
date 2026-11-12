package com.vex.baritone.config.ui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
/**
 * Shared shell for the Baritone config GUI.
 *
 * <p>Flat and dense by design: one window surface, one-pixel borders, a fixed
 * navigation column and compact setting rows. Motion is deliberately limited
 * to things that help you read the screen: the window fades in, the section
 * highlight glides, rows stagger in, and toggles settle smoothly.</p>
 */
public abstract class ConfigBase extends RemasteredUiBase {
    public static final int PANEL_MAX_W = 600;
    public static final int PANEL_MAX_H = 400;

    private static final int HEADER_H = 42;
    private static final int NAV_MAX_W = 148;
    private static final int FOOTER_H = 24;

    private static final class_2960 LOGO = class_2960.method_60655("baritone", "textures/icon.png");
    private static final int LOGO_SIZE = 512;

    protected final List<NavItem> navItems = new ArrayList<>();
    protected final List<SettingRow> rows = new ArrayList<>();

    // Layout (recomputed every frame so resizing is safe).
    protected int screenW;
    protected int screenH;
    protected int panelX;
    protected int panelY;
    protected int panelW;
    protected int panelH;
    protected int headerH;
    protected int navX;
    protected int navW;
    protected int navTop;
    protected int navItemH;
    protected int navGap;
    protected int contentX;
    protected int contentY;
    protected int contentW;
    protected int contentH;
    protected int viewX;
    protected int viewY;
    protected int viewW;
    protected int viewH;

    protected String sectionTitle = "Settings";
    protected String sectionSubtitle = "";
    protected String sectionInfo = "";
    protected boolean notice;

    private long lastFrame = System.nanoTime();
    private double openT;
    private double frameDt = 0.016;
    private double scroll;
    private double scrollTarget;
    private double contentFade = 1.0;
    private double navSelY = -1.0;
    private int openOffset;

    protected ConfigBase(String title) {
        super(class_2561.method_43470(title));
        ensureLayout();
    }

    // ---------------------------------------------------------------- layout

    protected final void ensureLayout() {
        this.screenW = RenderKit.width();
        this.screenH = RenderKit.height();

        this.panelW = Math.min(PANEL_MAX_W, Math.max(200, this.screenW - 32));
        this.panelH = Math.min(PANEL_MAX_H, Math.max(150, this.screenH - 32));
        this.panelW = Math.min(this.panelW, Math.max(140, this.screenW - 8));
        this.panelH = Math.min(this.panelH, Math.max(120, this.screenH - 8));
        this.panelX = (this.screenW - this.panelW) / 2;
        this.panelY = (this.screenH - this.panelH) / 2 + this.openOffset;

        this.headerH = HEADER_H;

        this.navX = this.panelX;
        this.navW = Math.min(NAV_MAX_W, Math.max(70, this.panelW / 3));
        this.navItemH = 24;
        this.navGap = 1;
        this.navTop = this.panelY + this.headerH + 26;

        this.contentX = this.navX + this.navW;
        this.contentY = this.panelY + this.headerH;
        this.contentW = this.panelW - this.navW;
        this.contentH = this.panelH - this.headerH;

        this.viewX = this.contentX + 8;
        this.viewW = Math.max(40, this.contentW - 8 - 10);
        this.viewY = this.contentY + 48;
        this.viewH = Math.max(20, this.panelY + this.panelH - FOOTER_H - 6 - this.viewY);
    }

    protected final void addNav(NavItem item) {
        this.navItems.add(item);
    }

    protected final void addRow(SettingRow row) {
        this.rows.add(row);
    }

    protected final void clearRows() {
        this.rows.clear();
    }

    protected final void setSection(String title, String subtitle, String info) {
        this.sectionTitle = title;
        this.sectionSubtitle = subtitle;
        this.sectionInfo = info;
    }

    protected final void setNotice(boolean notice) {
        this.notice = notice;
    }

    protected final void resetScroll() {
        this.scroll = 0.0;
        this.scrollTarget = 0.0;
    }

    /** Recomputes row positions after the row list or layout changed. */
    protected final void refreshRows() {
        ensureLayout();
        clampScroll();
        positionRows();
    }

    private int rowStride() {
        return SettingRow.ROW_HEIGHT + SettingRow.ROW_GAP;
    }

    private int contentHeightTotal() {
        return this.rows.size() * rowStride() + 6;
    }

    private int maxScroll() {
        return Math.max(0, contentHeightTotal() - this.viewH);
    }

    private void clampScroll() {
        int max = maxScroll();
        this.scrollTarget = Motion.clamp(this.scrollTarget, 0, max);
        this.scroll = Motion.clamp(this.scroll, 0, max);
    }

    private void positionRows() {
        int y = this.viewY + 2 - (int) Math.round(this.scroll);
        for (SettingRow row : this.rows) {
            row.setX(this.viewX);
            row.setY(y);
            row.setWidth(this.viewW);
            y += rowStride();
        }
    }

    // ------------------------------------------------------------- animation

    /** Restarts the staggered row entrance (used when a section changes). */
    protected final void animateRowsIn() {
        for (int i = 0; i < this.rows.size(); i++) {
            this.rows.get(i).appear(Math.min(i * 16, 140));
        }
        this.contentFade = 0.0;
    }

    // ---------------------------------------------------------------- render

    @Override
    public void method_25394(class_332 ctx, int mouseX, int mouseY, float delta) {
        super.method_25394(ctx, mouseX, mouseY, delta);

        double dt = Motion.delta(this.lastFrame);
        this.lastFrame = System.nanoTime();
        this.frameDt = dt;

        this.openT = Motion.clamp01(this.openT + dt / 0.20);
        double open = Motion.easeOutCubic(this.openT);
        this.openOffset = (int) Math.round((1.0 - open) * 10.0);

        this.contentFade = Motion.clamp01(this.contentFade + dt / 0.16);
        this.scroll = Motion.approach(this.scroll, this.scrollTarget, 18.0, dt);
        if (Math.abs(this.scrollTarget - this.scroll) < 0.35) {
            this.scroll = this.scrollTarget;
        }

        ensureLayout();
        positionRows();

        double alpha = 0.35 + 0.65 * open;
        renderBackdrop(ctx, alpha);
        renderWindow(ctx, alpha);
        renderHeader(ctx, alpha);
        renderNav(ctx, mouseX, mouseY, alpha);
        renderContent(ctx, mouseX, mouseY, alpha);
        renderFooter(ctx, alpha);
    }

    private void renderBackdrop(class_332 ctx, double t) {
        RenderKit.fillRect(ctx, 0, 0, this.screenW, this.screenH, RenderKit.alpha(Theme.OVERLAY, t));
    }

    private void renderWindow(class_332 ctx, double t) {
        RenderKit.fillSoftShadow(ctx, this.panelX, this.panelY, this.panelW, this.panelH, 2, 6,
            RenderKit.alpha(0x66000000, t));
        RenderKit.fillRect(ctx, this.panelX, this.panelY, this.panelW, this.panelH,
            RenderKit.alpha(Theme.PANEL, t));
        RenderKit.drawRect(ctx, this.panelX, this.panelY, this.panelW, this.panelH,
            RenderKit.alpha(Theme.PANEL_BORDER, t));
    }

    private void renderHeader(class_332 ctx, double t) {
        int left = this.panelX + 14;
        int midY = this.panelY + this.headerH / 2;
        int logo = 24;
        int logoY = midY - logo / 2;

        RenderKit.fillRect(ctx, this.panelX + 1, this.panelY + 1, this.panelW - 2, this.headerH - 2,
            RenderKit.alpha(Theme.HEADER, t));

        try {
            RenderKit.drawScaledTexture(ctx, LOGO, left, logoY, logo, logo, LOGO_SIZE, LOGO_SIZE);
        } catch (Throwable ignored) {
            RenderKit.fillRect(ctx, left, logoY, logo, logo, RenderKit.alpha(Theme.ACCENT_SOFT, t));
            RenderKit.drawCenteredText(ctx, "B", left + logo / 2, logoY + 8, 1.0F, false,
                RenderKit.alpha(Theme.ACCENT, t));
        }

        int textX = left + logo + 10;
        RenderKit.drawText(ctx, "Baritone Remastered", textX, midY - 10, 1.0F, false,
            RenderKit.alpha(Theme.TEXT, t));
        RenderKit.drawText(ctx, "Configuration", textX, midY + 2, 1.0F, false,
            RenderKit.alpha(Theme.TEXT_FAINT, t));

        String version = versionTag();
        int right = this.panelX + this.panelW - 14;
        RenderKit.drawRightText(ctx, version, right, midY - 10, 1.0F, false,
            RenderKit.alpha(Theme.TEXT_FAINT, t));

        boolean enabled = !com.vex.baritone.config.VisibilityConfig.isDisabled();
        String state = enabled ? "ENABLED" : "DISABLED";
        int stateColor = enabled ? Theme.GOOD : Theme.WARN;
        RenderKit.drawRightText(ctx, state, right, midY + 2, 1.0F, false,
            RenderKit.alpha(stateColor, t));
        RenderKit.fillCircle(ctx, right - RenderKit.textWidth(state, 1.0F) - 6, midY + 7, 2,
            RenderKit.alpha(stateColor, t));

        RenderKit.drawHorLine(ctx, this.panelX + 1, this.panelY + this.headerH - 1,
            this.panelW - 2, RenderKit.alpha(Theme.PANEL_BORDER, t));
    }

    private void renderNav(class_332 ctx, int mouseX, int mouseY, double t) {
        // Vertical divider between navigation and content.
        RenderKit.drawVerLine(ctx, this.contentX, this.contentY, this.contentH,
            RenderKit.alpha(Theme.PANEL_BORDER, t));

        if (this.navItems.isEmpty()) {
            return;
        }
        RenderKit.drawText(ctx, "SECTIONS", this.navX + 14, this.navTop - 15, 1.0F, false,
            RenderKit.alpha(Theme.TEXT_FAINT, t));

        int selected = 0;
        for (int i = 0; i < this.navItems.size(); i++) {
            if (this.navItems.get(i).isSelected()) {
                selected = i;
                break;
            }
        }
        double targetY = this.navTop + selected * (this.navItemH + this.navGap);
        if (this.navSelY < 0) {
            this.navSelY = targetY;
        }
        this.navSelY = Motion.approach(this.navSelY, targetY, 22.0, this.frameDt);

        int selY = (int) Math.round(this.navSelY);
        RenderKit.fillRect(ctx, this.navX + 1, selY, this.navW - 2, this.navItemH,
            RenderKit.alpha(Theme.NAV_SELECTED, t));
        RenderKit.fillRect(ctx, this.navX, selY, 2, this.navItemH,
            RenderKit.alpha(Theme.ACCENT, t));

        int y = this.navTop;
        for (NavItem item : this.navItems) {
            item.setX(this.navX);
            item.setY(y);
            item.setWidth(this.navW);
            item.setHeight(this.navItemH);
            item.renderFaded(ctx, mouseX, mouseY, t);
            y += this.navItemH + this.navGap;
        }
    }

    private void renderContent(class_332 ctx, int mouseX, int mouseY, double t) {
        if (this.contentW <= 0 || this.contentH <= 0) {
            return;
        }

        int textX = this.contentX + 18;
        RenderKit.drawText(ctx, this.sectionTitle, textX, this.contentY + 12, 1.0F, false,
            RenderKit.alpha(Theme.ACCENT, t));
        if (!this.sectionSubtitle.isEmpty() && this.contentW > 170) {
            RenderKit.drawText(ctx,
                RenderKit.ellipsize(this.sectionSubtitle, 1.0F, this.contentW - 120),
                textX, this.contentY + 27, 1.0F, false, RenderKit.alpha(Theme.TEXT_DIM, t));
        }
        if (!this.sectionInfo.isEmpty()) {
            RenderKit.drawRightText(ctx, this.sectionInfo, this.contentX + this.contentW - 16,
                this.contentY + 14, 1.0F, false, RenderKit.alpha(Theme.TEXT_FAINT, t));
        }
        RenderKit.drawHorLine(ctx, this.contentX, this.contentY + 45, this.contentW,
            RenderKit.alpha(Theme.PANEL_BORDER, t));

        double fade = t * Motion.easeOutCubic(this.contentFade);
        if (this.notice) {
            renderNotice(ctx, t);
        } else {
            renderRows(ctx, mouseX, mouseY, fade);
            renderScrollbar(ctx, t);
        }
    }

    private void renderRows(class_332 ctx, int mouseX, int mouseY, double fade) {
        boolean inside = mouseX >= this.viewX && mouseX < this.viewX + this.viewW
            && mouseY >= this.viewY && mouseY < this.viewY + this.viewH;

        RenderKit.pushClip(ctx, this.viewX, this.viewY, this.viewW, this.viewH);
        for (SettingRow row : this.rows) {
            row.renderFaded(ctx, inside ? mouseX : -9999, inside ? mouseY : -9999, fade);
        }
        RenderKit.popClip(ctx);
    }

    private void renderScrollbar(class_332 ctx, double t) {
        int max = maxScroll();
        if (max <= 0 || this.viewH <= 0) {
            return;
        }
        int trackX = this.panelX + this.panelW - 5;
        int trackY = this.viewY;
        int trackH = this.viewH;
        RenderKit.fillRect(ctx, trackX, trackY, 2, trackH, RenderKit.alpha(0x14FFFFFF, t));

        double fraction = this.viewH / (double) (this.viewH + max);
        int thumbH = Math.max(16, (int) (trackH * fraction));
        int thumbY = trackY + (int) ((trackH - thumbH) * (this.scroll / (double) max));
        RenderKit.fillRect(ctx, trackX, thumbY, 2, thumbH, RenderKit.alpha(0x55FFFFFF, t));
    }

    private void renderFooter(class_332 ctx, double t) {
        int footerY = this.panelY + this.panelH - FOOTER_H;
        RenderKit.fillRect(ctx, this.panelX + 1, footerY, this.panelW - 2, FOOTER_H - 1,
            RenderKit.alpha(Theme.HEADER, t));
        RenderKit.drawHorLine(ctx, this.panelX + 1, footerY, this.panelW - 2,
            RenderKit.alpha(Theme.PANEL_BORDER, t));

        int textY = footerY + (FOOTER_H - 9) / 2;
        RenderKit.drawText(ctx, "Changes apply instantly", this.contentX + 18, textY, 1.0F, false,
            RenderKit.alpha(Theme.TEXT_FAINT, t));
        if (this.contentW > 220) {
            RenderKit.drawRightText(ctx, "ESC to close", this.panelX + this.panelW - 16, textY, 1.0F,
                false, RenderKit.alpha(Theme.TEXT_FAINT, t));
        }
    }

    private void renderNotice(class_332 ctx, double t) {
        int centerX = this.contentX + this.contentW / 2;
        int blockHeight = 66;
        int top = this.viewY + Math.max(0, (this.viewH - blockHeight) / 2);

        RenderKit.drawRect(ctx, centerX - 11, top, 22, 22, RenderKit.alpha(Theme.ACCENT, t));
        RenderKit.drawCenteredText(ctx, "!", centerX, top + 7, 1.0F, false,
            RenderKit.alpha(Theme.ACCENT, t));

        RenderKit.drawCenteredText(ctx, "Baritone is disabled", centerX, top + 30, 1.0F, false,
            RenderKit.alpha(Theme.TEXT, t));
        if (this.contentW < 260) {
            RenderKit.drawCenteredText(ctx, "Options > Credits &", centerX, top + 43, 1.0F, false,
                RenderKit.alpha(Theme.TEXT_DIM, t));
            RenderKit.drawCenteredText(ctx, "Attribution to enable", centerX, top + 54, 1.0F, false,
                RenderKit.alpha(Theme.TEXT_DIM, t));
        } else {
            RenderKit.drawCenteredText(ctx, "Enable it from Options > Credits & Attribution",
                centerX, top + 43, 1.0F, false, RenderKit.alpha(Theme.TEXT_DIM, t));
            RenderKit.drawCenteredText(ctx, "Your settings are unchanged.", centerX, top + 54,
                1.0F, false, RenderKit.alpha(Theme.TEXT_FAINT, t));
        }
    }

    private static String cachedVersion;

    private static String versionTag() {
        if (cachedVersion != null) {
            return cachedVersion;
        }
        String version = "1.21.11";
        try {
            var container = net.fabricmc.loader.api.FabricLoader.getInstance()
                .getModContainer("baritone-meteor");
            if (container.isPresent()) {
                version = container.get().getMetadata().getVersion().getFriendlyString();
            }
        } catch (Throwable ignored) {
        }
        cachedVersion = "v" + version;
        return cachedVersion;
    }

    // ----------------------------------------------------------------- input

    @Override
    protected boolean handleMouse(double mouseX, double mouseY, int button) {
        ensureLayout();
        int x = (int) mouseX;
        int y = (int) mouseY;

        if (button == 0) {
            for (NavItem item : this.navItems) {
                if (item.isHovered(x, y)) {
                    item.activate();
                    return true;
                }
            }
            if (!this.notice && insideViewport(x, y)) {
                for (SettingRow row : this.rows) {
                    if (row.isHovered(x, y)) {
                        row.toggle();
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        ensureLayout();
        if (!this.notice && insideViewport(mouseX, mouseY)) {
            this.scrollTarget -= verticalAmount * (SettingRow.ROW_HEIGHT * 0.85);
            clampScroll();
            return true;
        }
        return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private boolean insideViewport(double mouseX, double mouseY) {
        return mouseX >= this.viewX && mouseX < this.viewX + this.viewW
            && mouseY >= this.viewY && mouseY < this.viewY + this.viewH;
    }
}
