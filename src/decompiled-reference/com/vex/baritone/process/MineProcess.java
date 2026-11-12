package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalTwoBlocks;
import com.vex.baritone.api.process.IMineProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.BlockOptionalMeta;
import com.vex.baritone.api.utils.BlockOptionalMetaLookup;
import com.vex.baritone.api.utils.BlockUtils;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.cache.CachedChunk;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BaritoneProcessHelper;
import com.vex.baritone.utils.BlockStateInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_2680;
import net.minecraft.class_638;

public final class MineProcess extends BaritoneProcessHelper implements IMineProcess {
   private BlockOptionalMetaLookup a;
   private List<class_2338> a;
   private List<class_2338> b;
   private Map<class_2338, Long> a;
   private BetterBlockPos a;
   private MineProcess$1 a;
   private int a;
   private int b;

   public MineProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final boolean isActive() {
      return this.a != null;
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      int var3;
      if (this.a > 0
         && (var3 = super.a.player().method_31548().method_67533().stream().filter(var1x -> this.a.has(var1x)).mapToInt(class_1799::method_7947).sum())
            >= this.a) {
         this.logDirect("Have " + var3 + " valid items");
         this.cancel();
         return null;
      }

      if (var1) {
         if (this.a.isEmpty() || !Baritone.a().blacklistClosestOnFailure.value) {
            this.logDirect("Unable to find any path to " + this.a + ", canceling mine");
            if (Baritone.a().notificationOnMineFail.value) {
               this.logNotification("Unable to find any path to " + this.a + ", canceling mine", true);
            }

            this.cancel();
            return null;
         }

         this.logDirect("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instance...");
         if (Baritone.a().notificationOnMineFail.value) {
            this.logNotification("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instance...", true);
         }

         this.a.stream().min(Comparator.comparingDouble(super.a.playerFeet()::method_10262)).ifPresent(this.b::add);
         this.a.removeIf(this.b::contains);
      }

      MineProcess var17 = this;
      HashMap var4 = new HashMap<>(var17.a);
      var17.a.getSelectedBlock().ifPresent(var2x -> {
         if (this.a.contains(var2x)) {
            var4.put(var2x, System.currentTimeMillis() + Baritone.a().mineDropLoiterDurationMSThanksLouca.value);
         }
      });

      for (class_2338 var6 : var17.a.keySet()) {
         if ((Long)var4.get(var6) < System.currentTimeMillis()) {
            var4.remove(var6);
         }
      }

      var17.a = var4;
      var3 = Baritone.a().mineGoalUpdateInterval.value;
      ArrayList var12 = new ArrayList<>(this.a);
      if (var3 != 0 && this.b++ % var3 == 0) {
         CalculationContext var19 = new CalculationContext(super.a, true);
         Baritone.a().execute(() -> this.a(var12, var19));
      }

      if (Baritone.a().legitMine.value) {
         MineProcess var20 = this;
         List var25 = this.a();
         var20.a.addAll(var25);
         BetterBlockPos var27 = var20.a.playerFeet();
         BlockStateInterface var29 = new BlockStateInterface(var20.a);
         BlockOptionalMetaLookup var7;
         boolean var33;
         if ((var7 = var20.a()) == null) {
            var33 = false;
         } else {
            for (int var8 = var27.method_10263() - 10; var8 <= var27.method_10263() + 10; var8++) {
               for (int var9 = var27.method_10264() - 10; var9 <= var27.method_10264() + 10; var9++) {
                  for (int var10 = var27.method_10260() - 10; var10 <= var27.method_10260() + 10; var10++) {
                     if (var7.has(var29.a(var8, var9, var10))) {
                        class_2338 var11 = new class_2338(var8, var9, var10);
                        if (Baritone.a().legitMineIncludeDiagonals.value && var20.a.stream().anyMatch(var1x -> var1x.method_10262(var11) <= 2.0)
                           || RotationUtils.reachable(var20.a, var11, 20.0).isPresent()) {
                           var20.a.add(var11);
                        }
                     }
                  }
               }
            }

            var20.a = a(new CalculationContext(var20.a), var20.a, var7, Baritone.a().mineMaxOreLocationsCount.value, var20.b, var25);
            var33 = true;
         }

         if (!var33) {
            this.cancel();
            return null;
         }
      }

      Optional var21 = var12.stream()
         .filter(var1x -> var1x.method_10263() == super.a.playerFeet().method_10263() && var1x.method_10260() == super.a.playerFeet().method_10260())
         .filter(var1x -> var1x.method_10264() >= super.a.playerFeet().method_10264())
         .filter(var1x -> !(BlockStateInterface.a(super.a, var1x).method_26204() instanceof class_2189))
         .min(Comparator.comparingDouble(super.a.playerFeet().above()::method_10262));
      super.a.a.clearAllKeys();
      if (var21.isPresent() && super.a.player().method_24828()) {
         class_2338 var13 = (class_2338)var21.get();
         class_2680 var22 = super.a.a.a(var13);
         Optional var23;
         if (!MovementHelper.a(super.a.a, var13.method_10263(), var13.method_10264(), var13.method_10260(), var22)
            && (var23 = RotationUtils.reachable(super.a, var13)).isPresent()
            && var2) {
            super.a.a.updateTarget((Rotation)var23.get(), true);
            MovementHelper.a(super.a, super.a.world().method_8320(var13));
            if (super.a.isLookingAt(var13) || super.a.playerRotations().isReallyCloseTo((Rotation)var23.get())) {
               super.a.a.setInputForceState(Input.CLICK_LEFT, true);
            }

            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         }
      }

      MineProcess var24 = this;
      BlockOptionalMetaLookup var26;
      PathingCommand var34;
      if ((var26 = this.a()) == null) {
         var34 = null;
      } else {
         boolean var28 = Baritone.a().legitMine.value;
         List var30 = var24.a;
         if (!var24.a.isEmpty()) {
            CalculationContext var31;
            List var14 = a(
               var31 = new CalculationContext(var24.a), new ArrayList<>(var30), var26, Baritone.a().mineMaxOreLocationsCount.value, var24.b, var24.a()
            );
            GoalComposite var16 = new GoalComposite(var14.stream().map(var3x -> {
               CalculationContext var4x = var31;
               List var11x = var14;
               class_2338 var10x = var3x;
               MineProcess var8x = this;
               boolean var5 = !(super.a.a.a(var10x.method_10084()).method_26204() instanceof class_2346);
               if (!Baritone.a().forceInternalMining.value) {
                  return var5 ? new MineProcess.GoalThreeBlocks(var10x) : new GoalTwoBlocks(var10x);
               } else {
                  boolean var6x = var8x.a(var10x.method_10084(), var4x, var11x);
                  boolean var7x = var8x.a(var10x.method_10074(), var4x, var11x);
                  boolean var9x = var8x.a(var10x.method_10087(2), var4x, var11x);
                  if (var6x == var7x) {
                     return var9x && var5 ? new MineProcess.GoalThreeBlocks(var10x) : new GoalTwoBlocks(var10x);
                  } else if (var6x) {
                     return new GoalBlock(var10x);
                  } else {
                     return var9x && var5 ? new GoalTwoBlocks(var10x.method_10074()) : new GoalBlock(var10x.method_10074());
                  }
               }
            }).toArray(Goal[]::new));
            var24.a = var14;
            var34 = new PathingCommand(var16, var28 ? PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH : PathingCommandType.REVALIDATE_GOAL_AND_PATH);
         } else if (!var28 && !Baritone.a().exploreForBlocks.value) {
            var34 = null;
         } else {
            int var32 = Baritone.a().legitMineYLevel.value;
            if (var24.a == null) {
               var24.a = var24.a.playerFeet();
            }

            if (var24.a == null) {
               var24.a = new MineProcess$1(var32, var24.a);
            }

            var34 = new PathingCommand(var24.a, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
         }
      }

      PathingCommand var15 = var34;
      if (var34 == null) {
         this.cancel();
         return null;
      } else {
         return var15;
      }
   }

   @Override
   public final void onLostControl() {
      this.mine(0, null);
   }

   @Override
   public final String displayName0() {
      return "Mine " + this.a;
   }

   private void a(List<class_2338> var1, CalculationContext var2) {
      BlockOptionalMetaLookup var3;
      if ((var3 = this.a()) != null) {
         if (!Baritone.a().legitMine.value) {
            List var4 = this.a();
            List var5;
            (var5 = a(var2, var3, Baritone.a().mineMaxOreLocationsCount.value, var1, this.b, var4)).addAll(var4);
            if (var5.isEmpty() && !Baritone.a().exploreForBlocks.value) {
               this.logDirect("No locations for " + var3 + " known, cancelling");
               if (Baritone.a().notificationOnMineFail.value) {
                  this.logNotification("No locations for " + var3 + " known, cancelling", true);
               }

               this.cancel();
            } else {
               this.a = var5;
            }
         }
      }
   }

   private boolean a(class_2338 var1, CalculationContext var2, List<class_2338> var3) {
      if (var3.contains(var1)) {
         return true;
      }

      class_2680 var4 = var2.a.a(var1);
      return Baritone.a().internalMiningAirException.value && var4.method_26204() instanceof class_2189 ? true : this.a.has(var4) && a(var2, var1);
   }

   private List<class_2338> a() {
      if (!Baritone.a().mineScanDroppedItems.value) {
         return Collections.emptyList();
      }

      ArrayList var1 = new ArrayList();
      Iterator var2 = ((class_638)super.a.world()).method_18112().iterator();

      while (var2.hasNext()) {
         class_1297 var3;
         if ((var3 = (class_1297)var2.next()) instanceof class_1542) {
            class_1542 var4 = (class_1542)var3;
            if (this.a.has(var4.method_6983())) {
               var1.add(var3.method_24515());
            }
         }
      }

      var1.addAll(this.a.keySet());
      return var1;
   }

   public static List<class_2338> a(
      CalculationContext var0, BlockOptionalMetaLookup var1, int var2, List<class_2338> var3, List<class_2338> var4, List<class_2338> var5
   ) {
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      Iterator var8 = var1.blocks().iterator();

      while (var8.hasNext()) {
         class_2248 var9 = ((BlockOptionalMeta)var8.next()).getBlock();
         if (CachedChunk.a.contains(var9)) {
            BetterBlockPos var10 = var0.a.getPlayerContext().playerFeet();
            var6.addAll(var0.a.getCachedWorld().getLocationsOf(BlockUtils.blockToString(var9), Baritone.a().maxCachedWorldScanCount.value, var10.x, var10.z, 2));
         } else {
            var7.add(var9);
         }
      }

      List var11 = a(var0, var6, var1, var2, var4, var5);
      if (!var7.isEmpty() || Baritone.a().extendCacheOnThreshold.value && var11.size() < var2) {
         var11.addAll(BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(var0.a.getPlayerContext(), var1, var2, 10, 32));
      }

      var11.addAll(var3);
      return a(var0, var11, var1, var2, var4, var5);
   }

   private static List<class_2338> a(
      CalculationContext var0, List<class_2338> var1, BlockOptionalMetaLookup var2, int var3, List<class_2338> var4, List<class_2338> var5
   ) {
      var5.removeIf(
         var3x -> {
            Iterator var5x = var1.iterator();

            while (var5x.hasNext()) {
               class_2338 var4x;
               if ((var4x = (class_2338)var5x.next()).method_10262(var3x) <= 9.0
                  && var2.has(var0.a(var4x.method_10263(), var4x.method_10264(), var4x.method_10260()))
                  && a(var0, var4x)) {
                  return true;
               }
            }

            return false;
         }
      );
      List var6;
      return (var6 = var1.stream()
                  .distinct()
                  .filter(
                     var3x -> !var0.a.a(var3x.method_10263(), var3x.method_10260())
                        || var2.has(var0.a(var3x.method_10263(), var3x.method_10264(), var3x.method_10260()))
                        || var5.contains(var3x)
                  )
                  .filter(var1x -> a(var0, var1x))
                  .filter(
                     var1x -> {
                        if (!Baritone.a().allowOnlyExposedOres.value) {
                           return true;
                        }

                        int var2x;
                        for (int var3x = -(var2x = Baritone.a().allowOnlyExposedOresDistance.value); var3x <= var2x; var3x++) {
                           for (int var4x = -var2x; var4x <= var2x; var4x++) {
                              for (int var5x = -var2x; var5x <= var2x; var5x++) {
                                 if (Math.abs(var3x) + Math.abs(var4x) + Math.abs(var5x) <= var2x
                                    && MovementHelper.a(var0.a(var1x.method_10263() + var3x, var1x.method_10264() + var4x, var1x.method_10260() + var5x))) {
                                    return true;
                                 }
                              }
                           }
                        }

                        return false;
                     }
                  )
                  .filter(var1x -> var1x.method_10264() >= Baritone.a().minYLevelWhileMining.value + var0.a.method_8597().comp_651())
                  .filter(var0x -> var0x.method_10264() <= Baritone.a().maxYLevelWhileMining.value)
                  .filter(var1x -> !var4.contains(var1x))
                  .sorted(Comparator.comparingDouble(var0.a.getPlayerContext().player().method_24515()::method_10262))
                  .collect(Collectors.toList()))
               .size()
            > var3
         ? var6.subList(0, var3)
         : var6;
   }

   private static boolean a(CalculationContext var0, class_2338 var1) {
      class_2680 var2 = var0.a.a(var1);
      if (MovementHelper.a(var0, var1.method_10263(), var1.method_10264(), var1.method_10260(), var2, true) >= 1000000.0) {
         return false;
      } else {
         return MovementHelper.a(var0.a, var1.method_10263(), var1.method_10264(), var1.method_10260(), var2)
            ? false
            : var0.a.a(var1.method_10084()).method_26204() != class_2246.field_9987 || var0.a.a(var1.method_10074()).method_26204() != class_2246.field_9987;
      }
   }

   @Override
   public final void mineByName(int var1, String... var2) {
      this.mine(var1, new BlockOptionalMetaLookup(var2));
   }

   @Override
   public final void mine(int var1, BlockOptionalMetaLookup var2) {
      this.a = var2;
      if (this.a() == null) {
         this.a = null;
      }

      this.a = var1;
      this.a = new ArrayList<>();
      this.b = new ArrayList<>();
      this.a = null;
      this.a = null;
      this.a = new HashMap<>();
      if (var2 != null) {
         this.a(new ArrayList<>(), new CalculationContext(super.a));
      }
   }

   private BlockOptionalMetaLookup a() {
      if (this.a == null) {
         return null;
      }

      if (!Baritone.a().allowBreak.value) {
         BlockOptionalMetaLookup var1;
         if ((var1 = new BlockOptionalMetaLookup(
               this.a.blocks().stream().filter(var0 -> Baritone.a().allowBreakAnyway.value.contains(var0.getBlock())).toArray(BlockOptionalMeta[]::new)
            ))
            .blocks()
            .isEmpty()) {
            this.logDirect("Unable to mine when allowBreak is false and target block is not in allowBreakAnyway!");
            return null;
         } else {
            return var1;
         }
      } else {
         return this.a;
      }
   }

   static class GoalThreeBlocks extends GoalTwoBlocks {
      public GoalThreeBlocks(class_2338 var1) {
         super(var1);
      }

      @Override
      public boolean isInGoal(int var1, int var2, int var3) {
         return var1 == super.x && (var2 == super.y || var2 == super.y - 1 || var2 == super.y - 2) && var3 == super.z;
      }

      @Override
      public double heuristic(int var1, int var2, int var3) {
         var1 -= super.x;
         var2 -= super.y;
         var3 -= super.z;
         return GoalBlock.calculate(var1, var2 < -1 ? var2 + 2 : (var2 == -1 ? 0 : var2), var3);
      }

      @Override
      public boolean equals(Object var1) {
         return super.equals(var1);
      }

      @Override
      public int hashCode() {
         return super.hashCode() * 393857768;
      }

      @Override
      public String toString() {
         return String.format(
            "GoalThreeBlocks{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z)
         );
      }
   }
}
