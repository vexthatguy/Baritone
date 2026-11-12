package com.vex.baritone.utils.pathing;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.utils.IPlayerContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_1308;
import net.minecraft.class_1560;
import net.minecraft.class_1590;
import net.minecraft.class_1628;
import net.minecraft.class_2338;

public class Avoidance {
   final int a;
   final int b;
   final int c;
   final double a;
   final int d;
   private final int e;

   private Avoidance(class_2338 var1, double var2, int var4) {
      this(var1.method_10263(), var1.method_10264(), var1.method_10260(), var2, var4);
   }

   private Avoidance(int var1, int var2, int var3, double var4, int var6) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.a = var4;
      this.d = var6;
      this.e = var6 * var6;
   }

   public static List<Avoidance> a(IPlayerContext var0) {
      if (!Baritone.a().avoidance.value) {
         return Collections.emptyList();
      }

      ArrayList var1 = new ArrayList();
      double var2 = Baritone.a().mobSpawnerAvoidanceCoefficient.value;
      double var4 = Baritone.a().mobAvoidanceCoefficient.value;
      if (var2 != 1.0) {
         var0.worldData()
            .getCachedWorld()
            .getLocationsOf("mob_spawner", 1, var0.playerFeet().x, var0.playerFeet().z, 2)
            .forEach(var3 -> var1.add(new Avoidance(var3, var2, Baritone.a().mobSpawnerAvoidanceRadius.value)));
      }

      if (var4 != 1.0) {
         var0.entitiesStream()
            .filter(var0x -> var0x instanceof class_1308)
            .filter(var1x -> !(var1x instanceof class_1628) || var0.player().method_5718() < 0.5)
            .filter(var0x -> !(var0x instanceof class_1590) || ((class_1590)var0x).method_6065() != null)
            .filter(var0x -> !(var0x instanceof class_1560) || ((class_1560)var0x).method_7028())
            .forEach(var3 -> var1.add(new Avoidance(var3.method_24515(), var4, Baritone.a().mobAvoidanceRadius.value)));
      }

      return var1;
   }
}
