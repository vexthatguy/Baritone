package com.vex.baritone.api.behavior;

import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.calc.IPathFinder;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.path.IPathExecutor;
import java.util.Optional;

public interface IPathingBehavior extends IBehavior {
   default Optional<Double> ticksRemainingInSegment() {
      return this.ticksRemainingInSegment(true);
   }

   default Optional<Double> ticksRemainingInSegment(boolean var1) {
      IPathExecutor var2;
      if ((var2 = this.getCurrent()) == null) {
         return Optional.empty();
      }

      var1 = var1 ? var2.getPosition() : var2.getPosition() + 1;
      return Optional.of(var2.getPath().ticksRemainingFrom(var1));
   }

   Optional<Double> estimatedTicksToGoal();

   Goal getGoal();

   boolean isPathing();

   default boolean hasPath() {
      return this.getCurrent() != null;
   }

   boolean cancelEverything();

   void forceCancel();

   default Optional<IPath> getPath() {
      return Optional.ofNullable(this.getCurrent()).map(IPathExecutor::getPath);
   }

   Optional<? extends IPathFinder> getInProgress();

   IPathExecutor getCurrent();

   IPathExecutor getNext();
}
