package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalStrictDirection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public class TunnelCommand extends Command {
   public TunnelCommand(Baritone var1) {
      super(var1, "tunnel");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(3);
      if (!var2.hasExactly(3)) {
         GoalStrictDirection var9 = new GoalStrictDirection(super.ctx.playerFeet(), super.ctx.player().method_5735());
         super.com.vex.baritone.getCustomGoalProcess().setGoalAndPath(var9);
         this.logDirect(String.format("Goal: %s", var9.toString()));
      } else {
         boolean var6 = true;
         int var3 = Integer.parseInt(var2.getArgs().get(0).getValue());
         int var4 = Integer.parseInt(var2.getArgs().get(1).getValue());
         int var10 = Integer.parseInt(var2.getArgs().get(2).getValue());
         if (var4 <= 0 || var3 < 2 || var10 <= 0 || var3 > super.ctx.world().method_31600()) {
            this.logDirect("Width and depth must at least be 1 block; Height must at least be 2 blocks, and cannot be greater than the build limit.");
            var6 = false;
         }

         if (var6) {
            var3--;
            var4--;
            class_2350 var7 = super.ctx.player().method_5735();
            int var5 = var4 % 2 == 0 ? 0 : 1;
            class_2338 var8;
            class_2338 var13;
            switch (var7) {
               case field_11034:
                  var8 = new class_2338(super.ctx.playerFeet().x, super.ctx.playerFeet().y, super.ctx.playerFeet().z - var4 / 2);
                  var13 = new class_2338(super.ctx.playerFeet().x + var10, super.ctx.playerFeet().y + var3, super.ctx.playerFeet().z + var4 / 2 + var5);
                  break;
               case field_11039:
                  var8 = new class_2338(super.ctx.playerFeet().x, super.ctx.playerFeet().y, super.ctx.playerFeet().z + var4 / 2 + var5);
                  var13 = new class_2338(super.ctx.playerFeet().x - var10, super.ctx.playerFeet().y + var3, super.ctx.playerFeet().z - var4 / 2);
                  break;
               case field_11043:
                  var8 = new class_2338(super.ctx.playerFeet().x - var4 / 2, super.ctx.playerFeet().y, super.ctx.playerFeet().z);
                  var13 = new class_2338(super.ctx.playerFeet().x + var4 / 2 + var5, super.ctx.playerFeet().y + var3, super.ctx.playerFeet().z - var10);
                  break;
               case field_11035:
                  var8 = new class_2338(super.ctx.playerFeet().x + var4 / 2 + var5, super.ctx.playerFeet().y, super.ctx.playerFeet().z);
                  var13 = new class_2338(super.ctx.playerFeet().x - var4 / 2, super.ctx.playerFeet().y + var3, super.ctx.playerFeet().z + var10);
                  break;
               default:
                  throw new IllegalStateException("Unexpected value: " + var7);
            }

            this.logDirect(String.format("Creating a tunnel %s block(s) high, %s block(s) wide, and %s block(s) deep", var3 + 1, var4 + 1, var10));
            super.com.vex.baritone.getBuilderProcess().clearArea(var8, var13);
         }
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Set a goal to tunnel in your current direction";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The tunnel command sets a goal that tells Baritone to mine completely straight in the direction that you're facing.",
         "",
         "Usage:",
         "> tunnel - No arguments, mines in a 1x2 radius.",
         "> tunnel <height> <width> <depth> - Tunnels in a user defined height, width and depth."
      );
   }
}
