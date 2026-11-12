package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RenderCommand extends Command {
   public RenderCommand(Baritone var1) {
      super(var1, "render");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      BetterBlockPos var3 = super.ctx.playerFeet();
      int var4 = (Integer)super.ctx.minecraft().field_1690.method_42503().method_41753() + 1 << 4;
      super.ctx
         .minecraft()
         .field_1769
         .method_18146(var3.x - var4, super.ctx.world().method_31607(), var3.z - var4, var3.x + var4, super.ctx.world().method_31600(), var3.z + var4);
      this.logDirect("Done");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Fix glitched chunks";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The render command fixes glitched chunk rendering without having to reload all of them.", "", "Usage:", "> render");
   }
}
