package com.vex.baritone.api.utils;

import com.vex.baritone.api.BaritoneAPI;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1934;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_3965;
import net.minecraft.class_746;

public interface IPlayerController {
   void syncHeldItem();

   boolean hasBrokenBlock();

   boolean onPlayerDamageBlock(class_2338 var1, class_2350 var2);

   void resetBlockRemoving();

   void windowClick(int var1, int var2, int var3, class_1713 var4, class_1657 var5);

   class_1934 getGameType();

   class_1269 processRightClickBlock(class_746 var1, class_1937 var2, class_1268 var3, class_3965 var4);

   class_1269 processRightClick(class_746 var1, class_1937 var2, class_1268 var3);

   boolean clickBlock(class_2338 var1, class_2350 var2);

   void setHittingBlock(boolean var1);

   default double getBlockReachDistance() {
      return this.getGameType().method_8386() ? 5.0 : BaritoneAPI.getSettings().blockReachDistance.value.floatValue();
   }
}
