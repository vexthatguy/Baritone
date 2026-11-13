package com.vex.baritone.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.vex.baritone.config.ui.RenderKit;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

/**
 * ModMenu entrypoint. The screen factory is wrapped so that if anything in
 * the config screen fails to construct, we log it instead of crashing the
 * screen open (and we fall back to a plain message screen).
 */
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            try {
                return new BaritoneConfigScreen(parent);
            } catch (Throwable t) {
                System.err.println("[Baritone] Failed to open config screen: " + t);
                t.printStackTrace();
                return new class_437(class_2561.method_43470("Baritone Config failed to open")) {
                    @Override
                    public void method_25394(class_332 ctx, int mouseX, int mouseY, float delta) {
                        super.method_25394(ctx, mouseX, mouseY, delta);
                        RenderKit.drawCenteredText(ctx, "Baritone config failed to open (see latest.log)",
                                RenderKit.width() / 2, RenderKit.height() / 2, 1.0F, false);
                    }
                };
            }
        };
    }
}
