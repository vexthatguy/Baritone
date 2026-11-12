package com.vex.baritone.utils.player;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.IWorldData;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.IPlayerController;
import com.vex.baritone.api.utils.RayTraceUtils;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.behavior.LookBehavior;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_746;

public final class BaritonePlayerContext implements IPlayerContext {
   private final Baritone a;
   private final class_310 a;
   private final BaritonePlayerController a;

   public BaritonePlayerContext(Baritone var1, class_310 var2) {
      this.a = var1;
      this.a = var2;
      this.a = new BaritonePlayerController(var2);
   }

   @Override
   public final class_310 minecraft() {
      return this.a;
   }

   @Override
   public final class_746 player() {
      return this.a.field_1724;
   }

   @Override
   public final IPlayerController playerController() {
      return this.a;
   }

   @Override
   public final class_1937 world() {
      return this.a.field_1687;
   }

   @Override
   public final IWorldData worldData() {
      return this.a.a.a();
   }

   @Override
   public final BetterBlockPos viewerPos() {
      class_1297 var1;
      return (var1 = this.a.method_1560()) == null ? this.playerFeet() : BetterBlockPos.from(var1.method_24515());
   }

   @Override
   public final Rotation playerRotations() {
      LookBehavior var1 = this.a.a;
      return (Baritone.a().freeLook.value ? Optional.ofNullable(var1.a) : Optional.empty()).orElseGet(() -> IPlayerContext.super.playerRotations());
   }

   @Override
   public final class_239 objectMouseOver() {
      return RayTraceUtils.rayTraceTowards(this.player(), this.playerRotations(), this.playerController().getBlockReachDistance());
   }
}
