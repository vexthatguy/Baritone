package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementTraverse;

enum Moves$4 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      return new MovementTraverse(var1.a, var2, var2.south());
   }

   @Override
   public final double a(CalculationContext var1, int var2, int var3, int var4) {
      return MovementTraverse.a(var1, var2, var3, var4, var2, var4 + 1);
   }
}
