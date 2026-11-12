package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.listener.AbstractGameEventListener;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.behavior.PathingBehavior;
import com.vex.baritone.pathing.path.PathExecutor;

class PathingControlManager$1 implements AbstractGameEventListener {
   PathingControlManager$1(PathingControlManager var1) {
      this.a = var1;
      super();
   }

   @Override
   public void onTick(TickEvent var1) {
      if (var1.getType() == TickEvent.Type.IN) {
         PathingControlManager var5 = this.a;
         if (this.a.a != null) {
            PathingBehavior var2 = var5.a.a;
            switch (var5.a.commandType) {
               case FORCE_REVALIDATE_GOAL_AND_PATH:
                  label34: {
                     if (var5.a.goal != null) {
                        Goal var4 = var5.a.goal;
                        PathExecutor var3 = var5.a.a.a;
                        if ((var5.a.a.a != null && !var4.isInGoal(var3.getPath().getDest()) ? var4.equals(var3.getPath().getGoal()) : true)
                           && !var5.a(var5.a.goal)) {
                           break label34;
                        }
                     }

                     var2.a();
                  }

                  var2.a(var5.a);
                  return;
               case REVALIDATE_GOAL_AND_PATH:
                  if (Baritone.a().cancelOnGoalInvalidation.value && (var5.a.goal == null || var5.a(var5.a.goal))) {
                     var2.a();
                  }

                  var2.a(var5.a);
            }
         }
      }
   }
}
