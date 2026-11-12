package com.vex.baritone.api.schematic;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2680;

public class CompositeSchematic extends AbstractSchematic {
   private final List<CompositeSchematicEntry> schematics = new ArrayList<>();
   private CompositeSchematicEntry[] schematicArr;

   private void recalcArr() {
      this.schematicArr = this.schematics.toArray(new CompositeSchematicEntry[0]);
      CompositeSchematicEntry[] var1 = this.schematicArr;
      int var2 = this.schematicArr.length;

      for (int var3 = 0; var3 < var2; var3++) {
         CompositeSchematicEntry var4 = var1[var3];
         super.x = Math.max(super.x, var4.x + var4.schematic.widthX());
         super.y = Math.max(super.y, var4.y + var4.schematic.heightY());
         super.z = Math.max(super.z, var4.z + var4.schematic.lengthZ());
      }
   }

   public CompositeSchematic(int var1, int var2, int var3) {
      super(var1, var2, var3);
      this.recalcArr();
   }

   public void put(ISchematic var1, int var2, int var3, int var4) {
      this.schematics.add(new CompositeSchematicEntry(var1, var2, var3, var4));
      this.recalcArr();
   }

   private CompositeSchematicEntry getSchematic(int var1, int var2, int var3, class_2680 var4) {
      CompositeSchematicEntry[] var5 = this.schematicArr;
      int var6 = this.schematicArr.length;

      for (int var7 = 0; var7 < var6; var7++) {
         CompositeSchematicEntry var8 = var5[var7];
         if (var1 >= var8.x && var2 >= var8.y && var3 >= var8.z && var8.schematic.inSchematic(var1 - var8.x, var2 - var8.y, var3 - var8.z, var4)) {
            return var8;
         }
      }

      return null;
   }

   @Override
   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      CompositeSchematicEntry var5;
      return (var5 = this.getSchematic(var1, var2, var3, var4)) != null && var5.schematic.inSchematic(var1 - var5.x, var2 - var5.y, var3 - var5.z, var4);
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      CompositeSchematicEntry var6;
      if ((var6 = this.getSchematic(var1, var2, var3, var4)) == null) {
         throw new IllegalStateException("couldn't find schematic for this position");
      } else {
         return var6.schematic.desiredState(var1 - var6.x, var2 - var6.y, var3 - var6.z, var4, var5);
      }
   }

   @Override
   public void reset() {
      CompositeSchematicEntry[] var1 = this.schematicArr;
      int var2 = this.schematicArr.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1[var3].schematic.reset();
      }
   }
}
