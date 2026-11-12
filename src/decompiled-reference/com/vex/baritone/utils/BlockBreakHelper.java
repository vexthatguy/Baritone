package com.vex.baritone.utils;

import com.vex.baritone.api.utils.IPlayerContext;

public final class BlockBreakHelper {
   final IPlayerContext a;
   boolean a;
   int a = 0;

   BlockBreakHelper(IPlayerContext var1) {
      this.a = var1;
   }

   public final void a() {
      if (this.a.player() != null && this.a) {
         this.a.playerController().setHittingBlock(false);
         this.a.playerController().resetBlockRemoving();
         this.a = false;
      }
   }
}
