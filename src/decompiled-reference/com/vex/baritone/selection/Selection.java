package com.vex.baritone.selection;

import com.vex.baritone.api.selection.ISelection;
import com.vex.baritone.api.utils.BetterBlockPos;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;

public class Selection implements ISelection {
   private final BetterBlockPos a;
   private final BetterBlockPos b;
   private final BetterBlockPos c;
   private final BetterBlockPos d;
   private final class_2382 a;
   private final class_238 a;

   public Selection(BetterBlockPos var1, BetterBlockPos var2) {
      this.a = var1;
      this.b = var2;
      this.c = new BetterBlockPos(Math.min(var1.x, var2.x), Math.min(var1.y, var2.y), Math.min(var1.z, var2.z));
      this.d = new BetterBlockPos(Math.max(var1.x, var2.x), Math.max(var1.y, var2.y), Math.max(var1.z, var2.z));
      this.a = new class_2382(this.d.x - this.c.x + 1, this.d.y - this.c.y + 1, this.d.z - this.c.z + 1);
      this.a = new class_238(this.c.x, this.c.y, this.c.z, this.d.x + 1, this.d.y + 1, this.d.z + 1);
   }

   @Override
   public BetterBlockPos pos1() {
      return this.a;
   }

   @Override
   public BetterBlockPos pos2() {
      return this.b;
   }

   @Override
   public BetterBlockPos min() {
      return this.c;
   }

   @Override
   public BetterBlockPos max() {
      return this.d;
   }

   @Override
   public class_2382 size() {
      return this.a;
   }

   @Override
   public class_238 aabb() {
      return this.a;
   }

   @Override
   public int hashCode() {
      return this.a.hashCode() ^ this.b.hashCode();
   }

   @Override
   public String toString() {
      return String.format("Selection{pos1=%s,pos2=%s}", this.a, this.b);
   }

   private boolean a(class_2350 var1) {
      boolean var2 = var1.method_10171().method_10181() < 0;
      switch (var1.method_10166()) {
         case field_11048:
            return this.b.x > this.a.x ^ var2;
         case field_11052:
            return this.b.y > this.a.y ^ var2;
         case field_11051:
            return this.b.z > this.a.z ^ var2;
         default:
            throw new IllegalStateException("Bad Direction.Axis");
      }
   }

   @Override
   public ISelection expand(class_2350 var1, int var2) {
      return this.a(var1) ? new Selection(this.a, this.b.relative(var1, var2)) : new Selection(this.a.relative(var1, var2), this.b);
   }

   @Override
   public ISelection contract(class_2350 var1, int var2) {
      return this.a(var1) ? new Selection(this.a.relative(var1, var2), this.b) : new Selection(this.a, this.b.relative(var1, var2));
   }

   @Override
   public ISelection shift(class_2350 var1, int var2) {
      return new Selection(this.a.relative(var1, var2), this.b.relative(var1, var2));
   }
}
