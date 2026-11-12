package com.vex.baritone.behavior;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.process.BuilderProcess;
import com.vex.baritone.utils.ToolSet;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.class_1268;
import net.minecraft.class_1304;
import net.minecraft.class_1713;
import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2350;
import net.minecraft.class_2371;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3965;
import net.minecraft.class_746;
import net.minecraft.class_9334;

public final class InventoryBehavior extends Behavior implements Helper {
   private int a;
   private int[] a;

   public InventoryBehavior(Baritone var1) {
      super(var1);
   }

   @Override
   public final void onTick(TickEvent var1) {
      if (Baritone.a().allowInventory.value) {
         if (var1.getType() != TickEvent.Type.OUT) {
            if (super.a.player().field_7512 == super.a.player().field_7498) {
               this.a++;
               if (this.a() >= 9) {
                  this.a(this.a(), 8);
               }

               class_2248 var2 = class_2246.field_10340;
               class_2371 var10 = super.a.player().method_31548().method_67533();
               int var3 = -1;
               double var6 = -1.0;

               for (int var4 = 0; var4 < var10.size(); var4++) {
                  class_1799 var5;
                  double var8;
                  if (!(var5 = (class_1799)var10.get(var4)).method_7960()
                     && (
                        !Baritone.a().itemSaver.value
                           || var5.method_7919() + Baritone.a().itemSaverThreshold.value < var5.method_7936()
                           || var5.method_7936() <= 1
                     )
                     && var5.method_7909().method_57347().method_57832(class_9334.field_50077)
                     && (var8 = ToolSet.a(var5, var2.method_9564())) > var6) {
                     var6 = var8;
                     var3 = var4;
                  }
               }

               int var11 = var3;
               if (var3 >= 9) {
                  this.a(var11, 0);
               }

               if (this.a != null) {
                  this.logDebug("Remembering to move " + this.a[0] + " " + this.a[1] + " from a previous tick");
                  this.a(this.a[0], this.a[1]);
               }
            }
         }
      }
   }

   public final boolean a(int var1, Predicate<Integer> var2) {
      Predicate var3 = var2;
      InventoryBehavior var6 = this;
      ArrayList var4 = new ArrayList();

      for (int var5 = 1; var5 < 8; var5++) {
         if (((class_1799)var6.a.player().method_31548().method_67533().get(var5)).method_7960() && !var3.test(var5)) {
            var4.add(var5);
         }
      }

      if (var4.isEmpty()) {
         for (int var8 = 1; var8 < 8; var8++) {
            if (!var3.test(var8)) {
               var4.add(var8);
            }
         }
      }

      OptionalInt var7;
      return !(var7 = var4.isEmpty() ? OptionalInt.empty() : OptionalInt.of((Integer)var4.get(new Random().nextInt(var4.size())))).isPresent()
         || this.a(var1, var7.getAsInt());
   }

   private boolean a(int var1, int var2) {
      this.a = new int[]{var1, var2};
      if (this.a < Baritone.a().ticksBetweenInventoryMoves.value) {
         this.logDebug("Inventory move requested but delaying " + this.a + " " + Baritone.a().ticksBetweenInventoryMoves.value);
         return false;
      } else if (Baritone.a().inventoryMoveOnlyIfStationary.value && !super.a.a.a()) {
         this.logDebug("Inventory move requested but delaying until stationary");
         return false;
      } else {
         super.a
            .playerController()
            .windowClick(super.a.player().field_7498.field_7763, var1 < 9 ? var1 + 36 : var1, var2, class_1713.field_7791, super.a.player());
         this.a = 0;
         this.a = null;
         return true;
      }
   }

   private int a() {
      class_2371 var1 = super.a.player().method_31548().method_67533();

      for (int var2 = 0; var2 < var1.size(); var2++) {
         if (Baritone.a().acceptableThrowawayItems.value.contains(((class_1799)var1.get(var2)).method_7909())) {
            return var2;
         }
      }

      return -1;
   }

   public final boolean a() {
      for (class_1792 var2 : Baritone.a().acceptableThrowawayItems.value) {
         if (this.a(false, var1 -> var2.equals(var1.method_7909()))) {
            return true;
         }
      }

      return false;
   }

   public final boolean a(boolean var1, int var2, int var3, int var4) {
      BuilderProcess var10000 = super.a.a;
      class_2680 var6 = super.a.a.a(var2, var3, var4);
      int var5 = var4;
      var4 = var3;
      var3 = var2;
      BuilderProcess var7 = var10000;
      class_2680 var8;
      class_2680 var9;
      if ((
               var9 = !var10000.isActive()
                  ? null
                  : (
                     !var7.a.inSchematic(var3 - var7.a.method_10263(), var4 - var7.a.method_10264(), var5 - var7.a.method_10260(), var6)
                        ? null
                        : (
                           (var8 = var7.a.desiredState(var3 - var7.a.method_10263(), var4 - var7.a.method_10264(), var5 - var7.a.method_10260(), var6, var7.a))
                                 .method_26204() instanceof class_2189
                              ? null
                              : var8
                        )
                  )
            )
            != null
         && this.a(
            var1,
            var2x -> var2x.method_7909() instanceof class_1747
               && var9.equals(
                  ((class_1747)var2x.method_7909())
                     .method_7711()
                     .method_9605(
                        new class_1750(
                           new InventoryBehavior$1(
                              super.a.world(),
                              super.a.player(),
                              class_1268.field_5808,
                              var2x,
                              new class_3965(
                                 new class_243(
                                    super.a.player().method_73189().field_1352,
                                    super.a.player().method_73189().field_1351,
                                    super.a.player().method_73189().field_1350
                                 ),
                                 class_2350.field_11036,
                                 super.a.playerFeet(),
                                 false
                              )
                           )
                        )
                     )
               )
         )) {
         return true;
      }

      if (var9 != null
         && this.a(var1, var1x -> var1x.method_7909() instanceof class_1747 && ((class_1747)var1x.method_7909()).method_7711().equals(var9.method_26204()))) {
         return true;
      }

      for (class_1792 var12 : Baritone.a().acceptableThrowawayItems.value) {
         if (this.a(var1, var1x -> var12.equals(var1x.method_7909()))) {
            return true;
         }
      }

      return false;
   }

   public final boolean a(boolean var1, Predicate<? super class_1799> var2) {
      return this.a(var1, var2, Baritone.a().allowInventory.value);
   }

   private boolean a(boolean var1, Predicate<? super class_1799> var2, boolean var3) {
      class_746 var4;
      class_2371 var5 = (var4 = super.a.player()).method_31548().method_67533();

      for (int var6 = 0; var6 < 9; var6++) {
         class_1799 var7 = (class_1799)var5.get(var6);
         if (var2.test(var7)) {
            if (var1) {
               var4.method_31548().method_61496(var6);
            }

            return true;
         }
      }

      if (var2.test(var4.method_6118(class_1304.field_6171))) {
         for (int var8 = 0; var8 < 9; var8++) {
            class_1799 var10;
            if ((var10 = (class_1799)var5.get(var8)).method_7960() || var10.method_7909().method_57347().method_57832(class_9334.field_50077)) {
               if (var1) {
                  var4.method_31548().method_61496(var8);
               }

               return true;
            }
         }
      }

      if (var3) {
         for (int var9 = 9; var9 < 36; var9++) {
            if (var2.test(var5.get(var9))) {
               if (var1) {
                  this.a(var9, 7);
                  var4.method_31548().method_61496(7);
               }

               return true;
            }
         }
      }

      return false;
   }
}
