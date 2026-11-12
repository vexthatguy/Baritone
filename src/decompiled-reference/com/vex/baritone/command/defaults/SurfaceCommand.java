package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_2189;

public class SurfaceCommand extends Command {
   protected SurfaceCommand(Baritone var1) {
      super(var1, "surface", "top");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      BetterBlockPos var5 = super.ctx.playerFeet();
      int var7 = super.ctx.world().method_8615();
      int var3 = super.ctx.world().method_31605();
      if (var5.method_10264() > var7 && super.ctx.world().method_8320(var5.above()).method_26204() instanceof class_2189) {
         this.logDirect("Already at surface");
      } else {
         for (int var8 = Math.max(var5.method_10264(), var7); var8 < var3; var8++) {
            BetterBlockPos var4 = new BetterBlockPos(var5.method_10263(), var8, var5.method_10260());
            if (!(super.ctx.world().method_8320(var4).method_26204() instanceof class_2189) && var4.method_10264() > var5.method_10264()) {
               GoalBlock var6 = new GoalBlock(var4.above());
               this.logDirect(String.format("Going to: %s", var6.toString()));
               super.com.vex.baritone.getCustomGoalProcess().setGoalAndPath(var6);
               return;
            }
         }

         this.logDirect("No higher location found");
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Used to get out of caves, mines, ...";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The surface/top command tells Baritone to head towards the closest surface-like area.",
         "",
         "This can be the surface or the highest available air space, depending on circumstances.",
         "",
         "Usage:",
         "> surface - Used to get out of caves, mines, ...",
         "> top - Used to get out of caves, mines, ..."
      );
   }
}
