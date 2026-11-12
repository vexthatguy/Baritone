package com.vex.baritone.cache;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public final class CachedChunk {
   public static final ImmutableSet<class_2248> a = ImmutableSet.of(
      class_2246.field_10443,
      class_2246.field_10181,
      class_2246.field_10034,
      class_2246.field_10380,
      class_2246.field_10027,
      class_2246.field_10398,
      new class_2248[]{
         class_2246.field_10260,
         class_2246.field_10499,
         class_2246.field_10282,
         class_2246.field_10199,
         class_2246.field_10407,
         class_2246.field_10063,
         class_2246.field_10203,
         class_2246.field_10600,
         class_2246.field_10275,
         class_2246.field_10051,
         class_2246.field_10140,
         class_2246.field_10320,
         class_2246.field_10532,
         class_2246.field_10268,
         class_2246.field_10605,
         class_2246.field_10373,
         class_2246.field_10055,
         class_2246.field_10068,
         class_2246.field_10371,
         class_2246.field_10316,
         class_2246.field_10312,
         class_2246.field_10327,
         class_2246.field_10333,
         class_2246.field_10042,
         class_2246.field_10509,
         class_2246.field_10337,
         class_2246.field_10472,
         class_2246.field_10432,
         class_2246.field_10208,
         class_2246.field_10241,
         class_2246.field_10581,
         class_2246.field_10481,
         class_2246.field_10388,
         class_2246.field_10177,
         class_2246.field_10101,
         class_2246.field_10485,
         class_2246.field_10535,
         class_2246.field_10120,
         class_2246.field_10410,
         class_2246.field_10230,
         class_2246.field_10621,
         class_2246.field_10356,
         class_2246.field_10180,
         class_2246.field_10610,
         class_2246.field_10141,
         class_2246.field_10326,
         class_2246.field_10109,
         class_2246.field_10019,
         class_2246.field_10527,
         class_2246.field_10288,
         class_2246.field_10561,
         class_2246.field_10069,
         class_2246.field_10461,
         class_2246.field_10081,
         class_2246.field_10223,
         class_2246.field_10613,
         class_2246.field_10343,
         class_2246.field_9974,
         class_2246.field_9983,
         class_2246.field_10597
      }
   );
   private int d;
   private int e;
   public final int a;
   public final int b;
   public final int c;
   final BitSet a;
   final Int2ObjectOpenHashMap<String> a;
   final class_2680[] a;
   final int[] a;
   final Map<String, List<class_2338>> a;
   public final long a;

   CachedChunk(int var1, int var2, int var3, BitSet var4, class_2680[] var5, Map<String, List<class_2338>> var6, long var7) {
      this.e = var3 << 9;
      this.a = this.e / 8;
      BitSet var10 = var4;
      CachedChunk var9 = this;
      if (var10.size() > var9.e) {
         throw new IllegalArgumentException("BitSet of invalid length provided");
      }

      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.a = var4;
      this.a = var5;
      this.a = new int[256];
      this.a = var6;
      this.a = var7;
      if (var6.isEmpty()) {
         this.a = null;
      } else {
         this.a = new Int2ObjectOpenHashMap();
         var9 = this;
         Iterator var20 = this.a.entrySet().iterator();

         while (var20.hasNext()) {
            for (class_2338 var15 : (List)(var11 = (Entry)var20.next()).getValue()) {
               var9.a.put(a(var15.method_10263(), var15.method_10264(), var15.method_10260()), (String)var11.getKey());
            }
         }
      }

      var9 = this;

      for (int var21 = 0; var21 < 16; var21++) {
         for (int var12 = 0; var12 < 16; var12++) {
            var2 = var21 << 4 | var12;
            var9.a[var2] = 0;

            for (int var16 = var9.d; var16 >= 0; var16--) {
               int var17 = a(var12, var16, var21);
               if (var9.a.get(var17) || var9.a.get(var17 + 1)) {
                  var9.a[var2] = var16;
                  break;
               }
            }
         }
      }
   }

   public static int a(int var0) {
      return var0 << 9;
   }

   public static int b(int var0) {
      return var0 / 8;
   }

   public static int a(int var0, int var1, int var2) {
      return var0 << 1 | var2 << 5 | var1 << 9;
   }
}
