package com.vex.baritone.command.defaults;

import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SchematicaCommand extends Command {
   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.com.vex.baritone.getBuilderProcess().buildOpenSchematic();
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "Builds the loaded schematic";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Builds the schematic currently open in Schematica.", "", "Usage:", "> schematica");
   }
}
