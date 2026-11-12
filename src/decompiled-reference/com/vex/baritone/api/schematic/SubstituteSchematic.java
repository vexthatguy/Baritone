package com.vex.baritone.api.schematic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2680;
import net.minecraft.class_2769;

public class SubstituteSchematic extends AbstractSchematic {
   private final ISchematic schematic;
   private final Map<class_2248, List<class_2248>> substitutions;
   private final Map<class_2680, Map<class_2248, class_2680>> blockStateCache = new HashMap<>();

   public SubstituteSchematic(ISchematic var1, Map<class_2248, List<class_2248>> var2) {
      super(var1.widthX(), var1.heightY(), var1.lengthZ());
      this.schematic = var1;
      this.substitutions = var2;
   }

   @Override
   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return this.schematic.inSchematic(var1, var2, var3, var4);
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      class_2680 var9;
      class_2248 var10 = (var9 = this.schematic.desiredState(var1, var2, var3, var4, var5)).method_26204();
      if (!this.substitutions.containsKey(var10)) {
         return var9;
      }

      List var11;
      if ((var11 = this.substitutions.get(var10)).contains(var4.method_26204()) && !(var4.method_26204() instanceof class_2189)) {
         return this.withBlock(var9, var4.method_26204());
      }

      Iterator var12 = var11.iterator();

      while (var12.hasNext()) {
         class_2248 var6;
         if ((var6 = (class_2248)var12.next()) instanceof class_2189) {
            if (var4.method_26204() instanceof class_2189) {
               return var4;
            }

            return class_2246.field_10124.method_9564();
         }

         for (class_2680 var8 : var5) {
            if (var6.equals(var8.method_26204())) {
               return this.withBlock(var9, var8.method_26204());
            }
         }
      }

      return ((class_2248)var11.get(0)).method_9564();
   }

   private class_2680 withBlock(class_2680 var1, class_2248 var2) {
      if (this.blockStateCache.containsKey(var1) && this.blockStateCache.get(var1).containsKey(var2)) {
         return this.blockStateCache.get(var1).get(var2);
      }

      Collection var3 = var1.method_28501();
      class_2680 var4 = var2.method_9564();

      for (class_2769 var5 : var3) {
         try {
            var4 = this.copySingleProp(var1, var4, var5);
         } catch (IllegalArgumentException var6) {
         }
      }

      this.blockStateCache.computeIfAbsent(var1, var0 -> new HashMap<>()).put(var2, var4);
      return var4;
   }

   private <T extends Comparable<T>> class_2680 copySingleProp(class_2680 var1, class_2680 var2, class_2769<T> var3) {
      return (class_2680)var2.method_11657(var3, var1.method_11654(var3));
   }
}
