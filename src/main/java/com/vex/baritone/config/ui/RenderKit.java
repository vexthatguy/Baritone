package com.vex.baritone.config.ui;

import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;

/**
 * Small, dependency-free drawing helper. Everything is built on the verified
 * vanilla DrawContext API (fill / gradient / text / texture / scissor), so it
 * behaves identically across GUI scales.
 */
public final class RenderKit {
    private RenderKit() {
    }

    // ---------------------------------------------------------------- shapes

    public static void fillRect(class_332 ctx, int x, int y, int w, int h, int color) {
        if (w <= 0 || h <= 0) {
            return;
        }
        ctx.method_25294(x, y, x + w, y + h, color);
    }

    public static void fillVerticalGradient(class_332 ctx, int x, int y, int w, int h, int colorTop, int colorBottom) {
        if (w <= 0 || h <= 0) {
            return;
        }
        ctx.method_25296(x, y, x + w, y + h, colorTop, colorBottom);
    }

    /**
     * Real anti-aliased-looking rounded rectangle drawn as 1px scanlines with
     * circular corner insets. Far cleaner than stacking overlapping squares.
     */
    public static void fillRoundRect(class_332 ctx, int x, int y, int w, int h, int radius, int color) {
        if (w <= 0 || h <= 0) {
            return;
        }
        int r = Math.min(radius, Math.min(w, h) / 2);
        if (r <= 0) {
            fillRect(ctx, x, y, w, h, color);
            return;
        }
        for (int row = 0; row < h; row++) {
            int inset;
            if (row < r) {
                inset = cornerInset(r, r - row - 1);
            } else if (row >= h - r) {
                inset = cornerInset(r, row - (h - r));
            } else {
                inset = 0;
            }
            fillRect(ctx, x + inset, y + row, w - inset * 2, 1, color);
        }
    }

    private static int cornerInset(int radius, int dy) {
        double remaining = (double) radius * radius - (double) dy * dy;
        return radius - (int) Math.round(Math.sqrt(Math.max(0.0, remaining)));
    }

    public static void fillCircle(class_332 ctx, int centerX, int centerY, int radius, int color) {
        fillRoundRect(ctx, centerX - radius, centerY - radius, radius * 2, radius * 2, radius, color);
    }

    /** 1px border with rounded corners: border shell, then surface inset by one. */
    public static void borderRoundRect(class_332 ctx, int x, int y, int w, int h, int radius,
                                       int surfaceColor, int borderColor) {
        fillRoundRect(ctx, x, y, w, h, radius, borderColor);
        fillRoundRect(ctx, x + 1, y + 1, w - 2, h - 2, Math.max(0, radius - 1), surfaceColor);
    }

    public static void fillRoundHoriLine(class_332 ctx, int x, int y, int length, int thickness, int color) {
        fillRoundRect(ctx, x, y, length, thickness, thickness / 2, color);
    }

    public static void drawHorLine(class_332 ctx, int x, int y, int length, int color) {
        fillRect(ctx, x, y, length, 1, color);
    }

    public static void drawVerLine(class_332 ctx, int x, int y, int length, int color) {
        fillRect(ctx, x, y, 1, length, color);
    }

    public static void drawRect(class_332 ctx, int x, int y, int w, int h, int color) {
        drawHorLine(ctx, x, y, w, color);
        drawVerLine(ctx, x, y + 1, h - 2, color);
        drawVerLine(ctx, x + w - 1, y + 1, h - 2, color);
        drawHorLine(ctx, x, y + h - 1, w, color);
    }

    public static void drawRoundRect(class_332 ctx, int x, int y, int w, int h, int radius, int color) {
        drawRect(ctx, x, y, w, h, color);
    }

    /** Soft drop shadow made of a few concentric translucent rounded rects. */
    public static void fillSoftShadow(class_332 ctx, int x, int y, int w, int h, int radius, int layers, int color) {
        for (int i = layers; i >= 1; i--) {
            int alpha = (int) (((color >>> 24) & 0xFF) * (i / (double) (layers + 1)));
            int layerColor = (alpha << 24) | (color & 0xFFFFFF);
            fillRoundRect(ctx, x - i, y - i, w + i * 2, h + i * 2, radius + i, layerColor);
        }
    }

    // -------------------------------------------------------------- scissor

    public static void pushClip(class_332 ctx, int x, int y, int w, int h) {
        ctx.method_44379(x, y, x + w, y + h);
    }

    public static void popClip(class_332 ctx) {
        ctx.method_44380();
    }

    // -------------------------------------------------------------- texture

