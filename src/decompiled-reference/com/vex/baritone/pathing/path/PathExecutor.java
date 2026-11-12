package com.vex.baritone.pathing.path;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.pathing.path.IPathExecutor;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.VecUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.behavior.PathingBehavior;
import com.vex.baritone.pathing.calc.AbstractNodeCostSearch;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.movements.MovementAscend;
import com.vex.baritone.pathing.movement.movements.MovementDescend;
import com.vex.baritone.pathing.movement.movements.MovementDiagonal;
import com.vex.baritone.pathing.movement.movements.MovementFall;
import com.vex.baritone.pathing.movement.movements.MovementParkour;
import com.vex.baritone.pathing.movement.movements.MovementTraverse;
import com.vex.baritone.utils.BlockStateInterface;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3545;

public class PathExecutor implements IPathExecutor, Helper {
   public final IPath a;
   public int a;
   private int b;
   private int c;
   private Double a;
   private Integer a;
   public boolean a;
   private boolean c = true;
   public HashSet<class_2338> a = new HashSet<>();
   public HashSet<class_2338> b = new HashSet<>();
   public HashSet<class_2338> c = new HashSet<>();
   private final PathingBehavior a;
   public final IPlayerContext a;
   public boolean b;

   public PathExecutor(PathingBehavior var1, IPath var2) {
      this.a = var1;
      this.a = var1.a;
      this.a = var2;
      this.a = 0;
   }

