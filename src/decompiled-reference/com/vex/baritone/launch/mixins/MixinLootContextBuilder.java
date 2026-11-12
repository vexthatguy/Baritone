package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.utils.BlockOptionalMeta;
import net.minecraft.class_3218;
import net.minecraft.class_47.class_48;
import net.minecraft.class_9383.class_9385;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(class_48.class)
public abstract class MixinLootContextBuilder {
   @Shadow
   public abstract class_3218 method_313();

   @Redirect(
      method = "create",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/MinecraftServer;reloadableRegistries()Lnet/minecraft/server/ReloadableServerRegistries$Holder;"
      )
   )
   private class_9385 create(MinecraftServer var1) {
      if (var1 != null) {
         return var1.method_58576();
      }

      class_3218 var2;
      return (var2 = this.method_313()) instanceof BlockOptionalMeta.ServerLevelStub ? ((BlockOptionalMeta.ServerLevelStub)var2).holder() : null;
   }
}
