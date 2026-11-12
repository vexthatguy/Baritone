package com.vex.baritone.api.schematic;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_2470;
import net.minecraft.class_2680;

public class RotatedSchematic implements ISchematic {
   private final ISchematic schematic;
   private final class_2470 rotation;
   private final class_2470 inverseRotation;

   public RotatedSchematic(ISchematic var1, class_2470 var2) {
      this.schematic = var1;
      this.rotation = var2;
      this.inverseRotation = var2.method_10501(var2).method_10501(var2);
   }

   @Override
   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return this.schematic
         .inSchematic(
            rotateX(var1, var3, this.widthX(), this.lengthZ(), this.inverseRotation),
            var2,
            rotateZ(var1, var3, this.widthX(), this.lengthZ(), this.inverseRotation),
            rotate(var4, this.inverseRotation)
         );
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return rotate(
         this.schematic
            .desiredState(
               rotateX(var1, var3, this.widthX(), this.lengthZ(), this.inverseRotation),
               var2,
               rotateZ(var1, var3, this.widthX(), this.lengthZ(), this.inverseRotation),
               rotate(var4, this.inverseRotation),
               rotate(var5, this.inverseRotation)
            ),
         this.rotation
      );
   }

   @Override
   public void reset() {
      this.schematic.reset();
   }

   @Override
   public int widthX() {
      return flipsCoordinates(this.rotation) ? this.schematic.lengthZ() : this.schematic.widthX();
   }

   @Override
   public int heightY() {
      return this.schematic.heightY();
   }

   @Override
   public int lengthZ() {
      return flipsCoordinates(this.rotation) ? this.schematic.widthX() : this.schematic.lengthZ();
   }

   private static boolean flipsCoordinates(class_2470 var0) {
      return var0 == class_2470.field_11463 || var0 == class_2470.field_11465;
   }

   private static int rotateX(int var0, int var1, int var2, int var3, class_2470 var4) {
      switch (var4) {
         case field_11467:
            return var0;
         case field_11463:
            return var3 - var1 - 1;
         case field_11464:
            return var2 - var0 - 1;
         case field_11465:
            return var1;
         default:
            throw new IllegalArgumentException("Unknown rotation");
      }
   }

   private static int rotateZ(int var0, int var1, int var2, int var3, class_2470 var4) {
      switch (var4) {
         case field_11467:
            return var1;
         case field_11463:
            return var0;
         case field_11464:
            return var3 - var1 - 1;
         case field_11465:
            return var2 - var0 - 1;
         default:
            throw new IllegalArgumentException("Unknown rotation");
      }
   }

   private static class_2680 rotate(class_2680 var0, class_2470 var1) {
      return var0 == null ? null : var0.method_26186(var1);
   }

   private static List<class_2680> rotate(List<class_2680> var0, class_2470 var1) {
      return var0 == null ? null : var0.stream().map(var1x -> rotate(var1x, var1)).collect(Collectors.toList());
   }
}