   public final boolean a() {
      if (this.a == this.a.length() - 1) {
         this.a++;
      }

      if (this.a >= this.a.length()) {
         return true;
      }

      Movement var1 = (Movement)this.a.movements().get(this.a);
      BetterBlockPos var2 = this.a.playerFeet();
      if (!var1.b().contains(var2)) {
         for (int var3 = 0; var3 < this.a && var3 < this.a.length(); var3++) {
            if (((Movement)this.a.movements().get(var3)).b().contains(var2)) {
               int var16 = this.a;
               this.a = var3;

               for (int var22 = this.a; var22 <= var16; var22++) {
                  this.a.movements().get(var22).reset();
               }

               this.b();
               this.a();
               return false;
            }
         }

         for (int var23 = this.a + 3; var23 < this.a.length() - 1; var23++) {
            if (((Movement)this.a.movements().get(var23)).b().contains(var2)) {
               if (var23 - this.a > 2) {
                  this.logDebug("Skipping forward " + (var23 - this.a) + " steps, to " + var23);
               }

               this.a = var23 - 1;
               this.b();
               this.a();
               return false;
            }
         }
      }

      IPath var4 = this.a;
      PathExecutor var17 = this;
      double var8 = -1.0;
      BetterBlockPos var5 = null;
      Iterator var29 = var4.movements().iterator();

      while (var29.hasNext()) {
         for (class_2338 var10 : ((Movement)((IMovement)var29.next())).b()) {
            double var11;
            if ((var11 = VecUtils.entityDistanceToCenter(var17.a.player(), var10)) < var8 || var8 == -1.0) {
               var8 = var11;
               var5 = var10;
            }
         }
      }

      class_3545 var24 = new class_3545(var8, var5);
      if (this.a(var24, 2.0)) {
         this.b++;
         System.out.println("FAR AWAY FROM PATH FOR " + this.b + " TICKS. Current distance: " + var24.method_15442() + ". Threshold: 2.0");
         if (this.b > 200.0) {
            this.logDebug("Too far away from path for too long, cancelling path");
            this.c();
            return false;
         }
      } else {
         this.b = 0;
      }

      if (this.a(var24, 3.0)) {
         this.logDebug("too far from path");
         this.c();
         return false;
      }

      BlockStateInterface var18 = new BlockStateInterface(this.a);

      for (int var25 = this.a - 10; var25 < this.a + 10; var25++) {
         if (var25 >= 0 && var25 < this.a.movements().size()) {
            Movement var6;
            List var47 = (var6 = (Movement)this.a.movements().get(var25)).a(var18);
            List var30 = var6.b(var18);
            List var36 = var6.c(var18);
            var6.resetBlockCache();
            if (!var47.equals(var6.a(var18))) {
               this.c = true;
            }

            if (!var30.equals(var6.b(var18))) {
               this.c = true;
            }

            if (!var36.equals(var6.c(var18))) {
               this.c = true;
            }
         }
      }

      if (this.c) {
         HashSet var26 = new HashSet();
         HashSet var42 = new HashSet();
         HashSet var48 = new HashSet();

         for (int var31 = this.a; var31 < this.a.movements().size(); var31++) {
            Movement var37 = (Movement)this.a.movements().get(var31);
            var26.addAll(var37.a(var18));
            var42.addAll(var37.b(var18));
            var48.addAll(var37.c(var18));
         }

         this.a = var26;
         this.b = var42;
         this.c = var48;
         this.c = false;
      }

      if (this.a < this.a.movements().size() - 1) {
         IMovement var27 = this.a.movements().get(this.a + 1);
         if (!this.a.a.a.a(var27.getDest().x, var27.getDest().z)) {
            this.logDebug("Pausing since destination is at edge of loaded chunks");
            this.a();
            return true;
         }
      }

      boolean var28 = var1.safeToCancel();
      if (this.a == null || this.a != this.a) {
         this.a = this.a;
         this.a = var1.getCost();

         for (int var43 = 1; var43 < Baritone.a().costVerificationLookahead.value && this.a + var43 < this.a.length() - 1; var43++) {
            if (((Movement)this.a.movements().get(this.a + var43)).a(this.a.a) >= 1000000.0 && var28) {
               this.logDebug("Something has changed in the world and a future movement has become impossible. Cancelling.");
               this.c();
               return true;
            }
         }
      }

      CalculationContext var32 = this.a.a;
      Movement var19 = var1;
      var1.a = null;
      CalculationContext var9 = var32;
      Movement var51 = var19;
      if (var19.a == null) {
         var51.a = var51.a(var9);
      }

      double var44;
      if ((var44 = var51.a) >= 1000000.0 && var28) {
         this.logDebug("Something has changed in the world and this movement has become impossible. Cancelling.");
         this.c();
         return true;
      }

      if (!var1.calculatedWhileLoaded() && var44 - this.a > Baritone.a().maxCostIncrease.value && var28) {
         this.logDebug("Original cost " + this.a + " current cost " + var44 + ". Cancelling.");
         this.c();
         return true;
      }

      PathExecutor var20 = this;
      Optional var33;
      Optional var52;
      List var54;
      if (!(var33 = this.a.getInProgress()).isPresent()
         ? false
         : (
            !var20.a.player().method_24828()
               ? false
               : (
                  !MovementHelper.b(var20.a, var20.a.playerFeet().below())
                     ? false
                     : (
                        MovementHelper.a(var20.a, var20.a.playerFeet()) && MovementHelper.a(var20.a, var20.a.playerFeet().above())
                           ? (
                              !var20.a.movements().get(var20.a).safeToCancel()
                                 ? false
                                 : (
                                    !(var52 = ((AbstractNodeCostSearch)var33.get()).bestPathSoFar()).isPresent()
                                       ? false
                                       : (
                                          (var54 = ((IPath)var52.get()).positions()).size() < 3
                                             ? false
                                             : var54.subList(1, var54.size()).contains(var20.a.playerFeet())
                                       )
                                 )
                           )
                           : false
                     )
               )
         )) {
         this.logDebug("Pausing since current best path is a backtrack");
         this.a();
         return true;
      }

      MovementStatus var34;
      if ((var34 = var1.update()) == MovementStatus.UNREACHABLE || var34 == MovementStatus.FAILED) {
         this.logDebug("Movement returns status " + var34);
         this.c();
         return true;
      }

      if (var34 == MovementStatus.SUCCESS) {
         this.a++;
         this.b();
         this.a();
         return true;
      }

      boolean var66;
      label441: {
         PathExecutor var21 = this;
         boolean var35 = this.a.a.a.isInputForcedDown(Input.SPRINT);
         var21.a.a.a.setInputForceState(Input.SPRINT, false);
         label298:
         if ((new CalculationContext(var21.a.a, false)).d) {
            IMovement var53;
            IMovement var55;
            if ((var53 = var21.a.movements().get(var21.a)) instanceof MovementTraverse
               && var21.a < var21.a.length() - 3
               && (var55 = var21.a.movements().get(var21.a + 1)) instanceof MovementAscend
               && a(var21.a, (MovementTraverse)var53, (MovementAscend)var55, var21.a.movements().get(var21.a + 2))) {
               label328: {
                  IMovement var49 = var53;
                  IPlayerContext var45 = var21.a;
                  if (!(
                     Math.abs(var49.getDirection().method_10263() * (var49.getSrc().z + 0.5 - var45.player().method_73189().field_1350))
                           + Math.abs(var49.getDirection().method_10260() * (var49.getSrc().x + 0.5 - var45.player().method_73189().field_1352))
                        > 0.1
                  )) {
                     class_2338 var12 = var49.getSrc().method_10059(var49.getDirection()).method_10086(2);
                     if (MovementHelper.a(var45, var12)) {
                        var66 = true;
                        break label328;
                     }

                     if (Math.abs(var49.getDirection().method_10263() * (var12.method_10263() + 0.5 - var45.player().method_73189().field_1352))
                           + Math.abs(var49.getDirection().method_10260() * (var12.method_10260() + 0.5 - var45.player().method_73189().field_1350))
                        > 0.8) {
                        var66 = true;
                        break label328;
                     }
                  }

                  var66 = false;
               }

               if (var66) {
                  var21.logDebug("Skipping traverse to straight ascend");
                  var21.a++;
                  var21.b();
                  var21.a();
                  var21.a.a.a.setInputForceState(Input.JUMP, true);
                  var66 = true;
                  break label441;
               }

               var21.logDebug("Too far to the side to safely sprint ascend");
            }

            if (var35) {
               var66 = true;
               break label441;
            }

            if (var53 instanceof MovementDescend) {
               if (var21.a < var21.a.length() - 2) {
                  IMovement var56 = var21.a.movements().get(var21.a + 1);
                  if (MovementHelper.c(var21.a, var56.getDest().below()) && (var56 instanceof MovementTraverse || var56 instanceof MovementParkour)) {
                     boolean var38 = Baritone.a().allowPlace.value && var21.a.a.a.a() && var56 instanceof MovementParkour;
                     if (!var53.getDirection().method_10084().method_10081(var56.getDirection()).equals(class_2338.field_10980)
                        && var53.getDirection().method_10084().method_10075(var56.getDirection()).equals(class_2338.field_10980)
                        && !var38) {
                        ((MovementDescend)var53).a = true;
                     }
                  }
               }

               if (((MovementDescend)var53).b() && !((MovementDescend)var53).c()) {
                  var21.logDebug("Sprinting would be unsafe");
                  break label298;
               }

               if (var21.a < var21.a.length() - 2) {
                  IMovement var57;
                  if ((var57 = var21.a.movements().get(var21.a + 1)) instanceof MovementAscend
                     && var53.getDirection().method_10084().equals(var57.getDirection().method_10074())) {
                     var21.a++;
                     var21.b();
                     var21.a();
                     var21.logDebug("Skipping descend to straight ascend");
                     var66 = true;
                     break label441;
                  }

                  if (a(var21.a, var53, var57)) {
                     IMovement var41;
                     if (var57 instanceof MovementDescend
                        && var21.a < var21.a.length() - 3
                        && (var41 = var21.a.movements().get(var21.a + 2)) instanceof MovementDescend
                        && !a(var21.a, var57, var41)) {
                        break label298;
                     }

                     if (var21.a.playerFeet().equals(var53.getDest())) {
                        var21.a++;
                        var21.b();
                        var21.a();
                     }

                     var66 = true;
                     break label441;
                  }
               }
            }

            if (var53 instanceof MovementAscend && var21.a != 0) {
               IMovement var58;
               if ((var58 = var21.a.movements().get(var21.a - 1)) instanceof MovementDescend
                  && var58.getDirection().method_10084().equals(var53.getDirection().method_10074())) {
                  var5 = var53.getSrc().above();
                  if (var21.a.player().method_73189().field_1351 >= var5.method_10264() - 0.07) {
                     var21.a.a.a.setInputForceState(Input.JUMP, false);
                     var66 = true;
                     break label441;
                  }
               }

               if (var21.a < var21.a.length() - 2
                  && var58 instanceof MovementTraverse
                  && a(var21.a, (MovementTraverse)var58, (MovementAscend)var53, var21.a.movements().get(var21.a + 1))) {
                  var66 = true;
                  break label441;
               }
            }

            if (var53 instanceof MovementFall var50) {
               PathExecutor var46 = var21;
               class_2338 var60;
               class_3545 var65;
               if ((var60 = var50.getDirection()).method_10264() < -3) {
                  var65 = null;
               } else if (!var50.a.isEmpty()) {
                  var65 = null;
               } else {
                  class_2382 var61 = new class_2382(var60.method_10263(), 0, var60.method_10260());

                  IMovement var13;
                  int var62;
                  label280:
                  for (var62 = var46.a + 1;
                     var62 < var46.a.length() - 1
                        && var62 < var46.a + 3
                        && (var13 = var46.a.movements().get(var62)) instanceof MovementTraverse
                        && var61.equals(var13.getDirection());
                     var62++
                  ) {
                     for (int var14 = var13.getDest().y; var14 <= var50.getSrc().y + 1; var14++) {
                        class_2338 var15 = new class_2338(var13.getDest().x, var14, var13.getDest().z);
                        if (!MovementHelper.a(var46.a, var15)) {
                           break label280;
                        }
                     }

                     if (!MovementHelper.b(var46.a, var13.getDest().below())) {
                        break;
                     }
                  }

                  if (--var62 == var46.a) {
                     var65 = null;
                  } else {
                     double var64 = var62 - var46.a - 0.4;
                     var65 = new class_3545(
                        new class_243(
                           var61.method_10263() * var64 + var50.getDest().x + 0.5, var50.getDest().y, var61.method_10260() * var64 + var50.getDest().z + 0.5
                        ),
                        var50.getDest().method_10069(var61.method_10263() * (var62 - var46.a), 0, var61.method_10260() * (var62 - var46.a))
                     );
                  }
               }

               class_3545 var59 = var65;
               if (var65 != null) {
                  var5 = new BetterBlockPos((class_2338)var59.method_15441());
                  if (!var21.a.positions().contains(var5)) {
                     throw new IllegalStateException(String.format("Fall override at %s %s %s returned illegal destination %s %s %s", var53.getSrc(), var5));
                  }

                  if (var21.a.playerFeet().equals(var5)) {
                     var21.a = var21.a.positions().indexOf(var5);
                     var21.b();
                     var21.a();
                     var66 = true;
                  } else {
                     var21.a();
                     var21.a
                        .a
                        .a
                        .updateTarget(
                           RotationUtils.calcRotationFromVec3d(var21.a.playerHead(), (class_243)var59.method_15442(), var21.a.playerRotations()), false
                        );
                     var21.a.a.a.setInputForceState(Input.MOVE_FORWARD, true);
                     var66 = true;
                  }
                  break label441;
               }
            }
         }

         var66 = false;
      }

      this.b = var66;
      if (!this.b) {
         this.a.player().method_5728(false);
      }

      this.c++;
      if (this.c > this.a + Baritone.a().movementTimeoutTicks.value.intValue()) {
         this.logDebug("This movement has taken too long (" + this.c + " ticks, expected " + this.a + "). Cancelling.");
         this.c();
         return true;
      } else {
         return var28;
      }
   }

