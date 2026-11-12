package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.ForBlockOptionalMeta;
import com.vex.baritone.api.command.datatypes.RelativeCoordinate;
import com.vex.baritone.api.command.datatypes.RelativeGoal;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.BlockOptionalMeta;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GotoCommand extends Command {
   protected GotoCommand(Baritone var1) {
      super(var1, "goto");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      if (var2.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
         var2.requireMax(3);
         BetterBlockPos var4 = super.ctx.playerFeet();
         Goal var5 = var2.getDatatypePost(RelativeGoal.INSTANCE, var4);
         this.logDirect(String.format("Going to: %s", var5.toString()));
         super.com.vex.baritone.getCustomGoalProcess().setGoalAndPath(var5);
      } else {
         var2.requireMax(1);
         BlockOptionalMeta var3 = var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
         super.com.vex.baritone.getGetToBlockProcess().getToBlock(var3);
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      var2.requireMax(1);
      return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
   }

   @Override
   public String getShortDesc() {
      return "Go to a coordinate or block";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The goto command tells Baritone to head towards a given goal or block.",
         "",
         "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.",
         "",
         "Usage:",
         "> goto <block> - Go to a block, wherever it is in the world",
         "> goto <y> - Go to a Y level",
         "> goto <x> <z> - Go to an X,Z position",
         "> goto <x> <y> <z> - Go to an X,Y,Z position"
      );
   }
}
