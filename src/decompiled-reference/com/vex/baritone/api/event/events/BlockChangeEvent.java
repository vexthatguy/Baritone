package com.vex.baritone.api.event.events;

import com.vex.baritone.api.utils.Pair;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public final class BlockChangeEvent {
   private final class_1923 chunk;
   private final List<Pair<class_2338, class_2680>> blocks;

   public BlockChangeEvent(class_1923 var1, List<Pair<class_2338, class_2680>> var2) {
      this.chunk = var1;
      this.blocks = var2;
   }

   public final class_1923 getChunkPos() {
      return this.chunk;
   }

   public final List<Pair<class_2338, class_2680>> getBlocks() {
      return this.blocks;
   }
}
