package com.vex.baritone.pathing.calc;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.calc.IPath;
import com.vex.baritone.api.pathing.calc.IPathFinder;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.PathCalculationResult;
import com.vex.baritone.pathing.movement.CalculationContext;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;

public abstract class AbstractNodeCostSearch implements IPathFinder, Helper {
   protected final BetterBlockPos a;
   protected final int a;
   protected final int b;
   protected final int c;
   protected final Goal a;
   private final CalculationContext a;
   private final Long2ObjectOpenHashMap<PathNode> a;
   protected PathNode a;
   protected PathNode b;
   protected final PathNode[] a;
   private volatile boolean b;
   protected boolean a;
   protected static final double[] a = new double[]{1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0};

   AbstractNodeCostSearch(BetterBlockPos var1, int var2, int var3, int var4, Goal var5, CalculationContext var6) {
      this.a = new PathNode[a.length];
      this.a = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.a = var5;
      this.a = var6;
      this.a = new Long2ObjectOpenHashMap(Baritone.a().pathingMapDefaultSize.value, Baritone.a().pathingMapLoadFactor.value);
   }

   public void a() {
      this.a = true;
   }

   @Override
   public synchronized PathCalculationResult calculate(long var1, long var3) {
      if (this.b) {
         throw new IllegalStateException("Path finder cannot be reused!");
      }

      this.a = false;

      try {
         IPath var9 = this.a(var1, var3).map(IPath::postProcess).orElse(null);
         if (this.a) {
            return new PathCalculationResult(PathCalculationResult.Type.CANCELLATION);
         }

         if (var9 == null) {
            return new PathCalculationResult(PathCalculationResult.Type.FAILURE);
         }

         int var2 = var9.length();
         IPath var10;
         if ((var10 = var9.cutoffAtLoadedChunks(this.a.a)).length() < var2) {
            Helper.HELPER.logDebug("Cutting off path at edge of loaded chunks");
            Helper.HELPER.logDebug("Length decreased by " + (var2 - var10.length()));
         } else {
            Helper.HELPER.logDebug("Path ends within loaded chunks");
         }

         var2 = var10.length();
         IPath var11;
         if ((var11 = var10.staticCutoff(this.a)).length() < var2) {
            Helper.HELPER.logDebug("Static cutoff " + var2 + " to " + var11.length());
         }

         return this.a.isInGoal(var11.getDest())
            ? new PathCalculationResult(PathCalculationResult.Type.SUCCESS_TO_GOAL, var11)
            : new PathCalculationResult(PathCalculationResult.Type.SUCCESS_SEGMENT, var11);
      } catch (Exception var7) {
         Helper.HELPER.logDirect("Pathing exception: " + var7);
         var7.printStackTrace();
         return new PathCalculationResult(PathCalculationResult.Type.EXCEPTION);
      } finally {
         this.b = true;
      }
   }

   protected abstract Optional<IPath> a(long var1, long var3);

   protected final double a(PathNode var1) {
      int var2 = var1.a - this.a;
      int var3 = var1.b - this.b;
      int var4 = var1.c - this.c;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   protected final PathNode a(int var1, int var2, int var3, long var4) {
      PathNode var6;
      if ((var6 = (PathNode)this.a.get(var4)) == null) {
         var6 = new PathNode(var1, var2, var3, this.a);
         this.a.put(var4, var6);
      }

      return var6;
   }

   @Override
   public Optional<IPath> pathToMostRecentNodeConsidered() {
      return Optional.ofNullable(this.b).map(var1 -> new Path(this.a, this.a, var1, 0, this.a, this.a));
   }

   @Override
   public Optional<IPath> bestPathSoFar() {
      return this.a(false, 0);
   }

   protected final Optional<IPath> a(boolean var1, int var2) {
      if (this.a == null) {
         return Optional.empty();
      }

      double var3 = 0.0;

      for (int var5 = 0; var5 < a.length; var5++) {
         if (this.a[var5] != null) {
            double var6;
            if ((var6 = this.a(this.a[var5])) > var3) {
               var3 = var6;
            }

            if (var6 > 25.0) {
               if (var1) {
                  if (a[var5] >= 3.0) {
                     System.out.println("Warning: cost coefficient is greater than three! Probably means that");
                     System.out.println("the path I found is pretty terrible (like sneak-bridging for dozens of blocks)");
                     System.out.println("But I'm going to do it anyway, because yolo");
                  }

                  System.out.println("Path goes for " + Math.sqrt(var6) + " blocks");
                  this.logDebug("A* cost coefficient " + a[var5]);
               }

               return Optional.of(new Path(this.a, this.a, this.a[var5], var2, this.a, this.a));
            }
         }
      }

      if (var1) {
         this.logDebug("Even with a cost coefficient of " + a[a.length - 1] + ", I couldn't get more than " + Math.sqrt(var3) + " blocks");
         this.logDebug("No path found =(");
         this.logNotification("No path found =(", true);
      }

      return Optional.empty();
   }

   @Override
   public final boolean isFinished() {
      return this.b;
   }

   @Override
   public final Goal getGoal() {
      return this.a;
   }

   public final BetterBlockPos a() {
      return new BetterBlockPos(this.a, this.b, this.c);
   }

   protected final int a() {
      return this.a.size();
   }
}
