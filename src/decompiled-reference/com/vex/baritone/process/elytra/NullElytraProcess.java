package com.vex.baritone.process.elytra;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.process.IElytraProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.utils.BaritoneProcessHelper;
import net.minecraft.class_2338;

public final class NullElytraProcess extends BaritoneProcessHelper implements IElytraProcess {
   public NullElytraProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final void repackChunks() {
      throw new UnsupportedOperationException("Called repackChunks() on NullElytraBehavior");
   }

   @Override
   public final class_2338 currentDestination() {
      return null;
   }

   @Override
   public final void pathTo(class_2338 var1) {
      throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
   }

   @Override
   public final void pathTo(Goal var1) {
      throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
   }

   @Override
   public final void resetState() {
   }

   @Override
   public final boolean isActive() {
      return false;
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      throw new UnsupportedOperationException("Called onTick on NullElytraProcess");
   }

   @Override
   public final void onLostControl() {
   }

   @Override
   public final String displayName0() {
      return "NullElytraProcess";
   }

   @Override
   public final boolean isLoaded() {
      return false;
   }

   @Override
   public final boolean isSafeToCancel() {
      return true;
   }
}
