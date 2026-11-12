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
import com.vex.baritone.utils.pathing.MutableMoveResult;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2350;
import net.minecraft.class_2510;
import net.minecraft.class_2680;
import net.minecraft.class_3621;

public class MovementParkour extends Movement {
   private static final BetterBlockPos[] b = new BetterBlockPos[0];
   private final class_2350 a;
   private final int a;
   private final boolean a;

   private MovementParkour(IBaritone var1, BetterBlockPos var2, int var3, class_2350 var4, boolean var5) {
      super(var1, var2, var2.relative(var4, var3).above(var5 ? 1 : 0), b, var2.relative(var4, var3).below(var5 ? 0 : 1));
      this.a = var4;
      this.a = var3;
      this.a = var5;
   }

   public static MovementParkour a(CalculationContext var0, BetterBlockPos var1, class_2350 var2) {
      MutableMoveResult var3 = new MutableMoveResult();
      a(var0, var1.x, var1.y, var1.z, var2, var3);
      int var4 = Math.abs(var3.a - var1.x) + Math.abs(var3.c - var1.z);
      return new MovementParkour(var0.a, var1, var4, var2, var3.b > var1.y);
   }

   public static void a(CalculationContext var0, int var1, int var2, int var3, class_2350 var4, MutableMoveResult var5) {
      if (var0.f) {
         if (var0.h || var2 < var0.a.method_31600()) {
            int var6 = var4.method_10148();
            int var16 = var4.method_10165();
            if (MovementHelper.b(var0, var1 + var6, var2, var3 + var16)) {
               class_2680 var7 = var0.a(var1 + var6, var2 - 1, var3 + var16);
               if (!MovementHelper.b(var0, var1 + var6, var2 - 1, var3 + var16, var7)) {
                  if (!MovementHelper.b(var7) || var7.method_26227().method_15772() instanceof class_3621) {
                     if (MovementHelper.b(var0, var1 + var6, var2 + 1, var3 + var16)) {
                        if (MovementHelper.b(var0, var1 + var6, var2 + 2, var3 + var16)) {
                           if (MovementHelper.b(var0, var1, var2 + 2, var3)) {
                              if ((var7 = var0.a(var1, var2 - 1, var3)).method_26204() != class_2246.field_10597
                                 && var7.method_26204() != class_2246.field_9983
                                 && !(var7.method_26204() instanceof class_2510)
                                 && !MovementHelper.c(var7)) {
                                 if (!var0.j || var7.method_26227().method_15769()) {
                                    if (var0.a(var1, var2, var3).method_26227().method_15769()) {
                                       byte var18;
                                       if (var7.method_26204() == class_2246.field_10114) {
                                          var18 = 2;
                                       } else if (var0.d) {
                                          var18 = 4;
                                       } else {
                                          var18 = 3;
                                       }

                                       int var8 = 1;

                                       for (int var9 = 2; var9 <= var18; var8 = var9++) {
                                          int var10 = var1 + var6 * var9;
                                          int var11 = var3 + var16 * var9;
                                          if (!MovementHelper.b(var0, var10, var2 + 1, var11) || !MovementHelper.b(var0, var10, var2 + 2, var11)) {
                                             break;
                                          }

                                          class_2680 var12 = var0.a.a(var10, var2, var11);
                                          if (!MovementHelper.a(var0, var12)) {
                                             if (var9 <= 3
                                                && var0.i
                                                && var0.d
                                                && MovementHelper.b(var0, var10, var2, var11, var12)
                                                && d(var0.a, var10 + var6, var2 + 1, var11 + var16)) {
                                                var5.a = var10;
                                                var5.b = var2 + 1;
                                                var5.c = var11;
                                                var5.a = var9 * 3.563791874554526 + var0.e;
                                                return;
                                             }
                                             break;
                                          }

                                          class_2680 var14;
                                          if ((var14 = var0.a.a(var10, var2 - 1, var11)).method_26204() != class_2246.field_10362
                                                && MovementHelper.b(var0, var10, var2 - 1, var11, var14)
                                             || Math.min(16, var0.a + 2) >= var9 && MovementHelper.b(var0, var14)) {
                                             if (d(var0.a, var10 + var6, var2, var11 + var16)) {
                                                var5.a = var10;
                                                var5.b = var2;
                                                var5.c = var11;
                                                var5.a = a(var9) + var0.e;
                                                return;
                                             }
                                             break;
                                          }

                                          if (!MovementHelper.b(var0, var10, var2 + 3, var11)) {
                                             break;
                                          }
                                       }

                                       if (var0.g) {
                                          for (int var21 = var8; var21 > 1; var21--) {
                                             int var22 = var1 + var21 * var6;
                                             int var23 = var3 + var21 * var16;
                                             class_2680 var24 = var0.a(var22, var2 - 1, var23);
                                             double var26;
                                             if (!((var26 = var0.a(var22, var2 - 1, var23, var24)) >= 1000000.0)
                                                && MovementHelper.a(var22, var23, var24, var0.a)
                                                && d(var0.a, var22 + var6, var2, var23 + var16)) {
                                                for (int var19 = 0; var19 < 5; var19++) {
                                                   var8 = var22 + a[var19].method_10148();
                                                   int var25 = var2 - 1 + a[var19].method_10164();
                                                   int var13 = var23 + a[var19].method_10165();
                                                   if ((var8 != var22 - var6 || var13 != var23 - var16) && MovementHelper.c(var0.a, var8, var25, var13)) {
                                                      var5.a = var22;
                                                      var5.b = var2;
                                                      var5.c = var23;
                                                      var5.a = a(var21) + var26 + var0.e;
                                                      return;
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean d(BlockStateInterface var0, int var1, int var2, int var3) {
      return !MovementHelper.b(var0.a(var1, var2, var3)) && !MovementHelper.b(var0.a(var1, var2 + 1, var3));
   }

   private static double a(int var0) {
      switch (var0) {
         case 2:
            return 9.26569376882094;
         case 3:
            return 13.89854065323141;
         case 4:
            return 14.255167498218103;
         default:
            throw new IllegalStateException("LOL " + var0);
      }
   }

   @Override
   public final double a(CalculationContext var1) {
      MutableMoveResult var2 = new MutableMoveResult();
      a(var1, super.a.x, super.a.y, super.a.z, this.a, var2);
      return var2.a == super.b.x && var2.b == super.b.y && var2.c == super.b.z ? var2.a : 1000000.0;
   }

   @Override
   public final Set<BetterBlockPos> a() {
      HashSet var1 = new HashSet();

      for (int var2 = 0; var2 <= this.a; var2++) {
         for (int var3 = 0; var3 < 2; var3++) {
            var1.add(super.a.relative(this.a, var2).above(var3));
         }
      }

      return var1;
   }

   @Override
   public final boolean b(MovementState var1) {
      return var1.a != MovementStatus.RUNNING;
   }

   @Override
   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      }

      if (super.a.playerFeet().y < super.a.y) {
         this.logDebug("sorry");
         MovementStatus var9 = MovementStatus.UNREACHABLE;
         MovementState var8 = var1;
         var1.a = var9;
         return var8;
      }

      if (this.a >= 4 || this.a) {
         var1.a(Input.SPRINT, true);
      }

      MovementHelper.a(super.a, var1, super.b);
      if (super.a.playerFeet().equals(super.b)) {
         class_2248 var2;
         if ((var2 = BlockStateInterface.a(super.a, super.b)) == class_2246.field_10597 || var2 == class_2246.field_9983) {
            MovementStatus var3 = MovementStatus.SUCCESS;
            MovementState var6 = var1;
            var1.a = var3;
            return var6;
         }

         if (super.a.player().method_73189().field_1351 - super.a.playerFeet().method_10264() < 0.094) {
            var1.a = MovementStatus.SUCCESS;
         }
      } else if (!super.a.playerFeet().equals(super.a)) {
         if (!super.a.playerFeet().equals(super.a.relative(this.a)) && !(super.a.player().method_73189().field_1351 - super.a.y > 1.0E-4)) {
            if (!super.a.playerFeet().equals(super.b.relative(this.a, -1))) {
               var1.a(Input.SPRINT, false);
               if (super.a.playerFeet().equals(super.a.relative(this.a, -1))) {
                  MovementHelper.a(super.a, var1, super.a);
               } else {
                  MovementHelper.a(super.a, var1, super.a.relative(this.a, -1));
               }
            }
         } else {
            if (Baritone.a().allowPlace.value
               && ((Baritone)super.a).a.a()
               && !MovementHelper.b(super.a, super.b.below())
               && !super.a.player().method_24828()
               && MovementHelper.a(var1, super.a, super.b.below(), true, false) == MovementHelper.PlaceResult.a) {
               var1.a(Input.CLICK_RIGHT, true);
            }

            if (this.a == 3 && !this.a) {
               double var7 = super.a.x + 0.5 - super.a.player().method_73189().field_1352;
               double var4 = super.a.z + 0.5 - super.a.player().method_73189().field_1350;
               if (Math.max(Math.abs(var7), Math.abs(var4)) < 0.7) {
                  return var1;
               }
            }

            var1.a(Input.JUMP, true);
         }
      }

      return var1;
   }
}
