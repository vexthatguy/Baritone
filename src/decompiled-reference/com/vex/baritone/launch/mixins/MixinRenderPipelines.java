package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IRenderPipelines;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import net.minecraft.class_10799;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_10799.class)
public class MixinRenderPipelines implements IRenderPipelines {
   @Final
   @Shadow
   private static Snippet field_56859;

   @Override
   public Snippet getLinesSnippet() {
      return field_56859;
   }
}
