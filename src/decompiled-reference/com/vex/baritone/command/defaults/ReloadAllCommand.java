package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReloadAllCommand extends Command {
   public ReloadAllCommand(Baritone var1) {
      super(var1, "reloadall");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.ctx.worldData().getCachedWorld().reloadAllFromDisk();
      this.logDirect("Reloaded");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Reloads Baritone's cache for this world";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("The reloadall command reloads Baritone's world cache.", "", "Usage:", "> reloadall");
   }
}
