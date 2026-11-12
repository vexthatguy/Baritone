package com.vex.baritone.utils;

import com.vex.baritone.api.utils.input.Input;
import net.minecraft.class_10185;
import net.minecraft.class_241;
import net.minecraft.class_744;

public class PlayerMovementInput extends class_744 {
   private final InputOverrideHandler a;

   PlayerMovementInput(InputOverrideHandler var1) {
      this.a = var1;
   }

   public void method_3129() {
      float var1 = 0.0F;
      float var2 = 0.0F;
      boolean var3 = this.a.isInputForcedDown(Input.JUMP);
      boolean var4;
      if (var4 = this.a.isInputForcedDown(Input.MOVE_FORWARD)) {
         var2 = 1.0F;
      }

      boolean var5;
      if (var5 = this.a.isInputForcedDown(Input.MOVE_BACK)) {
         var2--;
      }

      boolean var6;
      if (var6 = this.a.isInputForcedDown(Input.MOVE_LEFT)) {
         var1 = 1.0F;
      }

      boolean var7;
      if (var7 = this.a.isInputForcedDown(Input.MOVE_RIGHT)) {
         var1--;
      }

      boolean var8;
      if (var8 = this.a.isInputForcedDown(Input.SNEAK)) {
         var1 = (float)(var1 * 0.3);
         var2 = (float)(var2 * 0.3);
      }

      this.field_55868 = new class_241(var1, var2);
      boolean var9 = this.a.isInputForcedDown(Input.SPRINT);
      this.field_54155 = new class_10185(var4, var5, var6, var7, var3, var8, var9);
   }
}
