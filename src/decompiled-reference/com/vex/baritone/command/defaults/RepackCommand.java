package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RepackCommand extends Command {
   public RepackCommand(Baritone var1) {
      super(var1, "repack", "rescan");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      this.logDirect(String.format("Queued %d chunks for repacking", BaritoneAPI.getProvider().getWorldScanner().repack(super.ctx)));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Re-cache chunks";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Repack chunks around you. This basically re-caches them.", "", "Usage:", "> repack - Repack chunks.");
   }
}
