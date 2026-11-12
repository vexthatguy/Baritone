package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalGetToBlock;
import com.vex.baritone.api.process.IFarmProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.RayTraceUtils;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2256;
import net.minecraft.class_2282;
import net.minecraft.class_2302;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_2421;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3965;
import net.minecraft.class_2350.class_2353;

public final class FarmProcess extends BaritoneProcessHelper implements IFarmProcess {
   private boolean a;
   private List<class_2338> a;
   private int a;
   private int b;
   private class_2338 a;
   private static final List<class_1792> b = Arrays.asList(
      class_1802.field_8309, class_1802.field_46250, class_1802.field_8317, class_1802.field_46249, class_1802.field_8567, class_1802.field_8179
   );
   private static final List<class_1792> c = Arrays.asList(
      class_1802.field_8309,
      class_1802.field_8186,
      class_1802.field_46250,
      class_1802.field_8497,
      class_2246.field_46283.method_8389(),
      class_1802.field_8317,
      class_1802.field_8861,
      class_1802.field_46249,
      class_2246.field_46282.method_8389(),
      class_1802.field_8567,
      class_1802.field_8179,
      class_1802.field_8790,
      class_1802.field_8116,
      class_2246.field_10424.method_8389(),
      class_2246.field_10211.method_8389(),
      class_2246.field_10029.method_8389()
   );

   public FarmProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final boolean isActive() {
      return this.a;
   }

   @Override
   public final void farm(int var1, class_2338 var2) {
      if (var2 == null) {
         this.a = super.a.getPlayerContext().playerFeet();
      } else {
         this.a = var2;
      }

      this.b = var1;
      this.a = true;
      this.a = null;
   }

   private boolean a(class_1799 var1) {
      return b.contains(var1.method_7909());
   }

