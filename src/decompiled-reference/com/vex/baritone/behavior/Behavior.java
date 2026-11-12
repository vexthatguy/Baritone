package com.vex.baritone.behavior;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.behavior.IBehavior;
import com.vex.baritone.api.utils.IPlayerContext;

public class Behavior implements IBehavior {
   public final Baritone a;
   public final IPlayerContext a;

   public Behavior(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }
}
