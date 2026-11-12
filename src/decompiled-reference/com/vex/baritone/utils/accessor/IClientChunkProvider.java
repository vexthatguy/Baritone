package com.vex.baritone.utils.accessor;

import net.minecraft.class_631;

public interface IClientChunkProvider {
   class_631 createThreadSafeCopy();

   IChunkArray extractReferenceArray();
}
