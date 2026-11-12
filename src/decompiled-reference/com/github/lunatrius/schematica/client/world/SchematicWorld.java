package com.github.lunatrius.schematica.client.world;

import net.minecraft.class_2338;
import net.minecraft.class_2680;

/** Compile-time stub for the optional Schematica mod. */
public class SchematicWorld {
   public class_2338 position;
   private final SchematicSchematic schematic = new SchematicSchematic();

   public SchematicSchematic getSchematic() {
      return schematic;
   }

   public static class SchematicSchematic {
      public class_2680 getBlockState(class_2338 pos) {
         return null;
      }

      public int getWidth() {
         return 0;
      }

      public int getHeight() {
         return 0;
      }

      public int getLength() {
         return 0;
      }
   }
}
