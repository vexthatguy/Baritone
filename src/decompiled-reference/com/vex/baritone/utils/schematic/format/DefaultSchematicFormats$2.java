package com.vex.baritone.utils.schematic.format;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.utils.schematic.format.defaults.SpongeSchematic;
import java.io.InputStream;
import net.minecraft.class_2487;
import net.minecraft.class_2505;
import net.minecraft.class_2507;

enum DefaultSchematicFormats$2 {
   @Override
   public final IStaticSchematic parse(InputStream var1) {
      class_2487 var2;
      switch ((var2 = class_2507.method_10629(var1, class_2505.method_53898())).method_10550("Version").orElse(-1)) {
         case 1:
         case 2:
            return new SpongeSchematic(var2);
         default:
            throw new UnsupportedOperationException("Unsupported Version of a Sponge Schematic");
      }
   }
}
