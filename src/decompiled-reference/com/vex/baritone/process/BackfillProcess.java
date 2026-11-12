package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.pathing.movement.MovementState;
import com.vex.baritone.pathing.path.PathExecutor;
import com.vex.baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2812;

public final class BackfillProcess extends BaritoneProcessHelper {
   private HashMap<class_2338, class_2680> a = new HashMap<>();

   public BackfillProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final boolean isActive() {
      if (super.a.player() != null && super.a.world() != null) {
         if (!Baritone.a().backfill.value) {
            return false;
         }

         if (Baritone.a().allowParkour.value) {
            this.logDirect("Backfill cannot be used with allowParkour true");
            Baritone.a().backfill.value = Boolean.FALSE;
            return false;
         }

         for (class_2338 var2 : new ArrayList<>(this.a.keySet())) {
            if (super.a.world().method_22350(var2) instanceof class_2812 || super.a.world().method_8320(var2).method_26204() != class_2246.field_10124) {
               this.a.remove(var2);
            }
         }

         BackfillProcess var3 = this;
         if (super.a.getSelectedBlock().isPresent() && var3.a.a.isPathing()) {
            var3.a.put(var3.a.getSelectedBlock().get(), var3.a.world().method_8320(var3.a.getSelectedBlock().get()));
         }

         super.a.a.clearAllKeys();
         return !this.a().isEmpty();
      } else {
         return false;
      }
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      if (!var2) {
         return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
      }

      super.a.a.clearAllKeys();

      for (class_2338 var5 : this.a()) {
         MovementState var3 = new MovementState();
         switch (MovementHelper.a(var3, super.a, var5, false, false)) {
            case c:
               break;
            case a:
               super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            case b:
               super.a.a.updateTarget(Optional.ofNullable(var3.a.a).get(), true);
               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            default:
               throw new IllegalStateException();
         }
      }

      return new PathingCommand(null, PathingCommandType.DEFER);
   }

   private List<class_2338> a() {
      return this.a
         .keySet()
         .stream()
         .filter(var1 -> super.a.world().method_8320(var1).method_26204() == class_2246.field_10124)
         .filter(var1 -> super.a.a.a(var1, class_2246.field_10566.method_9564()))
         .filter(
            var1 -> {
               class_2338 var2 = var1;
               PathExecutor var3 = super.a.a.a;
               return super.a.a.a != null && !var3.b() && !var3.a
                  ? !Arrays.asList(((Movement)var3.getPath().movements().get(var3.getPosition())).a).contains(var2)
                  : true;
            }
         )
         .sorted(Comparator.comparingDouble(super.a.playerFeet()::method_10262).reversed())
         .collect(Collectors.toList());
   }

   @Override
   public final void onLostControl() {
      if (this.a != null && !this.a.isEmpty()) {
         this.a.clear();
      }
   }

   @Override
   public final String displayName0() {
      return "Backfill";
   }

   @Override
   public final boolean isTemporary() {
      return true;
   }

   @Override
   public final double priority() {
      return 5.0;
   }
}
