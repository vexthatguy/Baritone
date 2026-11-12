package com.vex.baritone.api.event.events;

import com.vex.baritone.api.utils.Rotation;

public final class RotationMoveEvent {
   private final RotationMoveEvent.Type type;
   private final Rotation original;
   private float yaw;
   private float pitch;

   public RotationMoveEvent(RotationMoveEvent.Type var1, float var2, float var3) {
      this.type = var1;
      this.original = new Rotation(var2, var3);
      this.yaw = var2;
      this.pitch = var3;
   }

   public final Rotation getOriginal() {
      return this.original;
   }

   public final void setYaw(float var1) {
      this.yaw = var1;
   }

   public final float getYaw() {
      return this.yaw;
   }

   public final void setPitch(float var1) {
      this.pitch = var1;
   }

   public final float getPitch() {
      return this.pitch;
   }

   public final RotationMoveEvent.Type getType() {
      return this.type;
   }

   public enum Type {
      MOTION_UPDATE,
      JUMP;
   }
}
