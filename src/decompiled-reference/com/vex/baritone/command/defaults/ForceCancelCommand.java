package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.behavior.IPathingBehavior;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ForceCancelCommand extends Command {
   public ForceCancelCommand(Baritone var1) {
      super(var1, "forcecancel");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      IPathingBehavior var3;
      (var3 = super.com.vex.baritone.getPathingBehavior()).cancelEverything();
      var3.forceCancel();
      this.logDirect("ok force canceled");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Force cancel";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Like cancel, but more forceful.", "", "Usage:", "> forcecancel");
   }
}
