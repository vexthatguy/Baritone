package com.vex.baritone.launch;

import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class FabricMixinPlugin implements IMixinConfigPlugin {
   private static final String mixinPackage = "com.vex.baritone.launch.mixins";
   private static boolean loaded;
   private static boolean isBaritonePresent;

   public void onLoad(String var1) {
      if (!loaded) {
         isBaritonePresent = FabricLoader.getInstance().isModLoaded("baritone");
         loaded = true;
      }
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String var1, String var2) {
      if (!var2.startsWith("com.vex.baritone.launch.mixins")) {
         throw new RuntimeException("Mixin " + var2 + " is not in the mixin package");
      } else {
         return !isBaritonePresent;
      }
   }

   public void acceptTargets(Set<String> var1, Set<String> var2) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String var1, ClassNode var2, String var3, IMixinInfo var4) {
   }

   public void postApply(String var1, ClassNode var2, String var3, IMixinInfo var4) {
   }
}
