package com.vex.baritone.utils.accessor;

public interface IPlayerControllerMP {
   void setIsHittingBlock(boolean var1);

   boolean isHittingBlock();

   void callSyncCurrentPlayItem();

   void setDestroyDelay(int var1);
}
