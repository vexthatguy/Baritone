package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.IPlayerContext;

public abstract class BaritoneProcessHelper implements IBaritoneProcess, Helper {
   public final Baritone a;
   public final IPlayerContext a;

   public BaritoneProcessHelper(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }

   @Override
   public boolean isTemporary() {
      return false;
   }
}