   private boolean b(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8324);
   }

   private boolean c(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8790);
   }

   private boolean d(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8116);
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      if (Baritone.a().mineGoalUpdateInterval.value != 0 && this.a++ % Baritone.a().mineGoalUpdateInterval.value == 0) {
         ArrayList var3 = new ArrayList();
         FarmProcess.Harvest[] var4;
         int var5 = (var4 = FarmProcess.Harvest.values()).length;

         for (int var6 = 0; var6 < var5; var6++) {
            FarmProcess.Harvest var7 = var4[var6];
            var3.add(var7.a);
         }

         if (Baritone.a().replantCrops.value) {
            var3.add(class_2246.field_10362);
            var3.add(class_2246.field_10306);
            if (Baritone.a().replantNetherWart.value) {
               var3.add(class_2246.field_10114);
            }
         }

         Baritone.a()
            .execute(() -> this.a = BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(super.a, var3, Baritone.a().farmMaxScanSize.value, 10, 10));
      }

      if (this.a == null) {
         return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
      }

      ArrayList var18 = new ArrayList();
      ArrayList var19 = new ArrayList();
      ArrayList var20 = new ArrayList();
      ArrayList var21 = new ArrayList();
      ArrayList var22 = new ArrayList();

      for (class_2338 var9 : this.a) {
         if (this.b == 0 || !(var9.method_10262(this.a) > this.b * this.b)) {
            class_2680 var10 = super.a.world().method_8320(var9);
            boolean var11 = super.a.world().method_8320(var9.method_10084()).method_26204() instanceof class_2189;
            if (var10.method_26204() == class_2246.field_10362) {
               if (var11) {
                  var19.add(var9);
               }
            } else if (var10.method_26204() == class_2246.field_10114) {
               if (var11) {
                  var21.add(var9);
               }
            } else if (var10.method_26204() == class_2246.field_10306) {
               for (class_2350 var44 : class_2353.field_11062) {
                  if (super.a.world().method_8320(var9.method_10093(var44)).method_26204() instanceof class_2189) {
                     var22.add(var9);
                     break;
                  }
               }
            } else {
               class_1937 var64 = super.a.world();
               class_2680 var13 = var10;
               class_2338 var12 = var9;
               class_1937 var25 = var64;
               FarmProcess.Harvest[] var14;
               int var15 = (var14 = FarmProcess.Harvest.values()).length;
               int var16 = 0;

               while (true) {
                  boolean var65;
                  if (var16 < var15) {
                     FarmProcess.Harvest var17;
                     if ((var17 = var14[var16]).a != var13.method_26204()) {
                        var16++;
                        continue;
                     }

                     var65 = var17.a(var25, var12, var13);
                  } else {
                     var65 = false;
                  }

                  if (var65) {
                     var18.add(var9);
                  } else {
                     class_2256 var37;
                     if (var10.method_26204() instanceof class_2256
                        && (var37 = (class_2256)var10.method_26204()).method_9651(super.a.world(), var9, var10)
                        && var37.method_9650(super.a.world(), super.a.world().field_9229, var9, var10)) {
                        var20.add(var9);
                     }
                  }
                  break;
               }
            }
         }
      }

      super.a.a.clearAllKeys();
      BetterBlockPos var23 = super.a.playerFeet();
      double var24 = super.a.playerController().getBlockReachDistance();

      for (class_2338 var39 : var18) {
         Optional var45;
         if (!(var23.method_10262(var39) > var24 * var24) && (var45 = RotationUtils.reachable(super.a, var39)).isPresent() && var2) {
            super.a.a.updateTarget((Rotation)var45.get(), true);
            MovementHelper.a(super.a, super.a.world().method_8320(var39));
            if (super.a.isLookingAt(var39)) {
               super.a.a.setInputForceState(Input.CLICK_LEFT, true);
            }

            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         }
      }

      ArrayList var27;
      (var27 = new ArrayList(var19)).addAll(var21);

      for (class_2338 var46 : var27) {
         if (!(var23.method_10262(var46) > var24 * var24)) {
            boolean var28 = var21.contains(var46);
            Optional var55;
            class_239 var59;
            if ((var55 = RotationUtils.reachableOffset(
                     super.a, var46, new class_243(var46.method_10263() + 0.5, var46.method_10264() + 1, var46.method_10260() + 0.5), var24, false
                  ))
                  .isPresent()
               && var2
               && super.a.a.a(true, var28 ? this::c : this::a)
               && (var59 = RayTraceUtils.rayTraceTowards(super.a.player(), (Rotation)var55.get(), var24)) instanceof class_3965
               && ((class_3965)var59).method_17780() == class_2350.field_11036) {
               super.a.a.updateTarget((Rotation)var55.get(), true);
               if (super.a.isLookingAt(var46)) {
                  super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
               }

               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
         }
      }

      for (class_2338 var47 : var22) {
         if (!(var23.method_10262(var47) > var24 * var24)) {
            for (class_2350 var56 : class_2353.field_11062) {
               if (super.a.world().method_8320(var47.method_10093(var56)).method_26204() instanceof class_2189) {
                  class_243 var60 = class_243.method_24953(var47).method_1019(class_243.method_24954(var56.method_62675()).method_1021(0.5));
                  Optional var61;
                  class_239 var63;
                  if ((var61 = RotationUtils.reachableOffset(super.a, var47, var60, var24, false)).isPresent()
                     && var2
                     && super.a.a.a(true, this::d)
                     && (var63 = RayTraceUtils.rayTraceTowards(super.a.player(), (Rotation)var61.get(), var24)) instanceof class_3965
                     && ((class_3965)var63).method_17780() == var56) {
                     super.a.a.updateTarget((Rotation)var61.get(), true);
                     if (super.a.isLookingAt(var47)) {
                        super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                     }

                     return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                  }
               }
            }
         }
      }

      for (class_2338 var48 : var20) {
         Optional var30;
         if (!(var23.method_10262(var48) > var24 * var24)
            && (var30 = RotationUtils.reachable(super.a, var48)).isPresent()
            && var2
            && super.a.a.a(true, this::b)) {
            super.a.a.updateTarget((Rotation)var30.get(), true);
            if (super.a.isLookingAt(var48)) {
               super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
            }

            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         }
      }

      if (var1) {
         this.logDirect("Farm failed");
         if (Baritone.a().notificationOnFarmFail.value) {
            this.logNotification("Farm failed", true);
         }

         this.onLostControl();
         return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
      } else {
         ArrayList var43 = new ArrayList();

         for (class_2338 var31 : var18) {
            var43.add(new BuilderProcess.GoalBreak(var31));
         }

         if (super.a.a.a(false, this::a)) {
            for (class_2338 var32 : var19) {
               var43.add(new GoalBlock(var32.method_10084()));
            }
         }

         if (super.a.a.a(false, this::c)) {
            for (class_2338 var33 : var21) {
               var43.add(new GoalBlock(var33.method_10084()));
            }
         }

         if (super.a.a.a(false, this::d)) {
            for (class_2338 var34 : var22) {
               for (class_2350 var62 : class_2353.field_11062) {
                  if (super.a.world().method_8320(var34.method_10093(var62)).method_26204() instanceof class_2189) {
                     var43.add(new GoalGetToBlock(var34.method_10093(var62)));
                  }
               }
            }
         }

         if (super.a.a.a(false, this::b)) {
            for (class_2338 var35 : var20) {
               var43.add(new GoalBlock(var35));
            }
         }

         Iterator var54 = super.a.entities().iterator();

         while (var54.hasNext()) {
            class_1297 var36;
            if ((var36 = (class_1297)var54.next()) instanceof class_1542 && var36.method_24828()) {
               class_1542 var58 = (class_1542)var36;
               if (c.contains(var58.method_6983().method_7909())) {
                  var43.add(
                     new GoalBlock(new BetterBlockPos(var36.method_73189().field_1352, var36.method_73189().field_1351 + 0.1, var36.method_73189().field_1350))
                  );
               }
            }
         }

         if (var43.isEmpty()) {
            this.logDirect("Farm failed");
            if (Baritone.a().notificationOnFarmFail.value) {
               this.logNotification("Farm failed", true);
            }

            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         } else {
            return new PathingCommand(new GoalComposite(var43.toArray(new Goal[0])), PathingCommandType.SET_GOAL_AND_PATH);
         }
      }
   }

   @Override
   public final void onLostControl() {
      this.a = false;
   }

   @Override
   public final String displayName0() {
      return "Farming";
   }

   enum Harvest {
      a((class_2302)class_2246.field_10293),
      b((class_2302)class_2246.field_10609),
      c((class_2302)class_2246.field_10247),
      d((class_2302)class_2246.field_10341),
      e(class_2246.field_46282, var0 -> true),
      f(class_2246.field_46283, var0 -> true),
      g(class_2246.field_9974, var0 -> (Integer)var0.method_11654(class_2421.field_11306) >= 3),
      h(class_2246.field_10302, var0 -> (Integer)var0.method_11654(class_2282.field_10779) >= 2),
      a,
      a,
      a;

      public final class_2248 a;
      private Predicate<class_2680> a;

      Harvest(class_2302 var3) {
         this(var3, var3::method_9825);
      }

      Harvest(class_2248 var3, Predicate<class_2680> var4) {
         this.a = var3;
         this.a = var4;
      }

      public boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
         return this.a.test(var3);
      }
   }
}
