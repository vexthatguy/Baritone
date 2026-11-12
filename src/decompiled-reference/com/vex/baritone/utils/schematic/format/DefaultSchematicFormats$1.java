package com.vex.baritone.utils.schematic.format;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.utils.schematic.format.defaults.MCEditSchematic;
import java.io.InputStream;
import net.minecraft.class_2505;
import net.minecraft.class_2507;

enum DefaultSchematicFormats$1 {
   @Override
   public final IStaticSchematic parse(InputStream var1) {
      return new MCEditSchematic(class_2507.method_10629(var1, class_2505.method_53898()));
   }
}
