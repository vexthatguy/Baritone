package com.vex.baritone.api.schematic;

import com.vex.baritone.api.command.registry.Registry;
import com.vex.baritone.api.schematic.format.ISchematicFormat;
import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ISchematicSystem {
   Registry<ISchematicFormat> getRegistry();

   Optional<ISchematicFormat> getByFile(File var1);

   List<String> getFileExtensions();
}
