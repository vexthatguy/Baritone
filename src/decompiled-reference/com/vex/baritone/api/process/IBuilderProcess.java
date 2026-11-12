package com.vex.baritone.api.process;

import com.vex.baritone.api.schematic.ISchematic;
import java.io.File;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_2680;
import net.minecraft.class_310;

public interface IBuilderProcess extends IBaritoneProcess {
   void build(String var1, ISchematic var2, class_2382 var3);

   boolean build(String var1, File var2, class_2382 var3);

   @Deprecated
   default boolean build(String var1, class_2338 var2) {
      File var3 = new File(new File(class_310.method_1551().field_1697, "schematics"), var1);
      return this.build(var1, var3, var2);
   }

   void buildOpenSchematic();

   void buildOpenLitematic(int var1);

   void pause();

   boolean isPaused();

   void resume();

   void clearArea(class_2338 var1, class_2338 var2);

   List<class_2680> getApproxPlaceable();

   Optional<Integer> getMinLayer();

   Optional<Integer> getMaxLayer();
}
