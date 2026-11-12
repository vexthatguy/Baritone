package com.vex.baritone.pathing.movement.movements;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.MovementState;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.pathing.MutableMoveResult;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_243;
import net.minecraft.class_2680;

public class MovementDescend extends Movement {
   private int a = 0;
   public boolean a = false;

   public MovementDescend(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, new BetterBlockPos[]{var3.above(2), var3.above(), var3}, var3.below());
   }

   @Override
   public void reset() {
      super.reset();
      this.a = 0;
      this.a = false;
   }

   @Override
   public final double a(CalculationContext var1) {
      MutableMoveResult var2 = new MutableMoveResult();
      a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z, var2);
      return var2.b != super.b.y ? 1000000.0 : var2.a;
   }

   @Override
   public final Set<BetterBlockPos> a() {
      return ImmutableSet.of(super.a, super.b.above(), super.b);
   }

   public static void a(CalculationContext var0, int var1, int var2, int var3, int var4, int var5, MutableMoveResult var6) {
      class_2680 var9 = var0.a(var4, var2 - 1, var5);
      double var7;
      if (!((var7 = 0.0 + MovementHelper.a(var0, var4, var2 - 1, var5, var9, false)) >= 1000000.0)) {
         if (!((var7 = var7 + MovementHelper.a(var0, var4, var2, var5, false)) >= 1000000.0)) {
            if (!((var7 = var7 + MovementHelper.a(var0, var4, var2 + 1, var5, true)) >= 1000000.0)) {
               class_2248 var12;
               if ((var12 = var0.a(var1, var2 - 1, var3).method_26204()) != class_2246.field_9983 && var12 != class_2246.field_10597) {
                  class_2680 var13 = var0.a(var4, var2 - 2, var5);
                  if (!MovementHelper.b(var0, var4, var2 - 2, var5, var13)) {
                     a(var0, var2, var4, var5, var7, var13, var6);
                  } else if (var9.method_26204() != class_2246.field_9983 && var9.method_26204() != class_2246.field_10597) {
                     if (!MovementHelper.b(var0, var9)) {
                        double var10 = 3.7062775075283763;
                        if (var12 == class_2246.field_10114) {
                           var10 = 7.4125550150567525;
                        }

                        var7 += var10 + Math.max(FALL_N_BLOCKS_COST[1], 0.9265693768820937);
                        var6.a = var4;
                        var6.b = var2 - 1;
                        var6.c = var5;
                        var6.a = var7;
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean a(CalculationContext var0, int var1, int var2, int var3, double var4, class_2680 var6, MutableMoveResult var7) {
      if (var4 != 0.0 && var0.a(var2, var1 + 2, var3).method_26204() instanceof class_2346) {
         return false;
      }

      if (!MovementHelper.a(var0, var2, var1 - 2, var3, var6)) {
         return false;
      }

      double var8 = 0.0;
      int var17 = var1;

      int var11;
      for (int var10 = 3; (var11 = var1 - var10) >= var0.a.method_31607(); var10++) {
         boolean var12 = var10 >= var0.b;
         class_2680 var13 = var0.a(var2, var11, var3);
         int var14 = var10 - (var1 - var17);
         double var15 = 3.7062775075283763 + FALL_N_BLOCKS_COST[var14] + var4 + var8;
         if (var12 && MovementHelper.d(var13)) {
            if (!MovementHelper.a(var0, var2, var11, var3, var13)) {
               return false;
            }

            if (var0.j) {
               return false;
            }

            if (MovementHelper.a(var2, var11, var3, var13, var0.a)) {
               return false;
            }

            if (!MovementHelper.c(var0, var2, var11 - 1, var3)) {
               return false;
            }

            var7.a = var2;
            var7.b = var11;
            var7.c = var3;
            var7.a = var15;
            return false;
         }

         if (var12 && var0.k && MovementHelper.e(var13)) {
            var7.a = var2;
            var7.b = var11;
            var7.c = var3;
            var7.a = var15;
            return false;
         }

         if (var14 > 11 || var13.method_26204() != class_2246.field_10597 && var13.method_26204() != class_2246.field_9983) {
            if (!MovementHelper.a(var0, var2, var11, var3, var13)) {
               if (!MovementHelper.b(var0, var2, var11, var3, var13)) {
                  return false;
               }

               if (MovementHelper.c(var13)) {
                  return false;
               }

               if (var12 && var14 <= var0.c + 1) {
                  var7.a = var2;
                  var7.b = var11 + 1;
                  var7.c = var3;
                  var7.a = var15;
                  return false;
               }

               if (var12 && var0.b && var14 <= var0.d + 1) {
                  var7.a = var2;
                  var7.b = var11 + 1;
                  var7.c = var3;
                  var7.a = var15 + var0.a();
                  return true;
               }

               return false;
            }
         } else {
            var8 = var8 + FALL_N_BLOCKS_COST[var14 - 1] + 6.666666666666667;
            var17 = var11;
         }
      }

      return false;
   }

   @Override
   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      BetterBlockPos var2 = super.a.playerFeet();
      class_2338 var3 = new class_2338(
         (super.b.method_10263() << 1) - super.a.method_10263(), super.b.method_10264(), (super.b.method_10260() << 1) - super.a.method_10260()
      );
      if (!var2.equals(super.b) && !var2.equals(var3)
         || !MovementHelper.e(super.a, super.b) && !(super.a.player().method_73189().field_1351 - super.b.method_10264() < 0.5)) {
         if (this.b()) {
            double var18 = (super.a.method_10263() + 0.5) * 0.17 + (super.b.method_10263() + 0.5) * 0.83;
            double var19 = (super.a.method_10260() + 0.5) * 0.17 + (super.b.method_10260() + 0.5) * 0.83;
            var1.a(
                  new MovementState.MovementTarget(
                     RotationUtils.calcRotationFromVec3d(super.a.playerHead(), new class_243(var18, super.b.method_10264(), var19), super.a.playerRotations())
                        .withPitch(super.a.playerRotations().getPitch()),
                     false
                  )
               )
               .a(Input.MOVE_FORWARD, true);
            return var1;
         }

         double var4 = super.a.player().method_73189().field_1352 - (super.b.method_10263() + 0.5);
         double var6 = super.a.player().method_73189().field_1350 - (super.b.method_10260() + 0.5);
         double var8 = Math.sqrt(var4 * var4 + var6 * var6);
         double var10 = super.a.player().method_73189().field_1352 - (super.a.method_10263() + 0.5);
         double var12 = super.a.player().method_73189().field_1350 - (super.a.method_10260() + 0.5);
         double var14 = Math.sqrt(var10 * var10 + var12 * var12);
         if (!var2.equals(super.b) || var8 > 0.25) {
            if (this.a++ < 20 && var14 < 1.25) {
               MovementHelper.a(super.a, var1, var3);
            } else {
               MovementHelper.a(super.a, var1, super.b);
            }
         }

         return var1;
      } else {
         MovementStatus var16 = MovementStatus.SUCCESS;
         MovementState var17 = var1;
         var1.a = var16;
         return var17;
      }
   }

   public final boolean b() {
      if (this.a) {
         return true;
      }

      class_2338 var1 = super.b.method_10059(super.a.below()).method_10081(super.b);
      if (this.c()) {
         return true;
      }

      for (int var2 = 0; var2 <= 2; var2++) {
         if (MovementHelper.b(BlockStateInterface.a(super.a, var1.method_10086(var2)))) {
            return true;
         }
      }

      return false;
   }

   public final boolean c() {
      class_2338 var1 = super.b.method_10059(super.a.below()).method_10081(super.b);
      return !MovementHelper.a(super.a, new BetterBlockPos(var1))
         && MovementHelper.a(super.a, new BetterBlockPos(var1).above())
         && MovementHelper.a(super.a, new BetterBlockPos(var1).above(2));
   }
}
