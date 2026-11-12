package com.vex.baritone.pathing.calc;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.cache.CachedRegion;
import com.vex.baritone.pathing.calc.openset.BinaryHeapOpenSet;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Moves;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.pathing.BetterWorldBorder;
import com.vex.baritone.utils.pathing.Favoring;
import com.vex.baritone.utils.pathing.MutableMoveResult;
import java.util.Optional;
import net.minecraft.class_2806;
import net.minecraft.class_2818;

public final class AStarPathFinder extends AbstractNodeCostSearch {
   private final Favoring a;
   private final CalculationContext a;

   public AStarPathFinder(BetterBlockPos var1, int var2, int var3, int var4, Goal var5, Favoring var6, CalculationContext var7) {
      super(var1, var2, var3, var4, var5, var7);
      this.a = var6;
      this.a = var7;
   }

   @Override
   protected final Optional<IPath> a(long var1, long var3) {
      int var5 = this.a.a.method_8597().comp_651();
      int var6 = this.a.a.method_8597().comp_652();
      super.a = this.a(super.a, super.b, super.c, BetterBlockPos.longHash(super.a, super.b, super.c));
      super.a.b = 0.0;
      super.a.c = super.a.a;
      BinaryHeapOpenSet var7;
      (var7 = new BinaryHeapOpenSet()).a(super.a);
      double[] var8 = new double[a.length];

      for (int var9 = 0; var9 < var8.length; var9++) {
         var8[var9] = super.a.a;
         super.a[var9] = super.a;
      }

      MutableMoveResult var52 = new MutableMoveResult();
      BetterWorldBorder var10 = new BetterWorldBorder(this.a.a.method_8621());
      long var11 = System.currentTimeMillis();
      boolean var13;
      if (var13 = Baritone.a().slowPath.value) {
         this.logDebug("slowPath is on, path timeout will be " + Baritone.a().slowPathTimeoutMS.value + "ms instead of " + var1 + "ms");
      }

      long var14 = var11 + (var13 ? Baritone.a().slowPathTimeoutMS.value : var1);
      long var16 = var11 + (var13 ? Baritone.a().slowPathTimeoutMS.value : var3);
      boolean var50 = true;
      int var2 = 0;
      int var51 = 0;
      int var4 = 0;
      boolean var18 = !this.a.a.isEmpty();
      int var19 = Baritone.a().pathingMaxChunkBorderFetch.value;
      double var24 = Baritone.a().minimumImprovementRepropagation.value ? 0.01 : 0.0;
      Moves[] var20 = Moves.values();

      long var27;
      while (
         var7.a != 0 && var4 < var19 && !super.a && ((var2 & 63) != 0 || (var27 = System.currentTimeMillis()) - var16 < 0L && (var50 || var27 - var14 < 0L))
      ) {
         if (var13) {
            try {
               Thread.sleep(Baritone.a().slowPathTimeDelayMS.value);
            } catch (InterruptedException var49) {
            }
         }

         BinaryHeapOpenSet var22 = var7;
         if (var7.a == 0) {
            throw new IllegalStateException("Cannot remove from empty heap");
         }

         PathNode var41 = var22.a[1];
         PathNode var42 = var22.a[var22.a];
         var22.a[1] = var42;
         var42.d = 1;
         var22.a[var22.a] = null;
         var22.a--;
         var41.d = -1;
         if (var22.a >= 2) {
            int var30 = 1;
            int var31 = 2;
            double var43 = var42.c;

            do {
               PathNode var32;
               double var45 = (var32 = var22.a[var31]).c;
               if (var31 < var22.a) {
                  PathNode var21;
                  double var47 = (var21 = var22.a[var31 + 1]).c;
                  if (var45 > var47) {
                     var31++;
                     var45 = var47;
                     var32 = var21;
                  }
               }

               if (var43 <= var45) {
                  break;
               }

               var22.a[var30] = var32;
               var22.a[var31] = var42;
               var42.d = var31;
               var32.d = var30;
               var30 = var31;
            } while ((var31 <<= 1) <= var22.a);
         }

         PathNode var58 = var41;
         super.b = var58;
         var2++;
         if (super.a.isInGoal(var58.a, var58.b, var58.c)) {
            this.logDebug("Took " + (System.currentTimeMillis() - var11) + "ms, " + var51 + " movements considered");
            return Optional.of(new Path(super.a, super.a, var58, var2, super.a, this.a));
         }

         Moves[] var28 = var20;
         int var53 = var20.length;

         for (int var54 = 0; var54 < var53; var54++) {
            Moves var23 = var28[var54];
            int var26 = var58.a + var23.a;
            int var29 = var58.c + var23.c;
            if (var26 >> 4 != var58.a >> 4 || var29 >> 4 != var58.c >> 4) {
               int var65 = var29;
               int var63 = var26;
               int var66 = var65;
               int var60 = var63;
               BlockStateInterface var59 = this.a.a;
               class_2818 var44 = this.a.a.a;
               boolean var10000;
               if (this.a.a.a != null && var44.method_12004().field_9181 == var60 >> 4 && var44.method_12004().field_9180 == var66 >> 4) {
                  var10000 = true;
               } else if ((var44 = var59.a.method_2857(var60 >> 4, var66 >> 4, class_2806.field_12803, false)) != null && !var44.method_12223()) {
                  var59.a = var44;
                  var10000 = true;
               } else {
                  CachedRegion var61 = var59.a;
                  if (var59.a != null && var61.getX() == var60 >> 9 && var61.getZ() == var66 >> 9) {
                     var10000 = var61.isCached(var60 & 511, var66 & 511);
                  } else if (var59.a == null) {
                     var10000 = false;
                  } else if ((var61 = var59.a.a.a(var60 >> 9, var66 >> 9)) == null) {
                     var10000 = false;
                  } else {
                     var59.a = var61;
                     var10000 = var61.isCached(var60 & 511, var66 & 511);
                  }
               }

               if (!var10000) {
                  if (!var23.a) {
                     var4++;
                  }
                  continue;
               }
            }

            if ((var23.a || var10.a(var26, var29)) && var58.b + var23.b <= var6 && var58.b + var23.b >= var5) {
               var52.a();
               var23.a(this.a, var58.a, var58.b, var58.c, var52);
               var51++;
               double var33 = var52.a;
               if (!(var52.a >= 1000000.0)) {
                  if (var33 <= 0.0 || Double.isNaN(var33)) {
                     throw new IllegalStateException(
                        String.format(
                           "%s from %s %s %s calculated implausible cost %s",
                           var23,
                           SettingsUtil.maybeCensor(var58.a),
                           SettingsUtil.maybeCensor(var58.b),
                           SettingsUtil.maybeCensor(var58.c),
                           var33
                        )
                     );
                  }

                  if (!var23.a || var10.a(var52.a, var52.c)) {
                     if (!var23.a && (var52.a != var26 || var52.c != var29)) {
                        throw new IllegalStateException(
                           String.format(
                              "%s from %s %s %s ended at x z %s %s instead of %s %s",
                              var23,
                              SettingsUtil.maybeCensor(var58.a),
                              SettingsUtil.maybeCensor(var58.b),
                              SettingsUtil.maybeCensor(var58.c),
                              SettingsUtil.maybeCensor(var52.a),
                              SettingsUtil.maybeCensor(var52.c),
                              SettingsUtil.maybeCensor(var26),
                              SettingsUtil.maybeCensor(var29)
                           )
                        );
                     }

                     if (!var23.b && var52.b != var58.b + var23.b) {
                        throw new IllegalStateException(
                           String.format(
                              "%s from %s %s %s ended at y %s instead of %s",
                              var23,
                              SettingsUtil.maybeCensor(var58.a),
                              SettingsUtil.maybeCensor(var58.b),
                              SettingsUtil.maybeCensor(var58.c),
                              SettingsUtil.maybeCensor(var52.b),
                              SettingsUtil.maybeCensor(var58.b + var23.b)
                           )
                        );
                     }

                     long var35 = BetterBlockPos.longHash(var52.a, var52.b, var52.c);
                     if (var18) {
                        long var64 = var35;
                        var33 *= this.a.a.get(var64);
                     }

                     PathNode var55 = this.a(var52.a, var52.b, var52.c, var35);
                     double var37 = var58.b + var33;
                     if (var55.b - var37 > var24) {
                        var55.a = var58;
                        var55.b = var37;
                        var55.c = var37 + var55.a;
                        if (var55.d != -1) {
                           var7.b(var55);
                        } else {
                           var7.a(var55);
                        }

                        for (int var56 = 0; var56 < a.length; var56++) {
                           double var39 = var55.a + var55.b / a[var56];
                           if (var8[var56] - var39 > var24) {
                              var8[var56] = var39;
                              super.a[var56] = var55;
                              if (var50 && this.a(var55) > 25.0) {
                                 var50 = false;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (super.a) {
         return Optional.empty();
      }

      System.out.println(var51 + " movements considered");
      System.out.println("Open set size: " + var7.a);
      System.out.println("PathNode map size: " + this.a());
      System.out.println((int)((double)var2 / ((float)(System.currentTimeMillis() - var11) / 1000.0F)) + " nodes per second");
      Optional var57;
      if ((var57 = this.a(true, var2)).isPresent()) {
         this.logDebug("Took " + (System.currentTimeMillis() - var11) + "ms, " + var51 + " movements considered");
      }

      return var57;
   }
}
