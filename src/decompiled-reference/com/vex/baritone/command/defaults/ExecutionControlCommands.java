package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;

public class ExecutionControlCommands {
   ExecutionControlCommands$2 a;
   ExecutionControlCommands$3 a;
   ExecutionControlCommands$4 a;
   ExecutionControlCommands$5 a;

   public ExecutionControlCommands(Baritone var1) {
      boolean[] var2 = new boolean[]{false};
      var1.getPathingControlManager().registerProcess(new ExecutionControlCommands$1(var2, var1));
      this.a = new ExecutionControlCommands$2(var1, new String[]{"pause", "p", "paws"}, var2);
      this.a = new ExecutionControlCommands$3(var1, new String[]{"resume", "r", "unpause", "unpaws"}, var2);
      this.a = new ExecutionControlCommands$4(var1, new String[]{"paused"}, var2);
      this.a = new ExecutionControlCommands$5(var1, new String[]{"cancel", "c", "stop"}, var2);
   }
}
