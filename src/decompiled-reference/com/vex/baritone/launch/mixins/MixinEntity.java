package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.event.events.RotationMoveEvent;
import net.minecraft.class_1297;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_1297.class)
public class MixinEntity {
   @Shadow
   private float field_6031;
   @Shadow
   private float field_5965;
   @Unique
   private RotationMoveEvent motionUpdateRotationEvent;

   @Inject(method = "moveRelative", at = @At("HEAD"))
   private void moveRelativeHead(CallbackInfo var1) {
      if (class_746.class.isInstance(this) && BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this) != null) {
         this.motionUpdateRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.MOTION_UPDATE, this.field_6031, this.field_5965);
         BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this).getGameEventHandler().onPlayerRotationMove(this.motionUpdateRotationEvent);
         this.field_6031 = this.motionUpdateRotationEvent.getYaw();
         this.field_5965 = this.motionUpdateRotationEvent.getPitch();
      }
   }

   @Inject(method = "moveRelative", at = @At("RETURN"))
   private void moveRelativeReturn(CallbackInfo var1) {
      if (this.motionUpdateRotationEvent != null) {
         this.field_6031 = this.motionUpdateRotationEvent.getOriginal().getYaw();
         this.field_5965 = this.motionUpdateRotationEvent.getOriginal().getPitch();
         this.motionUpdateRotationEvent = null;
      }
   }
}
