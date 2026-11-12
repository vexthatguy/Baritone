package com.vex.baritone.api.utils;

import java.util.Objects;

public final class Pair<A, B> {
   private final A a;
   private final B b;

   public Pair(A var1, B var2) {
      this.a = (A)var1;
      this.b = (B)var2;
   }

   public final A first() {
      return this.a;
   }

   public final B second() {
      return this.b;
   }

   @Override
   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && var1.getClass() == Pair.class) {
         var1 = var1;
         return Objects.equals(this.a, var1.a) && Objects.equals(this.b, var1.b);
      } else {
         return false;
      }
   }

   @Override
   public final int hashCode() {
      return 31 * Objects.hashCode(this.a) + Objects.hashCode(this.b);
   }
}
