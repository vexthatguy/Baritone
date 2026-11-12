package com.vex.baritone.api.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum SettingsUtil$Parser$2 {
   @Override
   public final Object parse(Type var1, String var2) {
      Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
      var1 = ((ParameterizedType)var1).getActualTypeArguments()[1];
      SettingsUtil.Parser var4 = SettingsUtil.Parser.getParser(var3);
      SettingsUtil.Parser var5 = SettingsUtil.Parser.getParser(var1);
      return Stream.of(var2.split(",(?=[^,]*->)"))
         .map(var0 -> var0.split("->"))
         .collect(Collectors.toMap(var2x -> var4.parse(var3, var2x[0]), var2x -> var5.parse(var1, var2x[1])));
   }

   @Override
   public final String toString(Type var1, Object var2) {
      Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
      var1 = ((ParameterizedType)var1).getActualTypeArguments()[1];
      SettingsUtil.Parser var4 = SettingsUtil.Parser.getParser(var3);
      SettingsUtil.Parser var5 = SettingsUtil.Parser.getParser(var1);
      return ((Map)var2)
         .entrySet()
         .stream()
         .map(var4x -> var4.toString(var3, var4x.getKey()) + "->" + var5.toString(var1, var4x.getValue()))
         .collect(Collectors.joining(","));
   }

   @Override
   public final boolean accepts(Type var1) {
      return Map.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
   }
}
