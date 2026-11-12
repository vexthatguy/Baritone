package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.utils.IInputOverrideHandler;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.behavior.Behavior;
import com.vex.baritone.utils.accessor.IPlayerControllerMP;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_743;
import net.minecraft.class_239.class_240;

public final class InputOverrideHandler extends Behavior implements IInputOverrideHandler {
   private final Map<Input, Boolean> a = new HashMap<>();
   public final BlockBreakHelper a;
   private final BlockPlaceHelper a;

   public InputOverrideHandler(Baritone var1) {
      super(var1);
      this.a = new BlockBreakHelper(var1.getPlayerContext());
      this.a = new BlockPlaceHelper(var1.getPlayerContext());
   }

   @Override
   public final boolean isInputForcedDown(Input var1) {
      return var1 == null ? false : this.a.getOrDefault(var1, Boolean.FALSE);
   }

   @Override
   public final void setInputForceState(Input var1, boolean var2) {
      this.a.put(var1, var2);
   }

   @Override
   public final void clearAllKeys() {
      this.a.clear();
   }

   @Override
   public final void onTick(TickEvent var1) {
      if (var1.getType() != TickEvent.Type.OUT) {
         if (this.isInputForcedDown(Input.CLICK_LEFT)) {
            this.setInputForceState(Input.CLICK_RIGHT, false);
         }

         BlockBreakHelper var10000 = this.a;
         boolean var2 = this.isInputForcedDown(Input.CLICK_LEFT);
         BlockBreakHelper var7 = var10000;
         if (var10000.a > 0) {
            var7.a--;
         } else {
            class_239 var3;
            boolean var4 = (var3 = var7.a.objectMouseOver()) != null && var3.method_17783() == class_240.field_1332;
            if (var2 && var4) {
               var7.a.playerController().setHittingBlock(var7.a);
               if (var7.a.playerController().hasBrokenBlock()) {
                  var7.a.playerController().syncHeldItem();
                  var7.a.playerController().clickBlock(((class_3965)var3).method_17777(), ((class_3965)var3).method_17780());
                  var7.a.player().method_6104(class_1268.field_5808);
               } else {
                  if (var7.a.playerController().onPlayerDamageBlock(((class_3965)var3).method_17777(), ((class_3965)var3).method_17780())) {
                     var7.a.player().method_6104(class_1268.field_5808);
                  }

                  if (var7.a.playerController().hasBrokenBlock()) {
                     var7.a = BaritoneAPI.getSettings().blockBreakSpeed.value - 1;
                     ((IPlayerControllerMP)var7.a.minecraft().field_1761).setDestroyDelay(0);
                  }
               }

               var7.a = !var7.a.playerController().hasBrokenBlock();
               var7.a.playerController().setHittingBlock(false);
            } else {
               var7.a = false;
            }
         }

         BlockPlaceHelper var17 = this.a;
         var2 = this.isInputForcedDown(Input.CLICK_RIGHT);
         BlockPlaceHelper var8 = var17;
         if (var17.a > 0) {
            var8.a--;
         } else {
            class_239 var13 = var8.a.objectMouseOver();
            if (var2 && !var8.a.player().method_3144() && var13 != null && var13.method_17783() == class_240.field_1332) {
               var8.a = Baritone.a().rightClickSpeed.value - 1;
               class_1268[] var14;
               int var5 = (var14 = class_1268.values()).length;

               for (int var11 = 0; var11 < var5; var11++) {
                  class_1268 var6 = var14[var11];
                  if (var8.a.playerController().processRightClickBlock(var8.a.player(), var8.a.world(), var6, (class_3965)var13) == class_1269.field_5812) {
                     var8.a.player().method_6104(var6);
                     break;
                  }

                  if (!var8.a.player().method_5998(var6).method_7960()
                     && var8.a.playerController().processRightClick(var8.a.player(), var8.a.world(), var6) == class_1269.field_5812) {
                     break;
                  }
               }
            }
         }

         InputOverrideHandler var9 = this;
         Input[] var12;
         int var18 = (var12 = new Input[]{Input.MOVE_FORWARD, Input.MOVE_BACK, Input.MOVE_LEFT, Input.MOVE_RIGHT, Input.SNEAK, Input.JUMP}).length;
         int var15 = 0;

         while (true) {
            if (var15 >= 6) {
               if (!var9.a.a.isPathing() && var9.a == BaritoneAPI.getProvider().getPrimaryBaritone()) {
                  var19 = false;
                  break;
               }

               var19 = true;
               break;
            }

            Input var16 = var12[var15];
            if (var9.isInputForcedDown(var16)) {
               var19 = true;
               break;
            }

            var15++;
         }

         if (var19) {
            if (super.a.player().field_3913.getClass() != PlayerMovementInput.class) {
               super.a.player().field_3913 = new PlayerMovementInput(this);
               return;
            }
         } else if (super.a.player().field_3913.getClass() == PlayerMovementInput.class) {
            super.a.player().field_3913 = new class_743(super.a.minecraft().field_1690);
         }
      }
   }
}
