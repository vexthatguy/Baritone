package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.ForBlockOptionalMeta;
import com.vex.baritone.api.utils.BlockOptionalMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MineCommand extends Command {
   public MineCommand(Baritone var1) {
      super(var1, "mine");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      int var4 = var2.getAsOrDefault(Integer.class, 0);
      var2.requireMin(1);
      ArrayList var3 = new ArrayList();

      while (var2.hasAny()) {
         var3.add(var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
      }

      BaritoneAPI.getProvider().getWorldScanner().repack(super.ctx);
      this.logDirect(String.format("Mining %s", var3.toString()));
      super.com.vex.baritone.getMineProcess().mine(var4, var3.toArray(new BlockOptionalMeta[0]));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      var2.getAsOrDefault(Integer.class, 0);

      while (var2.has(2)) {
         var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
      }

      return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
   }

   @Override
   public String getShortDesc() {
      return "Mine some blocks";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The mine command allows you to tell Baritone to search for and mine individual blocks.",
         "",
         "The specified blocks can be ores, or any other block.",
         "",
         "Also see the legitMine settings (see #set l legitMine).",
         "",
         "Usage:",
         "> mine diamond_ore - Mines all diamonds it can find."
      );
   }
}
