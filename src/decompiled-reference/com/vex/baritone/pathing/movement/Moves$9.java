package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementAscend;

enum Moves$9 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      return new MovementAscend(var1.a, var2, new BetterBlockPos(var2.x + 1, var2.y + 1, var2.z));
   }

   @Override
   public final double a(CalculationContext var1, int var2, int var3, int var4) {
      return MovementAscend.a(var1, var2, var3, var4, var2 + 1, var4);
   }
}
