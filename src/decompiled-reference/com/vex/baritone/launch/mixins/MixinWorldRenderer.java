package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.event.events.RenderEvent;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_761;
import net.minecraft.class_9779;
import net.minecraft.class_9922;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_761.class)
public class MixinWorldRenderer {
   @Inject(method = "renderLevel", at = @At("RETURN"))
   private void onStartHand(
      class_9922 var1,
      class_9779 var2,
      boolean var3,
      class_4184 var4,
      Matrix4f var5,
      Matrix4f var6,
      Matrix4f var7,
      GpuBufferSlice var8,
      Vector4f var9,
      boolean var10,
      CallbackInfo var11
   ) {
      for (IBaritone var13 : BaritoneAPI.getProvider().getAllBaritones()) {
         class_4587 var14;
         (var14 = new class_4587()).method_34425(var5);
         var13.getGameEventHandler().onRenderPass(new RenderEvent(var2.method_60637(false), var14, var6));
      }
   }
}
