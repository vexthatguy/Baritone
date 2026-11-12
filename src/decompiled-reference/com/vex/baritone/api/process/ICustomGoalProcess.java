package com.vex.baritone.api.process;

import com.vex.baritone.api.pathing.goals.Goal;

public interface ICustomGoalProcess extends IBaritoneProcess {
   void setGoal(Goal var1);

   void path();

   Goal getGoal();

   Goal mostRecentGoal();

   default void setGoalAndPath(Goal var1) {
      this.setGoal(var1);
      this.path();
   }
}
