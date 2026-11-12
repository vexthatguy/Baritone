package com.vex.baritone.behavior;

import com.vex.baritone.api.utils.Rotation;

class LookBehavior$AbstractAimProcessor$1 extends LookBehavior.AbstractAimProcessor {
   private Rotation a;

   LookBehavior$AbstractAimProcessor$1(LookBehavior.AbstractAimProcessor var1, LookBehavior.AbstractAimProcessor var2) {
      this.a = var1;
      super(var2);
      this.a = this.a.a();
   }

   @Override
   public Rotation nextRotation(Rotation var1) {
      return this.a = super.nextRotation(var1);
   }

   @Override
   protected final Rotation a() {
      return this.a;
   }
}
