package com.vex.baritone.api.pathing.movement;

import com.vex.baritone.api.utils.BetterBlockPos;
import net.minecraft.class_2338;

public interface IMovement {
   double getCost();

   MovementStatus update();

   void reset();

   void resetBlockCache();

   boolean safeToCancel();

   boolean calculatedWhileLoaded();

   BetterBlockPos getSrc();

   BetterBlockPos getDest();

   class_2338 getDirection();
}
