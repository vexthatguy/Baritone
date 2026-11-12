package io.github.itzispyder.clickcrystals.util.minecraft;

import net.minecraft.class_1799;
import net.minecraft.class_1844;
import net.minecraft.class_1887;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_9334;

public final class NbtUtils {
   public static int getEnchantLvL(class_1799 stack, class_5321<class_1887> enchant) {
      for (class_6880<class_1887> entry : stack.method_58657().method_57534()) {
         if (entry.method_40230().isPresent() && entry.method_40230().get() == enchant) {
            return stack.method_58657().method_57536(entry);
         }
      }

      return 0;
   }

   public static boolean hasEnchant(class_1799 item, String enchantmentId) {
      class_2960 id = class_2960.method_60656(enchantmentId);

      for (class_6880<class_1887> enchant : item.method_58657().method_57534()) {
         if (enchant.method_55840().equals(id.toString())) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasPotion(class_1799 item, String potionId) {
      class_1844 potion = (class_1844)item.method_58694(class_9334.field_49651);
      return potion != null && !potion.comp_2378().isEmpty()
         ? ((class_6880)potion.comp_2378().get()).method_55840().toLowerCase().contains(potionId.toLowerCase())
         : false;
   }

   public static boolean hasPotionOrEnchant(class_1799 item, String tag) {
      return hasEnchant(item, tag) || hasPotion(item, tag);
   }
}
