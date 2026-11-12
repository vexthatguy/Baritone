package com.vex.baritone.api.event.events;

public final class SprintStateEvent {
   private Boolean state;

   public final void setState(boolean var1) {
      this.state = var1;
   }

   public final Boolean getState() {
      return this.state;
   }
}
