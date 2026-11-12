package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.RenderEvent;
import com.vex.baritone.api.event.listener.AbstractGameEventListener;
import com.vex.baritone.utils.IRenderer;
import java.awt.Color;
import net.minecraft.class_238;
import net.minecraft.class_287;

class SelCommand$1 implements AbstractGameEventListener {
   SelCommand$1(SelCommand var1) {
      this.a = var1;
      super();
   }

   @Override
   public void onRenderPass(RenderEvent var1) {
      if (Baritone.a().renderSelectionCorners.value && this.a.a != null) {
         Color var2 = Baritone.a().colorSelectionPos1.value;
         float var3 = Baritone.a().selectionOpacity.value;
         float var4 = Baritone.a().selectionLineWidth.value;
         boolean var5 = Baritone.a().renderSelectionIgnoreDepth.value;
         class_287 var6;
         IRenderer.a(var6 = IRenderer.a(var2, var3), var1.getModelViewStack(), new class_238(this.a.a), var4);
         IRenderer.a(var6, var5);
      }
   }
}
