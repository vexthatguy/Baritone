package com.vex.baritone.pathing.movement;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.ActionCosts;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.RayTraceUtils;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.VecUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.precompute.PrecomputedData;
import com.vex.baritone.pathing.precompute.Ternary;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.ToolSet;
import java.util.Iterator;
import java.util.Optional;
import net.minecraft.class_10;
import net.minecraft.class_1304;
import net.minecraft.class_1893;
import net.minecraft.class_2189;
import net.minecraft.class_2190;
import net.minecraft.class_2211;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2334;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_2360;
import net.minecraft.class_2383;
import net.minecraft.class_2384;
import net.minecraft.class_239;
import net.minecraft.class_2397;
import net.minecraft.class_2404;
import net.minecraft.class_243;
import net.minecraft.class_2480;
import net.minecraft.class_2482;
import net.minecraft.class_2484;
import net.minecraft.class_2488;
import net.minecraft.class_2506;
import net.minecraft.class_2510;
import net.minecraft.class_2533;
import net.minecraft.class_2553;
import net.minecraft.class_2577;
import net.minecraft.class_2667;
import net.minecraft.class_2680;
import net.minecraft.class_2746;
import net.minecraft.class_2760;
import net.minecraft.class_2771;
import net.minecraft.class_2778;
import net.minecraft.class_3609;
import net.minecraft.class_3610;
import net.minecraft.class_3611;
import net.minecraft.class_3612;
import net.minecraft.class_3621;
import net.minecraft.class_3736;
import net.minecraft.class_3965;
import net.minecraft.class_4770;
import net.minecraft.class_5542;
import net.minecraft.class_5546;
import net.minecraft.class_5689;
import net.minecraft.class_5800;
import net.minecraft.class_6880;
import net.minecraft.class_2350.class_2351;
import net.minecraft.class_239.class_240;

public interface MovementHelper extends ActionCosts, Helper {
   static boolean a(BlockStateInterface var0, int var1, int var2, int var3, class_2680 var4) {
      if (!var0.a.b(var1, var3)) {
         return true;
      }

      class_2248 var5 = var4.method_26204();
      return Baritone.a().blocksToDisallowBreaking.value.contains(var5)
         || var5 == class_2246.field_10295
         || var5 instanceof class_2384
         || a(var0, var1, var2 + 1, var3, true)
         || a(var0, var1 + 1, var2, var3, false)
         || a(var0, var1 - 1, var2, var3, false)
         || a(var0, var1, var2, var3 + 1, false)
         || a(var0, var1, var2, var3 - 1, false);
   }

   static boolean a(BlockStateInterface var0, int var1, int var2, int var3, boolean var4) {
      class_2680 var5;
      class_2248 var6 = (var5 = var0.a(var1, var2, var3)).method_26204();
      if (!var4 && var6 instanceof class_2346 && Baritone.a().avoidUpdatingFallingBlocks.value && class_2346.method_10128(var0.a(var1, var2 - 1, var3))) {
         return true;
      }

      if (var6 instanceof class_2404) {
         if (var4 || Baritone.a().strictLiquidCheck.value) {
            return true;
         } else {
            return var5.method_11654(class_2404.field_11278) == 0 ? true : !(var0.a(var1, var2 - 1, var3).method_26204() instanceof class_2404);
         }
      } else {
         return !var5.method_26227().method_15769();
      }
   }

   static boolean a(IPlayerContext var0, BetterBlockPos var1) {
      return a(new BlockStateInterface(var0), var1.x, var1.y, var1.z);
   }

   static boolean a(BlockStateInterface var0, int var1, int var2, int var3) {
      return b(var0, var1, var2, var3, var0.a(var1, var2, var3));
   }

   static boolean a(CalculationContext var0, int var1, int var2, int var3, class_2680 var4) {
      return var0.a.a(var0.a, var1, var2, var3, var4);
   }

   static boolean a(CalculationContext var0, int var1, int var2, int var3) {
      return var0.a.a(var0.a, var1, var2, var3, var0.a(var1, var2, var3));
   }

   static boolean b(BlockStateInterface var0, int var1, int var2, int var3, class_2680 var4) {
      Ternary var5;
      if ((var5 = a(var4)) == Ternary.a) {
         return true;
      } else {
         return var5 == Ternary.c ? false : c(var0, var1, var2, var3, var4);
      }
   }

