package com.vex.baritone.api.utils;

import com.vex.baritone.api.BaritoneAPI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_7591;

public interface Helper {
   Helper HELPER = new Helper$1();
   @Deprecated
   class_310 mc = class_310.method_1551();
   class_7591 MESSAGE_TAG = new class_7591(16733695, null, class_2561.method_43470("Baritone message."), "Baritone");

   static class_2561 getPrefix() {
      Calendar var0;
      class_5250 var2;
      class_5250 var10000 = var2 = class_2561.method_43470(
         (var0 = Calendar.getInstance()).get(2) == 3 && var0.get(5) <= 3 ? "Baritoe" : (BaritoneAPI.getSettings().shortBaritonePrefix.value ? "B" : "Baritone")
      );
      var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1076));
      class_5250 var1;
      (var1 = class_2561.method_43470("")).method_10862(var2.method_10866().method_10977(class_124.field_1064));
      var1.method_27693("[");
      var1.method_10852(var2);
      var1.method_27693("]");
      return var1;
   }

   default void logToast(class_2561 var1, class_2561 var2) {
      class_310.method_1551().execute(() -> BaritoneAPI.getSettings().toaster.value.accept(var1, var2));
   }

   default void logToast(String var1, String var2) {
      this.logToast(class_2561.method_43470(var1), class_2561.method_43470(var2));
   }

   default void logToast(String var1) {
      this.logToast(getPrefix(), class_2561.method_43470(var1));
   }

   default void logNotification(String var1) {
      this.logNotification(var1, false);
   }

   default void logNotification(String var1, boolean var2) {
      if (BaritoneAPI.getSettings().desktopNotifications.value) {
         this.logNotificationDirect(var1, var2);
      }
   }

   default void logNotificationDirect(String var1) {
      this.logNotificationDirect(var1, false);
   }

   default void logNotificationDirect(String var1, boolean var2) {
      class_310.method_1551().execute(() -> BaritoneAPI.getSettings().notifier.value.accept(var1, var2));
   }

   default void logDebug(String var1) {
      if (BaritoneAPI.getSettings().chatDebug.value) {
         this.logDirect(var1, false);
      }
   }

   default void logDirect(boolean var1, class_2561... var2) {
      class_5250 var3 = class_2561.method_43470("");
      if (!var1 && !BaritoneAPI.getSettings().useMessageTag.value) {
         var3.method_10852(getPrefix());
         var3.method_10852(class_2561.method_43470(" "));
      }

      Arrays.asList(var2).forEach(var3::method_10852);
      if (var1) {
         this.logToast(getPrefix(), var3);
      } else {
         class_310.method_1551().execute(() -> BaritoneAPI.getSettings().logger.value.accept(var3));
      }
   }

   default void logDirect(class_2561... var1) {
      this.logDirect(BaritoneAPI.getSettings().logAsToast.value, var1);
   }

   default void logDirect(String var1, class_124 var2, boolean var3) {
      Stream.of(var1.split("\n")).forEach(var3x -> {
         class_5250 var4;
         class_5250 var10000 = var4 = class_2561.method_43470(var3x.replace("\t", "    "));
         var10000.method_10862(var10000.method_10866().method_10977(var2));
         this.logDirect(var3, var4);
      });
   }

   default void logDirect(String var1, class_124 var2) {
      this.logDirect(var1, var2, BaritoneAPI.getSettings().logAsToast.value);
   }

   default void logDirect(String var1, boolean var2) {
      this.logDirect(var1, class_124.field_1080, var2);
   }

   default void logDirect(String var1) {
      this.logDirect(var1, BaritoneAPI.getSettings().logAsToast.value);
   }

   default void logUnhandledException(Throwable var1) {
      HELPER.logDirect(
         "An unhandled exception occurred. The error is in your game's log, please report this at https://github.com/cabaletta/baritone/issues",
         class_124.field_1061
      );
      var1.printStackTrace();
   }
}
