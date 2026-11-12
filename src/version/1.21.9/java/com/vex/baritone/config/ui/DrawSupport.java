package com.vex.baritone.config.ui;

import net.minecraft.class_10799;
import net.minecraft.class_2960;
import net.minecraft.class_332;

/**
 * Rendering primitives for MC 1.21.6 (RenderPipeline-based drawTexture,
 * Matrix3x2fStack transform).
 */
public final class DrawSupport {

    private DrawSupport() {
    }

    public static void drawTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                   int texW, int texH) {
        ctx.method_25290(class_10799.field_56879, texture, x, y, 0.0F, 0.0F, w, h, texW, texH);
    }

    public static void drawScaledTexture(class_332 ctx, class_2960 texture, int x, int y, int w, int h,
                                         int texW, int texH) {
        ctx.method_25302(class_10799.field_56879, texture, x, y, 0.0F, 0.0F,
            w, h, texW, texH, texW, texH);
    }

    public static void beginTransform(class_332 ctx, float scale) {
        var matrices = ctx.method_51448().pushMatrix();
        matrices.scale(scale);
    }

    public static void endTransform(class_332 ctx) {
        ctx.method_51448().popMatrix();
    }
}