   private boolean a(class_3545<Double, class_2338> var1, double var2) {
      if ((Double)var1.method_15442() > var2) {
         if (this.a.movements().get(this.a) instanceof MovementFall) {
            class_2338 var4 = this.a.positions().get(this.a + 1);
            return VecUtils.entityFlatDistanceToCenter(this.a.player(), var4) >= var2;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean a(IPlayerContext var0, MovementTraverse var1, MovementAscend var2, IMovement var3) {
      if (!Baritone.a().sprintAscends.value) {
         return false;
      }

      if (!var1.getDirection().equals(var2.getDirection().method_10074())) {
         return false;
      }

      if (var3.getDirection().method_10263() == var2.getDirection().method_10263() && var3.getDirection().method_10260() == var2.getDirection().method_10260()) {
         if (!MovementHelper.b(var0, var1.getDest().below())) {
            return false;
         }

         if (!MovementHelper.b(var0, var2.getDest().below())) {
            return false;
         }

         if (!var2.a.isEmpty()) {
            return false;
         }

         for (int var6 = 0; var6 < 2; var6++) {
            for (int var4 = 0; var4 < 3; var4++) {
               class_2338 var5 = var1.getSrc().above(var4);
               if (var6 == 1) {
                  var5 = var5.method_10081(var1.getDirection());
               }

               if (!MovementHelper.a(var0, var5)) {
                  return false;
               }
            }
         }

         return MovementHelper.b(var0.world().method_8320(var1.getSrc().above(3)))
            ? false
            : !MovementHelper.b(var0.world().method_8320(var2.getDest().above(2)));
      } else {
         return false;
      }
   }

   private static boolean a(IPlayerContext var0, IMovement var1, IMovement var2) {
      if (var2 instanceof MovementDescend && var2.getDirection().equals(var1.getDirection())) {
         return true;
      } else if (!MovementHelper.b(var0, var1.getDest().method_10081(var1.getDirection()))) {
         return false;
      } else {
         return var2 instanceof MovementTraverse && var2.getDirection().equals(var1.getDirection())
            ? true
            : var2 instanceof MovementDiagonal && Baritone.a().allowOvershootDiagonalDescend.value;
      }
   }

   private void b() {
      this.a();
      this.c = 0;
   }

   public final void a() {
      this.a.a.a.clearAllKeys();
   }

   private void c() {
      this.a();
      this.a.a.a.a.a();
      this.a = this.a.length() + 3;
      this.a = true;
   }

   @Override
   public int getPosition() {
      return this.a;
   }

   public final PathExecutor a(PathExecutor var1) {
      return var1 == null ? this.a() : SplicedPath.a(this.a, var1.a).map(var2 -> {
         if (!var2.getDest().equals(var1.getPath().getDest())) {
            throw new IllegalStateException(String.format("Path has end %s instead of %s after splicing", var2.getDest(), var1.getPath().getDest()));
         }

         (var1 = new PathExecutor(this.a, var2)).a = this.a;
         var1.a = this.a;
         var1.a = this.a;
         var1.c = this.c;
         return var1;
      }).orElseGet(this::a);
   }

   private PathExecutor a() {
      if (this.a > Baritone.a().maxPathHistoryLength.value) {
         int var1 = Baritone.a().pathHistoryCutoffAmount.value;
         CutoffPath var2;
         if (!(var2 = new CutoffPath(this.a, var1, this.a.length() - 1)).getDest().equals(this.a.getDest())) {
            throw new IllegalStateException(String.format("Path has end %s instead of %s after trimming its start", var2.getDest(), this.a.getDest()));
         }

         this.logDebug("Discarding earliest segment movements, length cut from " + this.a.length() + " to " + var2.length());
         PathExecutor var3;
         (var3 = new PathExecutor(this.a, var2)).a = this.a - var1;
         var3.a = this.a;
         if (this.a != null) {
            var3.a = this.a - var1;
         }

         var3.c = this.c;
         return var3;
      } else {
         return this;
      }
   }

   @Override
   public IPath getPath() {
      return this.a;
   }

   public final boolean b() {
      return this.a >= this.a.length();
   }
}
