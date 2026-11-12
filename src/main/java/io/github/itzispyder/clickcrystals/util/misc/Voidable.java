package io.github.itzispyder.clickcrystals.util.misc;

import java.util.function.Consumer;
import java.util.function.Function;

public class Voidable<T> {
   private T value;

   public static <T> Voidable<T> of(T value) {
      return new Voidable<>(value);
   }

   public static <T> Voidable<T> empty() {
      return of(null);
   }

   private Voidable(T value) {
      this.value = value;
   }

   public T get() {
      return this.value;
   }

   public void set(T value) {
      this.value = value;
   }

   public boolean isPresent() {
      return this.value != null;
   }

   public boolean isAbsent() {
      return this.value == null;
   }

   public void accept(Consumer<T> action) {
      if (this.isPresent()) {
         action.accept(this.value);
      }
   }

   public void clear() {
      this.set(null);
   }

   public T getOrDef(T fallback) {
      return this.isPresent() ? this.value : fallback;
   }

   public T getOrThrow() {
      assert this.isPresent() : "value is not present.";
      return this.value;
   }

   public <U> Voidable<U> map(Function<T, U> function) {
      return this.isAbsent() ? empty() : of(function.apply(this.value));
   }
}
