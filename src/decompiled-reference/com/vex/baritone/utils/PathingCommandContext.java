package com.vex.baritone.utils;

import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.pathing.movement.CalculationContext;

public class PathingCommandContext extends PathingCommand {
   public final CalculationContext a;

   public PathingCommandContext(Goal var1, PathingCommandType var2, CalculationContext var3) {
      super(var1, var2);
      this.a = var3;
   }
}
