package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalGetToBlock;
import com.vex.baritone.api.pathing.goals.GoalTwoBlocks;
import com.vex.baritone.api.process.IGetToBlockProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.BlockOptionalMeta;
import com.vex.baritone.api.utils.BlockOptionalMetaLookup;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_1723;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public final class GetToBlockProcess extends BaritoneProcessHelper implements IGetToBlockProcess {
   private BlockOptionalMeta a;
   private List<class_2338> a;
   private List<class_2338> b;
   private BetterBlockPos a;
   private int a = 0;
   private int b = 0;

   public GetToBlockProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final void getToBlock(BlockOptionalMeta var1) {
      this.onLostControl();
      this.a = var1;
      this.a = super.a.playerFeet();
      this.b = new ArrayList<>();
      this.b = 0;
      this.a(new ArrayList<>(), new GetToBlockProcess.GetToBlockCalculationContext(false));
   }

   @Override
   public final boolean isActive() {
      return this.a != null;
   }

   @Override
   public final synchronized PathingCommand onTick(boolean var1, boolean var2) {
      if (this.a == null) {
         this.a(new ArrayList<>(), new GetToBlockProcess.GetToBlockCalculationContext(false));
      }

      if (this.a.isEmpty()) {
         if (Baritone.a().exploreForBlocks.value && !var1) {
            return new PathingCommand(new GetToBlockProcess$1(this.a), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
         }

         this.logDirect("No known locations of " + this.a + ", canceling GetToBlock");
         if (var2) {
            this.onLostControl();
         }

         return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
      } else {
         GoalComposite var3 = new GoalComposite(this.a.stream().map(this::a).toArray(Goal[]::new));
         if (var1) {
            if (Baritone.a().blacklistClosestOnFailure.value) {
               this.logDirect("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instances...");
               this.blacklistClosest();
               return this.onTick(false, var2);
            }

            this.logDirect("Unable to find any path to " + this.a + ", canceling GetToBlock");
            if (var2) {
               this.onLostControl();
            }

            return new PathingCommand(var3, PathingCommandType.CANCEL_AND_SET_GOAL);
         } else {
            if ((var1 = Baritone.a().mineGoalUpdateInterval.value) != 0 && this.a++ % var1 == 0) {
               ArrayList var6 = new ArrayList<>(this.a);
               GetToBlockProcess.GetToBlockCalculationContext var4 = new GetToBlockProcess.GetToBlockCalculationContext(true);
               Baritone.a().execute(() -> this.a(var6, var4));
            }

            if (var3.isInGoal(super.a.playerFeet()) && var3.isInGoal(super.a.a.a()) && var2) {
               if (!a(this.a.getBlock())) {
                  this.onLostControl();
                  return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }

               GetToBlockProcess var7 = this;
               Iterator var8 = this.a.iterator();

               boolean var12;
               while (true) {
                  if (!var8.hasNext()) {
                     var7.logDirect("Arrived but failed to right click open");
                     var12 = true;
                     break;
                  }

                  class_2338 var9 = (class_2338)var8.next();
                  Optional var10;
                  if ((var10 = RotationUtils.reachable(var7.a, var9, var7.a.playerController().getBlockReachDistance())).isPresent()) {
                     var7.a.a.updateTarget((Rotation)var10.get(), true);
                     if (var7.a.contains(var7.a.getSelectedBlock().orElse(null))) {
                        var7.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                        System.out.println(var7.a.player().field_7512);
                        if (!(var7.a.player().field_7512 instanceof class_1723)) {
                           var12 = true;
                           break;
                        }
                     }

                     if (var7.b++ > 20) {
                        var7.logDirect("Right click timed out");
                        var12 = true;
                     } else {
                        var12 = false;
                     }
                     break;
                  }
               }

               if (var12) {
                  this.onLostControl();
                  return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }
            }

            return new PathingCommand(var3, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
         }
      }
   }

   @Override
   public final synchronized boolean blacklistClosest() {
      ArrayList var1 = new ArrayList();
      this.a.stream().min(Comparator.comparingDouble(super.a.playerFeet()::method_10262)).ifPresent(var1::add);

      label33:
      while (true) {
         for (class_2338 var3 : this.a) {
            for (class_2338 var5 : var1) {
               class_2338 var6 = var5;
               var5 = var3;
               int var7 = Math.abs(var3.method_10263() - var6.method_10263());
               int var8 = Math.abs(var5.method_10264() - var6.method_10264());
               int var10 = Math.abs(var5.method_10260() - var6.method_10260());
               if (var7 + var8 + var10 == 1) {
                  var1.add(var3);
                  this.a.remove(var3);
                  continue label33;
               }
            }
         }

         var1.size();
         this.logDebug("Blacklisting unreachable locations " + var1);
         this.b.addAll(var1);
         if (!var1.isEmpty()) {
            return true;
         }

         return false;
      }
   }

   @Override
   public final synchronized void onLostControl() {
      this.a = null;
      this.a = null;
      this.a = null;
      this.b = null;
      super.a.a.clearAllKeys();
   }

   @Override
   public final String displayName0() {
      return this.a.isEmpty()
         ? "Exploring randomly to find " + this.a + ", no known locations"
         : "Get To " + this.a + ", " + this.a.size() + " known locations";
   }

   private synchronized void a(List<class_2338> var1, CalculationContext var2) {
      List var3;
      (var3 = MineProcess.a(var2, new BlockOptionalMetaLookup(this.a), 64, var1, this.b, Collections.emptyList())).removeIf(this.b::contains);
      this.a = var3;
   }

   private Goal a(class_2338 var1) {
      class_2248 var2 = this.a.getBlock();
      if (Baritone.a().enterPortal.value && var2 == class_2246.field_10316) {
         return new GoalTwoBlocks(var1);
      } else {
         return a(var2 = this.a.getBlock())
               && (var2 == class_2246.field_10443 || var2 == class_2246.field_10034 || var2 == class_2246.field_10380)
               && MovementHelper.h(super.a.a.a(var1.method_10084()))
            ? new GoalBlock(var1.method_10084())
            : new GoalGetToBlock(var1);
      }
   }

   private static boolean a(class_2248 var0) {
      return !Baritone.a().rightClickContainerOnArrival.value
         ? false
         : var0 == class_2246.field_9980
            || var0 == class_2246.field_10181
            || var0 == class_2246.field_16333
            || var0 == class_2246.field_10443
            || var0 == class_2246.field_10034
            || var0 == class_2246.field_10380;
   }

   public class GetToBlockCalculationContext extends CalculationContext {
      public GetToBlockCalculationContext(boolean var2) {
         super(GetToBlockProcess.a(GetToBlockProcess.this), var2);
      }

      @Override
      public final double b(int var1, int var2, int var3, class_2680 var4) {
         return 1.0;
      }
   }
}
