package com.vex.baritone.process.elytra;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.behavior.look.ITickableAimProcessor;
import com.vex.baritone.api.event.events.PacketEvent;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.Pair;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.process.ElytraProcess;
import com.vex.baritone.utils.BaritoneMath;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.accessor.IFireworkRocketEntity;
import dev.babbaj.pathfinder.NetherPathfinder;
import dev.babbaj.pathfinder.Octree;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1268;
import net.minecraft.class_1671;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1923;
import net.minecraft.class_2189;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2708;
import net.minecraft.class_2802;
import net.minecraft.class_2818;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_9284;
import net.minecraft.class_9334;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public final class ElytraBehavior implements Helper {
   public final Baritone a;
   public final IPlayerContext a;
   public final List<Pair<class_243, class_243>> a;
   public final List<Pair<class_243, class_243>> b;
   public List<class_243> c;
   public BetterBlockPos a;
   public List<BetterBlockPos> d;
   public final NetherPathfinderContext a;
   public final ElytraBehavior.PathManager a;
   final ElytraProcess a;
   public int a;
   public int b;
   public boolean a;
   public int c;
   public boolean b;
   public final int[] a;
   public BlockStateInterface a;
   public final BlockStateOctreeInterface a;
   public final BetterBlockPos b;
   final boolean c;
   private final ExecutorService a;
   public Future<ElytraBehavior.Solution> a;
   public ElytraBehavior.Solution a;
   public boolean d;
   public long a = 0L;
   public int d = 0;
   public final Queue<Runnable> a = new LinkedList<>();

   public ElytraBehavior(Baritone var1, ElytraProcess var2, class_2338 var3, boolean var4) {
      this.a = var1;
      this.a = var1.getPlayerContext();
      this.a = new CopyOnWriteArrayList<>();
      this.b = new CopyOnWriteArrayList<>();
      this.a = new ElytraBehavior.PathManager();
      this.a = var2;
      this.b = new BetterBlockPos(var3);
      this.c = var4;
      this.a = Executors.newSingleThreadExecutor();
      this.a = new int[2];
      this.a = new NetherPathfinderContext(Baritone.a().elytraNetherSeed.value);
      this.a = new BlockStateOctreeInterface(this.a);
   }

   public final void a(PacketEvent var1) {
      if (var1.getPacket() instanceof class_2708) {
         this.a.minecraft().execute(() -> this.b = Baritone.a().elytraFireworkSetbackUseDelay.value);
      }
   }

   public final void a() {
      if (!Baritone.a().elytraAutoJump.value || this.a.player().method_6128()) {
         this.a.a();
      }
   }

   public final void b() {
      if (this.a != null) {
         this.a.cancel(true);
      }

      this.a.shutdown();

      try {
         while (!this.a.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
         }
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

      NetherPathfinderContext var1 = this.a;
      NetherPathfinder.cancel(this.a.a);
      var1.a.shutdownNow();

      try {
         while (!var1.a.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
         }
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

      NetherPathfinder.freeContext(var1.a);
   }

   public final void c() {
      class_2802 var1 = this.a.world().method_8398();
      BetterBlockPos var2;
      int var3 = (var2 = this.a.playerFeet()).method_10263() >> 4;
      int var8 = var2.method_10260() >> 4;
      int var4 = var3 - 40;
      int var5 = var8 - 40;
      var3 += 40;
      int var9 = var8 + 40;

      while (var4 <= var3) {
         for (int var6 = var5; var6 <= var9; var6++) {
            class_2818 var7;
            if ((var7 = var1.method_12126(var4, var6, false)) != null && !var7.method_12223()) {
               this.a.a(var7);
            }
         }

         var4++;
      }
   }

   public final void a(TickEvent var1) {
      if (var1.getType() == TickEvent.Type.IN && this.d) {
         this.a.c();
         ElytraBehavior.SolverContext var2 = new ElytraBehavior.SolverContext(true);
         this.a = this.a.submit(() -> this.a(var2));
         this.d = false;
      }
   }

   public final ElytraBehavior.Solution a(ElytraBehavior.SolverContext var1) {
      NetherPath var2 = var1.a;
      int var3 = this.a ? var2.size() - 1 : var1.a;
      class_243 var4 = var1.a;
      ElytraBehavior.Solution var5 = null;

      for (int var6 = 0; var6 < 3; var6++) {
         int[] var7 = var1.a.a() ? new int[]{20, 10, 5, 0} : new int[]{0};
         int var8 = var6 == 0 ? 2 : 3;
         int var9 = var3;

         for (int var10 = Math.min(var3 + 20, var2.size() - 1); var10 >= var9; var10--) {
            ArrayList var11 = new ArrayList();
            int[] var12 = var7;
            int var13 = var7.length;

            for (int var14 = 0; var14 < var13; var14++) {
               int var15 = var12[var14];
               if (var6 != 0 && var10 != var9) {
                  if (var6 == 1) {
                     double[] var29;
                     int var10000 = (var29 = new double[]{1.0, 0.75, 0.5, 0.25}).length;

                     for (int var30 = 0; var30 < 4; var30++) {
                        double var32;
                        class_243 var26 = (var32 = var29[var30]) == 1.0
                           ? var2.a(var10)
                           : var2.a(var10).method_1021(var32).method_1019(var2.a(var10 - 1).method_1021(1.0 - var32));
                        var11.add(new Pair<>(var26, var15));
                     }
                  } else {
                     class_243 var16;
                     int var17 = BaritoneMath.a((var16 = var2.a(var10).method_1020(var2.a(var10 - 1))).method_1033());
                     var16 = var16.method_1029();
                     class_243 var18 = var2.a(var10);

                     for (int var19 = 0; var19 < var17; var19++) {
                        var11.add(new Pair<>(var18, var15));
                        var18 = var18.method_1020(var16);
                     }
                  }
               } else {
                  var11.add(new Pair<>(var2.a(var10), var15));
               }
            }

            Iterator var21 = var11.iterator();

            while (var21.hasNext()) {
               Pair var22;
               Integer var23 = (Integer)(var22 = (Pair)var21.next()).second();
               class_243 var24 = ((class_243)var22.first()).method_1031(0.0, var23.intValue(), 0.0);
               if (this.a) {
                  var24 = var24.method_1031(0.5, 0.5, 0.5);
               }

               if (var23 == 0
                  || var10 + var8 < var2.size()
                     && (
                        var4.method_1022(var24) < 40.0
                           ? this.a(var24, var2.a(var10 + var8).method_1031(0.0, var23.intValue(), 0.0), false) && this.a(var24, var2.a(var10 + var8), false)
                           : this.a(var24, var2.a(var10), false)
                     )) {
                  double var27 = Baritone.a().elytraMinimumAvoidance.value;
                  Double var28 = var6 == 2 ? null : var6 == 0 ? var27 * 2.0 : var27;
                  if (this.a(var1, var24, var28)) {
                     float var31 = RotationUtils.calcRotationFromVec3d(var4, var24, this.a.playerRotations()).getYaw();
                     Pair var33;
                     if ((var33 = this.a(var1, var24, var6)) != null) {
                        return new ElytraBehavior.Solution(var1, new Rotation(var31, (Float)var33.first()), var24, true, (Boolean)var33.second());
                     }

                     var5 = new ElytraBehavior.Solution(var1, new Rotation(var31, this.a.playerRotations().getPitch()), null, false, false);
                  }
               }
            }
         }
      }

      return var5;
   }

   public final void a(class_243 var1, class_243 var2, boolean var3, boolean var4) {
      if (this.b > 0) {
         this.logDebug("waiting for elytraFireworkSetbackUseDelay: " + this.b);
      } else if (!this.a) {
         boolean var5 = !Baritone.a().elytraConserveFireworks.value || this.a.player().method_73189().field_1351 < var2.field_1351 + 5.0;
         double var6 = new class_243(
               this.a.player().method_18798().field_1352,
               this.a.player().method_73189().field_1351 < var2.field_1351
                  ? Math.max(0.0, this.a.player().method_18798().field_1351)
                  : this.a.player().method_18798().field_1351,
               this.a.player().method_18798().field_1350
            )
            .method_1027();
         double var8 = Baritone.a().elytraFireworkSpeed.value;
         if (this.a <= 0
            && (
               var4
                  || !var3
                     && var5
                     && (
                        this.a.player().method_73189().field_1351 < var2.field_1351 - 5.0
                           || var1.method_1022(new class_243(var2.field_1352 + 0.5, this.a.player().method_73189().field_1351, var2.field_1350 + 0.5)) > 5.0
                     )
                     && var6 < var8 * var8
            )) {
            if (!this.a.a.a(true, ElytraBehavior::b) && !this.a.a.a(true, ElytraBehavior::a)) {
               this.logDirect("no fireworks");
               return;
            }

            this.a("attempting to use firework" + (var4 ? " (forced)" : ""));
            this.a.playerController().processRightClick(this.a.player(), this.a.world(), class_1268.field_5808);
            this.c = 10 * (1 + a(this.a.player().method_5998(class_1268.field_5808)).orElse(0));
            this.a = 10;
            this.b = true;
         }
      }
   }

   public static boolean a(class_1799 var0) {
      class_9284 var1;
      return var0.method_7909() != class_1802.field_8639
         ? false
         : (var1 = (class_9284)var0.method_58694(class_9334.field_49616)) != null && var1.comp_2392().isEmpty();
   }

   private static boolean b(class_1799 var0) {
      return a(var0).isPresent();
   }

   private static OptionalInt a(class_1799 var0) {
      class_9284 var1;
      return (var1 = (class_9284)var0.method_58694(class_9334.field_49616)) != null && var1.comp_2392().isEmpty()
         ? OptionalInt.of(var1.comp_2391())
         : OptionalInt.empty();
   }

   public final Optional<class_1671> a() {
      return this.a
         .entitiesStream()
         .filter(var0 -> var0 instanceof class_1671)
         .filter(var1 -> Objects.equals(((IFireworkRocketEntity)var1).getBoostedEntity(), this.a.player()))
         .map(var0 -> (class_1671)var0)
         .findFirst();
   }

   private boolean a(ElytraBehavior.SolverContext var1, class_243 var2, Double var3) {
      class_243 var4 = var1.a;
      boolean var5 = var1.a;
      if (!this.a(var4, var2, var5)) {
         return false;
      }

      if (var3 == null) {
         return true;
      }

      class_238 var13 = var1.a.method_1014(var3);
      double var7 = var2.field_1352 - var4.field_1352;
      double var9 = var2.field_1351 - var4.field_1351;
      double var11 = var2.field_1350 - var4.field_1350;
      double[] var15 = new double[]{
         var13.field_1323,
         var13.field_1322,
         var13.field_1321,
         var13.field_1323,
         var13.field_1322,
         var13.field_1324,
         var13.field_1323,
         var13.field_1325,
         var13.field_1321,
         var13.field_1323,
         var13.field_1325,
         var13.field_1324,
         var13.field_1320,
         var13.field_1322,
         var13.field_1321,
         var13.field_1320,
         var13.field_1322,
         var13.field_1324,
         var13.field_1320,
         var13.field_1325,
         var13.field_1321,
         var13.field_1320,
         var13.field_1325,
         var13.field_1324
      };
      double[] var14 = new double[]{
         var13.field_1323 + var7,
         var13.field_1322 + var9,
         var13.field_1321 + var11,
         var13.field_1323 + var7,
         var13.field_1322 + var9,
         var13.field_1324 + var11,
         var13.field_1323 + var7,
         var13.field_1325 + var9,
         var13.field_1321 + var11,
         var13.field_1323 + var7,
         var13.field_1325 + var9,
         var13.field_1324 + var11,
         var13.field_1320 + var7,
         var13.field_1322 + var9,
         var13.field_1321 + var11,
         var13.field_1320 + var7,
         var13.field_1322 + var9,
         var13.field_1324 + var11,
         var13.field_1320 + var7,
         var13.field_1325 + var9,
         var13.field_1321 + var11,
         var13.field_1320 + var7,
         var13.field_1325 + var9,
         var13.field_1324 + var11
      };
      if (Baritone.a().elytraRenderHitboxRaytraces.value) {
         boolean var16 = true;

         for (int var17 = 0; var17 < 8; var17++) {
            class_243 var18 = new class_243(var15[var17 * 3], var15[var17 * 3 + 1], var15[var17 * 3 + 2]);
            class_243 var6 = new class_243(var14[var17 * 3], var14[var17 * 3 + 1], var14[var17 * 3 + 2]);
            if (!this.a(var18, var6, false)) {
               var16 = false;
            }
         }

         return var16;
      } else {
         return this.a.a(var15, var14);
      }
   }

   public final boolean a(class_243 var1, class_243 var2, boolean var3) {
      if (!var3) {
         var3 = var1.equals(var2) || this.a.a(var1, var2);
      } else {
         var3 = this.a.world().method_17742(new class_3959(var1, var2, class_3960.field_17558, class_242.field_1348, this.a.player())).method_17783()
            == class_240.field_1333;
      }

      if (Baritone.a().elytraRenderRaytraces.value) {
         (var3 ? this.a : this.b).add(new Pair<>(var1, var2));
      }

      return var3;
   }

   private static FloatArrayList a(float var0, boolean var1) {
      float var2 = var1 ? -90.0F : Math.max(var0 - Baritone.a().elytraPitchRange.value.intValue(), -89.0F);
      float var5 = var1 ? 90.0F : Math.min(var0 + Baritone.a().elytraPitchRange.value.intValue(), 89.0F);
      FloatArrayList var3 = new FloatArrayList(BaritoneMath.b(var5 - var2) + 1);

      for (float var4 = var0; var4 <= var5; var4++) {
         var3.add(var4);
      }

      for (float var6 = var0 - 1.0F; var6 >= var2; var6--) {
         var3.add(var6);
      }

      return var3;
   }

   private Pair<Float, Boolean> a(ElytraBehavior.SolverContext var1, class_243 var2, int var3) {
      boolean var4 = var3 == 2;
      FloatArrayList var5 = a(RotationUtils.calcRotationFromVec3d(var1.a, var2, this.a.playerRotations()).getPitch(), var4);
      ElytraBehavior.IntTriFunction var9 = (var5x, var6x, var7x) -> {
         FloatListIterator var10004 = var5.iterator();
         int var8x = var7x;
         var7x = var6x;
         var6x = var5x;
         FloatListIterator var49 = var10004;
         int var48 = var3;
         class_243 var47 = var2;
         ElytraBehavior.SolverContext var46 = var1;
         ElytraBehavior var45 = this;
         class_243 var9x;
         class_243 var10x = (var9x = var47.method_1020(var46.a)).method_1029();
         ArrayDeque var11x = new ArrayDeque();

         while (var49.hasNext()) {
            float var12x = var49.nextFloat();
            class_243 var15 = var9x;
            ElytraBehavior.SolverContext var14 = var46;
            ElytraBehavior var13x = var45;
            ITickableAimProcessor var17 = var14.a.fork();
            class_243 var18 = var14.b;
            class_238 var19 = var14.a;
            ArrayList var20;
            (var20 = new ArrayList(var6x + 1)).add(class_243.field_1353);
            int var16 = var7x;

            ArrayList var72;
            label103: {
               for (int var21 = 0; var21 < var6x && !(var15.method_1027() < 1.0); var21++) {
                  Rotation var22;
                  class_243 var23 = RotationUtils.calcLookDirectionFromRotation(
                     var22 = var17.nextRotation(RotationUtils.calcRotationFromVec3d(class_243.field_1353, var15, var13x.a.playerRotations()).withPitch(var12x))
                  );
                  float var24 = var22.getPitch();
                  class_243 var61 = var23;
                  class_243 var58 = var18;
                  double var31 = var18.field_1352;
                  double var33 = var58.field_1351;
                  double var35 = var58.field_1350;
                  float var59 = var24 * (float) (Math.PI / 180.0);
                  double var37 = Math.sqrt(var61.field_1352 * var61.field_1352 + var61.field_1350 * var61.field_1350);
                  double var39 = Math.sqrt(var31 * var31 + var35 * var35);
                  double var41 = var61.method_1033();
                  var24 = (float)((double)(var24 = class_3532.method_15362(var59)) * var24 * Math.min(1.0, var41 / 0.4));
                  if ((var33 = var33 + (-0.08 + var24 * 0.06)) < 0.0 && var37 > 0.0) {
                     double var43 = var33 * -0.1 * var24;
                     var33 += var43;
                     var31 += var61.field_1352 * var43 / var37;
                     var35 += var61.field_1350 * var43 / var37;
                  }

                  if (var59 < 0.0F) {
                     double var71 = var39 * -class_3532.method_15374(var59) * 0.04;
                     var33 += var71 * 3.2;
                     var31 -= var61.field_1352 * var71 / var37;
                     var35 -= var61.field_1350 * var71 / var37;
                  }

                  if (var37 > 0.0) {
                     var31 += (var61.field_1352 / var37 * var39 - var31) * 0.1;
                     var35 += (var61.field_1350 / var37 * var39 - var35) * 0.1;
                  }

                  var31 *= 0.99F;
                  var33 *= 0.98F;
                  var35 *= 0.99F;
                  var18 = new class_243(var31, var33, var35);
                  var15 = var15.method_1020(var18);
                  class_238 var62;
                  int var66 = BaritoneMath.a((var62 = var19.method_1009(var18.field_1352, var18.field_1351, var18.field_1350).method_1014(0.01)).field_1323);
                  int var25 = BaritoneMath.b(var62.field_1320);
                  int var26 = BaritoneMath.a(var62.field_1322);
                  int var27 = BaritoneMath.b(var62.field_1325);
                  int var28 = BaritoneMath.a(var62.field_1321);
                  int var63 = BaritoneMath.b(var62.field_1324);

                  while (var66 < var25) {
                     for (int var29 = var26; var29 < var27; var29++) {
                        for (int var30 = var28; var30 < var63; var30++) {
                           if (!var13x.a(var66, var29, var30, var14.a)) {
                              var72 = null;
                              break label103;
                           }
                        }
                     }

                     var66++;
                  }

                  var19 = var19.method_997(var18);
                  var20.add(((class_243)var20.get(var20.size() - 1)).method_1019(var18));
                  if (var21 >= var8x && var16-- > 0) {
                     var18 = var18.method_1031(
                        var23.field_1352 * 0.1 + (var23.field_1352 * 1.5 - var18.field_1352) * 0.5,
                        var23.field_1351 * 0.1 + (var23.field_1351 * 1.5 - var18.field_1351) * 0.5,
                        var23.field_1350 * 0.1 + (var23.field_1350 * 1.5 - var18.field_1350) * 0.5
                     );
                  }
               }

               var72 = var20;
            }

            ArrayList var53 = var72;
            if (var72 != null) {
               class_243 var55 = (class_243)var53.get(var53.size() - 1);
               double var60 = var10x.method_1026(var55.method_1029());
               if (var45.a) {
                  var60 = -var9x.method_1020(var55).method_1033();
               }

               ElytraBehavior.PitchResult var56;
               if ((var56 = (ElytraBehavior.PitchResult)var11x.peek()) == null || var60 > var56.a) {
                  var11x.push(new ElytraBehavior.PitchResult(var12x, var60, var53));
               }
            }
         }

         Iterator var52 = var11x.iterator();

         ElytraBehavior.PitchResult var54;
         label73:
         while (true) {
            if (!var52.hasNext()) {
               return null;
            }

            var54 = (ElytraBehavior.PitchResult)var52.next();
            if (var48 < 2) {
               int var57 = var54.a.size() - 1;

               while (true) {
                  if (var57 <= 0) {
                     break label73;
                  }

                  if (!var45.a(var46.a.method_1019(var54.a.get(var57)), var47, var46.a)) {
                     break;
                  }

                  var57--;
               }
            } else if (var45.a(var46.a.method_1019(var54.a.get(var54.a.size() - 1)), var47, var46.a)) {
               break;
            }
         }

         var45.c = var54.a;
         return var54;
      };
      ArrayList var10 = new ArrayList();
      if (var1.a.a()) {
         int var11;
         if ((var11 = var1.a.a()) == 0) {
            int var6 = Math.max(4, 10 - var1.a.b());
            var10.add(new ElytraBehavior.IntTriple(var6, 1, 0));
         } else if (var11 <= 5) {
            var10.add(new ElytraBehavior.IntTriple(var11 + 5, var11, 0));
         } else {
            var10.add(new ElytraBehavior.IntTriple(var11 + 1, var11, 0));
         }
      }

      int var12 = var4 ? 3 : (var1.a.a() ? Math.max(5, var1.a.a()) : Baritone.a().elytraSimulationTicks.value);
      var10.add(new ElytraBehavior.IntTriple(var12, var1.a.a() ? var12 : 0, 0));
      Optional var13;
      if ((var13 = var10.stream().map(var1x -> (ElytraBehavior.PitchResult)var9.apply(var1x.a, var1x.b, var1x.c)).filter(Objects::nonNull).findFirst())
         .isPresent()) {
         return new Pair<>(((ElytraBehavior.PitchResult)var13.get()).a, Boolean.FALSE);
      }

      if (var4) {
         ArrayList var7;
         (var7 = new ArrayList()).add(new ElytraBehavior.IntTriple(var12, 10, 3));
         var7.add(new ElytraBehavior.IntTriple(var12, 10, 2));
         var7.add(new ElytraBehavior.IntTriple(var12, 10, 1));
         Optional var8;
         if ((var8 = var7.stream().map(var1x -> (ElytraBehavior.PitchResult)var9.apply(var1x.a, var1x.b, var1x.c)).filter(Objects::nonNull).findFirst())
            .isPresent()) {
            return new Pair<>(((ElytraBehavior.PitchResult)var8.get()).a, Boolean.TRUE);
         }
      }

      return null;
   }

   final boolean a(int var1, int var2, int var3, boolean var4) {
      if (var4) {
         class_2680 var8;
         return (var8 = this.a.a(var1, var2, var3)).method_26204() instanceof class_2189 || MovementHelper.e(var8);
      }

      var4 = var3;
      var3 = var2;
      var2 = var1;
      BlockStateOctreeInterface var7 = this.a;
      boolean var10000;
      if ((var3 | 127 - var3) < 0) {
         var10000 = false;
      } else {
         int var5 = var2 >> 4;
         int var6 = var4 >> 4;
         if (var7.b == 0L | (var5 ^ var7.a | var6 ^ var7.b) != 0) {
            var7.a = var5;
            var7.b = var6;
            var7.b = NetherPathfinder.getOrCreateChunk(var7.a, var5, var6);
         }

         var10000 = Octree.getBlock(var7.b, var2 & 15, var3 & 127, var4 & 15);
      }

      return !var10000;
   }

   public final void a(int var1, int var2, class_1713 var3) {
      this.a.add(() -> this.a.playerController().windowClick(var1, var2, var3x, var3, this.a.player()));
   }

   public final void a(String var1) {
      if (Baritone.a().elytraChatSpam.value) {
         this.logDebug(var1);
      }
   }

   public static final class FireworkBoost {
      private final Integer a;
      private final int a;
      private final int b;

      public FireworkBoost(Integer var1, int var2) {
         this.a = var1;
         this.a = var2;
         this.b = var2 + 11;
      }

      public final boolean a() {
         return this.a != null;
      }

      public final int a() {
         return this.a() ? Math.max(0, this.a - this.a) : 0;
      }

      public final int b() {
         return this.a() ? Math.max(0, this.b - this.a) : 0;
      }

      @Override
      public final boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && var1.getClass() == ElytraBehavior.FireworkBoost.class) {
            var1 = var1;
            return !this.a() && !var1.a() ? true : Objects.equals(this.a, var1.a) && this.a == var1.a && this.b == var1.b;
         } else {
            return false;
         }
      }
   }

   @FunctionalInterface
   interface IntTriFunction<T> {
      T apply(int var1, int var2, int var3);
   }

   static final class IntTriple {
      public final int a;
      public final int b;
      public final int c;

      public IntTriple(int var1, int var2, int var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }

   public final class PathManager {
      public NetherPath a;
      public boolean a;
      public boolean b;
      public int a;
      public int b;
      public int c;

      public PathManager() {
         this.a();
      }

      public final CompletableFuture<Void> a() {
         return this.a(ElytraBehavior.this.a.playerFeet());
      }

      public final CompletableFuture<Void> a(class_2338 var1) {
         long var2 = System.nanoTime();
         return this.a(var1, ElytraBehavior.this.b, UnaryOperator.identity()).thenRun(() -> {
            double var3 = this.a.a(0).distanceTo(this.a.a(this.a.size() - 1));
            if (this.a) {
               ElytraBehavior.this.a(String.format("Computed path (%.1f blocks in %.4f seconds)", var3, (System.nanoTime() - var2) / 1.0E9));
            } else {
               ElytraBehavior.this.a(String.format("Computed segment (Next %.1f blocks in %.4f seconds)", var3, (System.nanoTime() - var2) / 1.0E9));
            }
         }).whenComplete((var1x, var2x) -> {
            this.b = false;
            if (var2x != null) {
               Throwable var3;
               if ((var3 = var2x.getCause()) instanceof PathCalculationException) {
                  ElytraBehavior.this.logDirect("Failed to compute path to destination");
                  return;
               }

               ElytraBehavior.this.logUnhandledException(var3);
            }
         });
      }

      private CompletableFuture<Void> a(OptionalInt var1) {
         if (this.b) {
            throw new IllegalStateException("already recalculating");
         }

         this.b = true;
         List var2 = var1.isPresent() ? this.a.subList(var1.getAsInt() + 1, this.a.size()) : Collections.emptyList();
         boolean var3 = this.a;
         return this.a(ElytraBehavior.this.a.playerFeet(), var1.isPresent() ? this.a.a(var1.getAsInt()) : ElytraBehavior.this.b, var3x -> {
            Stream var10001 = var2.stream();
            boolean var6 = var3 || var3x.a && !var1.isPresent();
            Stream var5 = var10001;
            UnpackedSegment var4 = var3x;
            return new UnpackedSegment(Stream.concat(var4.a, var5), var6);
         }).whenComplete((var1x, var2x) -> {
            this.b = false;
            if (var2x != null) {
               Throwable var3x;
               if ((var3x = var2x.getCause()) instanceof PathCalculationException) {
                  ElytraBehavior.this.logDirect("Failed to recompute segment");
                  return;
               }

               ElytraBehavior.this.logUnhandledException(var3x);
            }
         });
      }

      public final void a(int var1) {
         if (!this.b) {
            this.b = true;
            List var2 = this.a.subList(0, var1 + 1);
            long var3 = System.nanoTime();
            BetterBlockPos var5 = this.a.a(var1);
            this.a(var5, ElytraBehavior.this.b, var1x -> {
               Stream var3x = var2.stream();
               UnpackedSegment var2x = var1x;
               return new UnpackedSegment(Stream.concat(var3x, var2x.a), var2x.a);
            }).thenRun(() -> {
               int var6 = this.a.size() - var2.size() - 1;
               double var4 = this.a.a(0).distanceTo(this.a.a(var6));
               if (this.a) {
                  ElytraBehavior.this.a(String.format("Computed path (%.1f blocks in %.4f seconds)", var4, (System.nanoTime() - var3) / 1.0E9));
               } else {
                  ElytraBehavior.this.a(String.format("Computed segment (Next %.1f blocks in %.4f seconds)", var4, (System.nanoTime() - var3) / 1.0E9));
               }
            }).whenComplete((var2x, var3x) -> {
               this.b = false;
               if (var3x != null) {
                  Throwable var4;
                  if ((var4 = var3x.getCause()) instanceof PathCalculationException) {
                     ElytraBehavior.this.logDirect("Failed to compute next segment");
                     if (ElytraBehavior.this.a.player().method_5707(var5.method_46558()) < 256.0) {
                        ElytraBehavior.this.a("Player is near the segment start, therefore repeating this calculation is pointless. Marking as complete");
                        this.a = true;
                        return;
                     }
                  } else {
                     ElytraBehavior.this.logUnhandledException(var4);
                  }
               }
            });
         }
      }

      public final void a() {
         this.a = NetherPath.a();
         this.a = true;
         this.b = false;
         this.c = 0;
         this.b = 0;
         this.a = 0;
      }

      private void a(UnpackedSegment var1) {
         List var2 = var1.a.collect(Collectors.toList());
         HashMap var3 = new HashMap();

         for (int var4 = 0; var4 < var2.size(); var4++) {
            BetterBlockPos var5 = (BetterBlockPos)var2.get(var4);
            if (var3.containsKey(var5)) {
               for (int var8 = (Integer)var3.get(var5); var4 > var8; var4--) {
                  var2.remove(var4);
               }
            } else {
               var3.put(var5, var4);
            }
         }

         if (ElytraBehavior.this.c) {
            BetterBlockPos var6 = ElytraBehavior.this.b;
            class_2338 var7;
            if ((var7 = !var2.isEmpty() ? (class_2338)var2.get(var2.size() - 1) : null) != null
               && ElytraBehavior.this.a(class_243.method_24954(var6), class_243.method_24954(var7), false)) {
               var2.add(new BetterBlockPos(var6));
            } else {
               ElytraBehavior.this.logDirect("unable to land at " + ElytraBehavior.this.b);
               ElytraBehavior.this.a.a(new BetterBlockPos(ElytraBehavior.this.b));
            }
         }

         this.a = new NetherPath(var2);
         this.a = var1.a;
         this.c = 0;
         this.b = 0;
         this.a = 0;
      }

      private CompletableFuture<Void> a(class_2338 var1, class_2338 var2, UnaryOperator<UnpackedSegment> var3) {
         return ElytraBehavior.this.a
            .a(var1, var2)
            .thenApply(UnpackedSegment::a)
            .thenApply(var3)
            .thenAcceptAsync(this::a, ElytraBehavior.this.a.minecraft()::execute);
      }

      public final void b() {
         if (!this.b) {
            int var1 = this.c;

            int var2;
            for (var2 = this.c; var2 < this.a.size(); var2++) {
               NetherPathfinderContext var10000 = ElytraBehavior.this.a;
               class_1923 var4 = new class_1923(this.a.a(var2));
               if (!NetherPathfinder.hasChunkFromJava(var10000.a, var4.field_9181, var4.field_9180)) {
                  break;
               }
            }

            if (var1 < var2) {
               BetterBlockPos var3 = this.a.a(var1);
               if (ElytraBehavior.this.a(var3.x, var3.y, var3.z, false)) {
                  if (ElytraBehavior.this.a.a != ElytraProcess.State.f && this.b > 100) {
                     this.a(OptionalInt.of(var2 - 1)).thenRun(() -> ElytraBehavior.this.a("Recalculating segment, no progress in last 100 ticks"));
                     this.b = 0;
                  } else {
                     boolean var11 = false;

                     for (int var12 = var1; var12 < var2 - 1; var12++) {
                        if (ElytraBehavior.this.a(ElytraBehavior.this.a.playerFeetAsVec(), this.a.a(var12), false)
                           || ElytraBehavior.this.a(ElytraBehavior.this.a.playerHead(), this.a.a(var12), false)) {
                           var11 = true;
                        }

                        if (!ElytraBehavior.this.a(this.a.a(var12), this.a.a(var12 + 1), false)) {
                           OptionalInt var9;
                           if (this.a.a(var2 - 1).distanceSq(ElytraBehavior.this.b) < ElytraBehavior.this.a.playerFeet().distanceSq(ElytraBehavior.this.b)) {
                              var9 = OptionalInt.of(var2 - 1);
                           } else {
                              var9 = OptionalInt.empty();
                           }

                           BetterBlockPos var10 = this.a.a(var12);
                           double var5 = ElytraBehavior.this.a.playerFeet().distanceTo(this.a.a(var9.orElse(this.a.size() - 1)));
                           long var7 = System.nanoTime();
                           this.a(var9)
                              .thenRun(
                                 () -> ElytraBehavior.this.a(
                                    String.format(
                                       "Recalculated segment around path blockage near %s %s %s (next %.1f blocks in %.4f seconds)",
                                       SettingsUtil.maybeCensor(var10.x),
                                       SettingsUtil.maybeCensor(var10.y),
                                       SettingsUtil.maybeCensor(var10.z),
                                       var5,
                                       (System.nanoTime() - var7) / 1.0E9
                                    )
                                 )
                              );
                           return;
                        }
                     }

                     if (!var11 && var1 < var2 - 2 && ElytraBehavior.this.a.a != ElytraProcess.State.c) {
                        this.a(OptionalInt.of(var2 - 1)).thenRun(() -> ElytraBehavior.this.a("Recalculated segment since no path points were visible"));
                     }
                  }
               }
            }
         }
      }

      public final void c() {
         if (!this.a.isEmpty()) {
            int var1 = this.c;
            BetterBlockPos var2 = ElytraBehavior.this.a.playerFeet();

            for (int var3 = var1; var3 >= Math.max(var1 - 1000, 0); var3 -= 10) {
               if (this.a.a(var3).distanceSq(var2) < this.a.a(var1).distanceSq(var2)) {
                  var1 = var3;
               }
            }

            for (int var4 = var1; var4 < Math.min(var1 + 1000, this.a.size()); var4 += 10) {
               if (this.a.a(var4).distanceSq(var2) < this.a.a(var1).distanceSq(var2)) {
                  var1 = var4;
               }
            }

            for (int var5 = var1; var5 >= Math.max(var1 - 50, 0); var5--) {
               if (this.a.a(var5).distanceSq(var2) < this.a.a(var1).distanceSq(var2)) {
                  var1 = var5;
               }
            }

            for (int var6 = var1; var6 < Math.min(var1 + 50, this.a.size()); var6++) {
               if (this.a.a(var6).distanceSq(var2) < this.a.a(var1).distanceSq(var2)) {
                  var1 = var6;
               }
            }

            this.c = var1;
         }
      }
   }

   static final class PitchResult {
      public final float a;
      public final double a;
      public final List<class_243> a;

      public PitchResult(float var1, double var2, List<class_243> var4) {
         this.a = var1;
         this.a = var2;
         this.a = var4;
      }
   }

   public static final class Solution {
      public final ElytraBehavior.SolverContext a;
      public final Rotation a;
      public final class_243 a;
      public final boolean a;
      public final boolean b;

      public Solution(ElytraBehavior.SolverContext var1, Rotation var2, class_243 var3, boolean var4, boolean var5) {
         this.a = var1;
         this.a = var2;
         this.a = var3;
         this.a = var4;
         this.b = var5;
      }
   }

   public final class SolverContext {
      public final NetherPath a;
      public final int a;
      public final class_243 a;
      public final class_243 b;
      public final class_238 a;
      public final boolean a;
      public final ElytraBehavior.FireworkBoost a;
      public final ITickableAimProcessor a;

      public SolverContext(boolean var2) {
         this.a = ElytraBehavior.this.a.a;
         this.a = ElytraBehavior.this.a.c;
         this.a = ElytraBehavior.this.a.playerFeetAsVec();
         this.b = ElytraBehavior.this.a.playerMotion();
         this.a = ElytraBehavior.this.a.player().method_5829();
         this.a = ElytraBehavior.this.a.player().method_5771();
         Integer var3;
         if (var2 && ElytraBehavior.this.b) {
            int[] var4 = ElytraBehavior.this.a;
            var3 = ElytraBehavior.this.a[1] > var4[0] ? 0 : null;
         } else {
            var3 = ElytraBehavior.this.a().map(var0 -> var0.field_6012).orElse(null);
         }

         this.a = new ElytraBehavior.FireworkBoost(var3, ElytraBehavior.this.c);
         ITickableAimProcessor var5 = ElytraBehavior.this.a.a.getAimProcessor().fork();
         if (var2) {
            var5.advance(1);
         }

         this.a = var5;
      }

      @Override
      public final boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && var1.getClass() == ElytraBehavior.SolverContext.class) {
            var1 = var1;
            return this.a == var1.a
               && this.a == var1.a
               && Objects.equals(this.a, var1.a)
               && Objects.equals(this.b, var1.b)
               && Objects.equals(this.a, var1.a)
               && this.a == var1.a
               && Objects.equals(this.a, var1.a);
         } else {
            return false;
         }
      }
   }
}
