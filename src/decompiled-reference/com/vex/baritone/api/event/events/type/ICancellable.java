package com.vex.baritone.api.event.events.type;

public interface ICancellable {
   void cancel();

   boolean isCancelled();
}
