package com.vex.baritone.utils.accessor;

import net.minecraft.class_2837;
import net.minecraft.class_6490;

public interface IPalettedContainer<T> {
   class_2837<T> getPalette();

   class_6490 getStorage();

   interface IData<T> {
      class_2837<T> getPalette();

      class_6490 getStorage();
   }
}
