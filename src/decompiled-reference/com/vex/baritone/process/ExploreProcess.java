package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.ICachedWorld;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.process.IExploreProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.MyChunkPos;
import com.vex.baritone.cache.CachedWorld;
import com.vex.baritone.utils.BaritoneProcessHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import net.minecraft.class_1923;
import net.minecraft.class_2338;

public final class ExploreProcess extends BaritoneProcessHelper implements IExploreProcess {
   private class_2338 a;
   private ExploreProcess.JsonChunkFilter a;
   private int a;

   public ExploreProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final boolean isActive() {
      return this.a != null;
   }

   @Override
   public final void explore(int var1, int var2) {
      this.a = new class_2338(var1, 0, var2);
      this.a = 0;
   }

   @Override
   public final void applyJsonFilter(Path var1, boolean var2) {
      this.a = new ExploreProcess.JsonChunkFilter(var1, var2);
   }

   private ExploreProcess.IChunkFilter a() {
      ExploreProcess.IChunkFilter var1;
      if (this.a != null) {
         var1 = this.a.new EitherChunk(this.a, new ExploreProcess.BaritoneChunkCache());
      } else {
         var1 = new ExploreProcess.BaritoneChunkCache();
      }

      return var1;
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      if (var1) {
         this.logDirect("Failed");
         if (Baritone.a().notificationOnExploreFinished.value) {
            this.logNotification("Exploration failed", true);
         }

         this.onLostControl();
         return null;
      } else {
         ExploreProcess.IChunkFilter var3 = this.a();
         if (!Baritone.a().disableCompletionCheck.value && var3.a() == 0) {
            this.logDirect("Explored all chunks");
            if (Baritone.a().notificationOnExploreFinished.value) {
               this.logNotification("Explored all chunks", false);
            }

            this.onLostControl();
            return null;
         } else {
            Goal[] var4;
            if ((var4 = this.a(this.a, var3)) == null) {
               this.logDebug("awaiting region load from disk");
               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            } else {
               return new PathingCommand(new GoalComposite(var4), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
            }
         }
      }
   }

   private Goal[] a(class_2338 var1, ExploreProcess.IChunkFilter var2) {
      int var3 = var1.method_10263() >> 4;
      int var15 = var1.method_10260() >> 4;
      int var4 = Math.min(var2.a(), Baritone.a().exploreChunkSetMinimumSize.value);
      ArrayList var5 = new ArrayList();
      int var6 = Baritone.a().worldExploringChunkOffset.value;
      int var7 = this.a;

      while (true) {
         for (int var8 = -var7; var8 <= var7; var8++) {
            int var9 = var7 - Math.abs(var8);
            int var10 = 0;

            while (var10 < 2) {
               int var11 = ((var10 << 1) - 1) * var9;
               int var12;
               if ((var12 = Math.abs(var8) + Math.abs(var11)) != var7) {
                  throw new IllegalStateException(String.format("Offset %s %s has distance %s, expected %s", var8, var11, var12, var7));
               }

               switch (var2.a(var3 + var8, var15 + var11)) {
                  case b:
                  default:
                     var12 = (var3 + var8 << 4) + 8;
                     int var13 = (var15 + var11 << 4) + 8;
                     int var14 = var6 << 4;
                     if (var8 < 0) {
                        var12 -= var14;
                     } else {
                        var12 += var14;
                     }

                     if (var11 < 0) {
                        var13 -= var14;
                     } else {
                        var13 += var14;
                     }

                     var5.add(new class_2338(var12, 0, var13));
                  case a:
                     var10++;
                     break;
                  case c:
                     return null;
               }
            }
         }

         if (var7 % 10 == 0) {
            var4 = Math.min(var2.a(), Baritone.a().exploreChunkSetMinimumSize.value);
         }

         if (var5.size() >= var4) {
            return var5.stream().map(var0 -> {
               int var1x = var0.method_10260();
               int var2x = var0.method_10263();
               return Baritone.a().exploreMaintainY.value == -1 ? new GoalXZ(var2x, var1x) : new ExploreProcess$1(var2x, var1x);
            }).toArray(Goal[]::new);
         }

         if (var5.isEmpty()) {
            this.a = var7 + 1;
         }

         var7++;
      }
   }

   @Override
   public final void onLostControl() {
      this.a = null;
   }

   @Override
   public final String displayName0() {
      return "Exploring around " + this.a + ", distance completed " + this.a + ", currently going to " + new GoalComposite(this.a(this.a, this.a()));
   }

   class BaritoneChunkCache implements ExploreProcess.IChunkFilter {
      private final ICachedWorld a = ExploreProcess.a(ExploreProcess.this).a.a().getCachedWorld();

      @Override
      public final ExploreProcess.Status a(int var1, int var2) {
         var1 <<= 4;
         var2 <<= 4;
         if (this.a.isCached(var1, var2)) {
            return ExploreProcess.Status.a;
         } else {
            CachedWorld var10000 = (CachedWorld)this.a;
            int var3 = var2;
            if (var10000.a(var1 >> 9, var3 >> 9) == null) {
               Baritone.a().execute(() -> ((CachedWorld)this.a).b(var1 >> 9, var2 >> 9));
               return ExploreProcess.Status.c;
            } else {
               return ExploreProcess.Status.b;
            }
         }
      }

      @Override
      public final int a() {
         return Integer.MAX_VALUE;
      }
   }

   class EitherChunk implements ExploreProcess.IChunkFilter {
      private final ExploreProcess.JsonChunkFilter a;
      private final ExploreProcess.BaritoneChunkCache a;

      EitherChunk(ExploreProcess.JsonChunkFilter var1, ExploreProcess.BaritoneChunkCache var2) {
         this.a = var1;
         this.a = var2;
      }

      @Override
      public final ExploreProcess.Status a(int var1, int var2) {
         return this.a.a(var1, var2) == ExploreProcess.Status.a ? ExploreProcess.Status.a : this.a.a(var1, var2);
      }

      @Override
      public final int a() {
         return Math.min(this.a.a(), this.a.a());
      }
   }

   interface IChunkFilter {
      ExploreProcess.Status a(int var1, int var2);

      int a();
   }

   class JsonChunkFilter implements ExploreProcess.IChunkFilter {
      private final boolean a;
      private final LongOpenHashSet a;
      private final MyChunkPos[] a;

      JsonChunkFilter(Path var2, boolean var3) {
         this.a = var3;
         Gson var7 = new GsonBuilder().create();
         this.a = (MyChunkPos[])var7.fromJson(new InputStreamReader(Files.newInputStream(var2)), MyChunkPos[].class);
         ExploreProcess.this.logDirect("Loaded " + this.a.length + " positions");
         this.a = new LongOpenHashSet();
         MyChunkPos[] var5 = this.a;
         int var6 = this.a.length;

         for (int var8 = 0; var8 < var6; var8++) {
            MyChunkPos var4 = var5[var8];
            this.a.add(class_1923.method_8331(var4.x, var4.z));
         }
      }

      @Override
      public final ExploreProcess.Status a(int var1, int var2) {
         return this.a.contains(class_1923.method_8331(var1, var2)) ^ this.a ? ExploreProcess.Status.a : ExploreProcess.Status.c;
      }

      @Override
      public final int a() {
         if (!this.a) {
            return Integer.MAX_VALUE;
         }

         int var1 = 0;
         ExploreProcess.BaritoneChunkCache var2 = ExploreProcess.this.new BaritoneChunkCache();
         MyChunkPos[] var3 = this.a;
         int var4 = this.a.length;

         for (int var5 = 0; var5 < var4; var5++) {
            MyChunkPos var6 = var3[var5];
            if (var2.a(var6.x, var6.z) != ExploreProcess.Status.a) {
               if (++var1 >= Baritone.a().exploreChunkSetMinimumSize.value) {
                  return var1;
               }
            }
         }

         return var1;
      }
   }

   enum Status {
      a,
      b,
      c;
   }
}