   static Ternary a(class_2680 var0) {
      class_2248 var1;
      if ((var1 = var0.method_26204()) instanceof class_2189) {
         return Ternary.a;
      }

      if (var1 instanceof class_4770
         || var1 == class_2246.field_10589
         || var1 == class_2246.field_10343
         || var1 == class_2246.field_10027
         || var1 == class_2246.field_10302
         || var1 instanceof class_2190
         || var1 == class_2246.field_10422
         || var1 instanceof class_2480
         || var1 instanceof class_2482
         || var1 instanceof class_2533
         || var1 == class_2246.field_21211
         || var1 == class_2246.field_10455
         || var1 == class_2246.field_16999
         || var1 == class_2246.field_28048
         || var1 instanceof class_5542
         || var1 instanceof class_5800) {
         return Ternary.c;
      }

      if (var1 == class_2246.field_28682) {
         return Ternary.c;
      }

      if (var1 == class_2246.field_27879) {
         return Ternary.c;
      }

      if (Baritone.a().blocksToAvoid.value.contains(var1)) {
         return Ternary.c;
      }

      if (!(var1 instanceof class_2323) && !(var1 instanceof class_2349)) {
         if (var1 instanceof class_2577) {
            return Ternary.b;
         } else if (var1 instanceof class_2488) {
            return Ternary.b;
         } else {
            class_3610 var2;
            if (!(var2 = var0.method_26227()).method_15769()) {
               return var2.method_15772().method_15779(var2) != 8 ? Ternary.c : Ternary.b;
            } else if (var1 instanceof class_5546) {
               return Ternary.c;
            } else {
               return var0.method_26171(class_10.field_50) ? Ternary.a : Ternary.c;
            }
         }
      } else {
         return var1 == class_2246.field_9973 ? Ternary.c : Ternary.a;
      }
   }

   static boolean c(BlockStateInterface var0, int var1, int var2, int var3, class_2680 var4) {
      class_2248 var5;
      if ((var5 = var4.method_26204()) instanceof class_2577) {
         return b(var0, var1, var2 - 1, var3);
      }

      if (var5 instanceof class_2488) {
         if (!var0.a(var1, var3)) {
            return true;
         } else {
            return var4.method_11654(class_2488.field_11518) >= 3 ? false : b(var0, var1, var2 - 1, var3);
         }
      } else {
         class_3610 var7;
         if (!(var7 = var4.method_26227()).method_15769()) {
            if (a(var1, var2, var3, var4, var0)) {
               return false;
            }

            if (Baritone.a().assumeWalkOnWater.value) {
               return false;
            }

            class_2680 var6;
            return (var6 = var0.a(var1, var2 + 1, var3)).method_26227().method_15769() && !(var6.method_26204() instanceof class_2553)
               ? var7.method_15772() instanceof class_3621
               : false;
         } else {
            return var4.method_26171(class_10.field_50);
         }
      }
   }

   static Ternary b(class_2680 var0) {
      class_2248 var1;
      if ((var1 = var0.method_26204()) instanceof class_2189) {
         return Ternary.a;
      } else if (var1 instanceof class_4770
         || var1 == class_2246.field_10589
         || var1 == class_2246.field_10343
         || var1 == class_2246.field_10597
         || var1 == class_2246.field_9983
         || var1 == class_2246.field_10302
         || var1 instanceof class_5800
         || var1 instanceof class_2323
         || var1 instanceof class_2349
         || var1 instanceof class_2488
         || !var0.method_26227().method_15769()
         || var1 instanceof class_2533
         || var1 instanceof class_2334
         || var1 instanceof class_2484
         || var1 instanceof class_2480) {
         return Ternary.c;
      } else {
         return var0.method_26171(class_10.field_50) ? Ternary.a : Ternary.c;
      }
   }

   static boolean b(CalculationContext var0, int var1, int var2, int var3) {
      return a(var0, var0.a(var1, var2, var3));
   }

   static boolean a(CalculationContext var0, class_2680 var1) {
      PrecomputedData var4 = var0.a;
      int var2 = class_2248.field_10651.method_10206(var1);
      int var3;
      if (((var3 = var4.a[var2]) & 1) == 0) {
         var3 = var4.a(var2, var1);
      }

      return (var3 & 64) != 0 ? a(var1) : (var3 & 32) != 0;
   }

