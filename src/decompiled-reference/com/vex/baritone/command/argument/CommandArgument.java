package com.vex.baritone.command.argument;

import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.vex.baritone.command.argparser.ArgParserManager;
import java.util.stream.Stream;

class CommandArgument implements ICommandArgument {
   private final int a;
   private final String a;
   private final String b;

   CommandArgument(int var1, String var2, String var3) {
      this.a = var1;
      this.a = var2;
      this.b = var3;
   }

   @Override
   public int getIndex() {
      return this.a;
   }

   @Override
   public String getValue() {
      return this.a;
   }

   @Override
   public String getRawRest() {
      return this.b;
   }

   @Override
   public <E extends Enum<?>> E getEnum(Class<E> var1) {
      return (E)Stream.of((Enum[])var1.getEnumConstants())
         .filter(var1x -> var1x.name().equalsIgnoreCase(this.a))
         .findFirst()
         .orElseThrow(() -> new CommandInvalidTypeException(this, var1.getSimpleName()));
   }

   @Override
   public <T> T getAs(Class<T> var1) {
      return ArgParserManager.a.parseStateless(var1, this);
   }

   @Override
   public <T> boolean is(Class<T> var1) {
      try {
         this.getAs(var1);
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   @Override
   public <T, S> T getAs(Class<T> var1, Class<S> var2, S var3) {
      return ArgParserManager.a.parseStated(var1, var2, this, var3);
   }

   @Override
   public <T, S> boolean is(Class<T> var1, Class<S> var2, S var3) {
      try {
         this.getAs(var1, var2, var3);
         return true;
      } catch (Throwable var4) {
         return false;
      }
   }
}
