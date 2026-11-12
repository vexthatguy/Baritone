package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.PacketEvent;
import com.vex.baritone.api.event.events.type.EventState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.Iterator;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_2598;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_2535.class)
public class MixinNetworkManager {
   @Shadow
   private Channel field_11651;
   @Shadow
   @Final
   private class_2598 field_11643;

   @Inject(method = "sendPacket", at = @At("HEAD"))
   private void preDispatchPacket(class_2596<?> var1, ChannelFutureListener var2, boolean var3, CallbackInfo var4) {
      if (this.field_11643 == class_2598.field_11942) {
         Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         while (var5.hasNext()) {
            IBaritone var6;
            if ((var6 = (IBaritone)var5.next()).getPlayerContext().player() != null
               && var6.getPlayerContext().player().field_3944.method_48296() == (class_2535)this) {
               var6.getGameEventHandler().onSendPacket(new PacketEvent((class_2535)this, EventState.PRE, var1));
            }
         }
      }
   }

   @Inject(method = "sendPacket", at = @At("RETURN"))
   private void postDispatchPacket(class_2596<?> var1, ChannelFutureListener var2, boolean var3, CallbackInfo var4) {
      if (this.field_11643 == class_2598.field_11942) {
         Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         while (var5.hasNext()) {
            IBaritone var6;
            if ((var6 = (IBaritone)var5.next()).getPlayerContext().player() != null
               && var6.getPlayerContext().player().field_3944.method_48296() == (class_2535)this) {
               var6.getGameEventHandler().onSendPacket(new PacketEvent((class_2535)this, EventState.POST, var1));
            }
         }
      }
   }

   @Inject(
      method = "channelRead0",
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/network/Connection.genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"
      )
   )
   private void preProcessPacket(ChannelHandlerContext var1, class_2596<?> var2, CallbackInfo var3) {
      if (this.field_11643 == class_2598.field_11942) {
         Iterator var4 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         while (var4.hasNext()) {
            IBaritone var5;
            if ((var5 = (IBaritone)var4.next()).getPlayerContext().player() != null
               && var5.getPlayerContext().player().field_3944.method_48296() == (class_2535)this) {
               var5.getGameEventHandler().onReceivePacket(new PacketEvent((class_2535)this, EventState.PRE, var2));
            }
         }
      }
   }

   @Inject(method = "channelRead0", at = @At("RETURN"))
   private void postProcessPacket(ChannelHandlerContext var1, class_2596<?> var2, CallbackInfo var3) {
      if (this.field_11651.isOpen() && this.field_11643 == class_2598.field_11942) {
         Iterator var4 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         while (var4.hasNext()) {
            IBaritone var5;
            if ((var5 = (IBaritone)var4.next()).getPlayerContext().player() != null
               && var5.getPlayerContext().player().field_3944.method_48296() == (class_2535)this) {
               var5.getGameEventHandler().onReceivePacket(new PacketEvent((class_2535)this, EventState.POST, var2));
            }
         }
      }
   }
}
