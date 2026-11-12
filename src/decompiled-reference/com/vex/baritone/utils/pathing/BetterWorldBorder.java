package com.vex.baritone.utils.pathing;

import net.minecraft.class_2784;

public class BetterWorldBorder {
   private final double a;
   private final double b;
   private final double c;
   private final double d;

   public BetterWorldBorder(class_2784 var1) {
      this.a = var1.method_11976();
      this.b = var1.method_11963();
      this.c = var1.method_11958();
      this.d = var1.method_11977();
   }

   public final boolean a(int var1, int var2) {
      return var1 + 1 > this.a && var1 < this.b && var2 + 1 > this.c && var2 < this.d;
   }

   public final boolean b(int var1, int var2) {
      return var1 > this.a && var1 + 1 < this.b && var2 > this.c && var2 + 1 < this.d;
   }
}
