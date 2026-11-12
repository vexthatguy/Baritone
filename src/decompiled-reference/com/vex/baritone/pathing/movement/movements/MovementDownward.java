package com.vex.baritone.pathing.movement.movements;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.MovementState;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2680;

public class MovementDownward extends Movement {
   private int a = 0;

   public MovementDownward(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, new BetterBlockPos[]{var3});
   }

   @Override
   public void reset() {
      super.reset();
      this.a = 0;
   }

   @Override
   public final double a(CalculationContext var1) {
      return a(var1, super.a.x, super.a.y, super.a.z);
   }

   @Override
   public final Set<BetterBlockPos> a() {
      return ImmutableSet.of(super.a, super.b);
   }

   public static double a(CalculationContext var0, int var1, int var2, int var3) {
      if (!var0.n) {
         return 1000000.0;
      }

      if (!MovementHelper.c(var0, var1, var2 - 2, var3)) {
         return 1000000.0;
      }

      class_2680 var4;
      class_2248 var5;
      return (var5 = (var4 = var0.a(var1, var2 - 1, var3)).method_26204()) != class_2246.field_9983 && var5 != class_2246.field_10597
         ? FALL_N_BLOCKS_COST[1] + MovementHelper.a(var0, var1, var2 - 1, var3, var4, false)
         : 6.666666666666667;
   }

   @Override
   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      if (super.a.playerFeet().equals(super.b)) {
         MovementStatus var9 = MovementStatus.SUCCESS;
         MovementState var11 = var1;
         var1.a = var9;
         return var11;
      }

      if (!this.a()) {
         MovementStatus var8 = MovementStatus.UNREACHABLE;
         MovementState var10 = var1;
         var1.a = var8;
         return var10;
      }

      double var2 = super.a.player().method_73189().field_1352 - (super.b.method_10263() + 0.5);
      double var4 = super.a.player().method_73189().field_1350 - (super.b.method_10260() + 0.5);
      double var6 = Math.sqrt(var2 * var2 + var4 * var4);
      if (this.a++ < 10 && var6 < 0.2) {
         return var1;
      }

      MovementHelper.a(super.a, var1, super.a[0]);
      return var1;
   }
}
