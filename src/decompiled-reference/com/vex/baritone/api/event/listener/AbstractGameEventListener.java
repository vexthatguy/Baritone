package com.vex.baritone.api.event.listener;

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

public interface AbstractGameEventListener extends IGameEventListener {
   @Override
   default void onTick(TickEvent var1) {
   }

   @Override
   default void onPostTick(TickEvent var1) {
   }

   @Override
   default void onPlayerUpdate(PlayerUpdateEvent var1) {
   }

   @Override
   default void onSendChatMessage(ChatEvent var1) {
   }

   @Override
   default void onPreTabComplete(TabCompleteEvent var1) {
   }

   @Override
   default void onChunkEvent(ChunkEvent var1) {
   }

   @Override
   default void onBlockChange(BlockChangeEvent var1) {
   }

   @Override
   default void onRenderPass(RenderEvent var1) {
   }

   @Override
   default void onWorldEvent(WorldEvent var1) {
   }

   @Override
   default void onSendPacket(PacketEvent var1) {
   }

   @Override
   default void onReceivePacket(PacketEvent var1) {
   }

   @Override
   default void onPlayerRotationMove(RotationMoveEvent var1) {
   }

   @Override
   default void onPlayerSprintState(SprintStateEvent var1) {
   }

   @Override
   default void onBlockInteract(BlockInteractEvent var1) {
   }

   @Override
   default void onPlayerDeath() {
   }

   @Override
   default void onPathEvent(PathEvent var1) {
   }
}
