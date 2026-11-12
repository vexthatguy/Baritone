package com.vex.baritone.pathing.movement.movements;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.VecUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.MovementState;
import com.vex.baritone.utils.BlockStateInterface;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_2399;
import net.minecraft.class_243;
import net.minecraft.class_2482;
import net.minecraft.class_2577;
import net.minecraft.class_2680;
import net.minecraft.class_2771;

public class MovementPillar extends Movement {
   public MovementPillar(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, new BetterBlockPos[]{var2.above(2)}, var2);
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
      class_2680 var4;
      class_2248 var5;
      boolean var6 = (var5 = (var4 = var0.a(var1, var2, var3)).method_26204()) == class_2246.field_9983 || var5 == class_2246.field_10597;
      class_2680 var7 = var0.a(var1, var2 - 1, var3);
      if (!var6) {
         if (var7.method_26204() == class_2246.field_9983 || var7.method_26204() == class_2246.field_10597) {
            return 1000000.0;
         }

         if (var7.method_26204() instanceof class_2482 && var7.method_11654(class_2482.field_11501) == class_2771.field_12681) {
            return 1000000.0;
         }
      }

      if (var5 == class_2246.field_10597 && !d(var0, var1, var2, var3)) {
         return 1000000.0;
      }

      class_2680 var8;
      class_2248 var9;
      if ((var9 = (var8 = var0.a(var1, var2 + 2, var3)).method_26204()) instanceof class_2349) {
         return 1000000.0;
      }

      class_2680 var10 = null;
      if (MovementHelper.d(var8) && MovementHelper.d(var4) && MovementHelper.d(var10 = var0.a(var1, var2 + 1, var3))) {
         return 8.51063829787234;
      }

      double var11 = 0.0;
      if (!var6) {
         if ((var11 = var0.a(var1, var2, var3, var4)) >= 1000000.0) {
            return 1000000.0;
         }

         if (var7.method_26204() instanceof class_2189) {
            var11 += 0.1;
         }
      }

      if (MovementHelper.f(var4) && !MovementHelper.a(var0.a, var1, var3, var7) || MovementHelper.f(var7) && var0.j) {
         return 1000000.0;
      }

      if ((var5 == class_2246.field_10588 || var5 instanceof class_2577) && !var7.method_26227().method_15769()) {
         return 1000000.0;
      }

      double var13;
      if ((var13 = MovementHelper.a(var0, var1, var2 + 2, var3, var8, true)) >= 1000000.0) {
         return 1000000.0;
      }

      if (var13 != 0.0) {
         if (var9 != class_2246.field_9983 && var9 != class_2246.field_10597) {
            if (var0.a(var1, var2 + 3, var3).method_26204() instanceof class_2346) {
               if (var10 == null) {
                  var10 = var0.a(var1, var2 + 1, var3);
               }

               if (!(var9 instanceof class_2346) || !(var10.method_26204() instanceof class_2346)) {
                  return 1000000.0;
               }
            }
         } else {
            var13 = 0.0;
         }
      }