    public static void drawTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h) {
        DrawSupport.drawTexture(ctx, texture, x, y, w, h, w, h);
    }

    /** Draws a full texture at an arbitrary size using its real pixel dimensions. */
    public static void drawTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                   int texW, int texH) {
        DrawSupport.drawTexture(ctx, texture, x, y, w, h, texW, texH);
    }

    /**
     * Draws the whole texture at an arbitrary on-screen size.
     *
     * <p>{@code drawTexture} ties the sampled region to the destination size,
     * so a 512px icon drawn at 24px only shows its top-left 24px corner. This
     * variant takes the region size separately, which is what scaling an icon
     * actually needs.</p>
     */
    public static void drawScaledTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                         int texW, int texH) {
        DrawSupport.drawScaledTexture(ctx, texture, x, y, w, h, texW, texH);
    }

    // ----------------------------------------------------------------- text

    public static class_327 font() {
        return class_310.method_1551().field_1772;
    }

    public static void drawText(class_332 ctx, String text, int x, int y, boolean shadow) {
        drawScaledText(ctx, class_2561.method_43470(text), x, y, 1.0F, shadow, -1);
    }

    public static void drawText(class_332 ctx, String text, int x, int y, float scale, boolean shadow) {
        drawScaledText(ctx, class_2561.method_43470(text), x, y, scale, shadow, -1);
    }

    public static void drawText(class_332 ctx, String text, int x, int y, float scale, boolean shadow, int color) {
        drawScaledText(ctx, class_2561.method_43470(text), x, y, scale, shadow, color);
    }

    public static void drawScaledText(class_332 ctx, class_2561 text, int x, int y,
                                      float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(ctx, scale);
        float rescale = 1.0F / scale;
        int xx = (int) (x * rescale);
        int yy = (int) (y * rescale);
        ctx.method_51439(font(), text, xx, yy, color, shadow);
        DrawSupport.endTransform(ctx);
    }

    public static void drawCenteredText(class_332 ctx, String text, int centerX, int y,
                                        float scale, boolean shadow) {
        drawCenteredText(ctx, text, centerX, y, scale, shadow, -1);
    }

    public static void drawCenteredText(class_332 ctx, String text, int centerX, int y, boolean shadow) {
        drawCenteredText(ctx, text, centerX, y, 1.0F, shadow, -1);
    }

    public static void drawCenteredText(class_332 ctx, String text, int centerX, int y,
                                        float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(ctx, scale);
        float rescale = 1.0F / scale;
        int x = (int) (centerX * rescale) - font().method_27525(class_2561.method_43470(text)) / 2;
        int yy = (int) (y * rescale);
        ctx.method_51439(font(), class_2561.method_43470(text), x, yy, color, shadow);
        DrawSupport.endTransform(ctx);
    }

    public static void drawRightText(class_332 ctx, String text, int rightX, int y,
                                     float scale, boolean shadow) {
        drawRightText(ctx, text, rightX, y, scale, shadow, -1);
    }

    public static void drawRightText(class_332 ctx, String text, int rightX, int y,
                                     float scale, boolean shadow, int color) {
        DrawSupport.beginTransform(ctx, scale);
        float rescale = 1.0F / scale;
        int x = (int) (rightX * rescale) - font().method_27525(class_2561.method_43470(text));
        int yy = (int) (y * rescale);
        ctx.method_51439(font(), class_2561.method_43470(text), x, yy, color, shadow);
        DrawSupport.endTransform(ctx);
    }

    public static int textWidth(String text, float scale) {
        return (int) (font().method_27525(class_2561.method_43470(text)) * scale);
    }

    /** Truncates text with an ellipsis so it never overflows {@code maxWidth}. */
    public static String ellipsize(String text, float scale, int maxWidth) {
        if (maxWidth <= 0) {
            return "";
        }
        if (textWidth(text, scale) <= maxWidth) {
            return text;
        }
        String ellipsis = "...";
        int limit = maxWidth - textWidth(ellipsis, scale);
        if (limit <= 0) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        int used = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int charWidth = textWidth(String.valueOf(c), scale);
            if (used + charWidth > limit) {
                break;
            }
            out.append(c);
            used += charWidth;
        }
        return out + ellipsis;
    }

    // --------------------------------------------------------------- helpers

    /** Multiply a colour's alpha channel by {@code factor} (0..1). */
    public static int alpha(int color, double factor) {
        int a = (int) (((color >>> 24) & 0xFF) * Math.max(0.0, Math.min(1.0, factor)));
        return (a << 24) | (color & 0xFFFFFF);
    }

    public static int lerp(int from, int to, double t) {
        int a = (int) (((from >>> 24) & 0xFF) + ((((to >>> 24) & 0xFF) - ((from >>> 24) & 0xFF)) * t));
        int r = (int) (((from >> 16) & 0xFF) + ((((to >> 16) & 0xFF) - ((from >> 16) & 0xFF)) * t));
        int g = (int) (((from >> 8) & 0xFF) + ((((to >> 8) & 0xFF) - ((from >> 8) & 0xFF)) * t));
        int b = (int) ((from & 0xFF) + (((to & 0xFF) - (from & 0xFF)) * t));
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int width() {
        return class_310.method_1551().method_22683().method_4486();
    }

    public static int height() {
        return class_310.method_1551().method_22683().method_4502();
    }
}
