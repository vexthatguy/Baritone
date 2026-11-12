package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.RotationMoveEvent;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_1309.class)
public abstract class MixinLivingEntity extends class_1297 {
   @Unique
   private RotationMoveEvent jumpRotationEvent;
   @Unique
   private RotationMoveEvent elytraRotationEvent;

   private MixinLivingEntity(class_1299<?> var1, class_1937 var2) {
      super(var1, var2);
   }

   @Inject(method = "jumpFromGround", at = @At("HEAD"))
   private void preMoveRelative(CallbackInfo var1) {
      this.getBaritone().ifPresent(var1x -> {
         this.jumpRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.JUMP, this.method_36454(), this.method_36455());
         var1x.getGameEventHandler().onPlayerRotationMove(this.jumpRotationEvent);
      });
   }

   @Redirect(method = "jumpFromGround", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.getYRot()F"))
   private float overrideYaw(class_1309 var1) {
      return var1 instanceof class_746 && BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this) != null
         ? this.jumpRotationEvent.getYaw()
         : var1.method_36454();
   }

   @Inject(
      method = "updateFallFlyingMovement",
      at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.getLookAngle()Lnet/minecraft/world/phys/Vec3;")
   )
   private void onPreElytraMove(class_243 var1, CallbackInfoReturnable<class_243> var2) {
      this.getBaritone().ifPresent(var1x -> {
         this.elytraRotationEvent = new RotationMoveEvent(RotationMoveEvent.Type.MOTION_UPDATE, this.method_36454(), this.method_36455());
         var1x.getGameEventHandler().onPlayerRotationMove(this.elytraRotationEvent);
         this.method_36456(this.elytraRotationEvent.getYaw());
         this.method_36457(this.elytraRotationEvent.getPitch());
      });
   }

   @Inject(
      method = "travelFallFlying",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/LivingEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
         shift = Shift.AFTER
      )
   )
   private void onPostElytraMove(CallbackInfo var1) {
      if (this.elytraRotationEvent != null) {
         this.method_36456(this.elytraRotationEvent.getOriginal().getYaw());
         this.method_36457(this.elytraRotationEvent.getOriginal().getPitch());
         this.elytraRotationEvent = null;
      }
   }

   @Unique
   private Optional<IBaritone> getBaritone() {
      return class_746.class.isInstance(this) ? Optional.ofNullable(BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) : Optional.empty();
   }
}
