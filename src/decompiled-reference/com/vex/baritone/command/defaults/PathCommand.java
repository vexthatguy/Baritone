package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.process.ICustomGoalProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PathCommand extends Command {
   public PathCommand(Baritone var1) {
      super(var1, "path");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      ICustomGoalProcess var3 = super.com.vex.baritone.getCustomGoalProcess();
      var2.requireMax(0);
      BaritoneAPI.getProvider().getWorldScanner().repack(super.ctx);
      var3.path();
      this.logDirect("Now pathing");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Start heading towards the goal";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The path command tells Baritone to head towards the current goal.", "", "Usage:", "> path - Start the pathing.");
   }
}
