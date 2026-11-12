package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ExecutionControlCommands$3 extends Command {
   ExecutionControlCommands$3(Baritone var1, String[] var2, boolean... var3) {
      this.a = var3;
      super(var1, var2);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.com.vex.baritone.getBuilderProcess().resume();
      if (!this.a[0]) {
         throw new CommandInvalidStateException("Not paused");
      }

      this.a[0] = false;
      this.logDirect("Resumed");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Resumes Baritone after a pause";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The resume command tells Baritone to resume whatever it was doing when you last used pause.", "", "Usage:", "> resume");
   }
}
