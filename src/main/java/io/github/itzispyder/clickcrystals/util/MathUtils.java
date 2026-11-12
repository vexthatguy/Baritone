package io.github.itzispyder.clickcrystals.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public final class MathUtils {
   public static double avg(Integer... ints) {
      List<Integer> list = Arrays.stream(ints).filter(Objects::nonNull).toList();
      return avg(list);
   }

   public static double avg(List<Integer> ints) {
      double sum = 0.0;

      for (Integer i : ints) {
         sum += i.intValue();
      }

      return sum / ints.size();
   }

   public static double round(double value, int nthPlace) {
      return Math.floor(value * nthPlace) / nthPlace;
   }

   public static int clamp(int val, int min, int max) {
      return Math.min(max, Math.max(min, val));
   }

   public static double clamp(double val, double min, double max) {
      return Math.min(max, Math.max(min, val));
   }

   public static double lerp(double a, double b, double delta) {
      return a + (b - a) * delta;
   }

   public static boolean oob(double val, double min, double max) {
      return val < min || val > max;
   }

   public static class_243 forward(class_243 pos, class_243 dir, double dist) {
      return pos.method_1019(dir.method_1029().method_1021(dist));
   }

   public static double floorDiff(double a) {
      return a - (int)a;
   }

   public static class_243 lerpVec(double x1, double y1, double z1, double x2, double y2, double z2, float tickDelta) {
      return new class_243(x1 + (x2 - x1) * tickDelta, y1 + (y2 - y1) * tickDelta, z1 + (z2 - z1) * tickDelta);
   }

   public static class_243 lerpEntityPosVec(class_1297 entity, float tickDelta) {
      return lerpVec(entity.field_6038, entity.field_5971, entity.field_5989, entity.method_23317(), entity.method_23318(), entity.method_23321(), tickDelta);
   }

   public static class_243 lerpEntityEyeVec(class_1309 entity, float tickDelta) {
      return lerpVec(entity.field_6038, entity.field_5971, entity.field_5989, entity.method_23317(), entity.method_23318(), entity.method_23321(), tickDelta)
         .method_1031(0.0, entity.method_5751(), 0.0);
   }

   public static float[] toPolar(double x, double y, double z) {
      double pi2 = Math.PI * 2;
      if (x == 0.0 && z == 0.0) {
         float pitch = y > 0.0 ? -90.0F : 90.0F;
         return new float[]{pitch, 0.0F};
      } else {
         double theta = Math.atan2(-x, z);
         float yaw = (float)Math.toDegrees((theta + pi2) % pi2);
         double xz = Math.sqrt(x * x + z * z);
         float pitch = (float)Math.toDegrees(Math.atan(-y / xz));
         return new float[]{pitch, yaw};
      }
   }

   public static boolean isWrapped(double deg) {
      double f = deg % 360.0;
      return f < 180.0 && f >= -180.0;
   }

   public static double wrapDegrees(double deg) {
      double f = deg % 360.0;
      if (f >= 180.0) {
         f -= 360.0;
      }

      if (f < -180.0) {
         f += 360.0;
      }

      return f;
   }

   public static double angleBetween(double deg1, double deg2) {
      double diff = deg2 - deg1;
      deg1 = wrapDegrees(deg1);
      deg2 = wrapDegrees(deg2);
      if ((!(deg1 < -90.0) || !(deg2 > 90.0)) && (!(deg1 > 90.0) || !(deg2 < -90.0))) {
         return !(diff >= 180.0) && !(diff < -180.0) ? Math.abs(wrapDegrees(diff)) : Math.abs(deg2 - deg1);
      } else {
         return 180.0 - Math.abs(deg1) + (180.0 - Math.abs(deg2));
      }
   }

   public static Vector3d toVector(float pitch, float yaw, float radius) {
      pitch = (float)Math.toRadians(pitch);
      yaw = (float)Math.toRadians(yaw);
      double x = radius * class_3532.method_15362(yaw) * class_3532.method_15362(pitch);
      double y = radius * class_3532.method_15374(pitch);
      double z = radius * class_3532.method_15374(yaw) * class_3532.method_15362(pitch);
      return new Vector3d(x, y, z);
   }

   public static float[] projectVertex(double x, double y, double z, float focalLen) {
      double depth = -(focalLen + z);
      if (depth >= 0.0) {
         depth = -1.0E-11;
      }

      float pixelX = (float)(focalLen * x / depth);
      float pixelY = (float)(focalLen * y / depth);
      return new float[]{pixelX, pixelY};
   }

   public static float[] projectVertex(Vector3d vector, Quaternionf rotation, float focalLen) {
      vector = rotation.transform(vector);
      return projectVertex(vector.x, vector.y, vector.z, focalLen);
   }

   public static float[] projectVertex(Vector3f vector, Quaternionf rotation, float focalLen) {
      vector = rotation.transform(vector);
      return projectVertex(vector.x, vector.y, vector.z, focalLen);
   }

   public static class_243 rotate(class_243 vec, class_243 og, float pitch, float yaw) {
      Vector3f vector = vec.method_1020(og).method_46409();
      Quaternionf qYaw = new Quaternionf().rotationY((float)Math.toRadians(yaw));
      Quaternionf qPitch = new Quaternionf().rotationX((float)Math.toRadians(pitch));
      vector = qYaw.mul(qPitch).transform(vector).add(og.method_46409());
      return new class_243(vector.x, vector.y, vector.z);
   }
}
