package com.vex.baritone.process;

import com.vex.baritone.api.pathing.goals.GoalRunAway;
import net.minecraft.class_2338;

class MineProcess$1 extends GoalRunAway {
   MineProcess$1(Integer var1, class_2338... var2) {
      super(1.0, var1, var2);
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return false;
   }

   @Override
   public double heuristic() {
      return Double.NEGATIVE_INFINITY;
   }
}
