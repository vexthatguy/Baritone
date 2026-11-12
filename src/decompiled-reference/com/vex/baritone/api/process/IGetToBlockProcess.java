package com.vex.baritone.api.process;

import com.vex.baritone.api.utils.BlockOptionalMeta;
import net.minecraft.class_2248;

public interface IGetToBlockProcess extends IBaritoneProcess {
   void getToBlock(BlockOptionalMeta var1);

   default void getToBlock(class_2248 var1) {
      this.getToBlock(new BlockOptionalMeta(var1));
   }

   boolean blacklistClosest();
}
