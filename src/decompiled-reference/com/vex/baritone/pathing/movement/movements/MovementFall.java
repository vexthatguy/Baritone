package com.vex.baritone.pathing.movement.movements;

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
import com.vex.baritone.utils.pathing.MutableMoveResult;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2399;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3621;

public class MovementFall extends Movement {
   private static final class_1799 a = new class_1799(class_1802.field_8705);
   private static final class_1799 b = new class_1799(class_1802.field_8550);

   public MovementFall(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, a(var2, var3));
   }

   @Override
   public final double a(CalculationContext var1) {
      MutableMoveResult var2 = new MutableMoveResult();
      MovementDescend.a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z, var2);
      return var2.b != super.b.y ? 1000000.0 : var2.a;
   }

   @Override
   public final Set<BetterBlockPos> a() {
      HashSet var1;
      (var1 = new HashSet()).add(super.a);

      for (int var2 = super.a.y - super.b.y; var2 >= 0; var2--) {
         var1.add(super.b.above(var2));
      }

      return var1;
   }

   @Override
   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      BetterBlockPos var2 = super.a.playerFeet();
      Rotation var3 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.getBlockPosCenter(super.b), super.a.playerRotations());
      Rotation var4 = null;
      class_2680 var5;
      (var5 = super.a.world().method_8320(super.b)).method_26204();
      boolean var18;
      if (!(var18 = var5.method_26227().method_15772() instanceof class_3621)) {
         MovementFall var6 = this;
         CalculationContext var7 = new CalculationContext(var6.a);
         MutableMoveResult var8 = new MutableMoveResult();
         if (MovementDescend.a(var7, var6.a.y, var6.b.x, var6.b.z, 0.0, var7.a(var6.b.x, var6.a.y - 2, var6.b.z), var8) && !var2.equals(super.b)) {
            if (!class_1661.method_7380(super.a.player().method_31548().method_7395(a)) || super.a.world().method_27983() == class_1937.field_25180) {
               MovementStatus var11 = MovementStatus.UNREACHABLE;
               MovementState var16 = var1;
               var1.a = var11;
               return var16;
            }

            if (super.a.player().method_73189().field_1351 - super.b.method_10264() < super.a.playerController().getBlockReachDistance()
               && !super.a.player().method_24828()) {
               super.a.player().method_31548().method_61496(super.a.player().method_31548().method_7395(a));
               var4 = new Rotation(var3.getYaw(), 90.0F);
               if (super.a.isLookingAt(super.b) || super.a.isLookingAt(super.b.below())) {
                  var1.a(Input.CLICK_RIGHT, true);
               }
            }
         }
      }

      if (var4 != null) {
         var1.a(new MovementState.MovementTarget(var4, true));
      } else {
         var1.a(new MovementState.MovementTarget(var3, false));
      }

      if (var2.equals(super.b) && (super.a.player().method_73189().field_1351 - var2.method_10264() < 0.094 || var18)) {
         if (!var18) {
            MovementStatus var10 = MovementStatus.SUCCESS;
            MovementState var15 = var1;
            var1.a = var10;
            return var15;
         }

         if (class_1661.method_7380(super.a.player().method_31548().method_7395(b))) {
            super.a.player().method_31548().method_61496(super.a.player().method_31548().method_7395(b));
            if (super.a.player().method_18798().field_1351 >= 0.0) {
               return var1.a(Input.CLICK_RIGHT, true);
            }

            return var1;
         }

         if (super.a.player().method_18798().field_1351 >= 0.0) {
            MovementStatus var9 = MovementStatus.SUCCESS;
            MovementState var14 = var1;
            var1.a = var9;
            return var14;
         }
      }

      class_243 var12 = VecUtils.getBlockPosCenter(super.b);
      if (Math.abs(super.a.player().method_73189().field_1352 + super.a.player().method_18798().field_1352 - var12.field_1352) > 0.1
         || Math.abs(super.a.player().method_73189().field_1350 + super.a.player().method_18798().field_1350 - var12.field_1350) > 0.1) {
         if (!super.a.player().method_24828() && Math.abs(super.a.player().method_18798().field_1351) > 0.4) {
            var1.a(Input.SNEAK, true);
         }

         var1.a(Input.MOVE_FORWARD, true);
      }

      MovementFall var19 = this;
      int var20 = 0;

      class_2350 var10000;
      while (true) {
         if (var20 >= 15) {
            var10000 = null;
            break;
         }

         class_2680 var21;
         if ((var21 = var19.a.world().method_8320(var19.a.playerFeet().below(var20))).method_26204() == class_2246.field_9983) {
            var10000 = (class_2350)var21.method_11654(class_2399.field_11253);
            break;
         }

         var20++;
      }

      Object var17;
      if ((var17 = (class_2382)Optional.ofNullable(var10000).map(class_2350::method_62675).orElse(null)) == null) {
         var17 = super.a.method_10059(super.b);
      } else if (Math.abs(var17.method_10263() * (var12.field_1352 - var17.method_10263() / 2.0 - super.a.player().method_73189().field_1352))
            + Math.abs(var17.method_10260() * (var12.field_1350 - var17.method_10260() / 2.0 - super.a.player().method_73189().field_1350))
         < 0.6) {
         var1.a(Input.MOVE_FORWARD, true);
      } else if (!super.a.player().method_24828()) {
         var1.a(Input.SNEAK, false);
      }

      if (var4 == null) {
         class_243 var13 = new class_243(var12.field_1352 + 0.125 * var17.method_10263(), var12.field_1351, var12.field_1350 + 0.125 * var17.method_10260());
         var1.a(new MovementState.MovementTarget(RotationUtils.calcRotationFromVec3d(super.a.playerHead(), var13, super.a.playerRotations()), false));
      }

      return var1;
   }

   @Override
   public final boolean b(MovementState var1) {
      return super.a.playerFeet().equals(super.a) || var1.a != MovementStatus.RUNNING;
   }

   private static BetterBlockPos[] a(BetterBlockPos var0, BetterBlockPos var1) {
      int var2 = var0.method_10263() - var1.method_10263();
      int var3 = var0.method_10260() - var1.method_10260();
      BetterBlockPos[] var5 = new BetterBlockPos[Math.abs(var0.method_10264() - var1.method_10264()) + 2];

      for (int var4 = 0; var4 < var5.length; var4++) {
         var5[var4] = new BetterBlockPos(var0.method_10263() - var2, var0.method_10264() + 1 - var4, var0.method_10260() - var3);
      }

      return var5;
   }

   @Override
   public final boolean a(MovementState var1) {
      if (var1.a == MovementStatus.WAITING) {
         return true;
      }

      for (int var2 = 0; var2 < 4 && var2 < super.a.length; var2++) {
         if (!MovementHelper.a(super.a, super.a[var2])) {
            return super.a(var1);
         }
      }

      return true;
   }
}
