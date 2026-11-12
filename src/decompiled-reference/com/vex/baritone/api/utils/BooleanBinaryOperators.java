package com.vex.baritone.api.utils;

public enum BooleanBinaryOperators implements BooleanBinaryOperator {
   OR((var0, var1) -> var0 || var1),
   AND((var0, var1) -> var0 && var1),
   XOR((var0, var1) -> var0 ^ var1);

   private final BooleanBinaryOperator op;

   BooleanBinaryOperators(BooleanBinaryOperator var3) {
      this.op = var3;
   }

   @Override
   public final boolean applyAsBoolean(boolean var1, boolean var2) {
      return this.op.applyAsBoolean(var1, var2);
   }
}
