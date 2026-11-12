package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.process.ICustomGoalProcess;
import com.vex.baritone.api.process.IElytraProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import net.minecraft.class_642;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2568.class_10613;

public class ElytraCommand extends Command {
   public ElytraCommand(Baritone var1) {
      super(var1, "elytra");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      ICustomGoalProcess var9 = super.com.vex.baritone.getCustomGoalProcess();
      IElytraProcess var3 = super.com.vex.baritone.getElytraProcess();
      if (var2.hasExactlyOne() && var2.peekString().equals("supported")) {
         this.logDirect(var3.isLoaded() ? "yes" : a());
      } else {
         if (!var3.isLoaded()) {
            throw new CommandInvalidStateException(a());
         }

         if (!var2.hasAny()) {
            if (Baritone.a().elytraTermsAccepted.value) {
               if (this.a()) {
                  ElytraCommand var13 = this;
                  long var6;
                  if (Baritone.a().elytraPredictTerrain.value
                     && (var6 = Baritone.a().elytraNetherSeed.value) != 146008555100680L
                     && var6 != -4100785268875389365L) {
                     var13.logDirect(class_2561.method_43470("It looks like you're on 2b2t, but elytraNetherSeed is incorrect."));
                     var13.logDirect(a());
                  }
               }
            } else {
               ElytraCommand var14 = this;
               class_5250 var20;
               (var20 = class_2561.method_43470("")).method_27693("To disable this message, enable the setting elytraTermsAccepted\n");
               var20.method_27693(
                  "Baritone Elytra is an experimental feature. It is only intended for long distance travel in the Nether using fireworks for vanilla boost. It will not work with any other mods (\"hacks\") for non-vanilla boost. "
               );
               class_5250 var7;
               class_5250 var10000 = var7 = class_2561.method_43470(
                  "If you want Baritone to attempt to take off from the ground for you, you can enable the elytraAutoJump setting (not advisable on laggy servers!). "
               );
               var10000.method_10862(
                  var10000.method_10866().method_10949(new class_10613(class_2561.method_43470(Baritone.a().prefix.value + "set elytraAutoJump true")))
               );
               var20.method_10852(var7);
               class_5250 var4;
               var10000 = var4 = class_2561.method_43470(
                  "If you want Baritone to go slower, enable the elytraConserveFireworks setting and/or decrease the elytraFireworkSpeed setting. "
               );
               var10000.method_10862(
                  var10000.method_10866()
                     .method_10949(
                        new class_10613(
                           class_2561.method_43470(
                              Baritone.a().prefix.value
                                 + "set elytraConserveFireworks true\n"
                                 + Baritone.a().prefix.value
                                 + "set elytraFireworkSpeed 0.6\n(the 0.6 number is just an example, tweak to your liking)"
                           )
                        )
                     )
               );
               var20.method_10852(var4);
               var4 = class_2561.method_43470("Baritone Elytra ");
               class_5250 var5;
               var10000 = var5 = class_2561.method_43470("wants to know the seed");
               var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1061).method_30938(Boolean.TRUE).method_10982(Boolean.TRUE));
               var4.method_10852(var5);
               var4.method_27693(
                  " of the world you are in. If it doesn't have the correct seed, it will frequently backtrack. It uses the seed to generate terrain far beyond what you can see, since terrain obstacles in the Nether can be much larger than your render distance. "
               );
               var20.method_10852(var4);
               var20.method_27693("\n");
               if (var14.a()) {
                  (var4 = class_2561.method_43470("It looks like you're on 2b2t. ")).method_10852(a());
                  if (!Baritone.a().elytraPredictTerrain.value) {
                     var4.method_27693(Baritone.a().prefix.value + "elytraPredictTerrain is currently disabled. ");
                  } else if (Baritone.a().elytraNetherSeed.value == 146008555100680L) {
                     var4.method_27693("You are using the newer seed. ");
                  } else if (Baritone.a().elytraNetherSeed.value == -4100785268875389365L) {
                     var4.method_27693("You are using the older seed. ");
                  } else {
                     var4.method_27693("Defaulting to the newer seed. ");
                     Baritone.a().elytraNetherSeed.value = 146008555100680L;
                  }

                  var20.method_10852(var4);
               } else if (Baritone.a().elytraNetherSeed.value == 146008555100680L) {
                  (var4 = class_2561.method_43470(
                        "Baritone doesn't know the seed of your world. Set it with: " + Baritone.a().prefix.value + "set elytraNetherSeed seedgoeshere\n"
                     ))
                     .method_27693("For the time being, elytraPredictTerrain is defaulting to false since the seed is unknown.");
                  var20.method_10852(var4);
                  Baritone.a().elytraPredictTerrain.value = Boolean.FALSE;
               } else if (Baritone.a().elytraPredictTerrain.value) {
                  var4 = class_2561.method_43470(
                     "Baritone Elytra is predicting terrain assuming that "
                        + Baritone.a().elytraNetherSeed.value
                        + " is the correct seed. Change that with "
                        + Baritone.a().prefix.value
                        + "set elytraNetherSeed seedgoeshere, or disable it with "
                        + Baritone.a().prefix.value
                        + "set elytraPredictTerrain false"
                  );
                  var20.method_10852(var4);
               } else {
                  var4 = class_2561.method_43470(
                     "Baritone Elytra is not predicting terrain. If you don't know the seed, this is the correct thing to do. If you do know the seed, input it with "
                        + Baritone.a().prefix.value
                        + "set elytraNetherSeed seedgoeshere, and then enable it with "
                        + Baritone.a().prefix.value
                        + "set elytraPredictTerrain true"
                  );
                  var20.method_10852(var4);
               }

               var14.logDirect(var20);
            }

            Goal var11;
            if ((var11 = var9.mostRecentGoal()) == null) {
               throw new CommandInvalidStateException("No goal has been set");
            }

            if (super.ctx.world().method_27983() != class_1937.field_25180) {
               throw new CommandInvalidStateException("Only works in the nether");
            }

            try {
               var3.pathTo(var11);
            } catch (IllegalArgumentException var8) {
               throw new CommandInvalidStateException(var8.getMessage());
            }
         } else {
            switch (var2.getString()) {
               case "reset":
                  var3.resetState();
                  this.logDirect("Reset state but still flying to same goal");
                  return;
               case "repack":
                  var3.repackChunks();
                  this.logDirect("Queued all loaded chunks for repacking");
                  return;
               default:
                  throw new CommandInvalidStateException("Invalid action");
            }
         }
      }
   }

   private static class_2561 a() {
      class_5250 var0;
      (var0 = class_2561.method_43470(""))
         .method_27693(
            "Within a few hundred blocks of spawn/axis/highways/etc, the terrain is too fragmented to be predictable. Baritone Elytra will still work, just with backtracking. "
         );
      var0.method_27693("However, once you get more than a few thousand blocks out, you should try ");
      class_5250 var1;
      class_5250 var10000 = var1 = class_2561.method_43470("the older seed (click here)");
      var10000.method_10862(
         var10000.method_10866()
            .method_30938(Boolean.TRUE)
            .method_10982(Boolean.TRUE)
            .method_10949(new class_10613(class_2561.method_43470(Baritone.a().prefix.value + "set elytraNetherSeed -4100785268875389365")))
            .method_10958(new class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set elytraNetherSeed -4100785268875389365"))
      );
      var0.method_10852(var1);
      var0.method_27693(". Once you're further out into newer terrain generation (this includes everything up through 1.12), you should try ");
      var10000 = var1 = class_2561.method_43470("the newer seed (click here)");
      var10000.method_10862(
         var10000.method_10866()
            .method_30938(Boolean.TRUE)
            .method_10982(Boolean.TRUE)
            .method_10949(new class_10613(class_2561.method_43470(Baritone.a().prefix.value + "set elytraNetherSeed 146008555100680")))
            .method_10958(new class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set elytraNetherSeed 146008555100680"))
      );
      var0.method_10852(var1);
      var0.method_27693(
         ". Once you get into 1.19 terrain, the terrain becomes unpredictable again, due to custom non-vanilla generation, and you should set #elytraPredictTerrain to false. "
      );
      return var0;
   }

   private boolean a() {
      class_642 var1;
      return (var1 = super.ctx.minecraft().method_1558()) != null && var1.field_3761.toLowerCase().contains("2b2t.org");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      TabCompleteHelper var3 = new TabCompleteHelper();
      if (var2.hasExactlyOne()) {
         var3.append("reset", "repack", "supported");
      }

      return var3.filterPrefix(var2.getString()).stream();
   }

   @Override
   public String getShortDesc() {
      return "elytra time";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The elytra command tells baritone to, in the nether, automatically fly to the current goal.",
         "",
         "Usage:",
         "> elytra - fly to the current goal",
         "> elytra reset - Resets the state of the process, but will try to keep flying to the same goal.",
         "> elytra repack - Queues all of the chunks in render distance to be given to the native library.",
         "> elytra supported - Tells you if baritone ships a native library that is compatible with your PC."
      );
   }

   private static String a() {
      String var0 = System.getProperty("os.arch");
      String var1 = System.getProperty("os.name");
      return String.format(
         "Failed loading native library. Your CPU is %s and your operating system is %s. Supported architectures are 64 bit x86, and 64 bit ARM. Supported operating systems are Windows, Linux, and Mac",
         var0,
         var1
      );
   }
}
