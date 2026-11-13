package com.vex.baritone.config.ui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

/**
 * Rendering primitives for MC 26.3 (GuiGraphicsExtractor render-state API).
 */
public final class DrawSupport {

    private DrawSupport() {
    }

    public static void drawTexture(GuiGraphicsExtractor gr, Identifier texture, int x, int y, int w, int h,
                                   int texW, int texH) {
        gr.blit(texture, x, y, w, h, 0.0F, 0.0F, texW, texH);
    }

    public static void drawScaledTexture(GuiGraphicsExtractor gr, Identifier texture, int x, int y, int w, int h,
                                         int texW, int texH) {
        gr.blit(texture, x, y, w, h, 0.0F, 0.0F, texW, texH);
    }

    public static void beginTransform(GuiGraphicsExtractor gr, float scale) {
        var matrices = gr.pose().pushMatrix();
        matrices.scale(scale);
    }

    public static void endTransform(GuiGraphicsExtractor gr) {
        gr.pose().popMatrix();
    }
}
