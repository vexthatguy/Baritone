package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;

class ExecutionControlCommands$1 implements IBaritoneProcess {
   ExecutionControlCommands$1(boolean[] var1, Baritone var2) {
      this.a = var1;
      this.a = var2;
      super();
   }

   @Override
   public boolean isActive() {
      return this.a[0];
   }

   @Override
   public PathingCommand onTick(boolean var1, boolean var2) {
      this.a.getInputOverrideHandler().clearAllKeys();
      return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
   }

   @Override
   public boolean isTemporary() {
      return true;
   }

   @Override
   public void onLostControl() {
   }

   @Override
   public double priority() {
      return 0.0;
   }

   @Override
   public String displayName0() {
      return "Pause/Resume Commands";
   }
}
