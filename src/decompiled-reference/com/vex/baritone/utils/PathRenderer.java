package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.event.events.RenderEvent;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalGetToBlock;
import com.vex.baritone.api.pathing.goals.GoalInverted;
import com.vex.baritone.api.pathing.goals.GoalTwoBlocks;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.pathing.goals.GoalYLevel;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.interfaces.IGoalRenderPos;
import com.vex.baritone.behavior.PathingBehavior;
import com.vex.baritone.pathing.path.PathExecutor;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_287;
import net.minecraft.class_2874;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public final class PathRenderer implements IRenderer {
   private PathRenderer() {
   }

   public static double a() {
      return a.renderPosX();
   }

   public static double b() {
      return a.renderPosY();
   }

   public static double c() {
      return a.renderPosZ();
   }

   public static void a(RenderEvent var0, PathingBehavior var1) {
      IPlayerContext var2 = var1.a;
      if (var1.a.world() != null) {
         if (var2.minecraft().field_1755 instanceof GuiClick) {
            GuiClick var10000 = (GuiClick)var2.minecraft().field_1755;
            Matrix4f var4 = var0.getProjectionMatrix();
            class_4587 var3 = var0.getModelViewStack();
            GuiClick var5 = var10000;
            var10000.a = new Matrix4f(var4);
            var5.a.mul(var3.method_23760().method_23761());
            var5.a.invert();
            if (var5.b != null) {
               class_1297 var9 = GuiClick.mc.method_1560();
               a(var3, var9, Collections.singletonList(var5.b), Color.CYAN);
               if (var5.a != null && !var5.a.equals(var5.b)) {
                  class_287 var10 = IRenderer.a(Color.RED);
                  BetterBlockPos var6 = new BetterBlockPos(var5.b);
                  BetterBlockPos var13 = new BetterBlockPos(var5.a);
                  IRenderer.a(
                     var10,
                     var3,
                     new class_238(
                        Math.min(var6.x, var13.x),
                        Math.min(var6.y, var13.y),
                        Math.min(var6.z, var13.z),
                        Math.max(var6.x, var13.x) + 1,
                        Math.max(var6.y, var13.y) + 1,
                        Math.max(var6.z, var13.z) + 1
                     ),
                     Baritone.a().pathRenderLineWidthPixels.value
                  );
                  IRenderer.a(var10, true);
               }
            }
         }

         float var7 = var0.getPartialTicks();
         Goal var11 = var1.getGoal();
         class_2874 var14 = var2.world().method_8597();
         class_2874 var16 = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world().method_8597();
         if (var14 == var16) {
            if (var11 != null && a.renderGoal.value) {
               a(var0.getModelViewStack(), var2, var11, var7, a.colorGoalBox.value);
            }

            if (a.renderPath.value) {
               PathExecutor var8 = var1.a;
               PathExecutor var12 = var1.b;
               if (var8 != null && a.renderSelectionBoxes.value) {
                  a(var0.getModelViewStack(), var2.player(), Collections.unmodifiableSet(var8.a), a.colorBlocksToBreak.value);
                  a(var0.getModelViewStack(), var2.player(), Collections.unmodifiableSet(var8.b), a.colorBlocksToPlace.value);
                  a(var0.getModelViewStack(), var2.player(), Collections.unmodifiableSet(var8.c), a.colorBlocksToWalkInto.value);
               }

               if (var8 != null && var8.getPath() != null) {
                  int var15 = Math.max(var8.getPosition() - 3, 0);
                  a(var0.getModelViewStack(), var8.getPath().positions(), var15, a.colorCurrentPath.value, a.fadePath.value);
               }

               if (var12 != null && var12.getPath() != null) {
                  a(var0.getModelViewStack(), var12.getPath().positions(), 0, a.colorNextPath.value, a.fadePath.value);
               }

               var1.getInProgress().ifPresent(var2x -> {
                  var2x.bestPathSoFar().ifPresent(var1xx -> a(var0.getModelViewStack(), var1xx.positions(), 0, a.colorBestPathSoFar.value, a.fadePath.value));
                  var2x.pathToMostRecentNodeConsidered().ifPresent(var2xx -> {
                     a(var0.getModelViewStack(), var2xx.positions(), 0, a.colorMostRecentConsidered.value, a.fadePath.value);
                     a(var0.getModelViewStack(), var2.player(), Collections.singletonList(var2xx.getDest()), a.colorMostRecentConsidered.value);
                  });
               });
            }
         }
      }
   }

   private static void a(class_4587 var0, List<BetterBlockPos> var1, int var2, Color var3, boolean var4) {
      a(var0, var1, var2, var3, var4, 10, 20, 0.5);
   }

   public static void a(class_4587 var0, List<BetterBlockPos> var1, int var2, Color var3, boolean var4, int var5, int var6, double var7) {
      class_287 var9 = IRenderer.a(var3);
      var5 += var2;
      var6 += var2;

      while (var2 < var1.size() - 1) {
         BetterBlockPos var11 = (BetterBlockPos)var1.get(var2);
         int var10;
         BetterBlockPos var12;
         int var13 = (var12 = (BetterBlockPos)var1.get(var10 = var2 + 1)).x - var11.x;
         int var14 = var12.y - var11.y;
         int var15 = var12.z - var11.z;

         while (
            var10 + 1 < var1.size()
               && (!var4 || var10 + 1 < var5)
               && var13 == ((BetterBlockPos)var1.get(var10 + 1)).x - var12.x
               && var14 == ((BetterBlockPos)var1.get(var10 + 1)).y - var12.y
               && var15 == ((BetterBlockPos)var1.get(var10 + 1)).z - var12.z
         ) {
            var12 = (BetterBlockPos)var1.get(++var10);
         }

         if (var4) {
            float var16;
            if (var2 <= var5) {
               var16 = 0.4F;
            } else {
               if (var2 > var6) {
                  break;
               }

               var16 = 0.4F * (1.0F - (float)(var2 - var5) / (var6 - var5));
            }

            IRenderer.a(var3, var16);
         }

         a(var9, var0, var11.x, var11.y, var11.z, var12.x, var12.y, var12.z, var7);
         var2 = var10;
      }

      IRenderer.a(var9, a.renderPathIgnoreDepth.value);
   }

   private static void a(class_287 var0, class_4587 var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14) {
      double var16 = var14 + 0.03;
      double var18 = a();
      double var20 = b();
      double var22 = c();
      boolean var24 = !a.renderPathAsLine.value;
      IRenderer.a(
         var0,
         var1,
         var2 + var14 - var18,
         var4 + var14 - var20,
         var6 + var14 - var22,
         var8 + var14 - var18,
         var10 + var14 - var20,
         var12 + var14 - var22,
         a.pathRenderLineWidthPixels.value
      );
      if (var24) {
         IRenderer.a(
            var0,
            var1,
            var8 + var14 - var18,
            var10 + var14 - var20,
            var12 + var14 - var22,
            var8 + var14 - var18,
            var10 + var16 - var20,
            var12 + var14 - var22,
            a.pathRenderLineWidthPixels.value
         );
         IRenderer.a(
            var0,
            var1,
            var8 + var14 - var18,
            var10 + var16 - var20,
            var12 + var14 - var22,
            var2 + var14 - var18,
            var4 + var16 - var20,
            var6 + var14 - var22,
            a.pathRenderLineWidthPixels.value
         );
         IRenderer.a(
            var0,
            var1,
            var2 + var14 - var18,
            var4 + var16 - var20,
            var6 + var14 - var22,
            var2 + var14 - var18,
            var4 + var14 - var20,
            var6 + var14 - var22,
            a.pathRenderLineWidthPixels.value
         );
      }
   }

   private static void a(class_4587 var0, class_1297 var1, Collection<class_2338> var2, Color var3) {
      class_287 var5 = IRenderer.a(var3);
      BlockStateInterface var4 = new BlockStateInterface(BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext());
      var2.forEach(
         var4x -> {
            class_265 var5x;
            class_238 var6 = ((var5x = var4.a(var4x).method_26218(var1.method_73183(), var4x)).method_1110()
                  ? class_259.method_1077().method_1107()
                  : var5x.method_1107())
               .method_996(var4x);
            IRenderer.a(var5, var0, var6, 0.002, a.pathRenderLineWidthPixels.value);
         }
      );
      IRenderer.a(var5, a.renderSelectionBoxesIgnoreDepth.value);
   }

   public static void a(class_4587 var0, IPlayerContext var1, Goal var2, float var3, Color var4) {
      a(null, var0, var1, var2, var3, var4, true);
   }

   private static void a(@Nullable class_287 var0, class_4587 var1, IPlayerContext var2, Goal var3, float var4, Color var5, boolean var6) {
      if (!var6 && var0 == null) {
         throw new RuntimeException("BufferBuilder must not be null if setupRender is false");
      }

      double var7 = a();
      double var9 = b();
      double var11 = c();
      double var25;
      if (!a.renderGoalAnimated.value) {
         var25 = 0.999F;
      } else {
         var25 = class_3532.method_15362((float)((float)(System.nanoTime() / 100000L % 20000L) / 20000.0F * Math.PI * 2.0));
      }

      if (!(var3 instanceof IGoalRenderPos)) {
         if (var3 instanceof GoalXZ var53) {
            double var44 = var2.world().method_31607();
            double var47 = var2.world().method_31600();
            a.renderGoalXZBeacon.value;
            double var36 = var53.getX() + 0.002 - var7;
            double var38 = var53.getX() + 1 - 0.002 - var7;
            double var40 = var53.getZ() + 0.002 - var11;
            double var42 = var53.getZ() + 1 - 0.002 - var11;
            var44 -= var9;
            var47 -= var9;
            a(var0, var1, var5, var36, var38, var40, var42, var44, var47, 0.0, 0.0, (boolean)var6);
         } else if (var3 instanceof GoalComposite) {
            boolean var52;
            if (var52 = Arrays.stream(((GoalComposite)var3).goals()).allMatch(IGoalRenderPos.class::isInstance)) {
               var0 = IRenderer.a(var5, a.goalRenderLineWidthPixels.value);
            }

            Goal[] var32;
            var6 = (var32 = ((GoalComposite)var3).goals()).length;

            for (int var34 = 0; var34 < var6; var34++) {
               Goal var8 = var32[var34];
               a(var0, var1, var2, var8, var4, var5, !var52);
            }

            if (var52) {
               IRenderer.a(var0, a.renderGoalIgnoreDepth.value);
            }
         } else if (var3 instanceof GoalInverted) {
            a(var1, var2, ((GoalInverted)var3).origin, var4, a.colorInvertedGoalBox.value);
         } else {
            if (var3 instanceof GoalYLevel var51) {
               double var35 = var2.player().method_73189().field_1352 - a.yLevelBoxSize.value - var7;
               double var39 = var2.player().method_73189().field_1350 - a.yLevelBoxSize.value - var11;
               double var37 = var2.player().method_73189().field_1352 + a.yLevelBoxSize.value - var7;
               double var41 = var2.player().method_73189().field_1350 + a.yLevelBoxSize.value - var11;
               double var43;
               double var46 = (var43 = ((GoalYLevel)var3).level - var9) + 2.0;
               double var49 = var25 + 1.0 + var51.level - var9;
               double var50 = 1.0 - var25 + var51.level - var9;
               a(var0, var1, var5, var35, var37, var39, var41, var43, var46, var49, var50, (boolean)var6);
            }
         }
      } else {
         class_2338 var31;
         double var13 = (var31 = ((IGoalRenderPos)var3).getGoalPos()).method_10263() + 0.002 - var7;
         double var15 = var31.method_10263() + 1 - 0.002 - var7;
         double var17 = var31.method_10260() + 0.002 - var11;
         double var19 = var31.method_10260() + 1 - 0.002 - var11;
         if (var3 instanceof GoalGetToBlock || var3 instanceof GoalTwoBlocks) {
            var25 /= 2.0;
         }

         double var27 = var25 + 1.0 + var31.method_10264() - var9;
         double var29 = 1.0 - var25 + var31.method_10264() - var9;
         double var21;
         double var23 = (var21 = var31.method_10264() - var9) + 2.0;
         if (var3 instanceof GoalGetToBlock || var3 instanceof GoalTwoBlocks) {
            var27 -= 0.5;
            var29 -= 0.5;
            var23--;
         }

         a(var0, var1, var5, var13, var15, var17, var19, var21, var23, var27, var29, (boolean)var6);
      }
   }

   private static void a(
      class_287 var0,
      class_4587 var1,
      Color var2,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      boolean var19
   ) {
      if (var19) {
         var0 = IRenderer.a(var2);
      }

      a(var0, var1, var3, var5, var7, var9, var15, a.goalRenderLineWidthPixels.value);
      a(var0, var1, var3, var5, var7, var9, var17, a.goalRenderLineWidthPixels.value);

      for (double var20 = var11; var20 < var13; var20 += 16.0) {
         double var22 = Math.min(var13, var20 + 16.0);
         IRenderer.a(var0, var1, var3, var20, var7, var3, var22, var7, 0.0, 1.0, 0.0, a.goalRenderLineWidthPixels.value);
         IRenderer.a(var0, var1, var5, var20, var7, var5, var22, var7, 0.0, 1.0, 0.0, a.goalRenderLineWidthPixels.value);
         IRenderer.a(var0, var1, var5, var20, var9, var5, var22, var9, 0.0, 1.0, 0.0, a.goalRenderLineWidthPixels.value);
         IRenderer.a(var0, var1, var3, var20, var9, var3, var22, var9, 0.0, 1.0, 0.0, a.goalRenderLineWidthPixels.value);
      }

      if (var19) {
         IRenderer.a(var0, a.renderGoalIgnoreDepth.value);
      }
   }

   private static void a(class_287 var0, class_4587 var1, double var2, double var4, double var6, double var8, double var10, float var12) {
      if (var10 != 0.0) {
         IRenderer.a(var0, var1, var2, var10, var6, var4, var10, var6, 1.0, 0.0, 0.0, var12);
         IRenderer.a(var0, var1, var4, var10, var6, var4, var10, var8, 0.0, 0.0, 1.0, var12);
         IRenderer.a(var0, var1, var4, var10, var8, var2, var10, var8, -1.0, 0.0, 0.0, var12);
         IRenderer.a(var0, var1, var2, var10, var8, var2, var10, var6, 0.0, 0.0, -1.0, var12);
      }
   }
}
