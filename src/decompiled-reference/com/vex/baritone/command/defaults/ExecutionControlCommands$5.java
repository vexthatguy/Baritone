package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ExecutionControlCommands$5 extends Command {
   ExecutionControlCommands$5(Baritone var1, String[] var2, boolean... var3) {
      this.a = var3;
      super(var1, var2);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      if (this.a[0]) {
         this.a[0] = false;
      }

      super.com.vex.baritone.getPathingBehavior().cancelEverything();
      this.logDirect("ok canceled");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Cancel what Baritone is currently doing";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The cancel command tells Baritone to stop whatever it's currently doing.", "", "Usage:", "> cancel");
   }
}
