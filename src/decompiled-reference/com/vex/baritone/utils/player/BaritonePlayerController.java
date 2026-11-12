package com.vex.baritone.utils.player;

import com.vex.baritone.api.utils.IPlayerController;
import com.vex.baritone.utils.accessor.IPlayerControllerMP;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1934;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_746;

public final class BaritonePlayerController implements IPlayerController {
   private final class_310 a;

   public BaritonePlayerController(class_310 var1) {
      this.a = var1;
   }

   @Override
   public final void syncHeldItem() {
      ((IPlayerControllerMP)this.a.field_1761).callSyncCurrentPlayItem();
   }

   @Override
   public final boolean hasBrokenBlock() {
      return !((IPlayerControllerMP)this.a.field_1761).isHittingBlock();
   }

   @Override
   public final boolean onPlayerDamageBlock(class_2338 var1, class_2350 var2) {
      return this.a.field_1761.method_2902(var1, var2);
   }

   @Override
   public final void resetBlockRemoving() {
      this.a.field_1761.method_2925();
   }

   @Override
   public final void windowClick(int var1, int var2, int var3, class_1713 var4, class_1657 var5) {
      this.a.field_1761.method_2906(var1, var2, var3, var4, var5);
   }

   @Override
   public final class_1934 getGameType() {
      return this.a.field_1761.method_2920();
   }

   @Override
   public final class_1269 processRightClickBlock(class_746 var1, class_1937 var2, class_1268 var3, class_3965 var4) {
      return this.a.field_1761.method_2896(var1, var3, var4);
   }

   @Override
   public final class_1269 processRightClick(class_746 var1, class_1937 var2, class_1268 var3) {
      return this.a.field_1761.method_2919(var1, var3);
   }

   @Override
   public final boolean clickBlock(class_2338 var1, class_2350 var2) {
      return this.a.field_1761.method_2910(var1, var2);
   }

   @Override
   public final void setHittingBlock(boolean var1) {
      ((IPlayerControllerMP)this.a.field_1761).setIsHittingBlock(var1);
   }
}
