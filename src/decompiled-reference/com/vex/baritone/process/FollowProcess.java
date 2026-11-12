package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalNear;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.process.IFollowProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.utils.BaritoneProcessHelper;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_2338;

public final class FollowProcess extends BaritoneProcessHelper implements IFollowProcess {
   private Predicate<class_1297> a;
   private List<class_1297> a;
   private boolean a;

   public FollowProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      this.a();
      GoalComposite var3 = new GoalComposite(this.a.stream().map(this::a).toArray(Goal[]::new));
      return new PathingCommand(var3, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
   }

   private Goal a(class_1297 var1) {
      class_2338 var3;
      if (Baritone.a().followOffsetDistance.value != 0.0 && !this.a) {
         GoalXZ var2 = GoalXZ.fromDirection(var1.method_73189(), Baritone.a().followOffsetDirection.value, Baritone.a().followOffsetDistance.value);
         var3 = new BetterBlockPos(var2.getX(), var1.method_73189().field_1351, var2.getZ());
      } else {
         var3 = var1.method_24515();
      }

      return this.a ? new GoalBlock(var3) : new GoalNear(var3, Baritone.a().followRadius.value);
   }

   private boolean a(class_1297 var1) {
      if (var1 == null) {
         return false;
      }

      if (!var1.method_5805()) {
         return false;
      }

      if (var1.equals(super.a.player())) {
         return false;
      }

      int var2;
      return (var2 = Baritone.a().followTargetMaxDistance.value) != 0 && var1.method_5858(super.a.player()) > var2 * var2
         ? false
         : super.a.entitiesStream().anyMatch(var1::equals);
   }

   private void a() {
      this.a = super.a.entitiesStream().filter(this::a).filter(this.a).distinct().collect(Collectors.toList());
   }

   @Override
   public final boolean isActive() {
      if (this.a == null) {
         return false;
      }

      this.a();
      return !this.a.isEmpty();
   }

   @Override
   public final void onLostControl() {
      this.a = null;
      this.a = null;
   }

   @Override
   public final String displayName0() {
      return "Following " + this.a;
   }

   @Override
   public final void follow(Predicate<class_1297> var1) {
      this.a = var1;
      this.a = false;
   }

   @Override
   public final void pickup(Predicate<class_1799> var1) {
      this.a = var1x -> var1x instanceof class_1542 && var1.test(((class_1542)var1x).method_6983());
      this.a = true;
   }

   @Override
   public final List<class_1297> following() {
      return this.a;
   }

   @Override
   public final Predicate<class_1297> currentFilter() {
      return this.a;
   }
}
