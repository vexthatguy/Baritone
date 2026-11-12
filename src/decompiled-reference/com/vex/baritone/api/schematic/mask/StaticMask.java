package com.vex.baritone.api.schematic.mask;

import com.vex.baritone.api.schematic.mask.operator.BinaryOperatorMask;
import com.vex.baritone.api.schematic.mask.operator.NotMask;
import com.vex.baritone.api.utils.BooleanBinaryOperators;
import net.minecraft.class_2680;

public interface StaticMask extends Mask {
   boolean partOfMask(int var1, int var2, int var3);

   @Override
   default boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return this.partOfMask(var1, var2, var3);
   }

   default StaticMask not() {
      return new NotMask.Static(this);
   }

   default StaticMask union(StaticMask var1) {
      return new BinaryOperatorMask.Static(this, var1, BooleanBinaryOperators.OR);
   }

   default StaticMask intersection(StaticMask var1) {
      return new BinaryOperatorMask.Static(this, var1, BooleanBinaryOperators.AND);
   }

   default StaticMask xor(StaticMask var1) {
      return new BinaryOperatorMask.Static(this, var1, BooleanBinaryOperators.XOR);
   }

   default StaticMask compute() {
      return new PreComputedMask(this);
   }
}
