package com.vex.baritone.api.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class TypeUtils {
   private TypeUtils() {
   }

   public static Class<?> resolveBaseClass(Type var0) {
      if (var0 instanceof Class) {
         return (Class<?>)var0;
      } else {
         return var0 instanceof ParameterizedType ? (Class)((ParameterizedType)var0).getRawType() : null;
      }
   }
}
