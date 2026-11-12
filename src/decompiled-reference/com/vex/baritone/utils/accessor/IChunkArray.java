package com.vex.baritone.utils.accessor;

import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.class_2818;

public interface IChunkArray {
   void copyFrom(IChunkArray var1);

   AtomicReferenceArray<class_2818> getChunks();

   int centerX();

   int centerZ();

   int viewDistance();
}
