package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.utils.accessor.IItemStack;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_1799.class)
public abstract class MixinItemStack implements IItemStack {
   @Shadow
   @Final
   private class_1792 field_8038;
   @Unique
   private int baritoneHash;

   @Shadow
   public abstract int method_7919();

   private void recalculateHash() {
      this.baritoneHash = this.field_8038 == null ? -1 : this.field_8038.hashCode() + this.method_7919();
   }

   @Inject(method = "<init>*", at = @At("RETURN"))
   private void onInit(CallbackInfo var1) {
      this.recalculateHash();
   }

   @Inject(method = "setDamageValue", at = @At("TAIL"))
   private void onItemDamageSet(CallbackInfo var1) {
      this.recalculateHash();
   }

   @Override
   public int getBaritoneHash() {
      if (this.baritoneHash == 0) {
         this.recalculateHash();
      }

      return this.baritoneHash;
   }
}
