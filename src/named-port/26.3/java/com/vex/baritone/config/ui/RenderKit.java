package com.vex.baritone.config.ui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * Small, dependency-free drawing helper for MC 26.3. Built on the
 * GuiGraphicsExtractor render-state API.
 */
public final class RenderKit {
    private RenderKit() {
    }

    // ---------------------------------------------------------------- shapes

    public static void fillRect(GuiGraphicsExtractor gr, int x, int y, int w, int h, int color) {
        if (w <= 0 || h <= 0) {
            return;
        }
        gr.fill(x, y, x + w, y + h, color);
    }

    public static void fillVerticalGradient(GuiGraphicsExtractor gr, int x, int y, int w, int h, int colorTop, int colorBottom) {
        if (w <= 0 || h <= 0) {
            return;
        }
        gr.fillGradient(x, y, x + w, y + h, colorTop, colorBottom);
    }

    public static void fillRoundRect(GuiGraphicsExtractor gr, int x, int y, int w, int h, int radius, int color) {
        fillRect(gr, x, y, w, h, color);
    }

    public static void fillCircle(GuiGraphicsExtractor gr, int centerX, int centerY, int radius, int color) {
        fillRect(gr, centerX - radius, centerY - radius, radius * 2, radius * 2, color);
    }

    public static void borderRoundRect(GuiGraphicsExtractor gr, int x, int y, int w, int h, int radius,
                                       int thickness, int color) {
        drawRect(gr, x, y, w, h, color);
    }

    public static void fillRoundHoriLine(GuiGraphicsExtractor gr, int x, int y, int length, int thickness, int color) {
        fillRect(gr, x, y, length, thickness, color);
    }

    public static void drawHorLine(GuiGraphicsExtractor gr, int x, int y, int length, int color) {
        gr.horizontalLine(x, x + length, y, color);
    }

    public static void drawVerLine(GuiGraphicsExtractor gr, int x, int y, int length, int color) {
        gr.verticalLine(x, y, y + length, color);
    }

    public static void drawRect(GuiGraphicsExtractor gr, int x, int y, int w, int h, int color) {
        if (w <= 0 || h <= 0) {
            return;
        }
        gr.outline(x, y, w, h, color);
    }

    public static void drawRoundRect(GuiGraphicsExtractor gr, int x, int y, int w, int h, int radius, int color) {
        drawRect(gr, x, y, w, h, color);
    }

    public static void fillSoftShadow(GuiGraphicsExtractor gr, int x, int y, int w, int h, int radius, int layers, int color) {
        fillRect(gr, x, y, w, h, color);
    }

    // -------------------------------------------------------------- scissor

    public static void pushClip(GuiGraphicsExtractor gr, int x, int y, int w, int h) {
        gr.enableScissor(x, y, w, h);
    }

    public static void popClip(GuiGraphicsExtractor gr) {
        gr.disableScissor();
    }

    // -------------------------------------------------------------- texture

    public static void drawTexture(GuiGraphicsExtractor gr, Identifier texture, int x, int y, int w, int h) {
        DrawSupport.drawTexture(gr, texture, x, y, w, h, w, h);
    }

    public static void drawTexture(GuiGraphicsExtractor gr, Identifier texture, int x, int y, int w, int h,
                                   int texW, int texH) {
        DrawSupport.drawTexture(gr, texture, x, y, w, h, texW, texH);
    }

    public static void drawScaledTexture(GuiGraphicsExtractor gr, Identifier texture, int x, int y, int w, int h,
                                         int texW, int texH) {
        DrawSupport.drawScaledTexture(gr, texture, x, y, w, h, texW, texH);
    }

    // ----------------------------------------------------------------- text

    public static Font font() {
        return Minecraft.getInstance().font;
    }

    public static void drawText(GuiGraphicsExtractor gr, String text, int x, int y, boolean shadow) {
        drawScaledText(gr, text, x, y, 1.0F, shadow, -1);
    }

