package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IPalettedContainer;
import net.minecraft.class_2837;
import net.minecraft.class_6490;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net/minecraft/world/level/chunk/PalettedContainer$Data")
public abstract class MixinPalettedContainer$Data<T> implements IPalettedContainer.IData<T> {
   @Accessor
   @Override
   public abstract class_2837<T> getPalette();

   @Accessor
   @Override
   public abstract class_6490 getStorage();
}
