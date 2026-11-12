package com.vex.baritone.api.command.argument;

import com.vex.baritone.api.command.datatypes.IDatatype;
import com.vex.baritone.api.command.datatypes.IDatatypeFor;
import com.vex.baritone.api.command.datatypes.IDatatypePost;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

public interface IArgConsumer {
   LinkedList<ICommandArgument> getArgs();

   Deque<ICommandArgument> getConsumed();

   boolean has(int var1);

   boolean hasAny();

   boolean hasAtMost(int var1);

   boolean hasAtMostOne();

   boolean hasExactly(int var1);

   boolean hasExactlyOne();

   ICommandArgument peek(int var1);

   ICommandArgument peek();

   boolean is(Class<?> var1, int var2);

   boolean is(Class<?> var1);

   String peekString(int var1);

   String peekString();

   <E extends Enum<?>> E peekEnum(Class<E> var1, int var2);

   <E extends Enum<?>> E peekEnum(Class<E> var1);

   <E extends Enum<?>> E peekEnumOrNull(Class<E> var1, int var2);

   <E extends Enum<?>> E peekEnumOrNull(Class<E> var1);

   <T> T peekAs(Class<T> var1, int var2);

   <T> T peekAs(Class<T> var1);

   <T> T peekAsOrDefault(Class<T> var1, T var2, int var3);

   <T> T peekAsOrDefault(Class<T> var1, T var2);

   <T> T peekAsOrNull(Class<T> var1, int var2);

   <T> T peekAsOrNull(Class<T> var1);

   <T> T peekDatatype(IDatatypeFor<T> var1);

   <T, O> T peekDatatype(IDatatypePost<T, O> var1);

   <T, O> T peekDatatype(IDatatypePost<T, O> var1, O var2);

   <T> T peekDatatypeOrNull(IDatatypeFor<T> var1);

   <T, O> T peekDatatypeOrNull(IDatatypePost<T, O> var1);

   <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(D var1, O var2);

   <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(D var1, O var2, T var3);

   <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(D var1, O var2);

   <T, D extends IDatatypeFor<T>> T peekDatatypeFor(Class<D> var1);

   <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(Class<D> var1, T var2);

   <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(Class<D> var1);

   ICommandArgument get();

   String getString();

   <E extends Enum<?>> E getEnum(Class<E> var1);

   <E extends Enum<?>> E getEnumOrDefault(Class<E> var1, E var2);

   <E extends Enum<?>> E getEnumOrNull(Class<E> var1);

   <T> T getAs(Class<T> var1);

   <T> T getAsOrDefault(Class<T> var1, T var2);

   <T> T getAsOrNull(Class<T> var1);

   <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(D var1, O var2);

   <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(D var1, O var2, T var3);

   <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(D var1, O var2);

   <T, D extends IDatatypeFor<T>> T getDatatypeFor(D var1);

   <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(D var1, T var2);

   <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(D var1);

   <T extends IDatatype> Stream<String> tabCompleteDatatype(T var1);

   String rawRest();

   void requireMin(int var1);

   void requireMax(int var1);

   void requireExactly(int var1);

   boolean hasConsumed();

   ICommandArgument consumed();

   String consumedString();

   IArgConsumer copy();
}