   static boolean a(IPlayerContext var0, class_2338 var1) {
      class_2680 var2;
      Ternary var3;
      if ((var3 = b(var2 = var0.world().method_8320(var1))) == Ternary.a) {
         return true;
      } else {
         return var3 == Ternary.c ? false : var2.method_26171(class_10.field_50);
      }
   }

   static boolean a(class_2680 var0) {
      return var0.method_26171(class_10.field_50);
   }

   static boolean a(int var0, int var1, class_2680 var2, BlockStateInterface var3) {
      class_2248 var4;
      if ((var4 = var2.method_26204()) instanceof class_2189) {
         return true;
      } else if (var4 instanceof class_2488) {
         return !var3.a(var0, var1) ? true : (Integer)var2.method_11654(class_2488.field_11518) == 1;
      } else {
         return var4 != class_2246.field_10313 && var4 != class_2246.field_10214 ? var2.method_45474() : true;
      }
   }

   static boolean a(IPlayerContext var0, BetterBlockPos var1, BetterBlockPos var2) {
      if (var2.equals(var1)) {
         return false;
      }

      class_2680 var3;
      return !((var3 = BlockStateInterface.a(var0, (class_2338)var1)).method_26204() instanceof class_2323)
         ? true
         : a(var1, var3, var2, class_2323.field_10945);
   }

   static boolean b(IPlayerContext var0, BetterBlockPos var1, BetterBlockPos var2) {
      if (var2.equals(var1)) {
         return false;
      }

      class_2680 var3;
      return !((var3 = BlockStateInterface.a(var0, (class_2338)var1)).method_26204() instanceof class_2349)
         ? true
         : (Boolean)var3.method_11654(class_2349.field_11026);
   }

   static boolean a(BetterBlockPos var0, class_2680 var1, BetterBlockPos var2, class_2746 var3) {
      if (var2.equals(var0)) {
         return false;
      }

      class_2351 var4 = ((class_2350)var1.method_11654(class_2383.field_11177)).method_10166();
      boolean var6 = (Boolean)var1.method_11654(var3);
      class_2351 var5;
      if (!var2.method_10095().equals(var0) && !var2.method_10072().equals(var0)) {
         if (!var2.method_10078().equals(var0) && !var2.method_10067().equals(var0)) {
            return true;
         }

         var5 = class_2351.field_11048;
      } else {
         var5 = class_2351.field_11051;
      }

      return var4 == var5 == var6;
   }

   static boolean b(class_2680 var0) {
      class_2248 var1 = var0.method_26204();
      return !var0.method_26227().method_15769()
         || var1 == class_2246.field_10092
         || var1 == class_2246.field_10029
         || var1 == class_2246.field_16999
         || var1 instanceof class_4770
         || var1 == class_2246.field_10027
         || var1 == class_2246.field_10343
         || var1 == class_2246.field_10422;
   }

   static boolean d(BlockStateInterface var0, int var1, int var2, int var3, class_2680 var4) {
      Ternary var5;
      if ((var5 = c(var4)) == Ternary.a) {
         return true;
      } else {
         return var5 == Ternary.c ? false : e(var0, var1, var2, var3, var4);
      }
   }

   static Ternary c(class_2680 var0) {
      class_2248 var1 = var0.method_26204();
      if (h(var0) && var1 != class_2246.field_10092 && var1 != class_2246.field_10422 && var1 != class_2246.field_21211) {
         return Ternary.a;
      }

      if (var1 instanceof class_5800) {
         return Ternary.a;
      }

      if (var1 != class_2246.field_9983 && (var1 != class_2246.field_10597 || !Baritone.a().allowVines.value)) {
         if (var1 == class_2246.field_10362 || var1 == class_2246.field_10194 || var1 == class_2246.field_10114) {
            return Ternary.a;
         } else if (var1 == class_2246.field_10443 || var1 == class_2246.field_10034 || var1 == class_2246.field_10380) {
            return Ternary.a;
         } else if (var1 == class_2246.field_10033 || var1 instanceof class_2506) {
            return Ternary.a;
         } else if (var1 instanceof class_2510) {
            return Ternary.a;
         } else if (d(var0)) {
            return Ternary.b;
         } else if (e(var0) && Baritone.a().assumeWalkOnLava.value) {
            return Ternary.b;
         } else if (!(var1 instanceof class_2482)) {
            return Ternary.c;
         } else if (Baritone.a().allowWalkOnBottomSlab.value) {
            return Ternary.a;
         } else {
            return var0.method_11654(class_2482.field_11501) != class_2771.field_12681 ? Ternary.a : Ternary.c;
         }
      } else {
         return Ternary.a;
      }
   }

