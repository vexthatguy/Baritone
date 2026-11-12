package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IChunkArray;
import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.class_1923;
import net.minecraft.class_2818;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.client.multiplayer.ClientChunkCache$Storage")
public abstract class MixinChunkArray implements IChunkArray {
   @Final
   @Shadow
   AtomicReferenceArray<class_2818> field_16251;
   @Final
   @Shadow
   int field_16253;
   @Final
   @Shadow
   private int field_16252;
   @Shadow
   int field_19204;
   @Shadow
   int field_19205;
   @Shadow
   int field_19143;

   @Shadow
   abstract boolean method_16034(int var1, int var2);

   @Shadow
   abstract int method_16027(int var1, int var2);

   @Shadow
   protected abstract void method_16031(int var1, class_2818 var2);

   @Override
   public int centerX() {
      return this.field_19204;
   }

   @Override
   public int centerZ() {
      return this.field_19205;
   }

   @Override
   public int viewDistance() {
      return this.field_16253;
   }

   @Override
   public AtomicReferenceArray<class_2818> getChunks() {
      return this.field_16251;
   }

   @Override
   public void copyFrom(IChunkArray var1) {
      this.field_19204 = var1.centerX();
      this.field_19205 = var1.centerZ();
      AtomicReferenceArray var5 = var1.getChunks();

      for (int var2 = 0; var2 < var5.length(); var2++) {
         class_2818 var3;
         if ((var3 = (class_2818)var5.get(var2)) != null) {
            class_1923 var4 = var3.method_12004();
            if (this.method_16034(var4.field_9181, var4.field_9180)) {
               int var6 = this.method_16027(var4.field_9181, var4.field_9180);
               if (this.field_16251.get(var6) != null) {
                  throw new IllegalStateException("Doing this would mutate the client's REAL loaded chunks?!");
               }

               this.method_16031(var6, var3);
            }
         }
      }
   }
}
