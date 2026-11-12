package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class CommandAlias extends Command {
   private final String a;
   private String b;

   public CommandAlias(IBaritone var1, List<String> var2, String var3, String var4) {
      super(var1, var2.toArray(new String[0]));
      this.a = var3;
      this.b = var4;
   }

   public CommandAlias(Baritone var1, String var2, String var3, String var4) {
      super(var1, var2);
      this.a = var3;
      this.b = var4;
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      super.com.vex.baritone.getCommandManager().execute(String.format("%s %s", this.b, var2.rawRest()));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return super.com.vex.baritone.getCommandManager().tabComplete(String.format("%s %s", this.b, var2.rawRest()));
   }

   @Override
   public String getShortDesc() {
      return this.a;
   }

   @Override
   public List<String> getLongDesc() {
      return Collections.singletonList(String.format("This command is an alias, for: %s ...", this.b));
   }
}
