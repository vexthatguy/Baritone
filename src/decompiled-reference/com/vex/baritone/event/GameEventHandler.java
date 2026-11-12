package com.vex.baritone.event;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.BlockChangeEvent;
import com.vex.baritone.api.event.events.BlockInteractEvent;
import com.vex.baritone.api.event.events.ChatEvent;
import com.vex.baritone.api.event.events.ChunkEvent;
import com.vex.baritone.api.event.events.PacketEvent;
import com.vex.baritone.api.event.events.PathEvent;
import com.vex.baritone.api.event.events.PlayerUpdateEvent;
import com.vex.baritone.api.event.events.RenderEvent;
import com.vex.baritone.api.event.events.RotationMoveEvent;
import com.vex.baritone.api.event.events.SprintStateEvent;
import com.vex.baritone.api.event.events.TabCompleteEvent;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.events.WorldEvent;
import com.vex.baritone.api.event.events.type.EventState;
import com.vex.baritone.api.event.listener.IEventBus;
import com.vex.baritone.api.event.listener.IGameEventListener;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.Pair;
import com.vex.baritone.cache.CachedChunk;
import com.vex.baritone.cache.WorldProvider;
import com.vex.baritone.utils.BlockStateInterface;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2818;
import net.minecraft.class_4970.class_4971;

public final class GameEventHandler implements IEventBus, Helper {
   private final Baritone a;
   private final List<IGameEventListener> a = new CopyOnWriteArrayList<>();

   public GameEventHandler(Baritone var1) {
      this.a = var1;
   }

   @Override
   public final void onTick(TickEvent var1) {
      label16: {
         if (var1.getType() == TickEvent.Type.IN) {
            try {
               this.a.a = new BlockStateInterface(this.a.getPlayerContext(), true);
               break label16;
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }

         this.a.a = null;
      }

      this.a.forEach(var1x -> var1x.onTick(var1));
   }

   @Override
   public final void onPostTick(TickEvent var1) {
      this.a.forEach(var1x -> var1x.onPostTick(var1));
   }

   @Override
   public final void onPlayerUpdate(PlayerUpdateEvent var1) {
      this.a.forEach(var1x -> var1x.onPlayerUpdate(var1));
   }

   @Override
   public final void onSendChatMessage(ChatEvent var1) {
      this.a.forEach(var1x -> var1x.onSendChatMessage(var1));
   }

   @Override
   public final void onPreTabComplete(TabCompleteEvent var1) {
      this.a.forEach(var1x -> var1x.onPreTabComplete(var1));
   }

   @Override
   public final void onChunkEvent(ChunkEvent var1) {
      EventState var2 = var1.getState();
      ChunkEvent.Type var3 = var1.getType();
      class_1937 var4 = this.a.getPlayerContext().world();
      boolean var5 = var2 == EventState.PRE && var3 == ChunkEvent.Type.UNLOAD && var4.method_8398().method_12121(var1.getX(), var1.getZ(), null, false) != null;
      if (var1.isPostPopulate() || var5) {
         this.a.a.ifWorldLoaded(var2x -> {
            class_2818 var3x = var4.method_8497(var1.getX(), var1.getZ());
            var2x.getCachedWorld().queueForPacking(var3x);
         });
      }

      this.a.forEach(var1x -> var1x.onChunkEvent(var1));
   }

   @Override
   public final void onBlockChange(BlockChangeEvent var1) {
      if (Baritone.a().repackOnAnyBlockChange.value
         && var1.getBlocks().stream().map(Pair::second).map(class_4971::method_26204).anyMatch(CachedChunk.a::contains)) {
         this.a.a.ifWorldLoaded(var2 -> {
            class_1937 var3 = this.a.getPlayerContext().world();
            class_1923 var4 = var1.getChunkPos();
            var2.getCachedWorld().queueForPacking(var3.method_8497(var4.field_9181, var4.field_9180));
         });
      }

      this.a.forEach(var1x -> var1x.onBlockChange(var1));
   }

   @Override
   public final void onRenderPass(RenderEvent var1) {
      this.a.forEach(var1x -> var1x.onRenderPass(var1));
   }

   @Override
   public final void onWorldEvent(WorldEvent var1) {
      WorldProvider var2 = this.a.a;
      if (var1.getState() == EventState.POST) {
         var2.a();
         if (var1.getWorld() != null) {
            var2.a(var1.getWorld());
         }
      }

      this.a.forEach(var1x -> var1x.onWorldEvent(var1));
   }

   @Override
   public final void onSendPacket(PacketEvent var1) {
      this.a.forEach(var1x -> var1x.onSendPacket(var1));
   }

   @Override
   public final void onReceivePacket(PacketEvent var1) {
      this.a.forEach(var1x -> var1x.onReceivePacket(var1));
   }

   @Override
   public final void onPlayerRotationMove(RotationMoveEvent var1) {
      this.a.forEach(var1x -> var1x.onPlayerRotationMove(var1));
   }

   @Override
   public final void onPlayerSprintState(SprintStateEvent var1) {
      this.a.forEach(var1x -> var1x.onPlayerSprintState(var1));
   }

   @Override
   public final void onBlockInteract(BlockInteractEvent var1) {
      this.a.forEach(var1x -> var1x.onBlockInteract(var1));
   }

   @Override
   public final void onPlayerDeath() {
      this.a.forEach(IGameEventListener::onPlayerDeath);
   }

   @Override
   public final void onPathEvent(PathEvent var1) {
      this.a.forEach(var1x -> var1x.onPathEvent(var1));
   }

   @Override
   public final void registerEventListener(IGameEventListener var1) {
      this.a.add(var1);
   }
}
