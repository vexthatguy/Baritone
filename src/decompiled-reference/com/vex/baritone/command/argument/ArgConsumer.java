package com.vex.baritone.command.argument;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.command.datatypes.IDatatype;
import com.vex.baritone.api.command.datatypes.IDatatypeContext;
import com.vex.baritone.api.command.datatypes.IDatatypeFor;
import com.vex.baritone.api.command.datatypes.IDatatypePost;
import com.vex.baritone.api.command.exception.CommandException;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.vex.baritone.api.command.exception.CommandNotEnoughArgumentsException;
import com.vex.baritone.api.command.exception.CommandTooManyArgumentsException;
import com.vex.baritone.api.command.manager.ICommandManager;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ArgConsumer implements IArgConsumer {
   final ICommandManager a;
   private final ArgConsumer.Context a;
   private final LinkedList<ICommandArgument> a;
   private final Deque<ICommandArgument> a;

   private ArgConsumer(ICommandManager var1, Deque<ICommandArgument> var2, Deque<ICommandArgument> var3) {
      this.a = var1;
      this.a = new ArgConsumer.Context();
      this.a = new LinkedList<>(var2);
      this.a = new LinkedList<>(var3);
   }

   public ArgConsumer(ICommandManager var1, List<ICommandArgument> var2) {
      this(var1, new LinkedList<>(var2), new LinkedList<>());
   }

   @Override
   public LinkedList<ICommandArgument> getArgs() {
      return this.a;
   }

   @Override
   public Deque<ICommandArgument> getConsumed() {
      return this.a;
   }

   @Override
   public boolean has(int var1) {
      return this.a.size() >= var1;
   }

   @Override
   public boolean hasAny() {
      return this.has(1);
   }

   @Override
   public boolean hasAtMost(int var1) {
      return this.a.size() <= var1;
   }

   @Override
   public boolean hasAtMostOne() {
      return this.hasAtMost(1);
   }

   @Override
   public boolean hasExactly(int var1) {
      return this.a.size() == var1;
   }

   @Override
   public boolean hasExactlyOne() {
      return this.hasExactly(1);
   }

   @Override
   public ICommandArgument peek(int var1) {
      this.requireMin(var1 + 1);
      return this.a.get(var1);
   }

   @Override
   public ICommandArgument peek() {
      return this.peek(0);
   }

   @Override
   public boolean is(Class<?> var1, int var2) {
      return this.peek(var2).is(var1);
   }

   @Override
   public boolean is(Class<?> var1) {
      return this.is(var1, 0);
   }

   @Override
   public String peekString(int var1) {
      return this.peek(var1).getValue();
   }

   @Override
   public String peekString() {
      return this.peekString(0);
   }

   @Override
   public <E extends Enum<?>> E peekEnum(Class<E> var1, int var2) {
      return this.peek(var2).getEnum(var1);
   }

   @Override
   public <E extends Enum<?>> E peekEnum(Class<E> var1) {
      return this.peekEnum(var1, 0);
   }

   @Override
   public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1, int var2) {
      try {
         return this.peekEnum(var1, var2);
      } catch (CommandInvalidTypeException var3) {
         return null;
      }
   }

   @Override
   public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1) {
      return this.peekEnumOrNull(var1, 0);
   }

   @Override
   public <T> T peekAs(Class<T> var1, int var2) {
      return this.peek(var2).getAs(var1);
   }

   @Override
   public <T> T peekAs(Class<T> var1) {
      return this.peekAs(var1, 0);
   }

   @Override
   public <T> T peekAsOrDefault(Class<T> var1, T var2, int var3) {
      try {
         return this.peekAs(var1, var3);
      } catch (CommandInvalidTypeException var4) {
         return (T)var2;
      }
   }

   @Override
   public <T> T peekAsOrDefault(Class<T> var1, T var2) {
      return this.peekAsOrDefault(var1, (T)var2, 0);
   }

   @Override
   public <T> T peekAsOrNull(Class<T> var1, int var2) {
      return this.peekAsOrDefault(var1, null, var2);
   }

   @Override
   public <T> T peekAsOrNull(Class<T> var1) {
      return this.peekAsOrNull(var1, 0);
   }

   @Override
   public <T> T peekDatatype(IDatatypeFor<T> var1) {
      return this.a().getDatatypeFor(var1);
   }

   @Override
   public <T, O> T peekDatatype(IDatatypePost<T, O> var1) {
      return this.peekDatatype(var1, null);
   }

   @Override
   public <T, O> T peekDatatype(IDatatypePost<T, O> var1, O var2) {
      return this.a().getDatatypePost(var1, var2);
   }

   @Override
   public <T> T peekDatatypeOrNull(IDatatypeFor<T> var1) {
      return this.a().getDatatypeForOrNull(var1);
   }

   @Override
   public <T, O> T peekDatatypeOrNull(IDatatypePost<T, O> var1) {
      return this.a().getDatatypePostOrNull(var1, null);
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(D var1, O var2) {
      return this.a().getDatatypePost((D)var1, var2);
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(D var1, O var2, T var3) {
      return this.a().getDatatypePostOrDefault((D)var1, var2, (T)var3);
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(D var1, O var2) {
      return this.peekDatatypePostOrDefault((D)var1, var2, null);
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T peekDatatypeFor(Class<D> var1) {
      return this.a().peekDatatypeFor(var1);
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(Class<D> var1, T var2) {
      return this.a().peekDatatypeForOrDefault(var1, (T)var2);
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(Class<D> var1) {
      return this.peekDatatypeForOrDefault(var1, null);
   }

   @Override
   public ICommandArgument get() {
      this.requireMin(1);
      ICommandArgument var1 = this.a.removeFirst();
      this.a.add(var1);
      return var1;
   }

   @Override
   public String getString() {
      return this.get().getValue();
   }

   @Override
   public <E extends Enum<?>> E getEnum(Class<E> var1) {
      return this.get().getEnum(var1);
   }

   @Override
   public <E extends Enum<?>> E getEnumOrDefault(Class<E> var1, E var2) {
      try {
         this.peekEnum(var1);
         return this.getEnum(var1);
      } catch (CommandInvalidTypeException var3) {
         return (E)var2;
      }
   }

   @Override
   public <E extends Enum<?>> E getEnumOrNull(Class<E> var1) {
      return this.getEnumOrDefault(var1, null);
   }

   @Override
   public <T> T getAs(Class<T> var1) {
      return this.get().getAs(var1);
   }

   @Override
   public <T> T getAsOrDefault(Class<T> var1, T var2) {
      try {
         Object var4 = this.peek().getAs(var1);
         this.get();
         return (T)var4;
      } catch (CommandInvalidTypeException var3) {
         return (T)var2;
      }
   }

   @Override
   public <T> T getAsOrNull(Class<T> var1) {
      return this.getAsOrDefault(var1, null);
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(D var1, O var2) {
      try {
         return (T)var1.apply(this.a, var2);
      } catch (Exception var3) {
         if (Baritone.a().verboseCommandExceptions.value) {
            var3.printStackTrace();
         }

         throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), var1.getClass().getSimpleName(), var3);
      }
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(D var1, O var2, T var3) {
      ArrayList var4 = new ArrayList<>(this.a);
      ArrayList var5 = new ArrayList<>(this.a);

      try {
         return this.getDatatypePost((D)var1, var2);
      } catch (Exception var6) {
         this.a.clear();
         this.a.addAll(var4);
         this.a.clear();
         this.a.addAll(var5);
         return (T)var3;
      }
   }

   @Override
   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(D var1, O var2) {
      return this.getDatatypePostOrDefault((D)var1, var2, null);
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T getDatatypeFor(D var1) {
      try {
         return (T)var1.get(this.a);
      } catch (Exception var3) {
         if (Baritone.a().verboseCommandExceptions.value) {
            var3.printStackTrace();
         }

         throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), var1.getClass().getSimpleName(), var3);
      }
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(D var1, T var2) {
      ArrayList var3 = new ArrayList<>(this.a);
      ArrayList var4 = new ArrayList<>(this.a);

      try {
         return this.getDatatypeFor((D)var1);
      } catch (Exception var5) {
         this.a.clear();
         this.a.addAll(var3);
         this.a.clear();
         this.a.addAll(var4);
         return (T)var2;
      }
   }

   @Override
   public <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(D var1) {
      return this.getDatatypeForOrDefault((D)var1, null);
   }

   @Override
   public <T extends IDatatype> Stream<String> tabCompleteDatatype(T var1) {
      try {
         return var1.tabComplete(this.a);
      } catch (CommandException var2) {
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return Stream.empty();
   }

   @Override
   public String rawRest() {
      return this.a.size() > 0 ? this.a.getFirst().getRawRest() : "";
   }

   @Override
   public void requireMin(int var1) {
      if (this.a.size() < var1) {
         throw new CommandNotEnoughArgumentsException(var1 + this.a.size());
      }
   }

   @Override
   public void requireMax(int var1) {
      if (this.a.size() > var1) {
         throw new CommandTooManyArgumentsException(var1 + this.a.size());
      }
   }

   @Override
   public void requireExactly(int var1) {
      this.requireMin(var1);
      this.requireMax(var1);
   }

   @Override
   public boolean hasConsumed() {
      return !this.a.isEmpty();
   }

   @Override
   public ICommandArgument consumed() {
      return this.a.size() > 0 ? this.a.getLast() : CommandArguments.a();
   }

   @Override
   public String consumedString() {
      return this.consumed().getValue();
   }

   private ArgConsumer a() {
      return new ArgConsumer(this.a, this.a, this.a);
   }

   final class Context implements IDatatypeContext {
      @Override
      public final IBaritone getBaritone() {
         return ArgConsumer.this.a.getBaritone();
      }
   }
}
