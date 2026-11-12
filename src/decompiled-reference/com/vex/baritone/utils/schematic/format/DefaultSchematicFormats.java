package com.vex.baritone.utils.schematic.format;

import com.vex.baritone.api.schematic.format.ISchematicFormat;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

public enum DefaultSchematicFormats implements ISchematicFormat {
   a,
   a,
   a;

   private final String a;

   DefaultSchematicFormats(String var3) {
      this.a = var3;
   }

   @Override
   public boolean isFileType(File var1) {
      return this.a.equalsIgnoreCase(FilenameUtils.getExtension(var1.getAbsolutePath()));
   }

   @Override
   public List<String> getFileExtensions() {
      return Collections.singletonList(this.a);
   }
}
