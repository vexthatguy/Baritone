package com.vex.baritone.utils.schematic.schematica;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import java.util.Optional;
import net.minecraft.class_2338;
import net.minecraft.class_3545;

public enum SchematicaHelper {
   public static boolean a() {
      try {
         Class.forName(Schematica.class.getName());
         return true;
      } catch (ClassNotFoundException | NoClassDefFoundError var0) {
         return false;
      }
   }

   public static Optional<class_3545<IStaticSchematic, class_2338>> a() {
      return Optional.ofNullable(ClientProxy.schematic).map(var0 -> new class_3545(new SchematicAdapter(var0), var0.position));
   }
}
