package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.Cancellable;

public final class ChatEvent extends Cancellable {
   private final String message;

   public ChatEvent(String var1) {
      this.message = var1;
   }

   public final String getMessage() {
      return this.message;
   }
}