      return var6 ? 8.51063829787234 + var13 * 5.0 : JUMP_ONE_BLOCK_COST + var11 + var0.e + var13;
   }

   private static boolean d(CalculationContext var0, int var1, int var2, int var3) {
      return MovementHelper.h(var0.a(var1 + 1, var2, var3))
         || MovementHelper.h(var0.a(var1 - 1, var2, var3))
         || MovementHelper.h(var0.a(var1, var2, var3 + 1))
         || MovementHelper.h(var0.a(var1, var2, var3 - 1));
   }

   public static BetterBlockPos a(CalculationContext var0, BetterBlockPos var1) {
      if (MovementHelper.h(var0.a(var1.north()))) {
         return var1.north();
      } else if (MovementHelper.h(var0.a(var1.south()))) {
         return var1.south();
      } else if (MovementHelper.h(var0.a(var1.east()))) {
         return var1.east();
      } else {
         return MovementHelper.h(var0.a(var1.west())) ? var1.west() : null;
      }
   }

   @Override
   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      if (super.a.playerFeet().y < super.a.y) {
         MovementStatus var20 = MovementStatus.UNREACHABLE;
         MovementState var27 = var1;
         var1.a = var20;
         return var27;
      }

      class_2680 var2;
      if (MovementHelper.d(var2 = BlockStateInterface.a(super.a, (class_2338)super.a)) && MovementHelper.d(super.a, super.b)) {
         var1.a(
            new MovementState.MovementTarget(
               RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.getBlockPosCenter(super.b), super.a.playerRotations()), false
            )
         );
         class_243 var28 = VecUtils.getBlockPosCenter(super.b);
         if (Math.abs(super.a.player().method_73189().field_1352 - var28.field_1352) > 0.2
            || Math.abs(super.a.player().method_73189().field_1350 - var28.field_1350) > 0.2) {
            var1.a(Input.MOVE_FORWARD, true);
         }

         if (super.a.playerFeet().equals(super.b)) {
            MovementStatus var19 = MovementStatus.SUCCESS;
            MovementState var26 = var1;
            var1.a = var19;
            return var26;
         } else {
            return var1;
         }
      } else {
         boolean var3 = var2.method_26204() == class_2246.field_9983 || var2.method_26204() == class_2246.field_10597;
         boolean var4 = var2.method_26204() == class_2246.field_10597;
         Rotation var5 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.getBlockPosCenter(super.c), super.a.playerRotations());
         if (!var3) {
            var1.a(new MovementState.MovementTarget(super.a.playerRotations().withPitch(var5.getPitch()), true));
         }

         boolean var6 = MovementHelper.b(super.a, super.a) || var3;
         if (var3) {
            BetterBlockPos var29;
            if ((
                  var29 = var4
                     ? a(new CalculationContext(super.a), super.a)
                     : super.a.relative(((class_2350)var2.method_11654(class_2399.field_11253)).method_10153())
               )
               == null) {
               this.logDirect("Unable to climb vines. Consider disabling allowVines.");
               MovementStatus var18 = MovementStatus.UNREACHABLE;
               MovementState var25 = var1;
               var1.a = var18;
               return var25;
            }

            if (!super.a.playerFeet().equals(var29.method_10084()) && !super.a.playerFeet().equals(super.b)) {
               if (MovementHelper.c(BlockStateInterface.a(super.a, (class_2338)super.a.below()))) {
                  var1.a(Input.JUMP, true);
               }

               MovementHelper.a(super.a, var1, var29);
               return var1;
            } else {
               MovementStatus var17 = MovementStatus.SUCCESS;
               MovementState var24 = var1;
               var1.a = var17;
               return var24;
            }
         } else {
            if (!((Baritone)super.a).a.a(true, super.a.x, super.a.y, super.a.z)) {
               MovementStatus var16 = MovementStatus.UNREACHABLE;
               MovementState var23 = var1;
               var1.a = var16;
               return var23;
            }

            var1.a(
               Input.SNEAK,
               super.a.player().method_73189().field_1351 > super.b.method_10264() || super.a.player().method_73189().field_1351 < super.a.method_10264() + 0.2
            );
            double var7 = super.a.player().method_73189().field_1352 - (super.b.method_10263() + 0.5);
            double var9 = super.a.player().method_73189().field_1350 - (super.b.method_10260() + 0.5);
            double var11 = Math.sqrt(var7 * var7 + var9 * var9);
            double var13 = Math.sqrt(
               super.a.player().method_18798().field_1352 * super.a.player().method_18798().field_1352
                  + super.a.player().method_18798().field_1350 * super.a.player().method_18798().field_1350
            );
            if (var11 > 0.17) {
               var1.a(Input.MOVE_FORWARD, true);
               var1.a(new MovementState.MovementTarget(var5, true));
            } else if (var13 < 0.05) {
               var1.a(Input.JUMP, super.a.player().method_73189().field_1351 < super.b.method_10264());
            }

            if (!var6) {
               if (!((var2 = BlockStateInterface.a(super.a, (class_2338)super.a)).method_26204() instanceof class_2189) && !var2.method_45474()) {
                  RotationUtils.reachable(super.a, super.a, super.a.playerController().getBlockReachDistance())
                     .map(var0 -> new MovementState.MovementTarget(var0, true))
                     .ifPresent(var1::a);
                  var1.a(Input.JUMP, false);
                  var1.a(Input.CLICK_LEFT, true);
                  var6 = false;
               } else if (super.a.player().method_18276()
                  && (super.a.isLookingAt(super.a.below()) || super.a.isLookingAt(super.a))
                  && super.a.player().method_73189().field_1351 > super.b.method_10264() + 0.1) {
                  var1.a(Input.CLICK_RIGHT, true);
               }
            }

            if (super.a.playerFeet().equals(super.b) && var6) {
               MovementStatus var15 = MovementStatus.SUCCESS;
               MovementState var22 = var1;
               var1.a = var15;
               return var22;
            } else {
               return var1;
            }
         }
      }
   }

   @Override
   public final boolean a(MovementState var1) {
      class_2248 var2;
      if ((super.a.playerFeet().equals(super.a) || super.a.playerFeet().equals(super.a.below()))
         && ((var2 = BlockStateInterface.a(super.a, super.a.below())) == class_2246.field_9983 || var2 == class_2246.field_10597)) {
         var1.a(Input.SNEAK, true);
      }

      return MovementHelper.d(super.a, super.b.above()) ? true : super.a(var1);
   }
}
