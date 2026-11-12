package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementDescend;
import com.vex.baritone.pathing.movement.movements.MovementFall;
import com.vex.baritone.utils.pathing.MutableMoveResult;

enum Moves$13 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      MutableMoveResult var3 = new MutableMoveResult();
      this.a(var1, var2.x, var2.y, var2.z, var3);
      return var3.b == var2.y - 1
         ? new MovementDescend(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c))
         : new MovementFall(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c));
   }

   @Override
   public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
      MovementDescend.a(var1, var2, var3, var4, var2, var4 - 1, var5);
   }
}