   static boolean e(BlockStateInterface var0, int var1, int var2, int var3, class_2680 var4) {
      var4.method_26204();
      if (d(var4)) {
         class_2680 var5;
         class_2248 var6;
         if ((var6 = (var5 = var0.a(var1, var2 + 1, var3)).method_26204()) != class_2246.field_10588 && !(var6 instanceof class_2577)) {
            return !a(var1, var2, var3, var4, var0) && var5.method_26227().method_15772() != class_3612.field_15909
               ? d(var5) ^ Baritone.a().assumeWalkOnWater.value
               : d(var5) && !Baritone.a().assumeWalkOnWater.value;
         } else {
            return true;
         }
      } else {
         return e(var4) && !a(var1, var2, var3, var4, var0) && Baritone.a().assumeWalkOnLava.value;
      }
   }

   static boolean b(CalculationContext var0, int var1, int var2, int var3, class_2680 var4) {
      class_2680 var5 = var4;
      int var12 = var3;
      var3 = var2;
      var2 = var1;
      BlockStateInterface var9 = var0.a;
      PrecomputedData var8 = var0.a;
      int var6 = class_2248.field_10651.method_10206(var5);
      int var7;
      if (((var7 = var8.a[var6]) & 1) == 0) {
         var7 = var8.a(var6, var5);
      }

      return (var7 & 4) != 0 ? e(var9, var2, var3, var12, var5) : (var7 & 2) != 0;
   }

   static boolean c(CalculationContext var0, int var1, int var2, int var3) {
      return b(var0, var1, var2, var3, var0.a(var1, var2, var3));
   }

   static boolean a(IPlayerContext var0, BetterBlockPos var1, class_2680 var2) {
      return d(new BlockStateInterface(var0), var1.x, var1.y, var1.z, var2);
   }

