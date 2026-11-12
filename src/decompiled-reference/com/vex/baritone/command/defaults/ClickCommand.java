package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClickCommand extends Command {
   public ClickCommand(Baritone var1) {
      super(var1, "click");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.com.vex.baritone.openClick();
      this.logDirect("aight dude");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Open click";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Opens click dude", "", "Usage:", "> click");
   }
}
