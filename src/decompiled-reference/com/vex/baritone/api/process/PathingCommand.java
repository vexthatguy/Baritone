package com.vex.baritone.api.process;

import com.vex.baritone.api.pathing.goals.Goal;
import java.util.Objects;

public class PathingCommand {
   public final Goal goal;
   public final PathingCommandType commandType;

   public PathingCommand(Goal var1, PathingCommandType var2) {
      Objects.requireNonNull(var2);
      this.goal = var1;
      this.commandType = var2;
   }

   @Override
   public String toString() {
      return this.commandType + " " + this.goal;
   }
}
