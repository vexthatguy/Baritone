package com.vex.baritone.api.event.events;

import net.minecraft.class_2338;

public final class BlockInteractEvent {
   private final class_2338 pos;
   private final BlockInteractEvent.Type type;

   public BlockInteractEvent(class_2338 var1, BlockInteractEvent.Type var2) {
      this.pos = var1;
      this.type = var2;
   }

   public final class_2338 getPos() {
      return this.pos;
   }

   public final BlockInteractEvent.Type getType() {
      return this.type;
   }

   public enum Type {
      START_BREAK,
      USE;
   }
}
