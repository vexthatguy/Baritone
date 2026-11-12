package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Helper;
import net.minecraft.class_11909;
import net.minecraft.class_124;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_746;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class GuiClick extends class_437 implements Helper {
   Matrix4f a;
   class_2338 a;
   class_2338 b;

   public GuiClick() {
      super(class_2561.method_43470("CLICK"));
   }

   public boolean method_25421() {
      return false;
   }

   public void method_25394(class_332 var1, int var2, int var3, float var4) {
      double var5 = mc.field_1729.method_1603();
      double var7 = mc.field_1729.method_1604();
      var7 = (mc.method_22683().method_4507() - var7) * ((double)mc.method_22683().method_4506() / mc.method_22683().method_4507());
      var5 *= (double)mc.method_22683().method_4489() / mc.method_22683().method_4480();
      class_243 var9 = this.a(var5, var7, 0.0);
      class_243 var11 = this.a(var5, var7, 1.0);
      if (var9 != null && var11 != null) {
         class_243 var12 = new class_243(PathRenderer.a(), PathRenderer.b(), PathRenderer.c());
         class_3965 var10;
         class_746 var13;
         if ((
                  var10 = (var13 = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().player())
                     .method_73183()
                     .method_17742(new class_3959(var9.method_1019(var12), var11.method_1019(var12), class_3960.field_17559, class_242.field_1348, var13))
               )
               != null
            && var10.method_17783() == class_240.field_1332) {
            this.b = var10.method_17777();
         }
      }
   }

   public boolean method_25406(class_11909 var1) {
      if (this.b != null) {
         if (var1.method_74245() == 0) {
            if (this.a != null && !this.a.equals(this.b)) {
               BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().removeAllSelections();
               BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().addSelection(BetterBlockPos.from(this.a), BetterBlockPos.from(this.b));
               class_5250 var2;
               class_5250 var10000 = var2 = class_2561.method_43470("Selection made! For usage: " + Baritone.a().prefix.value + "help sel");
               var10000.method_10862(
                  var10000.method_10866()
                     .method_10977(class_124.field_1068)
                     .method_10958(new class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + "help sel"))
               );
               Helper.HELPER.logDirect(var2);
               this.a = null;
            } else {
               BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.b));
            }
         } else if (var1.method_74245() == 1) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.b.method_10084()));
         }
      }

      this.a = null;
      return super.method_25406(var1);
   }

   public boolean method_25402(class_11909 var1, boolean var2) {
      this.a = this.b;
      return super.method_25402(var1, var2);
   }

   private class_243 a(double var1, double var3, double var5) {
      if (this.a == null) {
         return null;
      }

      var1 /= mc.method_22683().method_4489();
      var3 /= mc.method_22683().method_4506();
      var1 = var1 * 2.0 - 1.0;
      var3 = var3 * 2.0 - 1.0;
      Vector4f var9 = new Vector4f((float)var1, (float)var3, (float)var5, 1.0F);
      this.a.transform(var9);
      if (var9.w() == 0.0F) {
         return null;
      }

      var9.mul(1.0F / var9.w());
      return new class_243(var9.x(), var9.y(), var9.z());
   }
}
