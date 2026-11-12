package com.vex.baritone.api.utils;

import com.vex.baritone.api.pathing.calc.IPath;
import java.util.Objects;
import java.util.Optional;

public class PathCalculationResult {
   private final IPath path;
   private final PathCalculationResult.Type type;

   public PathCalculationResult(PathCalculationResult.Type var1) {
      this(var1, null);
   }

   public PathCalculationResult(PathCalculationResult.Type var1, IPath var2) {
      Objects.requireNonNull(var1);
      this.path = var2;
      this.type = var1;
   }

   public final Optional<IPath> getPath() {
      return Optional.ofNullable(this.path);
   }

   public final PathCalculationResult.Type getType() {
      return this.type;
   }

   public enum Type {
      SUCCESS_TO_GOAL,
      SUCCESS_SEGMENT,
      FAILURE,
      CANCELLATION,
      EXCEPTION;
   }
}
