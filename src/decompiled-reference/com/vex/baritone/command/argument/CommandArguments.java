package com.vex.baritone.command.argument;

import com.vex.baritone.api.command.argument.ICommandArgument;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandArguments {
   private static final Pattern a = Pattern.compile("\\S+");

   private CommandArguments() {
   }

   public static List<ICommandArgument> a(String var0, boolean var1) {
      ArrayList var2 = new ArrayList();
      Matcher var3 = a.matcher(var0);

      int var4;
      for (var4 = -1; var3.find(); var4 = var3.end()) {
         var2.add(new CommandArgument(var2.size(), var3.group(), var0.substring(var3.start())));
      }

      if (var1 && var4 < var0.length()) {
         var2.add(new CommandArgument(var2.size(), "", ""));
      }

      return var2;
   }

   public static CommandArgument a() {
      return new CommandArgument(-1, "<unknown>", "");
   }
}
