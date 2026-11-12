package com.vex.baritone.api.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum SettingsUtil$Parser$1 {
   @Override
   public final Object parse(Type var1, String var2) {
      Type var4;
      SettingsUtil.Parser var3 = SettingsUtil.Parser.getParser(var4 = ((ParameterizedType)var1).getActualTypeArguments()[0]);
      return Stream.of(var2.split(",")).map(var2x -> var3.parse(var4, var2x)).collect(Collectors.toList());
   }

   @Override
   public final String toString(Type var1, Object var2) {
      Type var4;
      SettingsUtil.Parser var3 = SettingsUtil.Parser.getParser(var4 = ((ParameterizedType)var1).getActualTypeArguments()[0]);
      return ((List)var2).stream().map(var2x -> var3.toString(var4, var2x)).collect(Collectors.joining(","));
   }

   @Override
   public final boolean accepts(Type var1) {
      return List.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
   }
}
