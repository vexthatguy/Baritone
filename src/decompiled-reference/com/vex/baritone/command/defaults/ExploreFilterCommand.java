package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.RelativeFile;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ExploreFilterCommand extends Command {
   public ExploreFilterCommand(Baritone var1) {
      super(var1, "explorefilter");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(2);
      File var7 = var2.getDatatypePost(RelativeFile.INSTANCE, super.ctx.minecraft().field_1697.getAbsoluteFile().getParentFile());
      boolean var3 = false;
      if (var2.hasAny()) {
         if (!var2.getString().equalsIgnoreCase("invert")) {
            throw new CommandInvalidTypeException(var2.consumed(), "either \"invert\" or nothing");
         }

         var3 = true;
      }

      try {
         super.com.vex.baritone.getExploreProcess().applyJsonFilter(var7.toPath().toAbsolutePath(), var3);
      } catch (NoSuchFileException var4) {
         throw new CommandInvalidStateException("File not found");
      } catch (JsonSyntaxException var5) {
         throw new CommandInvalidStateException("Invalid JSON syntax");
      } catch (Exception var6) {
         throw new IllegalStateException(var6);
      }

      this.logDirect(String.format("Explore filter applied. Inverted: %s", Boolean.toString(var3)));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return var2.hasExactlyOne() ? RelativeFile.tabComplete(var2, RelativeFile.gameDir(super.ctx.minecraft())) : Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Explore chunks from a json";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "Apply an explore filter before using explore, which tells the explore process which chunks have been explored/not explored.",
         "",
         "The JSON file will follow this format: [{\"x\":0,\"z\":0},...]",
         "",
         "If 'invert' is specified, the chunks listed will be considered NOT explored, rather than explored.",
         "",
         "Usage:",
         "> explorefilter <path> [invert] - Load the JSON file referenced by the specified path. If invert is specified, it must be the literal word 'invert'."
      );
   }
}
