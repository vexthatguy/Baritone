package com.vex.baritone.api;

import com.vex.baritone.api.utils.SettingsUtil;

public final class BaritoneAPI {
   private static final IBaritoneProvider provider;
   private static final Settings settings;

   public static IBaritoneProvider getProvider() {
      return provider;
   }

   public static Settings getSettings() {
      return settings;
   }

   static {
      SettingsUtil.readAndApply(settings = new Settings(), "settings.txt");

      try {
         provider = (IBaritoneProvider)Class.forName("com.vex.baritone.BaritoneProvider").newInstance();
      } catch (ReflectiveOperationException var1) {
         throw new RuntimeException(var1);
      }
   }
}
