package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GcCommand extends Command {
   public GcCommand(Baritone var1) {
      super(var1, "gc");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      System.gc();
      this.logDirect("ok called System.gc()");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Call System.gc()";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Calls System.gc().", "", "Usage:", "> gc");
   }
}
