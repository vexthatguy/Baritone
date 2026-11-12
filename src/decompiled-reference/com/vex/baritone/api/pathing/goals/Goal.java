package com.vex.baritone.api.pathing.goals;

import net.minecraft.class_2338;

public interface Goal {
   boolean isInGoal(int var1, int var2, int var3);

   double heuristic(int var1, int var2, int var3);

   default boolean isInGoal(class_2338 var1) {
      return this.isInGoal(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   default double heuristic(class_2338 var1) {
      return this.heuristic(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   default double heuristic() {
      return 0.0;
   }
}
