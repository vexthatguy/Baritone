package com.vex.baritone.config.ui;

import net.minecraft.class_2960;
import net.minecraft.class_332;

/**
 * Rendering primitives for MC 1.21 (drawTexture without RenderLayer,
 * MatrixStack transform).
 */
public final class DrawSupport {

    private DrawSupport() {
    }

    public static void drawTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                   int texW, int texH) {
        ctx.method_25290(texture, x, y, 0.0F, 0.0F, w, h, texW, texH);
    }

    public static void drawScaledTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                         int texW, int texH) {
        ctx.method_25290(texture, x, y, 0.0F, 0.0F, w, h, texW, texH);
    }

    public static void beginTransform(class_332 ctx, float scale) {
        ctx.method_51448().method_22903();
        ctx.method_51448().method_22905(scale, scale, scale);
    }

    public static void endTransform(class_332 ctx) {
        ctx.method_51448().method_22909();
    }
}
