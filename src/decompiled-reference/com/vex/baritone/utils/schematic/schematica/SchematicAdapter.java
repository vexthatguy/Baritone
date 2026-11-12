package com.vex.baritone.utils.schematic.schematica;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public final class SchematicAdapter implements IStaticSchematic {
   private final SchematicWorld a;

   public SchematicAdapter(SchematicWorld var1) {
      this.a = var1;
   }

   @Override
   public final class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return this.getDirect(var1, var2, var3);
   }

   @Override
   public final class_2680 getDirect(int var1, int var2, int var3) {
      return this.a.getSchematic().getBlockState(new class_2338(var1, var2, var3));
   }

   @Override
   public final int widthX() {
      return this.a.getSchematic().getWidth();
   }

   @Override
   public final int heightY() {
      return this.a.getSchematic().getHeight();
   }

   @Override
   public final int lengthZ() {
      return this.a.getSchematic().getLength();
   }
}
