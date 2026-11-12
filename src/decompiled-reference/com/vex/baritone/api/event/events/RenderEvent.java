package com.vex.baritone.api.event.events;

import net.minecraft.class_4587;
import org.joml.Matrix4f;

public final class RenderEvent {
   private final float partialTicks;
   private final Matrix4f projectionMatrix;
   private final class_4587 modelViewStack;

   public RenderEvent(float var1, class_4587 var2, Matrix4f var3) {
      this.partialTicks = var1;
      this.modelViewStack = var2;
      this.projectionMatrix = var3;
   }

   public final float getPartialTicks() {
      return this.partialTicks;
   }

   public final class_4587 getModelViewStack() {
      return this.modelViewStack;
   }

   public final Matrix4f getProjectionMatrix() {
      return this.projectionMatrix;
   }
}
