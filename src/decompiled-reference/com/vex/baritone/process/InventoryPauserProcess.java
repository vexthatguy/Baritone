package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.utils.BaritoneProcessHelper;

public class InventoryPauserProcess extends BaritoneProcessHelper {
   private boolean a;
   private boolean b;
   private int a;

   public InventoryPauserProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public boolean isActive() {
      return super.a.player() != null && super.a.world() != null;
   }

   public final boolean a() {
      this.a = true;
      return this.b && this.a > 1;
   }

   @Override
   public PathingCommand onTick(boolean var1, boolean var2) {
      this.b = var2;
      if (this.a) {
         this.a = false;
         if (super.a.player().method_18798().method_18805(1.0, 0.0, 1.0).method_1033() < 1.0E-5) {
            this.a++;
         }

         return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
      } else {
         this.a = 0;
         return new PathingCommand(null, PathingCommandType.DEFER);
      }
   }

   @Override
   public void onLostControl() {
   }

   @Override
   public String displayName0() {
      return "inventory pauser";
   }

   @Override
   public double priority() {
      return 5.1;
   }

   @Override
   public boolean isTemporary() {
      return true;
   }
}
