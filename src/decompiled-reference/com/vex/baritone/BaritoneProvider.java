package com.vex.baritone;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.IBaritoneProvider;
import com.vex.baritone.api.cache.IWorldScanner;
import com.vex.baritone.api.command.ICommandSystem;
import com.vex.baritone.api.schematic.ISchematicSystem;
import com.vex.baritone.cache.FasterWorldScanner;
import com.vex.baritone.command.CommandSystem;
import com.vex.baritone.command.ExampleBaritoneControl;
import com.vex.baritone.utils.schematic.SchematicSystem;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_310;

public final class BaritoneProvider implements IBaritoneProvider {
   private final List<IBaritone> a = new CopyOnWriteArrayList<>();
   private final List<IBaritone> b = Collections.unmodifiableList(this.a);

   public BaritoneProvider() {
      ((Baritone)this.createBaritone(class_310.method_1551())).a(ExampleBaritoneControl::new);
   }

   @Override
   public final IBaritone getPrimaryBaritone() {
      return this.a.get(0);
   }

   @Override
   public final List<IBaritone> getAllBaritones() {
      return this.b;
   }

   @Override
   public final synchronized IBaritone createBaritone(class_310 var1) {
      IBaritone var2;
      if ((var2 = this.getBaritoneForMinecraft(var1)) == null) {
         this.a.add(var2 = new Baritone(var1));
      }

      return var2;
   }

   @Override
   public final synchronized boolean destroyBaritone(IBaritone var1) {
      return var1 != this.getPrimaryBaritone() && this.a.remove(var1);
   }

   @Override
   public final IWorldScanner getWorldScanner() {
      return FasterWorldScanner.a;
   }

   @Override
   public final ICommandSystem getCommandSystem() {
      return CommandSystem.a;
   }

   @Override
   public final ISchematicSystem getSchematicSystem() {
      return SchematicSystem.a;
   }
}
