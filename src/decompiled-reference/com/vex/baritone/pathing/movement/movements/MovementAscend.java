package com.vex.baritone.pathing.movement.movements;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.MovementState;
import com.vex.baritone.utils.BlockStateInterface;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_2350;
import net.minecraft.class_2680;

public class MovementAscend extends Movement {
   private int a = 0;

   public MovementAscend(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, new BetterBlockPos[]{var3, var2.above(2), var3.above()}, var3.below());
   }

   @Override
   public void reset() {
      super.reset();
      this.a = 0;
   }

   @Override
   public final double a(CalculationContext var1) {
      return a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z);
   }

   @Override
   public final Set<BetterBlockPos> a() {
      BetterBlockPos var1 = new BetterBlockPos(super.a.method_10059(this.getDirection()).method_10084());
      return ImmutableSet.of(super.a, super.a.above(), super.b, var1, var1.above());
   }

   public static double a(CalculationContext var0, int var1, int var2, int var3, int var4, int var5) {
      class_2680 var6 = var0.a(var4, var2, var5);
      double var7 = 0.0;
      if (!MovementHelper.b(var0, var4, var2, var5, var6)) {
         if ((var7 = var0.a(var4, var2, var5, var6)) >= 1000000.0) {
            return 1000000.0;
         }

         if (!MovementHelper.a(var4, var5, var6, var0.a)) {
            return 1000000.0;
         }

         boolean var9 = false;

         for (int var10 = 0; var10 < 5; var10++) {
            int var11 = var4 + a[var10].method_10148();
            int var12 = var2 + a[var10].method_10164();
            int var13 = var5 + a[var10].method_10165();
            if ((var11 != var1 || var13 != var3) && MovementHelper.c(var0.a, var11, var12, var13)) {
               var9 = true;
               break;
            }
         }

         if (!var9) {
            return 1000000.0;
         }
      }

      class_2680 var17 = var0.a(var1, var2 + 2, var3);
      if (!(var0.a(var1, var2 + 3, var3).method_26204() instanceof class_2346)
         || !MovementHelper.a(var0, var1, var2 + 1, var3) && var17.method_26204() instanceof class_2346) {
         class_2680 var18;
         if ((var18 = var0.a(var1, var2 - 1, var3)).method_26204() != class_2246.field_9983 && var18.method_26204() != class_2246.field_10597) {
            boolean var19 = MovementHelper.c(var18);
            boolean var20 = MovementHelper.c(var6);
            if (var19 && !var20) {
               return 1000000.0;
            }

            double var21;
            label57: {
               label56: {
                  if (var20) {
                     if (!var19) {
                        var21 = 4.63284688441047;
                        break label57;
                     }
                  } else if (var6.method_26204() == class_2246.field_10114) {
                     var21 = 9.26569376882094;
                     break label56;
                  }

                  var21 = Math.max(JUMP_ONE_BLOCK_COST, 4.63284688441047);
               }

               var21 += var0.e;
            }

            double var15;
            if ((var15 = var21 + var7 + MovementHelper.a(var0, var1, var2 + 2, var3, var17, false)) >= 1000000.0) {
               return 1000000.0;
            }

            double var23;
            return (var23 = var15 + MovementHelper.a(var0, var4, var2 + 1, var5, false)) >= 1000000.0
               ? 1000000.0
               : var23 + MovementHelper.a(var0, var4, var2 + 2, var5, true);
         } else {
            return 1000000.0;
         }
      } else {
         return 1000000.0;
      }
   }

   @Override
   public final MovementState a(MovementState var1) {
      if (super.a.playerFeet().y < super.a.y) {
         MovementStatus var11 = MovementStatus.UNREACHABLE;
         MovementState var15 = var1;
         var1.a = var11;
         return var15;
      }

      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      if (!super.a.playerFeet().equals(super.b) && !super.a.playerFeet().equals(super.b.method_10081(this.getDirection().method_10074()))) {
         class_2680 var12 = BlockStateInterface.a(super.a, (class_2338)super.c);
         if (!MovementHelper.a(super.a, super.c, var12)) {
            this.a++;
            if (MovementHelper.a(var1, super.a, super.b.below(), false, true) == MovementHelper.PlaceResult.a) {
               var1.a(Input.SNEAK, true);
               if (super.a.player().method_18276()) {
                  var1.a(Input.CLICK_RIGHT, true);
               }
            }

            if (this.a > 10) {
               var1.a(Input.MOVE_BACK, true);
            }

            return var1;
         } else {
            MovementHelper.a(super.a, var1, super.b);
            if (MovementHelper.c(var12) && !MovementHelper.c(BlockStateInterface.a(super.a, (class_2338)super.a.below()))) {
               return var1;
            }

            if (!Baritone.a().assumeStep.value && !super.a.playerFeet().equals(super.a.above())) {
               int var13 = Math.abs(super.a.method_10263() - super.b.method_10263());
               int var3 = Math.abs(super.a.method_10260() - super.b.method_10260());
               double var5 = var13 * Math.abs(super.b.method_10263() + 0.5 - super.a.player().method_73189().field_1352)
                  + var3 * Math.abs(super.b.method_10260() + 0.5 - super.a.player().method_73189().field_1350);
               double var7 = var3 * Math.abs(super.b.method_10263() + 0.5 - super.a.player().method_73189().field_1352)
                  + var13 * Math.abs(super.b.method_10260() + 0.5 - super.a.player().method_73189().field_1350);
               if (Math.abs(var13 * super.a.player().method_18798().field_1350 + var3 * super.a.player().method_18798().field_1352) > 0.1) {
                  return var1;
               }

               MovementAscend var14 = this;
               BetterBlockPos var16 = super.a.above(2);
               int var4 = 0;

               boolean var17;
               while (true) {
                  if (var4 >= 4) {
                     var17 = true;
                     break;
                  }

                  BetterBlockPos var9 = var16.relative(class_2350.method_10139(var4));
                  if (!MovementHelper.a(var14.a, var9)) {
                     var17 = false;
                     break;
                  }

                  var4++;
               }

               if (var17) {
                  return var1.a(Input.JUMP, true);
               } else {
                  return !(var5 > 1.2) && !(var7 > 0.2) ? var1.a(Input.JUMP, true) : var1;
               }
            } else {
               return var1;
            }
         }
      } else {
         MovementStatus var10 = MovementStatus.SUCCESS;
         MovementState var2 = var1;
         var1.a = var10;
         return var2;
      }
   }

   @Override
   public final boolean b(MovementState var1) {
      return var1.a != MovementStatus.RUNNING || this.a == 0;
   }
}
