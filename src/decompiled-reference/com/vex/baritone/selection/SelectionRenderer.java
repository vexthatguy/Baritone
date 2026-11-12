package com.vex.baritone.selection;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.RenderEvent;
import com.vex.baritone.api.event.listener.AbstractGameEventListener;
import com.vex.baritone.api.selection.ISelection;
import com.vex.baritone.utils.IRenderer;
import net.minecraft.class_238;
import net.minecraft.class_287;
import net.minecraft.class_4587;

public class SelectionRenderer implements AbstractGameEventListener, IRenderer {
   private final SelectionManager a;

   SelectionRenderer(Baritone var1, SelectionManager var2) {
      this.a = var2;
      var1.getGameEventHandler().registerEventListener(this);
   }

   @Override
   public void onRenderPass(RenderEvent var1) {
      class_4587 var10000 = var1.getModelViewStack();
      ISelection[] var2 = this.a.getSelections();
      class_4587 var11 = var10000;
      float var3 = a.selectionOpacity.value;
      boolean var4 = a.renderSelectionIgnoreDepth.value;
      float var5 = a.selectionLineWidth.value;
      if (a.renderSelection.value && var2.length != 0) {
         class_287 var6 = IRenderer.a(a.colorSelection.value, var3);
         ISelection[] var7 = var2;
         int var8 = var2.length;

         for (int var9 = 0; var9 < var8; var9++) {
            ISelection var10 = var7[var9];
            IRenderer.a(var6, var11, var10.aabb(), 0.005, var5);
         }

         if (a.renderSelectionCorners.value) {
            IRenderer.a(a.colorSelectionPos1.value, var3);
            var7 = var2;
            var8 = var2.length;

            for (int var16 = 0; var16 < var8; var16++) {
               ISelection var18 = var7[var16];
               IRenderer.a(var6, var11, new class_238(var18.pos1()), var5);
            }

            IRenderer.a(a.colorSelectionPos2.value, var3);
            var7 = var2;
            var8 = var2.length;

            for (int var17 = 0; var17 < var8; var17++) {
               ISelection var19 = var7[var17];
               IRenderer.a(var6, var11, new class_238(var19.pos2()), var5);
            }
         }

         IRenderer.a(var6, var4);
      }
   }
}
