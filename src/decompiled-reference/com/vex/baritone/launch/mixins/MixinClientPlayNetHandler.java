package com.vex.baritone.launch.mixins;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.BlockChangeEvent;
import com.vex.baritone.api.event.events.ChatEvent;
import com.vex.baritone.api.event.events.ChunkEvent;
import com.vex.baritone.api.event.events.type.EventState;
import com.vex.baritone.api.utils.Pair;
import com.vex.baritone.cache.CachedChunk;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2535;
import net.minecraft.class_2626;
import net.minecraft.class_2637;
import net.minecraft.class_2666;
import net.minecraft.class_2672;
import net.minecraft.class_310;
import net.minecraft.class_5892;
import net.minecraft.class_634;
import net.minecraft.class_746;
import net.minecraft.class_8673;
import net.minecraft.class_8675;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_634.class)
public abstract class MixinClientPlayNetHandler extends class_8673 {
   protected MixinClientPlayNetHandler(class_310 var1, class_2535 var2, class_8675 var3) {
      super(var1, var2, var3);
   }

   @Inject(method = "sendChat(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
   private void sendChatMessage(String var1, CallbackInfo var2) {
      ChatEvent var4 = new ChatEvent(var1);
      IBaritone var3;
      if ((var3 = BaritoneAPI.getProvider().getBaritoneForPlayer(this.field_45588.field_1724)) != null) {
         var3.getGameEventHandler().onSendChatMessage(var4);
         if (var4.isCancelled()) {
            var2.cancel();
         }
      }
   }

   @Inject(method = "handleLevelChunkWithLight", at = @At("RETURN"))
   private void postHandleChunkData(class_2672 var1, CallbackInfo var2) {
      Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

      while (var5.hasNext()) {
         IBaritone var3;
         class_746 var4;
         if ((var4 = (var3 = (IBaritone)var5.next()).getPlayerContext().player()) != null && var4.field_3944 == (class_634)this) {
            var3.getGameEventHandler()
               .onChunkEvent(
                  new ChunkEvent(
                     EventState.POST,
                     !var1.method_11051() ? ChunkEvent.Type.POPULATE_FULL : ChunkEvent.Type.POPULATE_PARTIAL,
                     var1.method_11523(),
                     var1.method_11524()
                  )
               );
         }
      }
   }

   @Inject(method = "handleForgetLevelChunk", at = @At("HEAD"))
   private void preChunkUnload(class_2666 var1, CallbackInfo var2) {
      Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

      while (var5.hasNext()) {
         IBaritone var3;
         class_746 var4;
         if ((var4 = (var3 = (IBaritone)var5.next()).getPlayerContext().player()) != null && var4.field_3944 == (class_634)this) {
            var3.getGameEventHandler()
               .onChunkEvent(new ChunkEvent(EventState.PRE, ChunkEvent.Type.UNLOAD, var1.comp_1726().field_9181, var1.comp_1726().field_9180));
         }
      }
   }

   @Inject(method = "handleForgetLevelChunk", at = @At("RETURN"))
   private void postChunkUnload(class_2666 var1, CallbackInfo var2) {
      Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

      while (var5.hasNext()) {
         IBaritone var3;
         class_746 var4;
         if ((var4 = (var3 = (IBaritone)var5.next()).getPlayerContext().player()) != null && var4.field_3944 == (class_634)this) {
            var3.getGameEventHandler()
               .onChunkEvent(new ChunkEvent(EventState.POST, ChunkEvent.Type.UNLOAD, var1.comp_1726().field_9181, var1.comp_1726().field_9180));
         }
      }
   }

   @Inject(method = "handleBlockUpdate", at = @At("RETURN"))
   private void postHandleBlockChange(class_2626 var1, CallbackInfo var2) {
      if (Baritone.a().repackOnAnyBlockChange.value) {
         if (CachedChunk.a.contains(var1.method_11308().method_26204())) {
            Iterator var5 = BaritoneAPI.getProvider().getAllBaritones().iterator();

            while (var5.hasNext()) {
               IBaritone var3;
               class_746 var4;
               if ((var4 = (var3 = (IBaritone)var5.next()).getPlayerContext().player()) != null && var4.field_3944 == (class_634)this) {
                  var3.getGameEventHandler()
                     .onChunkEvent(
                        new ChunkEvent(
                           EventState.POST, ChunkEvent.Type.POPULATE_FULL, var1.method_11309().method_10263() >> 4, var1.method_11309().method_10260() >> 4
                        )
                     );
               }
            }
         }
      }
   }

   @Inject(method = "handleChunkBlocksUpdate", at = @At("RETURN"))
   private void postHandleMultiBlockChange(class_2637 var1, CallbackInfo var2) {
      IBaritone var4;
      if ((var4 = BaritoneAPI.getProvider().getBaritoneForConnection((class_634)this)) != null) {
         ArrayList var3 = new ArrayList();
         var1.method_30621((var1x, var2x) -> var3.add(new Pair<>(var1x.method_10062(), var2x)));
         if (!var3.isEmpty()) {
            var4.getGameEventHandler().onBlockChange(new BlockChangeEvent(new class_1923((class_2338)((Pair)var3.get(0)).first()), var3));
         }
      }
   }

   @Inject(method = "handlePlayerCombatKill", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;shouldShowDeathScreen()Z"))
   private void onPlayerDeath(class_5892 var1, CallbackInfo var2) {
      Iterator var4 = BaritoneAPI.getProvider().getAllBaritones().iterator();

      while (var4.hasNext()) {
         class_746 var3;
         IBaritone var5;
         if ((var3 = (var5 = (IBaritone)var4.next()).getPlayerContext().player()) != null && var3.field_3944 == (class_634)this) {
            var5.getGameEventHandler().onPlayerDeath();
         }
      }
   }
}
