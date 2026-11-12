package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IChunkArray;
import com.vex.baritone.utils.accessor.IClientChunkProvider;
import java.lang.reflect.Field;
import java.util.Arrays;
import net.minecraft.class_631;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_631.class)
public class MixinClientChunkProvider implements IClientChunkProvider {
   @Final
   @Shadow
   class_638 field_16525;

   @Override
   public class_631 createThreadSafeCopy() {
      IChunkArray var1 = this.extractReferenceArray();
      class_631 var2;
      IChunkArray var3;
      (var3 = ((IClientChunkProvider)(var2 = new class_631(this.field_16525, var1.viewDistance() - 3))).extractReferenceArray()).copyFrom(var1);
      if (var3.viewDistance() != var1.viewDistance()) {
         throw new IllegalStateException(var3.viewDistance() + " " + var1.viewDistance());
      } else {
         return var2;
      }
   }

   @Override
   public IChunkArray extractReferenceArray() {
      Field[] var1;
      int var2 = (var1 = class_631.class.getDeclaredFields()).length;

      for (int var3 = 0; var3 < var2; var3++) {
         Field var4 = var1[var3];
         if (IChunkArray.class.isAssignableFrom(var4.getType())) {
            try {
               return (IChunkArray)var4.get(this);
            } catch (IllegalAccessException var5) {
               throw new RuntimeException(var5);
            }
         }
      }

      throw new RuntimeException(Arrays.toString(class_631.class.getDeclaredFields()));
   }
}
