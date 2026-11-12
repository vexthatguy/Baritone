package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.pathing.calc.IPathingControlManager;
import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.process.PathingCommand;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProcCommand extends Command {
   public ProcCommand(Baritone var1) {
      super(var1, "proc");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      IPathingControlManager var3;
      IBaritoneProcess var4;
      if ((var4 = (var3 = super.com.vex.baritone.getPathingControlManager()).mostRecentInControl().orElse(null)) == null) {
         throw new CommandInvalidStateException("No process in control");
      }

      this.logDirect(
         String.format(
            "Class: %s\nPriority: %f\nTemporary: %b\nDisplay name: %s\nLast command: %s",
            var4.getClass().getTypeName(),
            var4.priority(),
            var4.isTemporary(),
            var4.displayName(),
            var3.mostRecentCommand().map(PathingCommand::toString).orElse("None")
         )
      );
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "View process state information";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The proc command provides miscellaneous information about the process currently controlling Baritone.",
         "",
         "You are not expected to understand this if you aren't familiar with how Baritone works.",
         "",
         "Usage:",
         "> proc - View process information, if present"
      );
   }
}
