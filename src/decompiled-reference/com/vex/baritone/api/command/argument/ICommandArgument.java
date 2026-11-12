package com.vex.baritone.api.command.argument;

public interface ICommandArgument {
   int getIndex();

   String getValue();

   String getRawRest();

   <E extends Enum<?>> E getEnum(Class<E> var1);

   <T> T getAs(Class<T> var1);

   <T> boolean is(Class<T> var1);

   <T, S> T getAs(Class<T> var1, Class<S> var2, S var3);

   <T, S> boolean is(Class<T> var1, Class<S> var2, S var3);
}
