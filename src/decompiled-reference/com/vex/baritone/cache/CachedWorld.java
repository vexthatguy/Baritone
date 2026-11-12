package com.vex.baritone.cache;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.cache.ICachedWorld;
import com.vex.baritone.api.cache.IWorldData;
import com.vex.baritone.api.utils.Helper;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2818;
import net.minecraft.class_2874;
import net.minecraft.class_5321;

public final class CachedWorld implements ICachedWorld, Helper {
   private Long2ObjectMap<CachedRegion> a = new Long2ObjectOpenHashMap();
   private final String a;
   final LinkedBlockingQueue<class_1923> a = new LinkedBlockingQueue<>();
   final Map<class_1923, class_2818> a = CacheBuilder.newBuilder().softValues().build().asMap();
   private final class_2874 a;
   private final class_5321<class_1937> a;

   CachedWorld(Path var1, class_2874 var2, class_5321<class_1937> var3) {
      if (!Files.exists(var1)) {
         try {
            Files.createDirectories(var1);
         } catch (IOException var4) {
         }
      }

      this.a = var1.toString();
      this.a = var2;
      this.a = var3;
      System.out.println("Cached world directory: " + var1);
      Baritone.a().execute(new CachedWorld.PackerThread());
      Baritone.a().execute(() -> {
         try {
            Thread.sleep(30000L);

            while (true) {
               this.save();
               Thread.sleep(600000L);
            }
         } catch (InterruptedException var1x) {
            var1x.printStackTrace();
         }
      });
   }

   @Override
   public final void queueForPacking(class_2818 var1) {
      if (this.a.put(var1.method_12004(), var1) == null) {
         this.a.add(var1.method_12004());
      }
   }

   @Override
   public final boolean isCached(int var1, int var2) {
      CachedRegion var3;
      return (var3 = this.a(var1 >> 9, var2 >> 9)) == null ? false : var3.isCached(var1 & 511, var2 & 511);
   }

   @Override
   public final ArrayList<class_2338> getLocationsOf(String var1, int var2, int var3, int var4, int var5) {
      ArrayList var6 = new ArrayList();
      var3 >>= 9;
      var4 >>= 9;

      for (int var7 = 0; var7 <= var5; var7++) {
         for (int var8 = -var7; var8 <= var7; var8++) {
            for (int var9 = -var7; var9 <= var7; var9++) {
               if (var8 * var8 + var9 * var9 == var7) {
                  int var10 = var8 + var3;
                  int var11 = var9 + var4;
                  CachedRegion var21;
                  if ((var21 = this.b(var10, var11)) != null) {
                     String var23 = var1;
                     CachedRegion var22 = var21;
                     ArrayList var12 = new ArrayList();

                     for (int var13 = 0; var13 < 32; var13++) {
                        for (int var14 = 0; var14 < 32; var14++) {
                           if (var22.a[var13][var14] != null) {
                              CachedChunk var10001 = var22.a[var13][var14];
                              String var16 = var23;
                              CachedChunk var15 = var10001;
                              ArrayList var26;
                              if (var10001.a.get(var16) == null) {
                                 var26 = null;
                              } else {
                                 ArrayList var17 = new ArrayList();

                                 for (class_2338 var18 : var15.a.get(var16)) {
                                    var17.add(
                                       new class_2338(var18.method_10263() + (var15.b << 4), var18.method_10264(), var18.method_10260() + (var15.c << 4))
                                    );
                                 }

                                 var26 = var17;
                              }

                              ArrayList var24 = var26;
                              if (var26 != null) {
                                 var12.addAll(var24);
                              }
                           }
                        }
                     }

                     var6.addAll(var12);
                  }
               }
            }
         }

         if (var6.size() >= var2) {
            return var6;
         }
      }

      return var6;
   }

