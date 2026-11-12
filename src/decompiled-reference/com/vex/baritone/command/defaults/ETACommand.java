package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.behavior.IPathingBehavior;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ETACommand extends Command {
   public ETACommand(Baritone var1) {
      super(var1, "eta");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      if (super.com.vex.baritone.getPathingControlManager().mostRecentInControl().orElse(null) == null) {
         throw new CommandInvalidStateException("No process in control");
      }

      IPathingBehavior var7;
      double var3 = (var7 = super.com.vex.baritone.getPathingBehavior()).ticksRemainingInSegment().orElse(Double.NaN);
      double var5 = var7.estimatedTicksToGoal().orElse(Double.NaN);
      this.logDirect(String.format("Next segment: %.1fs (%.0f ticks)\nGoal: %.1fs (%.0f ticks)", var3 / 20.0, var3, var5 / 20.0, var5));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "View the current ETA";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The ETA command provides information about the estimated time until the next segment.",
         "and the goal",
         "",
         "Be aware that the ETA to your goal is really unprecise",
         "",
         "Usage:",
         "> eta - View ETA, if present"
      );
   }
}
