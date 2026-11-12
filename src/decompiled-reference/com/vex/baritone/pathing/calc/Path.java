package com.vex.baritone.pathing.calc;

import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.Moves;
import com.vex.baritone.pathing.path.CutoffPath;
import com.vex.baritone.utils.pathing.PathBase;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Path extends PathBase {
   private final BetterBlockPos a;
   private final BetterBlockPos b;
   private final List<BetterBlockPos> a;
   private final List<Movement> b;
   private final List<PathNode> c;
   private final Goal a;
   private final int a;
   private final CalculationContext a;
   private volatile boolean a;

   Path(BetterBlockPos var1, PathNode var2, PathNode var3, int var4, Goal var5, CalculationContext var6) {
      this.b = new BetterBlockPos(var3.a, var3.b, var3.c);
      this.a = var4;
      this.b = new ArrayList<>();
      this.a = var5;
      this.a = var6;
      PathNode var9 = var3;
      ArrayList var11 = new ArrayList();
      ArrayList var7 = new ArrayList();

      while (var9 != null) {
         var7.add(var9);
         var11.add(new BetterBlockPos(var9.a, var9.b, var9.c));
         var9 = var9.a;
      }

      BetterBlockPos var10 = new BetterBlockPos(var2.a, var2.b, var2.c);
      if (!var1.equals(var10) && var2.equals(var3)) {
         this.a = var1;
         (var2 = new PathNode(var1.x, var1.y, var1.z, var5)).b = 0.0;
         var7.add(var2);
         var11.add(var1);
      } else {
         this.a = var10;
      }

      this.a = Lists.reverse(var11);
      this.c = Lists.reverse(var7);
   }

   @Override
   public Goal getGoal() {
      return this.a;
   }

   @Override
   public IPath postProcess() {
      if (this.a) {
         throw new IllegalStateException("Path must not be verified twice");
      }

      this.a = true;
      Path var1 = this;
      if (!this.a.isEmpty() && var1.b.isEmpty()) {
         int var2 = 0;

         boolean var16;
         while (true) {
            if (var2 >= var1.a.size() - 1) {
               var16 = false;
               break;
            }

            double var5 = var1.c.get(var2 + 1).b - var1.c.get(var2).b;
            BetterBlockPos var10001 = var1.a.get(var2);
            BetterBlockPos var10002 = var1.a.get(var2 + 1);
            double var10 = var5;
            BetterBlockPos var15 = var10002;
            BetterBlockPos var4 = var10001;
            Path var3 = var1;
            Moves[] var6;
            int var7 = (var6 = Moves.values()).length;
            int var8 = 0;

            while (true) {
               if (var8 >= var7) {
                  Helper.HELPER.logDebug("Movement became impossible during calculation " + var4 + " " + var15 + " " + var15.method_10059(var4));
                  var10000 = null;
                  break;
               }

               Movement var9;
               if ((var9 = var6[var8].a(var3.a, var4)).getDest().equals(var15)) {
                  var9.a = Math.min(var9.a(var3.a), var10);
                  var10000 = var9;
                  break;
               }

               var8++;
            }

            Movement var14 = var10000;
            if (var10000 == null) {
               var16 = true;
               break;
            }

            var1.b.add(var14);
            var2++;
         }

         boolean var12 = var16;
         this.b.forEach(var1x -> {
            CalculationContext var2x = this.a;
            Movement var3x = var1x;
            var1x.a = var2x.a.a(var3x.b.x, var3x.b.z);
         });
         if (var12) {
            CutoffPath var13;
            if ((var13 = new CutoffPath(this, this.movements().size())).movements().size() != this.b.size()) {
               throw new IllegalStateException("Path has wrong size after cutoff");
            } else {
               return var13;
            }
         } else {
            this.sanityCheck();
            return this;
         }
      } else {
         throw new IllegalStateException("Path must not be empty");
      }
   }

   @Override
   public List<IMovement> movements() {
      if (!this.a) {
         throw new IllegalStateException("Path not yet verified");
      } else {
         return Collections.unmodifiableList(this.b);
      }
   }

   @Override
   public List<BetterBlockPos> positions() {
      return Collections.unmodifiableList(this.a);
   }

   @Override
   public int getNumNodesConsidered() {
      return this.a;
   }

   @Override
   public BetterBlockPos getSrc() {
      return this.a;
   }

   @Override
   public BetterBlockPos getDest() {
      return this.b;
   }
}
