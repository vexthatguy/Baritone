package io.github.itzispyder.clickcrystals.gui.misc.animators;

public final class Animations {
   public static final Animations.AnimationController LINEAR = x -> x;
   public static final Animations.AnimationController UPWARDS_BOUNCE = x -> 1.2 * Math.sin(2.1555 * x);
   public static final Animations.AnimationController UPWARDS_BOUNCE_HEAVY = x -> 2.0 * Math.sin(2.6 * x);
   public static final Animations.AnimationController UPWARDS_BOUNCE_LIGHT = x -> 1.1 * Math.sin(2.0 * x);
   public static final Animations.AnimationController ELASTIC_BOUNCE = x -> 0.8 * Math.sin(5.0 * x - 2.5) + 0.5;
   public static final Animations.AnimationController FADE_IN_AND_OUT = x -> 1.0 / (1.0 + Math.exp(8.0 - 16.0 * x));
   public static final Animations.AnimationController FADE_IN_AND_OUT_SLIGHT = x -> 0.5 + 0.55 * Math.atan(2.55 * (x - 0.5));
   public static final Animations.AnimationController HARMONIC = x -> 0.5 * Math.sin((Math.PI * 2) * (x - 0.25)) + 0.5;
   public static final Animations.AnimationController INTERRUPTED_HARMONIC = x -> 0.3
         * (Math.pow(Math.sin(6.1 * (x + 0.0635)), 2.0) - Math.cos(6.1 * (x + 0.0635)) - Math.sin(6.1 * (x + 0.0635)))
      + 0.35;

   @FunctionalInterface
   public interface AnimationController {
      double f(double var1);
   }
}
