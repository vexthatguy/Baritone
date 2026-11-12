package com.vex.baritone.utils;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;
import com.vex.baritone.utils.accessor.IEntityRenderManager;
import com.vex.baritone.utils.accessor.IRenderPipelines;
import com.vex.baritone.utils.accessor.IRenderType;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.VertexFormat.class_5596;
import java.awt.Color;
import net.minecraft.class_10799;
import net.minecraft.class_12247;
import net.minecraft.class_12249;
import net.minecraft.class_1921;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_9801;
import net.minecraft.class_4587.class_4665;

public interface IRenderer {
   class_289 a = class_289.method_1348();
   IEntityRenderManager a = (IEntityRenderManager)class_310.method_1551().method_1561();
   Settings a = BaritoneAPI.getSettings();
   Snippet a = RenderPipeline.builder(new Snippet[]{((IRenderPipelines)(new class_10799())).getLinesSnippet()})
      .withBlend(new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO))
      .withDepthWrite(false)
      .withCull(false)
      .buildSnippet();
   class_1921 a;
   class_1921 b;
   float[] a = new float[]{1.0F, 1.0F, 1.0F, 255.0F};

   static void a(Color var0, float var1) {
      float[] var2 = var0.getColorComponents(null);
      a[0] = var2[0];
      a[1] = var2[1];
      a[2] = var2[2];
      a[3] = var1;
   }

   static class_287 a(Color var0, float var1) {
      a(var0, var1);
      return a.method_60827(class_5596.field_27377, class_290.field_63455);
   }

   static class_287 a(Color var0) {
      return a(var0, 0.4F);
   }

   static void a(class_287 var0, boolean var1) {
      class_9801 var2;
      if ((var2 = var0.method_60794()) != null) {
         if (var1) {
            b.method_60895(var2);
            return;
         }

         a.method_60895(var2);
      }
   }

   static void a(class_287 var0, class_4587 var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14) {
      double var15 = var8 - var2;
      double var17 = var10 - var4;
      double var19 = var12 - var6;
      double var21 = 1.0 / Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
      float var23 = (float)(var15 * var21);
      float var16 = (float)(var17 * var21);
      float var24 = (float)(var19 * var21);
      a(var0, var1, var2, var4, var6, var8, var10, var12, var23, var16, var24, var14);
   }

   static void a(
      class_287 var0,
      class_4587 var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      double var18,
      float var20
   ) {
      a(var0, var1, (float)var2, (float)var4, (float)var6, (float)var8, (float)var10, (float)var12, (float)var14, (float)var16, (float)var18, var20);
   }

   static void a(
      class_287 var0, class_4587 var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11
   ) {
      class_4665 var12 = var1.method_23760();
      var0.method_56824(var12, var2, var3, var4).method_22915(a[0], a[1], a[2], a[3]).method_60831(var12, var8, var9, var10).method_75298(var11);
      var0.method_56824(var12, var5, var6, var7).method_22915(a[0], a[1], a[2], a[3]).method_60831(var12, var8, var9, var10).method_75298(var11);
   }

   static void a(class_287 var0, class_4587 var1, class_238 var2, float var3) {
      var2 = var2.method_989(-a.renderPosX(), -a.renderPosY(), -a.renderPosZ());
      a(var0, var1, var2.field_1323, var2.field_1322, var2.field_1321, var2.field_1320, var2.field_1322, var2.field_1321, 1.0, 0.0, 0.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1322, var2.field_1321, var2.field_1320, var2.field_1322, var2.field_1324, 0.0, 0.0, 1.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1322, var2.field_1324, var2.field_1323, var2.field_1322, var2.field_1324, -1.0, 0.0, 0.0, var3);
      a(var0, var1, var2.field_1323, var2.field_1322, var2.field_1324, var2.field_1323, var2.field_1322, var2.field_1321, 0.0, 0.0, -1.0, var3);
      a(var0, var1, var2.field_1323, var2.field_1325, var2.field_1321, var2.field_1320, var2.field_1325, var2.field_1321, 1.0, 0.0, 0.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1325, var2.field_1321, var2.field_1320, var2.field_1325, var2.field_1324, 0.0, 0.0, 1.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1325, var2.field_1324, var2.field_1323, var2.field_1325, var2.field_1324, -1.0, 0.0, 0.0, var3);
      a(var0, var1, var2.field_1323, var2.field_1325, var2.field_1324, var2.field_1323, var2.field_1325, var2.field_1321, 0.0, 0.0, -1.0, var3);
      a(var0, var1, var2.field_1323, var2.field_1322, var2.field_1321, var2.field_1323, var2.field_1325, var2.field_1321, 0.0, 1.0, 0.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1322, var2.field_1321, var2.field_1320, var2.field_1325, var2.field_1321, 0.0, 1.0, 0.0, var3);
      a(var0, var1, var2.field_1320, var2.field_1322, var2.field_1324, var2.field_1320, var2.field_1325, var2.field_1324, 0.0, 1.0, 0.0, var3);
      a(var0, var1, var2.field_1323, var2.field_1322, var2.field_1324, var2.field_1323, var2.field_1325, var2.field_1324, 0.0, 1.0, 0.0, var3);
   }

   static void a(class_287 var0, class_4587 var1, class_238 var2, double var3, float var5) {
      a(var0, var1, var2.method_1009(var3, var3, var3), var5);
   }

   static void a(class_287 var0, class_4587 var1, class_243 var2, class_243 var3, float var4) {
      double var5 = a.renderPosX();
      double var7 = a.renderPosY();
      double var9 = a.renderPosZ();
      a(
         var0,
         var1,
         var2.field_1352 - var5,
         var2.field_1351 - var7,
         var2.field_1350 - var9,
         var3.field_1352 - var5,
         var3.field_1351 - var7,
         var3.field_1350 - var9,
         var4
      );
   }

   static {
      a = ((IRenderType)class_12249.method_76015())
         .createRenderType(
            "renderType/baritone_lines_with_depth",
            class_12247.method_75927(
                  RenderPipeline.builder(new Snippet[]{a})
                     .withLocation("pipelines/baritone_lines_with_depth")
                     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                     .build()
               )
               .method_75929(256)
               .method_75938()
         );
      b = ((IRenderType)class_12249.method_76015())
         .createRenderType(
            "renderType/baritone_lines_no_depth",
            class_12247.method_75927(
                  RenderPipeline.builder(new Snippet[]{a})
                     .withLocation("pipelines/baritone_lines_no_depth")
                     .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                     .build()
               )
               .method_75929(256)
               .method_75938()
         );
   }
}
