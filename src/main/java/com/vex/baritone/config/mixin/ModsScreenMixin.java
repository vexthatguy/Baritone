package com.vex.baritone.config.mixin;

import com.terraformersmc.modmenu.gui.ModsScreen;
import com.vex.baritone.config.ModMenuVisibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs before ModMenu builds its list. If the mod is switched off its entry is
 * already gone from ModMenu's own registry, so it never gets a row — it cannot
 * come back via search, resizing or filtering.
 */
@Mixin(ModsScreen.class)
public abstract class ModsScreenMixin {

    @Inject(method = "method_25426", at = @At("HEAD"))
    private void baritone$syncBeforeBuild(CallbackInfo ci) {
        ModMenuVisibility.sync();
    }
}
