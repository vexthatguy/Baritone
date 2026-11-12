package com.vex.baritone.api.schematic.format;

import com.vex.baritone.api.schematic.IStaticSchematic;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface ISchematicFormat {
   IStaticSchematic parse(InputStream var1);

   boolean isFileType(File var1);

   List<String> getFileExtensions();
}
