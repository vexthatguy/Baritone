package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.Cancellable;

public final class TabCompleteEvent extends Cancellable {
   public final String prefix;
   public String[] completions;

   public TabCompleteEvent(String var1) {
      this.prefix = var1;
      this.completions = null;
   }
}
