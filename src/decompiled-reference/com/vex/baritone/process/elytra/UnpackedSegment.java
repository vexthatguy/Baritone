package com.vex.baritone.process.elytra;

import com.vex.baritone.api.utils.BetterBlockPos;
import dev.babbaj.pathfinder.PathSegment;
import java.util.Arrays;
import java.util.stream.Stream;

public final class UnpackedSegment {
   final Stream<BetterBlockPos> a;
   final boolean a;

   public UnpackedSegment(Stream<BetterBlockPos> var1, boolean var2) {
      this.a = var1;
      this.a = var2;
   }

   public static UnpackedSegment a(PathSegment var0) {
      return new UnpackedSegment(Arrays.stream(var0.packed).mapToObj(BetterBlockPos::deserializeFromLong), var0.finished);
   }
}
