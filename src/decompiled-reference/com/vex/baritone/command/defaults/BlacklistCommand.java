package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.process.IGetToBlockProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BlacklistCommand extends Command {
   public BlacklistCommand(Baritone var1) {
      super(var1, "blacklist");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      IGetToBlockProcess var3;
      if (!(var3 = super.com.vex.baritone.getGetToBlockProcess()).isActive()) {
         throw new CommandInvalidStateException("GetToBlockProcess is not currently active");
      }

      if (var3.blacklistClosest()) {
         this.logDirect("Blacklisted closest instances");
      } else {
         throw new CommandInvalidStateException("No known locations, unable to blacklist");
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Blacklist closest block";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "While going to a block this command blacklists the closest block so that Baritone won't attempt to get to it.", "", "Usage:", "> blacklist"
      );
   }
}
