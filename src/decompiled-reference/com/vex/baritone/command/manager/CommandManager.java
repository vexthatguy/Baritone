package com.vex.baritone.command.manager;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.ICommand;
import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.command.exception.CommandException;
import com.vex.baritone.api.command.exception.CommandUnhandledException;
import com.vex.baritone.api.command.exception.ICommandException;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.command.manager.ICommandManager;
import com.vex.baritone.api.command.registry.Registry;
import com.vex.baritone.command.argument.ArgConsumer;
import com.vex.baritone.command.argument.CommandArguments;
import com.vex.baritone.command.defaults.DefaultCommands;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.class_3545;

public class CommandManager implements ICommandManager {
   private final Registry<ICommand> a = new Registry<>();
   private final Baritone a;

   public CommandManager(Baritone var1) {
      this.a = var1;
      DefaultCommands.a(var1).forEach(this.a::register);
   }

   @Override
   public IBaritone getBaritone() {
      return this.a;
   }

   @Override
   public Registry<ICommand> getRegistry() {
      return this.a;
   }

   @Override
   public ICommand getCommand(String var1) {
      Iterator var2 = this.a.entries.iterator();

      while (var2.hasNext()) {
         ICommand var3;
         if ((var3 = (ICommand)var2.next()).getNames().contains(var1.toLowerCase(Locale.US))) {
            return var3;
         }
      }

      return null;
   }

   @Override
   public boolean execute(String var1) {
      return this.execute(a(var1, false));
   }

   @Override
   public boolean execute(class_3545<String, List<ICommandArgument>> var1) {
      CommandManager.ExecutionWrapper var5;
      if ((var5 = this.a(var1)) != null) {
         CommandManager.ExecutionWrapper var2 = var5;

         try {
            var2.a.execute(var2.a, var2.a);
         } catch (Throwable var4) {
            (var4 instanceof ICommandException ? (ICommandException)var4 : new CommandUnhandledException(var4)).handle(var2.a, var2.a.getArgs());
         }
      }

      return var5 != null;
   }

   @Override
   public Stream<String> tabComplete(class_3545<String, List<ICommandArgument>> var1) {
      CommandManager.ExecutionWrapper var2;
      return (var2 = this.a(var1)) == null ? Stream.empty() : var2.a();
   }

   @Override
   public Stream<String> tabComplete(String var1) {
      class_3545 var3;
      String var2 = (String)(var3 = a(var1, true)).method_15442();
      return ((List)var3.method_15441()).isEmpty() ? new TabCompleteHelper().addCommands(this.a.a).filterPrefix(var2).stream() : this.tabComplete(var3);
   }

   private CommandManager.ExecutionWrapper a(class_3545<String, List<ICommandArgument>> var1) {
      String var2 = (String)var1.method_15442();
      ArgConsumer var4 = new ArgConsumer(this, (List<ICommandArgument>)var1.method_15441());
      ICommand var3;
      return (var3 = this.getCommand(var2)) == null ? null : new CommandManager.ExecutionWrapper(var3, var2, var4);
   }

   private static class_3545<String, List<ICommandArgument>> a(String var0, boolean var1) {
      String var2 = var0.split("\\s", 2)[0];
      List var3 = CommandArguments.a(var0.substring(var2.length()), var1);
      return new class_3545(var2, var3);
   }

   public static class_3545<String, List<ICommandArgument>> a(String var0) {
      return a(var0, false);
   }

   static final class ExecutionWrapper {
      ICommand a;
      String a;
      ArgConsumer a;

      ExecutionWrapper(ICommand var1, String var2, ArgConsumer var3) {
         this.a = var1;
         this.a = var2;
         this.a = var3;
      }

      final Stream<String> a() {
         try {
            return this.a.tabComplete(this.a, this.a);
         } catch (CommandException var1) {
         } catch (Throwable var2) {
            var2.printStackTrace();
         }

         return Stream.empty();
      }
   }
}
