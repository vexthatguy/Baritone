package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.utils.accessor.ILootTable;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.class_1799;
import net.minecraft.class_47;
import net.minecraft.class_52;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(class_52.class)
public abstract class MixinLootTable implements ILootTable {
   @Invoker
   @Override
   public abstract ObjectArrayList<class_1799> invokeGetRandomItems(class_47 var1);
}
