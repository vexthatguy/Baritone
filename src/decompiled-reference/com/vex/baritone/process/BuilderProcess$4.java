package com.vex.baritone.process;

import com.vex.baritone.api.pathing.goals.GoalGetToBlock;
import net.minecraft.class_2338;

class BuilderProcess$4 extends GoalGetToBlock {
   BuilderProcess$4(class_2338 var1) {
      super(var1);
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return var2 <= super.y && (var1 != super.x || var2 != super.y || var3 != super.z) ? super.isInGoal(var1, var2, var3) : false;
   }
}
