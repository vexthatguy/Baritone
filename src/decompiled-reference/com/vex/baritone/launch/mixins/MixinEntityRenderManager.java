package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IEntityRenderManager;
import net.minecraft.class_898;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(class_898.class)
public class MixinEntityRenderManager implements IEntityRenderManager {
   @Override
   public double renderPosX() {
      return ((class_898)this).field_4686.method_71156().field_1352;
   }

   @Override
   public double renderPosY() {
      return ((class_898)this).field_4686.method_71156().field_1351;
   }

   @Override
   public double renderPosZ() {
      return ((class_898)this).field_4686.method_71156().field_1350;
   }
}
