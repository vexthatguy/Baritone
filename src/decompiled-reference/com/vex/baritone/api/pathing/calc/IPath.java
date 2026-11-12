package com.vex.baritone.api.pathing.calc;

import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.HashSet;
import java.util.List;

public interface IPath {
   List<IMovement> movements();

   List<BetterBlockPos> positions();

   default IPath postProcess() {
      throw new UnsupportedOperationException();
   }

   default int length() {
      return this.positions().size();
   }

   Goal getGoal();

   int getNumNodesConsidered();

   default BetterBlockPos getSrc() {
      return this.positions().get(0);
   }

   default BetterBlockPos getDest() {
      List var10000 = this.positions();
      return (BetterBlockPos)var10000.get(var10000.size() - 1);
   }

   default double ticksRemainingFrom(int var1) {
      double var2 = 0.0;
      List var4 = this.movements();

      while (var1 < var4.size()) {
         var2 += ((IMovement)var4.get(var1)).getCost();
         var1++;
      }

      return var2;
   }

   default IPath cutoffAtLoadedChunks(Object var1) {
      throw new UnsupportedOperationException();
   }

   default IPath staticCutoff(Goal var1) {
      throw new UnsupportedOperationException();
   }

   default void sanityCheck() {
      List var1 = this.positions();
      List var2 = this.movements();
      if (!this.getSrc().equals(var1.get(0))) {
         throw new IllegalStateException("Start node does not equal first path element");
      }

      if (!this.getDest().equals(var1.get(var1.size() - 1))) {
         throw new IllegalStateException("End node does not equal last path element");
      }

      if (var1.size() != var2.size() + 1) {
         throw new IllegalStateException("Size of path array is unexpected");
      }

      HashSet var3 = new HashSet();

      for (int var4 = 0; var4 < var1.size() - 1; var4++) {
         BetterBlockPos var5 = (BetterBlockPos)var1.get(var4);
         BetterBlockPos var6 = (BetterBlockPos)var1.get(var4 + 1);
         IMovement var7 = (IMovement)var2.get(var4);
         if (!var5.equals(var7.getSrc())) {
            throw new IllegalStateException("Path source is not equal to the movement source");
         }

         if (!var6.equals(var7.getDest())) {
            throw new IllegalStateException("Path destination is not equal to the movement destination");
         }

         if (var3.contains(var5)) {
            throw new IllegalStateException("Path doubles back on itself, making a loop");
         }

         var3.add(var5);
      }
   }
}
