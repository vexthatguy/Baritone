package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ThisWayCommand extends Command {
   public ThisWayCommand(Baritone var1) {
      super(var1, "thisway", "forward");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireExactly(1);
      GoalXZ var3 = GoalXZ.fromDirection(super.ctx.playerFeetAsVec(), super.ctx.player().method_5791(), var2.getAs(Double.class));
      super.com.vex.baritone.getCustomGoalProcess().setGoal(var3);
      this.logDirect(String.format("Goal: %s", var3));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Travel in your current direction";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "Creates a GoalXZ some amount of blocks in the direction you're currently looking",
         "",
         "Usage:",
         "> thisway <distance> - makes a GoalXZ distance blocks in front of you"
      );
   }
}
