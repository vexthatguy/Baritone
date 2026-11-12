package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.ForWaypoints;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FarmCommand extends Command {
   public FarmCommand(Baritone var1) {
      super(var1, "farm");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(2);
      int var4 = 0;
      BetterBlockPos var3 = null;
      if (var2.has(1)) {
         var4 = var2.getAs(Integer.class);
      }

      if (var2.has(1)) {
         IWaypoint[] var5;
         switch ((var5 = var2.getDatatypeFor(ForWaypoints.INSTANCE)).length) {
            case 0:
               throw new CommandInvalidStateException("No waypoints found");
            case 1:
               IWaypoint var6 = var5[0];
               var3 = var6.getLocation();
               break;
            default:
               throw new CommandInvalidStateException("Multiple waypoints were found");
         }
      }

      super.com.vex.baritone.getFarmProcess().farm(var4, var3);
      this.logDirect("Farming");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Farm nearby crops";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The farm command starts farming nearby plants. It harvests mature crops and plants new ones.",
         "",
         "Usage:",
         "> farm - farms every crop it can find.",
         "> farm <range> - farm crops within range from the starting position.",
         "> farm <range> <waypoint> - farm crops within range from waypoint."
      );
   }
}