    public static void drawText(GuiGraphicsExtractor gr, String text, int x, int y, float scale, boolean shadow) {
        drawScaledText(gr, text, x, y, scale, shadow, -1);
    }

    public static void drawText(GuiGraphicsExtractor gr, String text, int x, int y, float scale, boolean shadow, int color) {
        drawScaledText(gr, text, x, y, scale, shadow, color);
    }

    public static void drawScaledText(GuiGraphicsExtractor gr, String text, int x, int y,
                                      float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(gr, scale);
        float rescale = 1.0F / scale;
        int xx = (int) (x * rescale);
        int yy = (int) (y * rescale);
        gr.text(font(), Component.literal(text), xx, yy, color, shadow);
        DrawSupport.endTransform(gr);
    }

    public static void drawCenteredText(GuiGraphicsExtractor gr, String text, int centerX, int y,
                                        float scale, boolean shadow) {
        drawCenteredText(gr, text, centerX, y, scale, shadow, -1);
    }

    public static void drawCenteredText(GuiGraphicsExtractor gr, String text, int centerX, int y, boolean shadow) {
        drawCenteredText(gr, text, centerX, y, 1.0F, shadow, -1);
    }

    public static void drawCenteredText(GuiGraphicsExtractor gr, String text, int centerX, int y,
                                        float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(gr, scale);
        float rescale = 1.0F / scale;
        int x = (int) (centerX * rescale) - textWidth(text, 1.0F) / 2;
        int yy = (int) (y * rescale);
        gr.text(font(), Component.literal(text), x, yy, color, shadow);
        DrawSupport.endTransform(gr);
    }

    public static void drawRightText(GuiGraphicsExtractor gr, String text, int rightX, int y,
                                     float scale, boolean shadow) {
        drawRightText(gr, text, rightX, y, scale, shadow, -1);
    }

    public static void drawRightText(GuiGraphicsExtractor gr, String text, int rightX, int y,
                                     float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(gr, scale);
        float rescale = 1.0F / scale;
        int x = (int) (rightX * rescale) - textWidth(text, 1.0F);
        int yy = (int) (y * rescale);
        gr.text(font(), Component.literal(text), x, yy, color, shadow);
        DrawSupport.endTransform(gr);
    }

    public static int textWidth(String text, float scale) {
        return (int) (text.length() * 6.5F * scale);
    }

    public static String ellipsize(String text, float scale, int maxWidth) {
        if (maxWidth <= 0) {
            return "";
        }
        if (textWidth(text, scale) <= maxWidth) {
            return text;
        }
        String out = text;
        while (out.length() > 1 && textWidth(out + "...", scale) > maxWidth) {
            out = out.substring(0, out.length() - 1);
        }
        return out + "...";
    }

    // ---------------------------------------------------------------- utils

    public static int alpha(int color, double factor) {
        if (factor <= 0.0) {
            return 0;
        }
        if (factor >= 1.0) {
            return color;
        }
        int a = (color >>> 24) & 0xFF;
        a = (int) (a * factor);
        return (a << 24) | (color & 0xFFFFFF);
    }

    public static int lerp(int from, int to, double t) {
        if (t <= 0.0) {
            return from;
        }
        if (t >= 1.0) {
            return to;
        }
        int a = (from >>> 24) & 0xFF;
        int r = (from >> 16) & 0xFF;
        int g = (from >> 8) & 0xFF;
        int b = from & 0xFF;
        int a2 = (to >>> 24) & 0xFF;
        int r2 = (to >> 16) & 0xFF;
        int g2 = (to >> 8) & 0xFF;
        int b2 = to & 0xFF;
        return (int) (a + (a2 - a) * t) << 24
                | (int) (r + (r2 - r) * t) << 16
                | (int) (g + (g2 - g) * t) << 8
                | (int) (b + (b2 - b) * t);
    }

    public static int width() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    public static int height() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }
}
