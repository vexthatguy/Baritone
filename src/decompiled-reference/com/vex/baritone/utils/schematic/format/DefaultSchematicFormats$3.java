package com.vex.baritone.utils.schematic.format;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.utils.schematic.format.defaults.LitematicaSchematic;
import java.io.InputStream;
import net.minecraft.class_2487;
import net.minecraft.class_2505;
import net.minecraft.class_2507;

enum DefaultSchematicFormats$3 {
   @Override
   public final IStaticSchematic parse(InputStream var1) {
      class_2487 var2;
      switch ((var2 = class_2507.method_10629(var1, class_2505.method_53898())).method_10550("Version").orElse(-1)) {
         case 4:
         case 5:
            throw new UnsupportedOperationException("This litematic Version is too old.");
         case 6:
            throw new UnsupportedOperationException("This litematic Version is too old.");
         case 7:
            return new LitematicaSchematic(var2);
         default:
            throw new UnsupportedOperationException("Unsuported Version of a Litematica Schematic");
      }
   }
}
