package com.vex.baritone.api.schematic;

import java.util.List;
import net.minecraft.class_2680;
import net.minecraft.class_2350.class_2351;

public interface ISchematic {
   default boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return var1 >= 0 && var1 < this.widthX() && var2 >= 0 && var2 < this.heightY() && var3 >= 0 && var3 < this.lengthZ();
   }

   default int size(class_2351 var1) {
      switch (var1) {
         case field_11048:
            return this.widthX();
         case field_11052:
            return this.heightY();
         case field_11051:
            return this.lengthZ();
         default:
            throw new UnsupportedOperationException(var1 + "");
      }
   }

   class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5);

   default void reset() {
   }

   int widthX();

   int heightY();

   int lengthZ();
}
