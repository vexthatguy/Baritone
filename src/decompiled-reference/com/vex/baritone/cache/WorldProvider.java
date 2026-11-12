package com.vex.baritone.cache;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.IWorldProvider;
import com.vex.baritone.api.utils.IPlayerContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_3545;
import net.minecraft.class_5218;
import net.minecraft.class_642;
import org.apache.commons.lang3.SystemUtils;

public class WorldProvider implements IWorldProvider {
   private static final Map<Path, WorldData> a = new HashMap<>();
   private final Baritone a;
   private final IPlayerContext a;
   private WorldData a;
   private class_1937 a;

   public WorldProvider(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }

   public final WorldData a() {
      this.b();
      return this.a;
   }

   public final void a(class_1937 var1) {
      Optional var10000;
      label29: {
         WorldProvider var2 = this;
         Path var4;
         Path var5;
         if (this.a.minecraft().method_1496()) {
            if ((var5 = var2.a.minecraft().method_1576().method_27050(class_5218.field_24188))
                  .relativize(var2.a.minecraft().field_1697.toPath())
                  .getNameCount()
               != 2) {
               var5 = var5.getParent();
            }

            var4 = var5 = var5.resolve("baritone");
         } else {
            class_642 var6;
            if ((var6 = var2.a.minecraft().method_1558()) == null) {
               System.out.println("World seems to be a replay. Not loading Baritone cache.");
               var2.a = null;
               var2.a = var2.a.world();
               var10000 = Optional.empty();
               break label29;
            }

            String var7 = var6.method_52811() ? "realms" : var6.field_3761;
            if (SystemUtils.IS_OS_WINDOWS) {
               var7 = var7.replace(":", "_");
            }

            var5 = var2.a.a.resolve(var7);
            var4 = var2.a.a;
         }

         var10000 = Optional.of(new class_3545(var5, var4));
      }

      var10000.ifPresent(var2x -> {
         Path var3x = (Path)var2x.method_15442();
         Path var8 = (Path)var2x.method_15441();

         try {
            Files.createDirectories(var8);
            Files.write(var8.resolve("readme.txt"), "https://github.com/cabaletta/baritone\n".getBytes(StandardCharsets.US_ASCII));
         } catch (IOException var7x) {
         }

         class_1937 var11 = var1;
         Path var9 = var3x;
         class_2960 var4x = var11.method_27983().method_29177();
         int var12 = var11.method_8597().comp_653();
         Path var10 = var9.resolve(var4x.method_12836()).resolve(var4x.method_12832() + "_" + var12);

         try {
            Files.createDirectories(var10);
         } catch (IOException var6x) {
         }

         System.out.println("Baritone world data dir: " + var10);
         synchronized (a) {
            this.a = a.computeIfAbsent(var10, var1xx -> new WorldData(var1xx, var1.method_8597(), var1.method_27983()));
         }

         this.a = this.a.world();
      });
   }

   public final void a() {
      WorldData var1 = this.a;
      this.a = null;
      this.a = null;
      if (var1 != null) {
         var1.a();
      }
   }

   private void b() {
      if (this.a != this.a.world()) {
         if (this.a != null) {
            System.out.println("mc.world unloaded unnoticed! Unloading Baritone cache now.");
            this.a();
         }

         if (this.a.world() != null) {
            System.out.println("mc.world loaded unnoticed! Loading Baritone cache now.");
            this.a(this.a.world());
            return;
         }
      } else if (this.a == null && this.a.world() != null && (this.a.minecraft().method_1496() || this.a.minecraft().method_1558() != null)) {
         System.out.println("Retrying to load Baritone cache");
         this.a(this.a.world());
      }
   }
}
