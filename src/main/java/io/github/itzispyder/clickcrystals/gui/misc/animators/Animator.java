package io.github.itzispyder.clickcrystals.gui.misc.animators;

import io.github.itzispyder.clickcrystals.gui.misc.Color;
import io.github.itzispyder.clickcrystals.util.MathUtils;

public class Animator {
   private long start = System.currentTimeMillis();
   private long length;
   private boolean reversed;
   private Animations.AnimationController animationController;

   public Animator(long length, Animations.AnimationController animationController) {
      this.length = length;
      this.reversed = false;
      this.animationController = animationController;
   }

   public Animator(long length) {
      this(length, Animations.LINEAR);
   }

   private double getAnimation(double x) {
      return !(x <= 0.0) && !(x >= 1.0) ? this.animationController.f(x) : x;
   }

   public double getAnimation() {
      return this.getAnimation(this.getProgressClamped());
   }

   public double getAnimationReversed() {
      return this.getAnimation(this.getProgressClampedReversed());
   }

   public Animations.AnimationController getAnimationController() {
      return this.animationController;
   }

   public void setAnimationController(Animations.AnimationController animationController) {
      this.animationController = animationController;
   }

   public double getProgress() {
      long pass = System.currentTimeMillis() - this.start;
      double rat = (double)pass / this.length;
      return this.reversed ? 1.0 - rat : rat;
   }

   public double getProgressClamped() {
      return MathUtils.clamp(this.getProgress(), 0.0, 1.0);
   }

   public double getProgressReversed() {
      return 1.0 - this.getProgress();
   }

   public double getProgressClampedReversed() {
      return MathUtils.clamp(this.getProgressReversed(), 0.0, 1.0);
   }

   public boolean isFinished() {
      double p = this.getProgress();
      return this.reversed ? p <= 0.0 : p >= 1.0;
   }

   public void reverse() {
      this.reversed = !this.reversed;
   }

   public void setReversed(boolean reversed) {
      this.reversed = reversed;
   }

   public boolean isReversed() {
      return this.reversed;
   }

   public void reset(long length) {
      this.start = System.currentTimeMillis();
      this.length = length;
   }

   public void reset() {
      this.start = System.currentTimeMillis();
   }

   public static int transformColorOpacity(Animator animator, int hex) {
      return new Color(hex).getHexCustomAlpha(animator.getProgressClamped());
   }

   public long getLength() {
      return this.length;
   }
}
