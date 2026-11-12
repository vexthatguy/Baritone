package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.PlayerUpdateEvent;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.events.WorldEvent;
import com.vex.baritone.api.event.events.type.EventState;
import java.util.Iterator;
import java.util.function.BiFunction;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_638;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_310.class)
public class MixinMinecraft {
   @Shadow
   public class_746 field_1724;
   @Shadow
   public class_638 field_1687;
   @Unique
   private BiFunction<EventState, TickEvent.Type, TickEvent> tickProvider;

   @Inject(method = "<init>", at = @At("RETURN"))
   private void postInit(CallbackInfo var1) {
      BaritoneAPI.getProvider().getPrimaryBaritone();
   }

   @Inject(
      method = "tick",
      at = @At(
         value = "FIELD",
         opcode = 180,
         target = "net/minecraft/client/Minecraft.screen:Lnet/minecraft/client/gui/screens/Screen;",
         ordinal = 0,
         shift = Shift.BEFORE
      ),
      slice = @Slice(from = @At(value = "FIELD", opcode = 181, target = "net/minecraft/client/Minecraft.missTime:I"))
   )
   private void runTick(CallbackInfo var1) {
      this.tickProvider = TickEvent.createNextProvider();
      Iterator var4 = BaritoneAPI.getProvider().getAllBaritones().iterator();

      while (var4.hasNext()) {
         IBaritone var2;
         TickEvent.Type var3 = (var2 = (IBaritone)var4.next()).getPlayerContext().player() != null && var2.getPlayerContext().world() != null
            ? TickEvent.Type.IN
            : TickEvent.Type.OUT;
         var2.getGameEventHandler().onTick(this.tickProvider.apply(EventState.PRE, var3));
      }
   }

   @Inject(method = "tick", at = @At("RETURN"))
   private void postRunTick(CallbackInfo var1) {
      if (this.tickProvider != null) {
         Iterator var4 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         while (var4.hasNext()) {
            IBaritone var2;
            TickEvent.Type var3 = (var2 = (IBaritone)var4.next()).getPlayerContext().player() != null && var2.getPlayerContext().world() != null
               ? TickEvent.Type.IN
               : TickEvent.Type.OUT;
            var2.getGameEventHandler().onPostTick(this.tickProvider.apply(EventState.POST, var3));
         }

         this.tickProvider = null;
      }
   }

   @Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/ClientLevel.tickEntities()V", shift = Shift.AFTER))
   private void postUpdateEntities(CallbackInfo var1) {
      IBaritone var2;
      if ((var2 = BaritoneAPI.getProvider().getBaritoneForPlayer(this.field_1724)) != null) {
         var2.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.POST));
      }
   }

   @Inject(method = "setLevel", at = @At("HEAD"))
   private void preLoadWorld(class_638 var1, CallbackInfo var2) {
      if (this.field_1687 != null || var1 != null) {
         BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(var1, EventState.PRE));
      }
   }

   @Inject(method = "setLevel", at = @At("RETURN"))
   private void postLoadWorld(class_638 var1, CallbackInfo var2) {
      BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(var1, EventState.POST));
   }

   @Redirect(
      method = "tick",
      at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;"),
      slice = @Slice(
         from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;showDebugScreen()Z"),
         to = @At(value = "CONSTANT", args = "stringValue=Keybindings")
      )
   )
   private class_437 passEvents(class_310 var1) {
      return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && this.field_1724 != null ? null : var1.field_1755;
   }
}
