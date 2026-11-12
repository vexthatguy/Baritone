package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.BlockById;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.cache.CachedChunk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_5250;
import net.minecraft.class_7923;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2568.class_10613;

public class FindCommand extends Command {
   public FindCommand(Baritone var1) {
      super(var1, "find");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMin(1);
      ArrayList var3 = new ArrayList();

      while (var2.hasAny()) {
         var3.add(var2.getDatatypeFor(BlockById.INSTANCE));
      }

      BetterBlockPos var5 = super.ctx.playerFeet();
      class_2561[] var4;
      if ((
            var4 = var3.stream()
               .flatMap(
                  var2x -> super.ctx
                     .worldData()
                     .getCachedWorld()
                     .getLocationsOf(class_7923.field_41175.method_10221(var2x).method_12832(), Integer.MAX_VALUE, var5.x, var5.y, 4)
                     .stream()
               )
               .map(BetterBlockPos::new)
               .map(this::a)
               .toArray(class_2561[]::new)
         ).length
         > 0) {
         Arrays.asList(var4).forEach(var1x -> this.logDirect(var1x));
      } else {
         this.logDirect("No positions known, are you sure the blocks are cached?");
      }
   }

   private class_2561 a(BetterBlockPos var1) {
      String var2 = String.format("%s %s %s", var1.x, var1.y, var1.z);
      String var3 = String.format("%sgoal %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var2);
      class_5250 var5 = class_2561.method_43470(var1.toString());
      class_5250 var4 = class_2561.method_43470("Click to set goal to this position");
      var5.method_10862(
         var5.method_10866().method_10977(class_124.field_1080).method_10975(var2).method_10958(new class_10609(var3)).method_10949(new class_10613(var4))
      );
      return var5;
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return new TabCompleteHelper()
         .append(CachedChunk.a.stream().<class_2960>map(class_7923.field_41175::method_10221).map(Object::toString))
         .filterPrefixNamespaced(var2.getString())
         .sortAlphabetically()
         .stream();
   }

   @Override
   public String getShortDesc() {
      return "Find positions of a certain block";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The find command searches through Baritone's cache and attempts to find the location of the block.",
         "Tab completion will suggest only cached blocks and uncached blocks can not be found.",
         "",
         "Usage:",
         "> find <block> [...] - Try finding the listed blocks"
      );
   }
}
