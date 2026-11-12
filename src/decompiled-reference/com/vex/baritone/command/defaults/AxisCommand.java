package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalAxis;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AxisCommand extends Command {
   public AxisCommand(Baritone var1) {
      super(var1, "axis", "highway");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      GoalAxis var3 = new GoalAxis();
      super.com.vex.baritone.getCustomGoalProcess().setGoal(var3);
      this.logDirect(String.format("Goal: %s", var3.toString()));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Set a goal to the axes";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The axis command sets a goal that tells Baritone to head towards the nearest axis. That is, X=0 or Z=0.", "", "Usage:", "> axis");
   }
}
