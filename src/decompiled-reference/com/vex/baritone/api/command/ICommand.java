package com.vex.baritone.api.command;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.utils.Helper;
import java.util.List;
import java.util.stream.Stream;

public interface ICommand extends Helper {
   void execute(String var1, IArgConsumer var2);

   Stream<String> tabComplete(String var1, IArgConsumer var2);

   String getShortDesc();

   List<String> getLongDesc();

   List<String> getNames();

   default boolean hiddenFromHelp() {
      return false;
   }
}
