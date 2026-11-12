package com.vex.baritone.pathing.path;

import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.utils.pathing.PathBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SplicedPath extends PathBase {
   private final List<BetterBlockPos> a;
   private final List<IMovement> b;
   private final int a;
   private final Goal a;

   private SplicedPath(List<BetterBlockPos> var1, List<IMovement> var2, int var3, Goal var4) {
      this.a = var1;
      this.b = var2;
      this.a = var3;
      this.a = var4;
      this.sanityCheck();
   }

   @Override
   public Goal getGoal() {
      return this.a;
   }

   @Override
   public List<IMovement> movements() {
      return Collections.unmodifiableList(this.b);
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
   public int length() {
      return this.a.size();
   }

   public static Optional<SplicedPath> a(IPath var0, IPath var1) {
      if (var1 != null && var0 != null) {
         if (!var0.getDest().equals(var1.getSrc())) {
            return Optional.empty();
         }

         HashSet var2 = new HashSet<>(var1.positions());
         int var3 = -1;

         for (int var4 = 0; var4 < var0.length() - 1; var4++) {
            if (var2.contains(var0.positions().get(var4))) {
               var3 = var4;
               break;
            }
         }

         if (var3 != -1) {
            return Optional.empty();
         }

         var3 = var0.length() - 1;
         int var8;
         if ((var8 = var1.positions().indexOf(var0.positions().get(var3))) != 0) {
            throw new IllegalStateException("Paths to be spliced are overlapping incorrectly");
         }

         ArrayList var6 = new ArrayList();
         ArrayList var5 = new ArrayList();
         var6.addAll(var0.positions().subList(0, var3 + 1));
         var5.addAll(var0.movements().subList(0, var3));
         var6.addAll(var1.positions().subList(var8 + 1, var1.length()));
         var5.addAll(var1.movements().subList(var8, var1.length() - 1));
         return Optional.of(new SplicedPath(var6, var5, var0.getNumNodesConsidered() + var1.getNumNodesConsidered(), var0.getGoal()));
      } else {
         return Optional.empty();
      }
   }
}
