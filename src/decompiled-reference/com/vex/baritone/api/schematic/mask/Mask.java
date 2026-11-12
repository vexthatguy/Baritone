package com.vex.baritone.api.schematic.mask;

import com.vex.baritone.api.schematic.mask.operator.BinaryOperatorMask;
import com.vex.baritone.api.schematic.mask.operator.NotMask;
import com.vex.baritone.api.utils.BooleanBinaryOperators;
import net.minecraft.class_2680;

public interface Mask {
   boolean partOfMask(int var1, int var2, int var3, class_2680 var4);

   int widthX();

   int heightY();

   int lengthZ();

   default Mask not() {
      return new NotMask(this);
   }

   default Mask union(Mask var1) {
      return new BinaryOperatorMask(this, var1, BooleanBinaryOperators.OR);
   }

   default Mask intersection(Mask var1) {
      return new BinaryOperatorMask(this, var1, BooleanBinaryOperators.AND);
   }

   default Mask xor(Mask var1) {
      return new BinaryOperatorMask(this, var1, BooleanBinaryOperators.XOR);
   }
}
