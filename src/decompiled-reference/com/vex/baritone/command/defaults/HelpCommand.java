package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.command.ICommand;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandNotFoundException;
import com.vex.baritone.api.command.helpers.Paginator;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2568.class_10613;

public class HelpCommand extends Command {
   public HelpCommand(Baritone var1) {
      super(var1, "help", "?");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(1);
      if (var2.hasAny() && !var2.is(Integer.class)) {
         String var4 = var2.getString().toLowerCase();
         ICommand var3;
         if ((var3 = super.com.vex.baritone.getCommandManager().getCommand(var4)) == null) {
            throw new CommandNotFoundException(var4);
         }

         this.logDirect(String.format("%s - %s", String.join(" / ", var3.getNames()), var3.getShortDesc()));
         this.logDirect("");
         var3.getLongDesc().forEach(this::logDirect);
         this.logDirect("");
         class_5250 var5;
         class_5250 var10000 = var5 = class_2561.method_43470("Click to return to the help menu");
         var10000.method_10862(var10000.method_10866().method_10958(new class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + var1)));
         this.logDirect(var5);
      } else {
         Paginator.paginate(
            var2,
            new Paginator<>(
               super.com.vex.baritone.getCommandManager().getRegistry().descendingStream().filter(var0 -> !var0.hiddenFromHelp()).collect(Collectors.toList())
            ),
            () -> this.logDirect("All Baritone commands (clickable):"),
            var1x -> {
               String var2x = String.join("/", var1x.getNames());
               String var3x = var1x.getNames().get(0);
               class_5250 var4x;
               class_5250 var10000x = var4x = class_2561.method_43470(" - " + var1x.getShortDesc());
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1063));
               class_5250 var8;
               var10000x = var8 = class_2561.method_43470(var2x);
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1068));
               class_5250 var5x;
               var10000x = var5x = class_2561.method_43470("");
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1080));
               var5x.method_10852(var8);
               var5x.method_27693("\n" + var1x.getShortDesc());
               var5x.method_27693("\n\nClick to view full help");
               var1 = IBaritoneChatControl.FORCE_COMMAND_PREFIX + String.format("%s %s", var1, var1x.getNames().get(0));
               class_5250 var7;
               var10000x = var7 = class_2561.method_43470(var3x);
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1080));
               var7.method_10852(var4x);
               var7.method_10862(var7.method_10866().method_10949(new class_10613(var5x)).method_10958(new class_10609(var1)));
               return var7;
            },
            IBaritoneChatControl.FORCE_COMMAND_PREFIX + var1
         );
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return var2.hasExactlyOne()
         ? new TabCompleteHelper().addCommands(super.com.vex.baritone.getCommandManager()).filterPrefix(var2.getString()).stream()
         : Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "View all commands or help on specific ones";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "Using this command, you can view detailed help information on how to use certain commands of Baritone.",
         "",
         "Usage:",
         "> help - Lists all commands and their short descriptions.",
         "> help <command> - Displays help information on a specific command."
      );
   }
}
