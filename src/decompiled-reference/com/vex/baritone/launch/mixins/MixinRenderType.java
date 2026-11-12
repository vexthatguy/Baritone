package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IRenderType;
import net.minecraft.class_12247;
import net.minecraft.class_1921;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_1921.class)
public abstract class MixinRenderType implements IRenderType {
   @Shadow
   static class_1921 method_75940(String var0, class_12247 var1) {
      return null;
   }

   @Override
   public class_1921 createRenderType(String var1, class_12247 var2) {
      return method_75940(var1, var2);
   }
}
