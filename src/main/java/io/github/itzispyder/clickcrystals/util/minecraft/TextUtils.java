package io.github.itzispyder.clickcrystals.util.minecraft;

import io.github.itzispyder.clickcrystals.Global;
import io.github.itzispyder.clickcrystals.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_327;

public class TextUtils implements Global {
   public static List<String> wordWrap(String input, int width) {
      return wordWrap(input, width, 1.0F);
   }

   public static List<String> charWrap(String input, int width) {
      return charWrap(input, width, 1.0F);
   }

   public static List<String> wordWrap(String input, int width, float textScale) {
      List<String> result = new ArrayList<>();
      class_327 tr = mc.field_1772;
      if (input != null && !input.isEmpty() && tr != null && width > 0) {
         for (String line : StringUtils.lines(input)) {
            StringBuilder current = new StringBuilder();

            for (String word : line.split(" ")) {
               int nextWidth = (int)(tr.method_1727(current + " " + word) * textScale);
               if (nextWidth == width) {
                  result.add(current + word);
                  current = new StringBuilder();
               }

               if (nextWidth > width) {
                  result.add(current.toString());
                  current = new StringBuilder();
               }

               if (nextWidth != width) {
                  current.append(word).append(" ");
               }
            }

            result.add(current.toString());
         }

         return result;
      } else {
         return result;
      }
   }

   public static List<String> charWrap(String input, int width, float textScale) {
      List<String> result = new ArrayList<>();
      class_327 tr = mc.field_1772;
      if (input != null && !input.isEmpty() && tr != null && width > 0) {
         for (String line : StringUtils.lines(input)) {
            StringBuilder current = new StringBuilder();

            for (char c : line.toCharArray()) {
               if (tr.method_1727("" + current + c) * textScale > width) {
                  result.add(current.toString());
                  current = new StringBuilder();
               }

               current.append(c);
            }

            result.add(current.toString());
         }

         return result;
      } else {
         return result;
      }
   }

   public static int getSelectionMax(String input, int toWidth) {
      class_327 tr = mc.field_1772;
      if (tr != null && !input.isEmpty()) {
         if (tr.method_1727(input) <= toWidth) {
            return input.length();
         }

         StringBuilder current = new StringBuilder();

         for (char c : input.toCharArray()) {
            current.append(c);
            if (tr.method_1727(current.toString()) >= toWidth) {
               return current.length();
            }
         }

         return 0;
      } else {
         return 0;
      }
   }
}
