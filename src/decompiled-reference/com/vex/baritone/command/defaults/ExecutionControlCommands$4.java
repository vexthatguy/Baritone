package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ExecutionControlCommands$4 extends Command {
   ExecutionControlCommands$4(Baritone var1, String[] var2, boolean... var3) {
      this.a = var3;
      super(var1, var2);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      this.logDirect(String.format("Baritone is %spaused", this.a[0] ? "" : "not "));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Tells you if Baritone is paused";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The paused command tells you if Baritone is currently paused by use of the pause command.", "", "Usage:", "> paused");
   }
}
