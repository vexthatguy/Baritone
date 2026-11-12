package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ComeCommand extends Command {
   public ComeCommand(Baritone var1) {
      super(var1, "come");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.com.vex.baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(super.ctx.viewerPos()));
      this.logDirect("Coming");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Start heading towards your camera";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The come command tells Baritone to head towards your camera.",
         "",
         "This can be useful in hacked clients where freecam doesn't move your player position.",
         "",
         "Usage:",
         "> come"
      );
   }
}
