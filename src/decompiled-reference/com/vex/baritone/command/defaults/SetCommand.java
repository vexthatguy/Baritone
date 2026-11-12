package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.RelativeFile;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.vex.baritone.api.command.helpers.Paginator;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.utils.SettingsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2558.class_10610;
import net.minecraft.class_2568.class_10613;

public class SetCommand extends Command {
   public SetCommand(Baritone var1) {
      super(var1, "set", "setting", "settings");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var1 = var2.hasAny() ? var2.getString().toLowerCase(Locale.US) : "list";
      if (Arrays.asList("s", "save").contains(var1)) {
         SettingsUtil.save(Baritone.a());
         this.logDirect("Settings saved");
      } else if (Arrays.asList("load", "ld").contains(var1)) {
         String var12 = "settings.txt";
         if (var2.hasAny()) {
            var12 = var2.getString();
         }

         SettingsUtil.modifiedSettings(Baritone.a()).forEach(Settings.Setting::reset);
         SettingsUtil.readAndApply(Baritone.a(), var12);
         this.logDirect("Settings reloaded from " + var12);
      } else {
         boolean var3 = Arrays.asList("m", "mod", "modified").contains(var1);
         boolean var4 = Arrays.asList("all", "l", "list").contains(var1);
         if (var3 || var4) {
            String var14 = var2.hasAny() && var2.peekAsOrNull(Integer.class) == null ? var2.getString() : "";
            var2.requireMax(1);
            List var15 = (var3 ? SettingsUtil.modifiedSettings(Baritone.a()) : Baritone.a().allSettings)
               .stream()
               .filter(var0 -> !var0.isJavaOnly())
               .filter(var1x -> var1x.getName().toLowerCase(Locale.US).contains(var14.toLowerCase(Locale.US)))
               .sorted((var0, var1x) -> String.CASE_INSENSITIVE_ORDER.compare(var0.getName(), var1x.getName()))
               .collect(Collectors.toList());
            Paginator.paginate(var2, new Paginator<>(var15), () -> {
               String var10001;
               Object[] var10002;
               Object[] var10003;
               byte var10004;
               String var10005;
               if (!var14.isEmpty()) {
                  var10001 = "All %ssettings containing the string '%s':";
                  var10002 = new Object[]{var3 ? "modified " : "", null};
                  var10003 = var10002;
                  var10004 = 1;
                  var10005 = var14;
               } else {
                  var10001 = "All %ssettings:";
                  var10002 = new Object[1];
                  var10003 = var10002;
                  var10004 = 0;
                  var10005 = var3 ? "modified " : "";
               }

               var10003[var10004] = var10005;
               this.logDirect(String.format(var10001, var10002));
            }, var0 -> {
               class_5250 var1x;
               class_5250 var10000x = var1x = class_2561.method_43470(String.format(" (%s)", SettingsUtil.settingTypeToString(var0)));
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1063));
               class_5250 var2x;
               var10000x = var2x = class_2561.method_43470("");
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1080));
               var2x.method_27693(var0.getName());
               var2x.method_27693(String.format("\nType: %s", SettingsUtil.settingTypeToString(var0)));
               var2x.method_27693(String.format("\n\nValue:\n%s", SettingsUtil.settingValueToString(var0)));
               var2x.method_27693(String.format("\n\nDefault Value:\n%s", SettingsUtil.settingDefaultToString(var0)));
               String var3x = Baritone.a().prefix.value + String.format("set %s ", var0.getName());
               class_5250 var4x;
               var10000x = var4x = class_2561.method_43470(var0.getName());
               var10000x.method_10862(var10000x.method_10866().method_10977(class_124.field_1080));
               var4x.method_10852(var1x);
               var4x.method_10862(var4x.method_10866().method_10949(new class_10613(var2x)).method_10958(new class_10610(var3x)));
               return var4x;
            }, IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set " + var1 + " " + var14);
         } else {
            var2.requireMax(1);
            var4 = var1.equalsIgnoreCase("reset");
            boolean var5 = var1.equalsIgnoreCase("toggle");
            var3 = var4 || var5;
            if (var4) {
               if (!var2.hasAny()) {
                  this.logDirect("Please specify 'all' as an argument to reset to confirm you'd really like to do this");
                  this.logDirect("ALL settings will be reset. Use the 'set modified' or 'modified' commands to see what will be reset.");
                  this.logDirect("Specify a setting name instead of 'all' to only reset one setting");
               } else if (var2.peekString().equalsIgnoreCase("all")) {
                  SettingsUtil.modifiedSettings(Baritone.a()).forEach(Settings.Setting::reset);
                  this.logDirect("All settings have been reset to their default values");
                  SettingsUtil.save(Baritone.a());
                  return;
               }
            }

            if (var5) {
               var2.requireMin(1);
            }

            String var6 = var3 ? var2.getString() : var1;
            Settings.Setting var16;
            if ((var16 = Baritone.a().allSettings.stream().filter(var1x -> var1x.getName().equalsIgnoreCase(var6)).findFirst().orElse(null)) == null) {
               throw new CommandInvalidTypeException(var2.consumed(), "a valid setting");
            }

            if (var16.isJavaOnly()) {
               throw new CommandInvalidStateException(String.format("Setting %s can only be used via the api.", var16.getName()));
            }

            if (!var3 && !var2.hasAny()) {
               this.logDirect(String.format("Value of setting %s:", var16.getName()));
               this.logDirect(SettingsUtil.settingValueToString(var16));
            } else {
               String var11 = SettingsUtil.settingValueToString(var16);
               if (var4) {
                  var16.reset();
               } else if (var5) {
                  if (var16.getValueClass() != Boolean.class) {
                     throw new CommandInvalidTypeException(var2.consumed(), "a toggleable setting", "some other setting");
                  }

                  var16.value = (T)(Boolean)var16.value ^ true;
                  this.logDirect(String.format("Toggled setting %s to %s", var16.getName(), Boolean.toString((Boolean)var16.value)));
               } else {
                  String var7 = var2.getString();

                  try {
                     SettingsUtil.parseAndApply(Baritone.a(), var1, var7);
                  } catch (Throwable var8) {
                     var8.printStackTrace();
                     throw new CommandInvalidTypeException(var2.consumed(), "a valid value", var8);
                  }
               }

               if (!var5) {
                  this.logDirect(String.format("Successfully %s %s to %s", var4 ? "reset" : "set", var16.getName(), SettingsUtil.settingValueToString(var16)));
               }

               class_5250 var17;
               class_5250 var10000 = var17 = class_2561.method_43470(String.format("Old value: %s", var11));
               var10000.method_10862(
                  var10000.method_10866()
                     .method_10977(class_124.field_1080)
                     .method_10949(new class_10613(class_2561.method_43470("Click to set the setting back to this value")))
                     .method_10958(new class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + String.format("set %s %s", var16.getName(), var11)))
               );
               this.logDirect(var17);
               if ((!var16.getName().equals("chatControl") || (Boolean)var16.value || Baritone.a().chatControlAnyway.value)
                  && (!var16.getName().equals("chatControlAnyway") || (Boolean)var16.value || Baritone.a().chatControl.value)) {
                  if (var16.getName().equals("prefixControl") && !(Boolean)var16.value) {
                     this.logDirect(
                        "Warning: Prefixed commands will no longer work. If you want to revert this change, use chat control (if enabled) or click the old value listed above.",
                        class_124.field_1061
                     );
                  }
               } else {
                  this.logDirect(
                     "Warning: Chat commands will no longer work. If you want to revert this change, use prefix control (if enabled) or click the old value listed above.",
                     class_124.field_1061
                  );
               }
            }

            SettingsUtil.save(Baritone.a());
         }
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasAny()) {
         var1 = var2.getString();
         if (var2.hasExactlyOne() && !Arrays.asList("s", "save").contains(var2.peekString().toLowerCase(Locale.US))) {
            if (var1.equalsIgnoreCase("reset")) {
               return new TabCompleteHelper().addModifiedSettings().prepend("all").filterPrefix(var2.getString()).stream();
            }

            if (var1.equalsIgnoreCase("toggle")) {
               return new TabCompleteHelper().addToggleableSettings().filterPrefix(var2.getString()).stream();
            }

            if (Arrays.asList("ld", "load").contains(var1.toLowerCase(Locale.US))) {
               return RelativeFile.tabComplete(var2, class_310.method_1551().field_1697.toPath().resolve("baritone").toFile());
            }

            Settings.Setting var5;
            if ((var5 = Baritone.a().byLowerName.get(var1.toLowerCase(Locale.US))) != null) {
               if (var5.getType() == Boolean.class) {
                  TabCompleteHelper var3 = new TabCompleteHelper();
                  if ((Boolean)var5.value) {
                     var3.append("true", "false");
                  } else {
                     var3.append("false", "true");
                  }

                  return var3.filterPrefix(var2.getString()).stream();
               }

               return Stream.of(SettingsUtil.settingValueToString(var5));
            }
         } else if (!var2.hasAny()) {
            return new TabCompleteHelper()
               .addSettings()
               .sortAlphabetically()
               .prepend("list", "modified", "reset", "toggle", "save", "load")
               .filterPrefix(var1)
               .stream();
         }
      }

      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "View or change settings";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "Using the set command, you can manage all of Baritone's settings. Almost every aspect is controlled by these settings - go wild!",
         "",
         "Usage:",
         "> set - Same as `set list`",
         "> set list [page] - View all settings",
         "> set modified [page] - View modified settings",
         "> set <setting> - View the current value of a setting",
         "> set <setting> <value> - Set the value of a setting",
         "> set reset all - Reset ALL SETTINGS to their defaults",
         "> set reset <setting> - Reset a setting to its default",
         "> set toggle <setting> - Toggle a boolean setting",
         "> set save - Save all settings (this is automatic tho)",
         "> set load - Load settings from settings.txt",
         "> set load [filename] - Load settings from another file in your minecraft/baritone"
      );
   }
}
