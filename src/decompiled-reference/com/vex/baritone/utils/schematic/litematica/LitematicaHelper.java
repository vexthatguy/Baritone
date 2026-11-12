package com.vex.baritone.utils.schematic.litematica;

import com.vex.baritone.api.schematic.CompositeSchematic;
import com.vex.baritone.api.schematic.ISchematic;
import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.utils.schematic.StaticSchematic;
import com.google.common.collect.UnmodifiableIterator;
import fi.dy.masa.litematica.Litematica;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.litematica.schematic.placement.SubRegionPlacement;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import fi.dy.masa.litematica.world.WorldSchematic;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_2415;
import net.minecraft.class_2470;
import net.minecraft.class_2680;
import net.minecraft.class_3545;

public final class LitematicaHelper {
   public static boolean a() {
      try {
         Class.forName(Litematica.class.getName());
         return true;
      } catch (ClassNotFoundException | NoClassDefFoundError var0) {
         return false;
      }
   }

   public static boolean a(int var0) {
      return var0 >= 0 && var0 < DataManager.getSchematicPlacementManager().getAllSchematicsPlacements().size();
   }

   private static class_2382 a(class_2382 var0, class_2415 var1, class_2470 var2) {
      int var3 = var0.method_10263();
      int var4 = var0.method_10260();
      if (var1 == class_2415.field_11300) {
         var4 = -var4;
      } else if (var1 == class_2415.field_11301) {
         var3 = -var3;
      }

      switch (var2) {
         case field_11463:
            return new class_2382(-var4, var0.method_10264(), var3);
         case field_11464:
            return new class_2382(-var3, var0.method_10264(), -var4);
         case field_11465:
            return new class_2382(var4, var0.method_10264(), -var3);
         default:
            return new class_2382(var3, var0.method_10264(), var4);
      }
   }

   public static class_3545<IStaticSchematic, class_2382> a(int var0) {
      SchematicPlacement var16 = (SchematicPlacement)DataManager.getSchematicPlacementManager().getAllSchematicsPlacements().get(var0);
      int var1 = Integer.MAX_VALUE;
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MAX_VALUE;
      HashMap var4 = new HashMap();
      WorldSchematic var5 = SchematicWorldHandler.getSchematicWorld();
      UnmodifiableIterator var6 = var16.getEnabledRelativeSubRegionPlacements().entrySet().iterator();

      while (var6.hasNext()) {
         Entry var7;
         SubRegionPlacement var8;
         class_2382 var9 = a((var8 = (SubRegionPlacement)(var7 = (Entry)var6.next()).getValue()).getPos(), var16.getMirror(), var16.getRotation());
         class_2382 var18;
         int var21 = Math.min(
            (var18 = a(a(var16.getSchematic().getAreaSize((String)var7.getKey()), var16.getMirror(), var16.getRotation()), var8.getMirror(), var8.getRotation()))
                  .method_10263()
               + 1,
            0
         );
         int var10 = Math.min(var18.method_10264() + 1, 0);
         int var11 = Math.min(var18.method_10260() + 1, 0);
         var1 = Math.min(var1, var9.method_10263() + var21);
         var2 = Math.min(var2, var9.method_10264() + var10);
         var3 = Math.min(var3, var9.method_10260() + var11);
         class_2338 var12 = var16.getOrigin().method_10081(var9).method_10069(var21, var10, var11);
         class_2680[][][] var19 = new class_2680[Math.abs(var18.method_10263())][Math.abs(var18.method_10260())][Math.abs(var18.method_10264())];

         for (int var13 = 0; var13 < var19.length; var13++) {
            for (int var14 = 0; var14 < var19[var13].length; var14++) {
               for (int var15 = 0; var15 < var19[var13][var14].length; var15++) {
                  var19[var13][var14][var15] = var5.method_8320(var12.method_10069(var13, var15, var14));
               }
            }
         }

         StaticSchematic var24 = new StaticSchematic(var19);
         var4.put(var9.method_34592(var21, var10, var11), var24);
      }

      LitematicaHelper.LitematicaPlacementSchematic var17 = new LitematicaHelper.LitematicaPlacementSchematic(var16.getName());
      Iterator var20 = var4.entrySet().iterator();

      while (var20.hasNext()) {
         Entry var22;
         class_2382 var23 = ((class_2382)(var22 = (Entry)var20.next()).getKey()).method_34592(-var1, -var2, -var3);
         var17.put((ISchematic)var22.getValue(), var23.method_10263(), var23.method_10264(), var23.method_10260());
      }

      return new class_3545(var17, var16.getOrigin().method_10069(var1, var2, var3));
   }

   static class LitematicaPlacementSchematic extends CompositeSchematic implements IStaticSchematic {
      private final String a;

      public LitematicaPlacementSchematic(String var1) {
         super(0, 0, 0);
         this.a = var1;
      }

      @Override
      public class_2680 getDirect(int var1, int var2, int var3) {
         return this.inSchematic(var1, var2, var3, null) ? this.desiredState(var1, var2, var3, null, Collections.emptyList()) : null;
      }

      @Override
      public String toString() {
         return this.a;
      }
   }
}
