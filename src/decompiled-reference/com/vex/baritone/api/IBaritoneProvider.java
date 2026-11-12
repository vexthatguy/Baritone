package com.vex.baritone.api;

import com.vex.baritone.api.cache.IWorldScanner;
import com.vex.baritone.api.command.ICommandSystem;
import com.vex.baritone.api.schematic.ISchematicSystem;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_746;

public interface IBaritoneProvider {
   IBaritone getPrimaryBaritone();

   List<IBaritone> getAllBaritones();

   default IBaritone getBaritoneForPlayer(class_746 var1) {
      for (IBaritone var3 : this.getAllBaritones()) {
         if (Objects.equals(var1, var3.getPlayerContext().player())) {
            return var3;
         }
      }

      return null;
   }

   default IBaritone getBaritoneForMinecraft(class_310 var1) {
      for (IBaritone var3 : this.getAllBaritones()) {
         if (Objects.equals(var1, var3.getPlayerContext().minecraft())) {
            return var3;
         }
      }

      return null;
   }

   default IBaritone getBaritoneForConnection(class_634 var1) {
      Iterator var2 = this.getAllBaritones().iterator();

      while (var2.hasNext()) {
         IBaritone var3;
         class_746 var4;
         if ((var4 = (var3 = (IBaritone)var2.next()).getPlayerContext().player()) != null && var4.field_3944 == var1) {
            return var3;
         }
      }

      return null;
   }

   IBaritone createBaritone(class_310 var1);

   boolean destroyBaritone(IBaritone var1);

   IWorldScanner getWorldScanner();

   ICommandSystem getCommandSystem();

   ISchematicSystem getSchematicSystem();
}
