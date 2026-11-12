package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ExecutionControlCommands$2 extends Command {
   ExecutionControlCommands$2(Baritone var1, String[] var2, boolean... var3) {
      this.a = var3;
      super(var1, var2);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      if (this.a[0]) {
         throw new CommandInvalidStateException("Already paused");
      }

      this.a[0] = true;
      this.logDirect("Paused");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Pauses Baritone until you use resume";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The pause command tells Baritone to temporarily stop whatever it's doing.",
         "",
         "This can be used to pause pathing, building, following, whatever. A single use of the resume command will start it right back up again!",
         "",
         "Usage:",
         "> pause"
      );
   }
}
