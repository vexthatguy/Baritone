package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.utils.pathing.MutableMoveResult;

public enum Moves {
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a,
   a;

   public final boolean a;
   public final boolean b;
   public final int a;
   public final int b;
   public final int c;

   Moves(int var3, int var4, int var5, boolean var6, boolean var7) {
      this.a = var3;
      this.b = var4;
      this.c = var5;
      this.a = var6;
      this.b = var7;
   }

   Moves(int var3, int var4, int var5) {
      this(var3, var4, var5, false, false);
   }

   public abstract Movement a(CalculationContext var1, BetterBlockPos var2);

   public void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
      if (!this.a && !this.b) {
         var5.a = var2 + this.a;
         var5.b = var3 + this.b;
         var5.c = var4 + this.c;
         var5.a = this.a(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Movements with dynamic offset must override `apply`");
      }
   }

   public double a(CalculationContext var1, int var2, int var3, int var4) {
      throw new UnsupportedOperationException("Movements must override `cost` or `apply`");
   }
}
