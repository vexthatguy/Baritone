package com.vex.baritone.api.process;

import net.minecraft.class_2338;

public interface IFarmProcess extends IBaritoneProcess {
   void farm(int var1, class_2338 var2);

   default void farm() {
      this.farm(0, null);
   }

   default void farm(int var1) {
      this.farm(var1, null);
   }
}
