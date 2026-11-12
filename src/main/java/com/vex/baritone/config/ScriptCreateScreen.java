package com.vex.baritone.config;

import com.vex.baritone.config.ui.RenderKit;
import com.vex.baritone.config.ui.Theme;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_437;
import net.minecraft.class_11909;

/**
 * Minimal flat editor for scripts: type one command at a time, press Add,
 * then Save. Lines are stored as one command per line.
 */
public class ScriptCreateScreen extends class_437 {

    private static final int PANEL_W = 460;
    private static final int PANEL_H = 330;
    private static final int HEADER_H = 40;

    private final class_437 parent;
    private final String scriptName;
    private final java.nio.file.Path runDir;
    private final List<String> lines = new ArrayList<>();
    private String input = "";

    private class_342 field;
    private int panelX;
    private int panelY;
    private int listX;
    private int listY;
    private int listW;
    private int listH;
    private int fieldX;
    private int fieldY;
    private int fieldW;
    private int fieldH;
    private int addX;
    private int addY;
    private int remX;
    private int clearX;
    private int saveX;

    public ScriptCreateScreen(class_437 parent, String name, List<String> existing, java.nio.file.Path runDir) {
        super(class_2561.method_43470("Script Editor"));
        this.parent = parent;
        this.scriptName = name;
        this.runDir = runDir;
        if (existing != null) {
            this.lines.addAll(existing);
        }
    }

    @Override
    protected void method_25426() {
        panelX = (RenderKit.width() - PANEL_W) / 2;
        panelY = (RenderKit.height() - PANEL_H) / 2;
        listX = panelX + 16;
        listY = panelY + HEADER_H + 10;
        listW = PANEL_W - 32;
        listH = 150;
        fieldX = listX;
        fieldY = listY + listH + 16;
        fieldW = listW - 96;
        fieldH = 22;
        addX = fieldX + fieldW + 8;
        addY = fieldY;
        remX = addX + 60;
        clearX = remX + 60;
        saveX = panelX + PANEL_W - 16 - 96;
        field = new class_342(RenderKit.font(), fieldX, fieldY, fieldW, fieldH,
                class_2561.method_43470("type a command, then Add"));
        field.method_1863(value -> this.input = value);
        this.method_37063(field);
    }

    @Override
    public void method_25394(class_332 ctx, int mouseX, int mouseY, float delta) {
        super.method_25394(ctx, mouseX, mouseY, delta);

        RenderKit.fillRect(ctx, 0, 0, RenderKit.width(), RenderKit.height(), Theme.OVERLAY);
        RenderKit.fillRect(ctx, panelX, panelY, PANEL_W, PANEL_H, Theme.PANEL);
        RenderKit.drawRect(ctx, panelX, panelY, PANEL_W, PANEL_H, Theme.PANEL_BORDER);
        RenderKit.fillRect(ctx, panelX + 1, panelY + 1, PANEL_W - 2, HEADER_H - 2, Theme.HEADER);
        RenderKit.drawText(ctx, "Script: " + scriptName, panelX + 16, panelY + 12, 1.0F, false, Theme.TEXT);

        // command list
        RenderKit.fillRect(ctx, listX, listY, listW, listH, Theme.SURFACE);
        RenderKit.drawRect(ctx, listX, listY, listW, listH, Theme.SURFACE_BORDER);
        int y = listY + 6;
        int shown = Math.min(lines.size(), listH / 14);
        for (int i = lines.size() - shown; i < lines.size(); i++) {
            RenderKit.drawText(ctx, (i + 1) + ". " + RenderKit.ellipsize(lines.get(i), 1.0F, listW - 30),
                    listX + 8, y, 1.0F, false, Theme.TEXT_DIM);
            y += 14;
        }
        if (lines.isEmpty()) {
            RenderKit.drawText(ctx, "no commands yet", listX + 8, listY + 8, 1.0F, false, Theme.TEXT_FAINT);
        }

        // input row
        RenderKit.drawRect(ctx, fieldX, fieldY, fieldW, fieldH, Theme.SURFACE_BORDER);
        field.method_48579(ctx, mouseX, mouseY, delta);

        drawButton(ctx, "Add", addX, addY, mouseX, mouseY, Theme.ACCENT);
        drawButton(ctx, "Last", remX, addY, mouseX, mouseY, Theme.ACCENT_DEEP);
        drawButton(ctx, "Clear", clearX, addY, mouseX, mouseY, Theme.ACCENT_DEEP);
        drawButton(ctx, "Save", saveX, addY, mouseX, mouseY, Theme.GOOD);

        RenderKit.drawText(ctx, "Lines: " + lines.size(), listX + listW - 90, listY + listH + 4,
                1.0F, false, Theme.TEXT_FAINT);
    }

    private void drawButton(class_332 ctx, String text, int x, int y, int mx, int my, int color) {
        boolean hover = mx >= x && mx <= x + 52 && my >= y && my <= y + fieldH;
        RenderKit.fillRect(ctx, x, y, 52, fieldH, hover ? Theme.ROW_HOVER : Theme.SURFACE);
        RenderKit.drawRect(ctx, x, y, 52, fieldH, Theme.SURFACE_BORDER);
        RenderKit.drawCenteredText(ctx, text, x + 26, y + 5, 1.0F, false, hover ? color : Theme.TEXT_DIM);
    }

    @Override
    public boolean method_25402(class_11909 click, boolean doubled) {
        int mx = (int) click.comp_4798();
        int my = (int) click.comp_4799();

        if (mx >= fieldX && mx <= fieldX + fieldW && my >= fieldY && my <= fieldY + fieldH) {
            field.method_25348(click, false);
            return true;
        }
        if (inButton(mx, my, addX)) {
            String v = input == null ? "" : input.trim();
            if (!v.isEmpty()) {
                lines.add(v);
                input = "";
                field.method_1852("");
            }
            return true;
        }
        if (inButton(mx, my, remX)) {
            if (!lines.isEmpty()) {
                lines.remove(lines.size() - 1);
            }
            return true;
        }
        if (inButton(mx, my, clearX)) {
            lines.clear();
            return true;
        }
        if (inButton(mx, my, saveX)) {
            ScriptManager.save(runDir, scriptName, lines);
            closeToParent();
            return true;
        }
        return super.method_25402(click, doubled);
    }

    private boolean inButton(int mx, int my, int x) {
        return mx >= x && mx <= x + 52 && my >= addY && my <= addY + fieldH;
    }

    private void closeToParent() {
        if (parent != null) {
            this.field_22787.method_1507(parent);
        } else {
            this.method_25419();
        }
    }
}
