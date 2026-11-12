package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IPlayerControllerMP;
import net.minecraft.class_2338;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(class_636.class)
public abstract class MixinPlayerController implements IPlayerControllerMP {
   @Accessor("isDestroying")
   @Override
   public abstract void setIsHittingBlock(boolean var1);

   @Accessor("isDestroying")
   @Override
   public abstract boolean isHittingBlock();

   @Accessor("destroyBlockPos")
   public abstract class_2338 getCurrentBlock();

   @Invoker("ensureHasSentCarriedItem")
   @Override
   public abstract void callSyncCurrentPlayItem();

   @Accessor("destroyDelay")
   @Override
   public abstract void setDestroyDelay(int var1);
}
