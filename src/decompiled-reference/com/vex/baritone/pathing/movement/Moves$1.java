package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementDownward;

enum Moves$1 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      return new MovementDownward(var1.a, var2, var2.below());
   }

   @Override
   public final double a(CalculationContext var1, int var2, int var3, int var4) {
      return MovementDownward.a(var1, var2, var3, var4);
   }
}
