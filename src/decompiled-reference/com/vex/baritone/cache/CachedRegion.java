package com.vex.baritone.cache;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.ICachedRegion;
import com.vex.baritone.api.utils.BlockUtils;
import com.vex.baritone.utils.pathing.PathingBlockType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2874;
import net.minecraft.class_5321;

public final class CachedRegion implements ICachedRegion {
   final CachedChunk[][] a = new CachedChunk[32][32];
   private final int a;
   private final int b;
   private final class_2874 a;
   private final class_5321<class_1937> a;
   private boolean a;

   CachedRegion(int var1, int var2, class_2874 var3, class_5321<class_1937> var4) {
      this.a = var1;
      this.b = var2;
      this.a = false;
      this.a = var3;
      this.a = var4;
   }

   @Override
   public final class_2680 getBlock(int var1, int var2, int var3) {
      var2 -= this.a.comp_651();
      CachedChunk var4;
      if ((var4 = this.a[var1 >> 4][var3 >> 4]) == null) {
         return null;
      }

      int var10001 = var1 & 15;
      int var10003 = var3 & 15;
      class_5321 var6 = this.a;
      class_2874 var5 = this.a;
      int var16 = var10003;
      var3 = var2;
      var2 = var10001;
      CachedChunk var10 = var4;
      int var7 = CachedChunk.a(var2, var3, var16);
      int var9 = var7;
      CachedChunk var8 = var10;
      PathingBlockType var17 = PathingBlockType.a(var10.a.get(var9), var8.a.get(var9 + 1));
      var2 = var16 << 4 | var2;
      if (var10.a[var2] == var3 && var17 != PathingBlockType.c) {
         return var10.a[var2];
      }

      String var11;
      if (var10.a != null && (var11 = (String)var10.a.get(var7)) != null) {
         return BlockUtils.stringToBlockRequired(var11).method_9564();
      }

      if (var17 == PathingBlockType.d) {
         if (var3 == var5.comp_653() - 1 && var5.comp_643()) {
            return class_2246.field_9987.method_9564();
         }

         if ((var6 == class_1937.field_25179 || var6 == class_1937.field_25180) && var3 < var5.comp_651() + 5) {
            return class_2246.field_10540.method_9564();
         }
      }

      return ChunkPacker.a(var17, var6);
   }

   @Override
   public final boolean isCached(int var1, int var2) {
      return this.a[var1 >> 4][var2 >> 4] != null;
   }

   public final synchronized void a(int var1, int var2, CachedChunk var3) {
      this.a[var1][var2] = var3;
      this.a = true;
   }

   public final synchronized void a(String var1) {
      if (this.a) {
         this.a();

         try {
            Path var16;
            if (!Files.exists(var16 = Paths.get(var1))) {
               Files.createDirectories(var16);
            }

            System.out.println("Saving region " + this.a + "," + this.b + " to disk " + var16);
            Path var17;
            if (!Files.exists(var17 = a(var16, this.a, this.b))) {
               Files.createFile(var17);
            }

            try (
               FileOutputStream var18 = new FileOutputStream(var17.toFile());
               GZIPOutputStream var2 = new GZIPOutputStream(var18, 16384);
               DataOutputStream var3 = new DataOutputStream(var2);
            ) {
               var3.writeInt(456022911);

               for (int var4 = 0; var4 < 32; var4++) {
                  for (int var5 = 0; var5 < 32; var5++) {
                     CachedChunk var6;
                     if ((var6 = this.a[var4][var5]) == null) {
                        var3.write(0);
                     } else {
                        var3.write(1);
                        byte[] var7 = var6.a.toByteArray();
                        var3.write(var7);
                        var3.write(new byte[var6.a - var7.length]);
                     }
                  }
               }

               for (int var19 = 0; var19 < 32; var19++) {
                  for (int var22 = 0; var22 < 32; var22++) {
                     if (this.a[var19][var22] != null) {
                        for (int var25 = 0; var25 < 256; var25++) {
                           var3.writeUTF(BlockUtils.blockToString(this.a[var19][var22].a[var25].method_26204()));
                        }
                     }
                  }
               }

               for (int var20 = 0; var20 < 32; var20++) {
                  for (int var23 = 0; var23 < 32; var23++) {
                     if (this.a[var20][var23] != null) {
                        Map var26 = this.a[var20][var23].a;
                        var3.writeShort(var26.entrySet().size());

                        for (Entry var27 : var26.entrySet()) {
                           var3.writeUTF((String)var27.getKey());
                           var3.writeShort(((List)var27.getValue()).size());

                           for (class_2338 var8 : (List)var27.getValue()) {
                              var3.writeByte((byte)(var8.method_10260() << 4 | var8.method_10263()));
                              var3.writeInt(var8.method_10264() - this.a.comp_651());
                           }
                        }
                     }
                  }
               }

               for (int var21 = 0; var21 < 32; var21++) {
                  for (int var24 = 0; var24 < 32; var24++) {
                     if (this.a[var21][var24] != null) {
                        var3.writeLong(this.a[var21][var24].a);
                     }
                  }
               }
            }

            this.a = false;
            System.out.println("Saved region successfully");
         } catch (Exception var15) {
            var15.printStackTrace();
         }
      }
   }

