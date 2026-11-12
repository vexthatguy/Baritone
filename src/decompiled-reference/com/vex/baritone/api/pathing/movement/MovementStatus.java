package com.vex.baritone.api.pathing.movement;

public enum MovementStatus {
   PREPPING(false),
   WAITING(false),
   RUNNING(false),
   SUCCESS(true),
   UNREACHABLE(true),
   FAILED(true),
   CANCELED(true);

   private final boolean complete;

   MovementStatus(boolean var3) {
      this.complete = var3;
   }

   public final boolean isComplete() {
      return this.complete;
   }
}
