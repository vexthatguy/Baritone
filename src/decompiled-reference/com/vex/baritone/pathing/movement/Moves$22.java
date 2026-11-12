package com.vex.baritone.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.movements.MovementParkour;
import com.vex.baritone.utils.pathing.MutableMoveResult;
import net.minecraft.class_2350;

enum Moves$22 {
   @Override
   public final Movement a(CalculationContext var1, BetterBlockPos var2) {
      return MovementParkour.a(var1, var2, class_2350.field_11039);
   }

   @Override
   public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
      MovementParkour.a(var1, var2, var3, var4, class_2350.field_11039, var5);
   }
}
