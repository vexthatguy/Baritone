package com.vex.baritone.api.selection;

import com.vex.baritone.api.utils.BetterBlockPos;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;

public interface ISelection {
   BetterBlockPos pos1();

   BetterBlockPos pos2();

   BetterBlockPos min();

   BetterBlockPos max();

   class_2382 size();

   class_238 aabb();

   ISelection expand(class_2350 var1, int var2);

   ISelection contract(class_2350 var1, int var2);

   ISelection shift(class_2350 var1, int var2);
}
