package com.vex.baritone.behavior;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.Waypoint;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.event.events.BlockInteractEvent;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.utils.BlockStateInterface;
import net.minecraft.class_124;
import net.minecraft.class_2244;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2742;
import net.minecraft.class_5250;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2568.class_10613;

public class WaypointBehavior extends Behavior {
   public WaypointBehavior(Baritone var1) {
      super(var1);
   }

   @Override
   public void onBlockInteract(BlockInteractEvent var1) {
      if (Baritone.a().doBedWaypoints.value) {
         if (var1.getType() == BlockInteractEvent.Type.USE) {
            BetterBlockPos var3 = BetterBlockPos.from(var1.getPos());
            class_2680 var2;
            if ((var2 = BlockStateInterface.a(super.a, (class_2338)var3)).method_26204() instanceof class_2244) {
               if (var2.method_11654(class_2244.field_9967) == class_2742.field_12557) {
                  var3 = var3.relative((class_2350)var2.method_11654(class_2244.field_11177));
               }

               if (!super.a.a.a().getWaypoints().getByTag(IWaypoint.Tag.BED).stream().map(IWaypoint::getLocation).filter(var3::equals).findFirst().isPresent()) {
                  super.a.a.a().getWaypoints().addWaypoint(new Waypoint("bed", IWaypoint.Tag.BED, var3));
               }
            }
         }
      }
   }

   @Override
   public void onPlayerDeath() {
      if (Baritone.a().doDeathWaypoints.value) {
         Waypoint var1 = new Waypoint("death", IWaypoint.Tag.DEATH, super.a.playerFeet());
         super.a.a.a().getWaypoints().addWaypoint(var1);
         class_5250 var2;
         class_5250 var10000 = var2 = class_2561.method_43470("Death position saved.");
         var10000.method_10862(
            var10000.method_10866()
               .method_10977(class_124.field_1068)
               .method_10949(new class_10613(class_2561.method_43470("Click to goto death")))
               .method_10958(
                  new class_10609(
                     String.format("%s%s goto %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, "wp", var1.getTag().getName(), var1.getCreationTimestamp())
                  )
               )
         );
         Helper.HELPER.logDirect(var2);
      }
   }
}
