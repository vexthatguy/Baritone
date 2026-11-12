package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.pathing.goals.GoalYLevel;

class ExploreProcess$1 extends GoalXZ {
   ExploreProcess$1(int var1, int var2) {
      super(var1, var2);
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      return super.heuristic(var1, var2, var3) + GoalYLevel.calculate(Baritone.a().exploreMaintainY.value, var2);
   }
}
