package com.vex.baritone.api.utils;

import com.vex.baritone.api.cache.IWorldData;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2482;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_239.class_240;

public interface IPlayerContext {
   class_310 minecraft();

   class_746 player();

   IPlayerController playerController();

   class_1937 world();

   default Iterable<class_1297> entities() {
      return ((class_638)this.world()).method_18112();
   }

   default Stream<class_1297> entitiesStream() {
      return StreamSupport.stream(this.entities().spliterator(), false);
   }

   IWorldData worldData();

   class_239 objectMouseOver();

   default BetterBlockPos playerFeet() {
      BetterBlockPos var1 = new BetterBlockPos(
         this.player().method_73189().field_1352, this.player().method_73189().field_1351 + 0.1251, this.player().method_73189().field_1350
      );

      try {
         if (this.world().method_8320(var1).method_26204() instanceof class_2482) {
            return var1.above();
         }
      } catch (NullPointerException var2) {
      }

      return var1;
   }

   default class_243 playerFeetAsVec() {
      return new class_243(this.player().method_73189().field_1352, this.player().method_73189().field_1351, this.player().method_73189().field_1350);
   }

   default class_243 playerHead() {
      return new class_243(
         this.player().method_73189().field_1352,
         this.player().method_73189().field_1351 + this.player().method_5751(),
         this.player().method_73189().field_1350
      );
   }

   default class_243 playerMotion() {
      return this.player().method_18798();
   }

   BetterBlockPos viewerPos();

   default Rotation playerRotations() {
      return new Rotation(this.player().method_36454(), this.player().method_36455());
   }

   @Deprecated
   static double eyeHeight(boolean var0) {
      return var0 ? 1.27 : 1.62;
   }

   default Optional<class_2338> getSelectedBlock() {
      class_239 var1;
      return (var1 = this.objectMouseOver()) != null && var1.method_17783() == class_240.field_1332
         ? Optional.of(((class_3965)var1).method_17777())
         : Optional.empty();
   }

   default boolean isLookingAt(class_2338 var1) {
      return this.getSelectedBlock().equals(Optional.of(var1));
   }
}
