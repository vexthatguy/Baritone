package com.vex.baritone.api.command.helpers;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.vex.baritone.api.utils.Helper;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_5250;
import net.minecraft.class_2558.class_10609;
import net.minecraft.class_2568.class_10613;

public class Paginator<E> implements Helper {
   public final List<E> entries;
   public int pageSize = 8;
   public int page = 1;

   public Paginator(List<E> var1) {
      this.entries = var1;
   }

   public Paginator(E... var1) {
      this.entries = Arrays.asList((E[])var1);
   }

   public Paginator<E> setPageSize(int var1) {
      this.pageSize = var1;
      return this;
   }

   public int getMaxPage() {
      return (this.entries.size() - 1) / this.pageSize + 1;
   }

   public boolean validPage(int var1) {
      return var1 > 0 && var1 <= this.getMaxPage();
   }

   public Paginator<E> skipPages(int var1) {
      this.page += var1;
      return this;
   }

   public void display(Function<E, class_2561> var1, String var2) {
      int var3;
      for (int var4 = var3 = (this.page - 1) * this.pageSize; var4 < var3 + this.pageSize; var4++) {
         if (var4 < this.entries.size()) {
            this.logDirect((class_2561)var1.apply(this.entries.get(var4)));
         } else {
            this.logDirect("--", class_124.field_1063);
         }
      }

      boolean var8 = var2 != null && this.validPage(this.page - 1);
      boolean var5 = var2 != null && this.validPage(this.page + 1);
      class_5250 var7 = class_2561.method_43470("<<");
      if (var8) {
         var7.method_10862(
            var7.method_10866()
               .method_10958(new class_10609(String.format("%s %d", var2, this.page - 1)))
               .method_10949(new class_10613(class_2561.method_43470("Click to view previous page")))
         );
      } else {
         var7.method_10862(var7.method_10866().method_10977(class_124.field_1063));
      }

      class_5250 var9 = class_2561.method_43470(">>");
      if (var5) {
         var9.method_10862(
            var9.method_10866()
               .method_10958(new class_10609(String.format("%s %d", var2, this.page + 1)))
               .method_10949(new class_10613(class_2561.method_43470("Click to view next page")))
         );
      } else {
         var9.method_10862(var9.method_10866().method_10977(class_124.field_1063));
      }

      class_5250 var6;
      class_5250 var10000 = var6 = class_2561.method_43470("");
      var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1080));
      var6.method_10852(var7);
      var6.method_27693(" | ");
      var6.method_10852(var9);
      var6.method_27693(String.format(" %d/%d", this.page, this.getMaxPage()));
      this.logDirect(var6);
   }

   public void display(Function<E, class_2561> var1) {
      this.display(var1, null);
   }

   public static <T> void paginate(IArgConsumer var0, Paginator<T> var1, Runnable var2, Function<T, class_2561> var3, String var4) {
      int var5 = 1;
      var0.requireMax(1);
      if (var0.hasAny()) {
         var5 = var0.getAs(Integer.class);
         if (!var1.validPage(var5)) {
            throw new CommandInvalidTypeException(var0.consumed(), String.format("a valid page (1-%d)", var1.getMaxPage()), var0.consumed().getValue());
         }
      }

      var1.skipPages(var5 - var1.page);
      if (var2 != null) {
         var2.run();
      }

      var1.display(var3, var4);
   }

   public static <T> void paginate(IArgConsumer var0, List<T> var1, Runnable var2, Function<T, class_2561> var3, String var4) {
      paginate(var0, new Paginator(var1), var2, var3, var4);
   }

   public static <T> void paginate(IArgConsumer var0, T[] var1, Runnable var2, Function<T, class_2561> var3, String var4) {
      paginate(var0, Arrays.asList(var1), var2, var3, var4);
   }

   public static <T> void paginate(IArgConsumer var0, Paginator<T> var1, Function<T, class_2561> var2, String var3) {
      paginate(var0, var1, null, var2, var3);
   }

   public static <T> void paginate(IArgConsumer var0, List<T> var1, Function<T, class_2561> var2, String var3) {
      paginate(var0, new Paginator(var1), null, var2, var3);
   }

   public static <T> void paginate(IArgConsumer var0, T[] var1, Function<T, class_2561> var2, String var3) {
      paginate(var0, Arrays.asList(var1), null, var2, var3);
   }

   public static <T> void paginate(IArgConsumer var0, Paginator<T> var1, Runnable var2, Function<T, class_2561> var3) {
      paginate(var0, var1, var2, var3, null);
   }

   public static <T> void paginate(IArgConsumer var0, List<T> var1, Runnable var2, Function<T, class_2561> var3) {
      paginate(var0, new Paginator(var1), var2, var3, null);
   }

   public static <T> void paginate(IArgConsumer var0, T[] var1, Runnable var2, Function<T, class_2561> var3) {
      paginate(var0, Arrays.asList(var1), var2, var3, null);
   }

   public static <T> void paginate(IArgConsumer var0, Paginator<T> var1, Function<T, class_2561> var2) {
      paginate(var0, var1, null, var2, null);
   }

   public static <T> void paginate(IArgConsumer var0, List<T> var1, Function<T, class_2561> var2) {
      paginate(var0, new Paginator(var1), null, var2, null);
   }

   public static <T> void paginate(IArgConsumer var0, T[] var1, Function<T, class_2561> var2) {
      paginate(var0, Arrays.asList(var1), null, var2, null);
   }
}
