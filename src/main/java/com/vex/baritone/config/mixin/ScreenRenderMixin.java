package com.vex.baritone.config.mixin;

import com.vex.baritone.config.CreditsToggle;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_8219;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Safety net for the Credits screen: if it rebuilds its widget list (tab
 * changes, resize, resource reload), the toggle is put back on the next
 * frame. The check is a cheap identity test plus a list lookup.
 */
@Mixin(class_437.class)
public abstract class ScreenRenderMixin {

    @Inject(method = "method_25394", at = @At("TAIL"))
    private void baritone$ensureToggle(class_332 ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if ((Object) this instanceof class_8219) {
            CreditsToggle.ensure(this);
        }
    }
}
