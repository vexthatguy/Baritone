package io.github.itzispyder.clickcrystals.gui;

public enum ClickType {
   RELEASE,
   CLICK,
   UNKNOWN;

   public static ClickType of(int action) {
      return switch (action) {
         case 0 -> RELEASE;
         case 1 -> CLICK;
         default -> UNKNOWN;
      };
   }

   public boolean isRelease() {
      return this == RELEASE;
   }

   public boolean isDown() {
      return this == CLICK;
   }

   public boolean isUnknown() {
      return this == UNKNOWN;
   }
}
