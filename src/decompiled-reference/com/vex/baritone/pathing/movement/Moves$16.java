package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementDiagonal;
import com.vex.baritone.utils.pathing.MutableMoveResult;
import net.minecraft.class_2350;

enum Moves$16 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      MutableMoveResult var3 = new MutableMoveResult();
      this.a(var1, var2.x, var2.y, var2.z, var3);
      return new MovementDiagonal(var1.a, var2, class_2350.field_11043, class_2350.field_11039, var3.b - var2.y);
   }

   @Override
   public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
      MovementDiagonal.a(var1, var2, var3, var4, var2 - 1, var4 - 1, var5);
   }
}
