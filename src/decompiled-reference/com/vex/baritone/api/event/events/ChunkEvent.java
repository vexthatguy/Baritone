package com.vex.baritone.api.event.events;

import com.vex.baritone.api.event.events.type.EventState;

public final class ChunkEvent {
   private final EventState state;
   private final ChunkEvent.Type type;
   private final int x;
   private final int z;

   public ChunkEvent(EventState var1, ChunkEvent.Type var2, int var3, int var4) {
      this.state = var1;
      this.type = var2;
      this.x = var3;
      this.z = var4;
   }

   public final EventState getState() {
      return this.state;
   }

   public final ChunkEvent.Type getType() {
      return this.type;
   }

   public final int getX() {
      return this.x;
   }

   public final int getZ() {
      return this.z;
   }

   public final boolean isPostPopulate() {
      return this.state == EventState.POST && this.type.isPopulate();
   }

   public enum Type {
      LOAD,
      UNLOAD,
      POPULATE_FULL,
      POPULATE_PARTIAL;

      public final boolean isPopulate() {
         return this == POPULATE_FULL || this == POPULATE_PARTIAL;
      }
   }
}