   static boolean b(IPlayerContext var0, class_2338 var1) {
      return b(new BlockStateInterface(var0), var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   static boolean b(IPlayerContext var0, BetterBlockPos var1) {
      return b(new BlockStateInterface(var0), var1.x, var1.y, var1.z);
   }

   static boolean b(BlockStateInterface var0, int var1, int var2, int var3) {
      return d(var0, var1, var2, var3, var0.a(var1, var2, var3));
   }

   static boolean b(CalculationContext var0, class_2680 var1) {
      return var0.a != 0 && var1 == class_2360.method_51170() && (Integer)var1.method_11654(class_2404.field_11278) == 0;
   }

   static boolean c(IPlayerContext var0, BetterBlockPos var1) {
      boolean var2 = false;
      class_1304[] var3;
      int var4 = (var3 = class_1304.values()).length;

      label29:
      for (int var5 = 0; var5 < var4; var5++) {
         class_1304 var6 = var3[var5];
         Iterator var8 = var0.player().method_6118(var6).method_58657().method_57534().iterator();

         while (var8.hasNext()) {
            if (((class_6880)var8.next()).method_40225(class_1893.field_9122)) {
               var2 = true;
               break label29;
            }
         }
      }

      class_2680 var7 = BlockStateInterface.a(var0, (class_2338)var1);
      return var2 && var7 == class_2360.method_51170() && (Integer)var7.method_11654(class_2404.field_11278) == 0;
   }

   static boolean c(CalculationContext var0, int var1, int var2, int var3, class_2680 var4) {
      class_2248 var5;
      if ((var5 = var4.method_26204()) != class_2246.field_9983 && var5 != class_2246.field_10597) {
         if (!var4.method_26227().method_15769()) {
            if (var5 instanceof class_2482) {
               if (var4.method_11654(class_2482.field_11501) != class_2771.field_12681) {
                  return true;
               }
            } else if (var5 instanceof class_2510) {
               if (var4.method_11654(class_2510.field_11572) == class_2760.field_12619) {
                  return true;
               }

               class_2778 var6;
               if ((var6 = (class_2778)var4.method_11654(class_2510.field_11565)) == class_2778.field_12712 || var6 == class_2778.field_12713) {
                  return true;
               }
            } else if (var5 instanceof class_2533) {
               if (!(Boolean)var4.method_11654(class_2533.field_11631) && var4.method_11654(class_2533.field_11625) == class_2760.field_12619) {
                  return true;
               }
            } else {
               if (var5 == class_2246.field_16492) {
                  return true;
               }

               if (var5 instanceof class_2397) {
                  return true;
               }
            }

            if (var0.j) {
               return false;
            }

            if (var0.a(var1, var2 + 1, var3) instanceof class_2404) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   static boolean c(BlockStateInterface var0, int var1, int var2, int var3) {
      return a(var0, var1, var3, var0.a(var1, var2, var3));
   }

   static boolean a(BlockStateInterface var0, class_2338 var1) {
      return c(var0, var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   static boolean c(IPlayerContext var0, class_2338 var1) {
      return a(new BlockStateInterface(var0), var1);
   }

   static boolean a(BlockStateInterface var0, int var1, int var2, class_2680 var3) {
      return !var0.a.b(var1, var2) ? false : h(var3) || var3.method_26204() == class_2246.field_10033 || var3.method_26204() instanceof class_2506;
   }

   static double a(CalculationContext var0, int var1, int var2, int var3, boolean var4) {
      return a(var0, var1, var2, var3, var0.a(var1, var2, var3), var4);
   }

   static double a(CalculationContext var0, int var1, int var2, int var3, class_2680 var4, boolean var5) {
      var4.method_26204();
      if (!a(var0, var1, var2, var3, var4)) {
         if (!var4.method_26227().method_15769()) {
            return 1000000.0;
         }

         double var6;
         if ((var6 = var0.b(var1, var2, var3, var4)) >= 1000000.0) {
            return 1000000.0;
         }

         if (a(var0.a, var1, var2, var3, var4)) {
            return 1000000.0;
         }

         class_2680 var8 = var4;
         ToolSet var12 = var0.a;
         double var14;
         if ((var14 = var0.a.a.computeIfAbsent(var8.method_26204(), var12.a)) <= 0.0) {
            return 1000000.0;
         }

         double var10 = (1.0 / var14 + var0.c) * var6;
         if (var5 && (var4 = var0.a(var1, var2 + 1, var3)).method_26204() instanceof class_2346) {
            var10 += a(var0, var1, var2 + 1, var3, var4, true);
         }

         return var10;
      } else {
         return 0.0;
      }
   }

   static boolean c(class_2680 var0) {
      return var0.method_26204() instanceof class_2482 && var0.method_11654(class_2482.field_11501) == class_2771.field_12681;
   }

   static void a(IPlayerContext var0, class_2680 var1) {
      a(var0, var1, new ToolSet(var0.player()), BaritoneAPI.getSettings().preferSilkTouch.value);
   }

   static void a(IPlayerContext var0, class_2680 var1, ToolSet var2, boolean var3) {
      if (Baritone.a().autoTool.value && !Baritone.a().assumeExternalAutoTool.value) {
         var0.player().method_31548().method_61496(var2.a(var1.method_26204(), var3, false));
      }
   }

   static void a(IPlayerContext var0, MovementState var1, class_2338 var2) {
      var1.a(
            new MovementState.MovementTarget(
               RotationUtils.calcRotationFromVec3d(var0.playerHead(), VecUtils.getBlockPosCenter(var2), var0.playerRotations())
                  .withPitch(var0.playerRotations().getPitch()),
               false
            )
         )
         .a(Input.MOVE_FORWARD, true);
   }

   static boolean d(class_2680 var0) {
      class_3611 var1;
      return (var1 = var0.method_26227().method_15772()) == class_3612.field_15910 || var1 == class_3612.field_15909;
   }

   static boolean d(IPlayerContext var0, BetterBlockPos var1) {
      return d(BlockStateInterface.a(var0, (class_2338)var1));
   }

   static boolean e(class_2680 var0) {
      class_3611 var1;
      return (var1 = var0.method_26227().method_15772()) == class_3612.field_15908 || var1 == class_3612.field_15907;
   }

   static boolean e(IPlayerContext var0, BetterBlockPos var1) {
      return f(BlockStateInterface.a(var0, (class_2338)var1));
   }

   static boolean f(class_2680 var0) {
      return !var0.method_26227().method_15769();
   }

   static boolean g(class_2680 var0) {
      class_3610 var1;
      return (var1 = var0.method_26227()).method_15772() instanceof class_3609 && var1.method_15772().method_15779(var1) != 8;
   }

   static boolean a(int var0, int var1, int var2, class_2680 var3, BlockStateInterface var4) {
      class_3610 var5;
      if (!((var5 = var3.method_26227()).method_15772() instanceof class_3609)) {
         return false;
      } else {
         return var5.method_15772().method_15779(var5) != 8
            ? true
            : g(var4.a(var0 + 1, var1, var2)) || g(var4.a(var0 - 1, var1, var2)) || g(var4.a(var0, var1, var2 + 1)) || g(var4.a(var0, var1, var2 - 1));
      }
   }

   static boolean h(class_2680 var0) {
      class_2248 var1;
      if (!((var1 = var0.method_26204()) instanceof class_2211)
         && !(var1 instanceof class_2667)
         && !(var1 instanceof class_3736)
         && !(var1 instanceof class_2480)
         && !(var1 instanceof class_5689)
         && !(var1 instanceof class_5542)) {
         try {
            return class_2248.method_9614(var0.method_26220(null, null));
         } catch (Exception var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   static MovementHelper.PlaceResult a(MovementState var0, IBaritone var1, class_2338 var2, boolean var3, boolean var4) {
      IPlayerContext var5;
      Optional var6 = RotationUtils.reachable(var5 = var1.getPlayerContext(), var2, var4);
      boolean var7 = false;
      if (var6.isPresent()) {
         var0.a(new MovementState.MovementTarget((Rotation)var6.get(), true));
         var7 = true;
      }

      int var16 = 0;

      while (true) {
         label80: {
            if (var16 < 5) {
               class_2338 var8 = var2.method_10093(Movement.a[var16]);
               if (!c(var5, var8)) {
                  break label80;
               }

               if (!((Baritone)var1).a.a(false, var2.method_10263(), var2.method_10264(), var2.method_10260())) {
                  Helper.HELPER.logDebug("bb pls get me some blocks. dirt, netherrack, cobble");
                  var0.a = MovementStatus.UNREACHABLE;
                  return MovementHelper.PlaceResult.c;
               }

               double var10 = (var2.method_10263() + var8.method_10263() + 1.0) * 0.5;
               double var12 = (var2.method_10264() + var8.method_10264() + 0.5) * 0.5;
               double var14 = (var2.method_10260() + var8.method_10260() + 1.0) * 0.5;
               Rotation var9 = RotationUtils.calcRotationFromVec3d(
                  var4 ? RayTraceUtils.inferSneakingEyePosition(var5.player()) : var5.playerHead(), new class_243(var10, var12, var14), var5.playerRotations()
               );
               Rotation var19 = var1.getLookBehavior().getAimProcessor().peekRotation(var9);
               class_239 var20;
               if ((var20 = RayTraceUtils.rayTraceTowards(var5.player(), var19, var5.playerController().getBlockReachDistance(), var4)) == null
                  || var20.method_17783() != class_240.field_1332
                  || !((class_3965)var20).method_17777().equals(var8)
                  || !((class_3965)var20).method_17777().method_10093(((class_3965)var20).method_17780()).equals(var2)) {
                  break label80;
               }

               var0.a(new MovementState.MovementTarget(var9, true));
               var7 = true;
               if (var3) {
                  break label80;
               }
            }

            if (var5.getSelectedBlock().isPresent()) {
               class_2338 var17 = var5.getSelectedBlock().get();
               class_2350 var18 = ((class_3965)var5.objectMouseOver()).method_17780();
               if (var17.equals(var2) || c(var5, var17) && var17.method_10093(var18).equals(var2)) {
                  if (var4) {
                     var0.a(Input.SNEAK, true);
                  }

                  ((Baritone)var1).a.a(true, var2.method_10263(), var2.method_10264(), var2.method_10260());
                  return MovementHelper.PlaceResult.a;
               }
            }

            if (var7) {
               if (var4) {
                  var0.a(Input.SNEAK, true);
               }

               ((Baritone)var1).a.a(true, var2.method_10263(), var2.method_10264(), var2.method_10260());
               return MovementHelper.PlaceResult.b;
            }

            return MovementHelper.PlaceResult.c;
         }

         var16++;
      }
   }

   static boolean a(class_2248 var0) {
      return var0 instanceof class_2189 || var0 == class_2246.field_10164 || var0 == class_2246.field_10382;
   }

   enum PlaceResult {
      a,
      b,
      c;
   }
}
