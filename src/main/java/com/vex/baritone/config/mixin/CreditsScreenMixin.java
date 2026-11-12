package com.vex.baritone.config.mixin;

import com.vex.baritone.config.CreditsToggle;
import net.minecraft.class_8219;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Attaches the single "Baritone: ON / OFF" button to the Credits and
 * Attribution screen right after its widgets are built.
 */
@Mixin(class_8219.class)
public abstract class CreditsScreenMixin {

    @Inject(method = "method_25426", at = @At("TAIL"))
    private void baritone$addToggle(CallbackInfo ci) {
        CreditsToggle.ensure(this);
    }
}
