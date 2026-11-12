package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.PlayerUpdateEvent;
import com.vex.baritone.api.event.events.SprintStateEvent;
import com.vex.baritone.api.event.events.type.EventState;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.behavior.LookBehavior;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import net.minecraft.class_10185;
import net.minecraft.class_1656;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_746.class)
public class MixinClientPlayerEntity {
   @Unique
   private static final MethodHandle MAY_FLY = baritone$resolveMayFly();

   @Unique
   private static MethodHandle baritone$resolveMayFly() {
      try {
         return MethodHandles.publicLookup().findVirtual(class_746.class, "mayFly", MethodType.methodType(boolean.class));
      } catch (NoSuchMethodException var1) {
         return null;
      } catch (IllegalAccessException var2) {
         throw new RuntimeException(var2);
      }
   }

   @Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/client/player/AbstractClientPlayer.tick()V", shift = Shift.AFTER))
   private void onPreUpdate(CallbackInfo var1) {
      IBaritone var2;
      if ((var2 = BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) != null) {
         var2.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.PRE));
      }
   }

   @Redirect(method = "aiStep", at = @At(value = "FIELD", target = "net/minecraft/world/entity/player/Abilities.mayfly:Z"))
   @Group(name = "mayFly", min = 1, max = 1)
   private boolean isAllowFlying(class_1656 var1) {
      IBaritone var2;
      return (var2 = BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) == null
         ? var1.field_7478
         : !var2.getPathingBehavior().isPathing() && var1.field_7478;
   }

   @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;mayFly()Z"))
   @Group(name = "mayFly", min = 1, max = 1)
   private boolean onMayFlyNeoforge(class_746 var1) {
      IBaritone var2;
      return (var2 = BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) == null
         ? (boolean)MAY_FLY.invokeExact((class_746)var1)
         : !var2.getPathingBehavior().isPathing() && (boolean)MAY_FLY.invokeExact((class_746)var1);
   }

   @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Input;sprint()Z"))
   private boolean redirectSprintInput(class_10185 var1) {
      IBaritone var2;
      if ((var2 = BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) == null) {
         return var1.comp_3165();
      } else {
         SprintStateEvent var3 = new SprintStateEvent();
         var2.getGameEventHandler().onPlayerSprintState(var3);
         if (var3.getState() != null) {
            return var3.getState();
         } else {
            return var2 != BaritoneAPI.getProvider().getPrimaryBaritone() ? false : var1.comp_3165();
         }
      }
   }

   @Inject(method = "rideTick", at = @At("HEAD"))
   private void updateRidden(CallbackInfo var1) {
      IBaritone var3;
      LookBehavior var4;
      if ((var3 = BaritoneAPI.getProvider().getBaritoneForPlayer((class_746)this)) != null && (var4 = (LookBehavior)var3.getLookBehavior()).a != null) {
         Rotation var2 = var4.a.peekRotation(var4.a.a);
         var4.a.player().method_36456(var2.getYaw());
      }
   }

   @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;tryToStartFallFlying()Z"))
   private boolean tryToStartFallFlying(class_746 var1) {
      IBaritone var2;
      return (var2 = BaritoneAPI.getProvider().getBaritoneForPlayer(var1)) != null && var2.getPathingBehavior().isPathing() ? false : var1.method_23668();
   }
}
