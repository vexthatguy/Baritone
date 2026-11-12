package com.vex.baritone.launch.mixins;

import com.vex.baritone.utils.accessor.IFireworkRocketEntity;
import java.util.OptionalInt;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1671;
import net.minecraft.class_1937;
import net.minecraft.class_2940;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_1671.class)
public abstract class MixinFireworkRocketEntity extends class_1297 implements IFireworkRocketEntity {
   @Shadow
   @Final
   private static class_2940<OptionalInt> field_7611;
   @Shadow
   private class_1309 field_7616;

   @Shadow
   public abstract boolean method_7476();

   private MixinFireworkRocketEntity(class_1937 var1) {
      super(class_1299.field_6133, var1);
   }

   @Override
   public class_1309 getBoostedEntity() {
      class_1297 var1;
      if (this.method_7476()
         && this.field_7616 == null
         && (var1 = this.method_73183().method_8469(((OptionalInt)this.field_6011.method_12789(field_7611)).getAsInt())) instanceof class_1309) {
         this.field_7616 = (class_1309)var1;
      }

      return this.field_7616;
   }
}
