package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.EventState;
import net.minecraft.class_638;

public final class WorldEvent {
   private final class_638 world;
   private final EventState state;

   public WorldEvent(class_638 var1, EventState var2) {
      this.world = var1;
      this.state = var2;
   }

   public final class_638 getWorld() {
      return this.world;
   }

   public final EventState getState() {
      return this.state;
   }
}
