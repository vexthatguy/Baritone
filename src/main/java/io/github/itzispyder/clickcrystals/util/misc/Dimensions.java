package io.github.itzispyder.clickcrystals.util.misc;

import io.github.itzispyder.clickcrystals.util.minecraft.PlayerUtils;
import java.util.Optional;
import net.minecraft.class_2874;
import net.minecraft.class_5321;
import net.minecraft.class_7134;

public enum Dimensions {
   OVERWORLD,
   THE_END,
   THE_NETHER;

   public static boolean isOverworld() {
      return check(class_7134.field_37666, class_7134.field_37669);
   }

   public static boolean isNether() {
      return check(class_7134.field_37667);
   }

   public static boolean isEnd() {
      return check(class_7134.field_37668);
   }

   @SafeVarargs
   private static boolean check(class_5321<class_2874>... dimKeys) {
      if (PlayerUtils.invalid()) {
         return false;
      }

      Optional<class_5321<class_2874>> entry = PlayerUtils.getWorld().method_40134().method_40230();
      if (entry.isEmpty()) {
         return false;
      }

      class_5321<class_2874> target = entry.get();

      for (class_5321<class_2874> key : dimKeys) {
         if (key == target) {
            return true;
         }
      }

      return false;
   }
}
