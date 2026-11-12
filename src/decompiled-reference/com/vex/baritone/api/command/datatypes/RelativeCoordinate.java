package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum RelativeCoordinate implements IDatatypePost<Double, Double> {
   INSTANCE;

   private static String ScalesAliasRegex = "[kKmM]";
   private static Pattern PATTERN = Pattern.compile("^(~?)([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(" + "[kKmM]" + "?)|)$");

   public final Double apply(IDatatypeContext var1, Double var2) {
      if (var2 == null) {
         var2 = 0.0;
      }

      Matcher var6;
      if (!(var6 = PATTERN.matcher(var1.getConsumer().getString())).matches()) {
         throw new IllegalArgumentException("pattern doesn't match");
      }

      boolean var3 = !var6.group(1).isEmpty();
      double var4 = var6.group(2).isEmpty() ? 0.0 : Double.parseDouble(var6.group(2).replaceAll(ScalesAliasRegex, ""));
      if (var6.group(2).toLowerCase().contains("k")) {
         var4 *= 1000.0;
      }

      if (var6.group(2).toLowerCase().contains("m")) {
         var4 *= 1000000.0;
      }

      return var3 ? var2 + var4 : var4;
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      IArgConsumer var2;
      return !(var2 = var1.getConsumer()).has(2) && var2.getString().matches("^(~|$)") ? Stream.of("~") : Stream.empty();
   }
}
