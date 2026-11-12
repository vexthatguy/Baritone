package com.vex.baritone.api.process;

import com.vex.baritone.api.pathing.goals.Goal;
import net.minecraft.class_2338;

public interface IElytraProcess extends IBaritoneProcess {
   void repackChunks();

   class_2338 currentDestination();

   void pathTo(class_2338 var1);

   void pathTo(Goal var1);

   void resetState();

   boolean isLoaded();

   boolean isSafeToCancel();
}
