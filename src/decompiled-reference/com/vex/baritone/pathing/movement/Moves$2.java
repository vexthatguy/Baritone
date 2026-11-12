package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementPillar;

enum Moves$2 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      return new MovementPillar(var1.a, var2, var2.above());
   }

   @Override
   public final double a(CalculationContext var1, int var2, int var3, int var4) {
      return MovementPillar.a(var1, var2, var3, var4);
   }
}
