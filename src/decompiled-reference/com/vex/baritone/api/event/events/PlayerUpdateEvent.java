package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.EventState;

public final class PlayerUpdateEvent {
   private final EventState state;

   public PlayerUpdateEvent(EventState var1) {
      this.state = var1;
   }

   public final EventState getState() {
      return this.state;
   }
}
