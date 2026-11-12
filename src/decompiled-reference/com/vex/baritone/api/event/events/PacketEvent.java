package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.EventState;
import net.minecraft.class_2535;
import net.minecraft.class_2596;

public final class PacketEvent {
   private final class_2535 networkManager;
   private final EventState state;
   private final class_2596<?> packet;

   public PacketEvent(class_2535 var1, EventState var2, class_2596<?> var3) {
      this.networkManager = var1;
      this.state = var2;
      this.packet = var3;
   }

   public final class_2535 getNetworkManager() {
      return this.networkManager;
   }

   public final EventState getState() {
      return this.state;
   }

   public final class_2596<?> getPacket() {
      return this.packet;
   }

   public final <T extends class_2596<?>> T cast() {
      return (T)this.packet;
   }
}