   public final synchronized void b(String var1) {
      try {
         Path var28;
         if (!Files.exists(var28 = Paths.get(var1))) {
            Files.createDirectories(var28);
         }

         Path var2;
         if (Files.exists(var2 = a(var28, this.a, this.b))) {
            System.out.println("Loading region " + this.a + "," + this.b + " from disk " + var28);
            long var4 = System.nanoTime() / 1000000L;

            try (
               FileInputStream var6 = new FileInputStream(var2.toFile());
               GZIPInputStream var7 = new GZIPInputStream(var6, 32768);
               DataInputStream var29 = new DataInputStream(var7);
            ) {
               int var30;
               if ((var30 = var29.readInt()) != 456022911) {
                  throw new IOException("Bad magic value " + var30);
               }

               boolean[][] var3 = new boolean[32][32];
               BitSet[][] var31 = new BitSet[32][32];
               Map[][] var8 = new Map[32][32];
               class_2680[][][] var9 = new class_2680[32][32][];
               long[][] var10 = new long[32][32];

               for (int var11 = 0; var11 < 32; var11++) {
                  for (int var12 = 0; var12 < 32; var12++) {
                     switch (var29.read()) {
                        case 1:
                           byte[] var14 = new byte[CachedChunk.b(CachedChunk.a(this.a.comp_652()))];
                           var29.readFully(var14);
                           var31[var11][var12] = BitSet.valueOf(var14);
                           var8[var11][var12] = new HashMap();
                           var9[var11][var12] = new class_2680[256];
                           var3[var11][var12] = true;
                           break;
                        case 0:
                        default:
                           throw new IOException("Malformed stream");
                     }
                  }
               }

               for (int var33 = 0; var33 < 32; var33++) {
                  for (int var37 = 0; var37 < 32; var37++) {
                     if (var3[var33][var37]) {
                        for (int var13 = 0; var13 < 256; var13++) {
                           var9[var33][var37][var13] = BlockUtils.stringToBlockRequired(var29.readUTF()).method_9564();
                        }
                     }
                  }
               }

               for (int var34 = 0; var34 < 32; var34++) {
                  for (int var38 = 0; var38 < 32; var38++) {
                     if (var3[var34][var38]) {
                        int var41 = var29.readShort() & '\uffff';

                        for (int var43 = 0; var43 < var41; var43++) {
                           String var15;
                           BlockUtils.stringToBlockRequired(var15 = var29.readUTF());
                           ArrayList var16 = new ArrayList();
                           var8[var34][var38].put(var15, var16);
                           int var45;
                           if ((var45 = var29.readShort() & '\uffff') == 0) {
                              var45 = 65536;
                           }

                           for (int var17 = 0; var17 < var45; var17++) {
                              byte var18;
                              int var19 = (var18 = var29.readByte()) & 15;
                              var18 = var18 >>> 4 & 15;
                              int var20 = var29.readInt();
                              var16.add(new class_2338(var19, var20 + this.a.comp_651(), var18));
                           }
                        }
                     }
                  }
               }

               for (int var35 = 0; var35 < 32; var35++) {
                  for (int var39 = 0; var39 < 32; var39++) {
                     if (var3[var35][var39]) {
                        var10[var35][var39] = var29.readLong();
                     }
                  }
               }

               for (int var36 = 0; var36 < 32; var36++) {
                  for (int var40 = 0; var40 < 32; var40++) {
                     if (var3[var36][var40]) {
                        int var42 = this.a;
                        int var44 = this.b;
                        int var46 = var36 + (var42 << 5);
                        int var47 = var40 + (var44 << 5);
                        this.a[var36][var40] = new CachedChunk(
                           var46, var47, this.a.comp_652(), var31[var36][var40], var9[var36][var40], var8[var36][var40], var10[var36][var40]
                        );
                     }
                  }
               }
            }

            this.a();
            this.a = false;
            long var32 = System.nanoTime() / 1000000L;
            System.out.println("Loaded region successfully in " + (var32 - var4) + "ms");
         }
      } catch (Exception var27) {
         var27.printStackTrace();
      }
   }

   public final synchronized void a() {
      long var1;
      if ((var1 = Baritone.a().cachedChunksExpirySeconds.value) >= 0L) {
         long var3;
         long var5 = (var3 = System.currentTimeMillis()) - var1 * 1000L;

         for (int var7 = 0; var7 < 32; var7++) {
            for (int var8 = 0; var8 < 32; var8++) {
               if (this.a[var7][var8] != null && this.a[var7][var8].a < var5) {
                  System.out
                     .println(
                        "Removing chunk "
                           + (var7 + 32 * this.a)
                           + ","
                           + (var8 + 32 * this.b)
                           + " because it was cached "
                           + (var3 - this.a[var7][var8].a) / 1000L
                           + " seconds ago, and max age is "
                           + var1
                     );
                  this.a[var7][var8] = null;
               }
            }
         }
      }
   }

   public final synchronized CachedChunk a() {
      CachedChunk var1 = null;

      for (int var2 = 0; var2 < 32; var2++) {
         for (int var3 = 0; var3 < 32; var3++) {
            if (this.a[var2][var3] != null && (var1 == null || this.a[var2][var3].a > var1.a)) {
               var1 = this.a[var2][var3];
            }
         }
      }

      return var1;
   }

   @Override
   public final int getX() {
      return this.a;
   }

   @Override
   public final int getZ() {
      return this.b;
   }

   private static Path a(Path var0, int var1, int var2) {
      return Paths.get(var0.toString(), "r." + var1 + "." + var2 + ".bcr");
   }
}