   @Override
   public final void save() {
      if (!Baritone.a().chunkCaching.value) {
         System.out.println("Not saving to disk; chunk caching is disabled.");
         this.a().forEach(var0 -> {
            if (var0 != null) {
               var0.a();
            }
         });
         this.a();
      } else {
         long var1 = System.nanoTime() / 1000000L;
         this.a().parallelStream().forEach(var1x -> {
            if (var1x != null) {
               var1x.a(this.a);
            }
         });
         long var3 = System.nanoTime() / 1000000L;
         System.out.println("World save took " + (var3 - var1) + "ms");
         this.a();
      }
   }

   private synchronized void a() {
      if (Baritone.a().pruneRegionsFromRAM.value) {
         CachedWorld var1 = this;
         Iterator var2 = BaritoneAPI.getProvider().getAllBaritones().iterator();

         class_2338 var10000;
         while (true) {
            if (var2.hasNext()) {
               IBaritone var10;
               IWorldData var12;
               if ((var12 = (var10 = (IBaritone)var2.next()).getWorldProvider().getCurrentWorld()) == null
                  || var12.getCachedWorld() != var1
                  || var10.getPlayerContext().player() == null) {
                  continue;
               }

               var10000 = var10.getPlayerContext().playerFeet();
               break;
            }

            CachedChunk var8 = null;
            Iterator var3 = var1.a().iterator();

            while (var3.hasNext()) {
               CachedRegion var4;
               CachedChunk var6;
               if ((var4 = (CachedRegion)var3.next()) != null && (var6 = var4.a()) != null && (var8 == null || var8.a < var6.a)) {
                  var8 = var6;
               }
            }

            var10000 = var8 == null ? new class_2338(0, 0, 0) : new class_2338((var8.b << 4) + 8, 0, (var8.c << 4) + 8);
            break;
         }

         class_2338 var7 = var10000;
         var2 = this.a().iterator();

         while (var2.hasNext()) {
            CachedRegion var11;
            if ((var11 = (CachedRegion)var2.next()) != null) {
               int var13 = (var11.getX() << 9) + 256 - var7.method_10263();
               int var5 = (var11.getZ() << 9) + 256 - var7.method_10260();
               if (Math.sqrt(var13 * var13 + var5 * var5) > 1024.0) {
                  this.logDebug("Deleting cached region from ram");
                  this.a.remove(a(var11.getX(), var11.getZ()));
               }
            }
         }
      }
   }

   private synchronized List<CachedRegion> a() {
      return new ArrayList<>(this.a.values());
   }

   @Override
   public final void reloadAllFromDisk() {
      long var1 = System.nanoTime() / 1000000L;
      this.a().forEach(var1x -> {
         if (var1x != null) {
            var1x.b(this.a);
         }
      });
      long var3 = System.nanoTime() / 1000000L;
      System.out.println("World load took " + (var3 - var1) + "ms");
   }

   public final synchronized CachedRegion a(int var1, int var2) {
      return (CachedRegion)this.a.get(a(var1, var2));
   }

   public final synchronized CachedRegion b(int var1, int var2) {
      return (CachedRegion)this.a.computeIfAbsent(a(var1, var2), var3 -> {
         CachedRegion var5;
         (var5 = new CachedRegion(var1, var2, this.a, this.a)).b(this.a);
         return var5;
      });
   }

   private static long a(int var0, int var1) {
      return !a(var0, var1) ? 0L : var0 & 4294967295L | (var1 & 4294967295L) << 32;
   }

   private static boolean a(int var0, int var1) {
      return var0 <= 58594 && var0 >= -58594 && var1 <= 58594 && var1 >= -58594;
   }

   class PackerThread implements Runnable {
      @Override
      public void run() {
         while (true) {
            try {
               class_1923 var1 = CachedWorld.this.a.take();
               class_2818 var4 = CachedWorld.this.a.remove(var1);
               if (CachedWorld.this.a.size() <= Baritone.a().chunkPackerQueueMaxSize.value) {
                  CachedChunk var5 = ChunkPacker.a(var4);
                  CachedWorld.this.b(var5.b >> 5, var5.c >> 5).a(var5.b & 31, var5.c & 31, var5);
               }
            } catch (InterruptedException var2) {
               var2.printStackTrace();
               return;
            } catch (Throwable var3) {
               var3.printStackTrace();
            }
         }
      }
   }
}
