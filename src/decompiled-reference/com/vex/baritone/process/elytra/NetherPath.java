package com.vex.baritone.process.elytra;

import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_243;

public final class NetherPath extends AbstractList<BetterBlockPos> {
   private static final NetherPath a = new NetherPath(Collections.emptyList());
   private final List<BetterBlockPos> a;

   NetherPath(List<BetterBlockPos> var1) {
      this.a = var1;
   }

   public final BetterBlockPos a(int var1) {
      return this.a.get(var1);
   }

   @Override
   public final int size() {
      return this.a.size();
   }

   public final BetterBlockPos a() {
      return this.isEmpty() ? null : this.a.get(this.a.size() - 1);
   }

   public final class_243 a(int var1) {
      BetterBlockPos var2 = this.a(var1);
      return new class_243(var2.x, var2.y, var2.z);
   }

   public static NetherPath a() {
      return a;
   }
}
