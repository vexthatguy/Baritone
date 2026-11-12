package com.vex.baritone.utils.schematic.format.defaults;

import com.vex.baritone.utils.schematic.StaticSchematic;
import com.vex.baritone.utils.type.VarInt;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_6880.class_6883;

public final class SpongeSchematic extends StaticSchematic {
   public SpongeSchematic(class_2487 var1) {
      super.x = var1.method_10550("Width").orElse(0);
      super.y = var1.method_10550("Height").orElse(0);
      super.z = var1.method_10550("Length").orElse(0);
      super.a = new class_2680[super.x][super.z][super.y];
      Int2ObjectArrayMap var2 = new Int2ObjectArrayMap();

      class_2487 var3;
      for (String var5 : (var3 = var1.method_10562("Palette").orElse(new class_2487())).method_10541()) {
         int var6 = var3.method_10550(var5).orElse(0);
         SpongeSchematic.SerializedBlockState var7;
         if ((var7 = SpongeSchematic.SerializedBlockState.a(var5)) == null) {
            throw new IllegalArgumentException("Unable to parse palette tag");
         }

         class_2680 var8;
         if ((var8 = var7.a()) == null) {
            throw new IllegalArgumentException("Unable to deserialize palette tag");
         }

         var2.put(var6, var8);
      }

      byte[] var11 = (byte[])var1.method_10547("BlockData").orElseThrow();
      int[] var13 = new int[super.x * super.y * super.z];
      int var14 = 0;

      for (int var15 = 0; var15 < var13.length; var15++) {
         if (var14 >= var11.length) {
            throw new IllegalArgumentException("No remaining bytes in BlockData for complete schematic");
         }

         VarInt var17 = VarInt.a(var11, var14);
         var13[var15] = var17.a;
         var14 += var17.b;
      }

      for (int var16 = 0; var16 < super.y; var16++) {
         for (int var18 = 0; var18 < super.z; var18++) {
            for (int var9 = 0; var9 < super.x; var9++) {
               int var10 = (var16 * super.z + var18) * super.x + var9;
               class_2680 var12;
               if ((var12 = (class_2680)var2.get(var13[var10])) == null) {
                  throw new IllegalArgumentException("Invalid Palette Index " + var10);
               }

               super.a[var9][var18][var16] = var12;
            }
         }
      }
   }

   static final class SerializedBlockState {
      private static final Pattern a = Pattern.compile("(?<location>(\\w+:)?\\w+)(\\[(?<properties>(\\w+=\\w+,?)+)])?");
      private final class_2960 a;
      private final Map<String, String> a;
      private class_2680 a;

      private SerializedBlockState(class_2960 var1, Map<String, String> var2) {
         this.a = var1;
         this.a = var2;
      }

      final class_2680 a() {
         if (this.a == null) {
            class_2248 var1 = class_7923.field_41175.method_10223(this.a).<class_2248>map(class_6883::comp_349).orElse(class_2246.field_10124);
            this.a = var1.method_9564();
            this.a.keySet().stream().sorted(String::compareTo).forEachOrdered(var2 -> {
               class_2769 var4;
               if ((var4 = var1.method_9595().method_11663(var2)) != null) {
                  class_2680 var10001 = this.a;
                  String var3 = this.a.get(var2);
                  class_2769 var6 = var4;
                  class_2680 var5 = var10001;
                  Optional var7;
                  if (!(var7 = var6.method_11900(var3)).isPresent()) {
                     throw new IllegalArgumentException("Invalid value for property " + var6);
                  }

                  this.a = (class_2680)var5.method_11657(var6, (Comparable)var7.get());
               }
            });
         }

         return this.a;
      }

      static SpongeSchematic.SerializedBlockState a(String var0) {
         Matcher var7;
         if (!(var7 = a.matcher(var0)).matches()) {
            return null;
         }

         try {
            String var1 = var7.group("location");
            var0 = var7.group("properties");
            class_2960 var10 = class_2960.method_60654(var1);
            HashMap var2 = new HashMap();
            if (var0 != null) {
               String[] var9;
               int var3 = (var9 = var0.split(",")).length;

               for (int var4 = 0; var4 < var3; var4++) {
                  String[] var5 = var9[var4].split("=");
                  var2.put(var5[0], var5[1]);
               }
            }

            return new SpongeSchematic.SerializedBlockState(var10, var2);
         } catch (Exception var6) {
            var6.printStackTrace();
            return null;
         }
      }
   }
}
