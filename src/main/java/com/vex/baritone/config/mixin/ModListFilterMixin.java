package com.vex.baritone.config.mixin;

import com.terraformersmc.modmenu.gui.widget.ModListWidget;
import com.vex.baritone.config.ModMenuVisibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keeps the ModMenu registry in sync every time the list is rebuilt (typing in
 * the search box, resizing, toggling libraries). Without this the entry could
 * sneak back into the map-driven rebuild.
 */
@Mixin(ModListWidget.class)
public abstract class ModListFilterMixin {

    @Inject(method = "filter(Ljava/lang/String;ZZ)V", at = @At("HEAD"))
    private void baritone$syncBeforeFilter(CallbackInfo ci) {
        ModMenuVisibility.sync();
    }
}
