package io.github.itzispyder.clickcrystals.util.misc;

import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;

public class Raytracer {
   public static Pair<class_2338, class_2680> trace(class_1937 world, class_243 start, class_243 dir, double dist, Raytracer.HitCondition predicate) {
      class_2338 prev = null;

      for (double curDist = 0.0; curDist <= dist; curDist += 0.5) {
         class_243 point = start.method_1031(dir.field_1352 * curDist, dir.field_1351 * curDist, dir.field_1350 * curDist);
         class_2338 pos = new class_2338((int)Math.floor(point.field_1352), (int)Math.floor(point.field_1351), (int)Math.floor(point.field_1350));
         if (prev == null) {
            prev = pos;
         }

         if (!prev.equals(pos)) {
            if (predicate.test(pos, point)) {
               return Pair.of(pos, world.method_8320(pos));
            }

            prev = pos;
         }
      }

      class_243 point = start.method_1031(dir.field_1352 * dist, dir.field_1351 * dist, dir.field_1350 * dist);
      class_2338 pos = new class_2338((int)Math.floor(point.field_1352), (int)Math.floor(point.field_1351), (int)Math.floor(point.field_1350));
      return Pair.of(pos, world.method_8320(pos));
   }

   @FunctionalInterface
   public interface HitCondition {
      boolean test(class_2338 var1, class_243 var2);
   }
}
