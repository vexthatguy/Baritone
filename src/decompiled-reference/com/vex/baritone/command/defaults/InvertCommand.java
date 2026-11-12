package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalInverted;
import com.vex.baritone.api.process.ICustomGoalProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class InvertCommand extends Command {
   public InvertCommand(Baritone var1) {
      super(var1, "invert");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      ICustomGoalProcess var3;
      Goal var4;
      if ((var4 = (var3 = super.com.vex.baritone.getCustomGoalProcess()).getGoal()) == null) {
         throw new CommandInvalidStateException("No goal");
      }

      Goal var5;
      if (var4 instanceof GoalInverted) {
         var5 = ((GoalInverted)var4).origin;
      } else {
         var5 = new GoalInverted(var4);
      }

      var3.setGoalAndPath(var5);
      this.logDirect(String.format("Goal: %s", var5.toString()));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Run away from the current goal";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The invert command tells Baritone to head away from the current goal rather than towards it.", "", "Usage:", "> invert - Invert the current goal."
      );
   }
}
