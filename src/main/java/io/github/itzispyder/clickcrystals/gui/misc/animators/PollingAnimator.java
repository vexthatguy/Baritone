package io.github.itzispyder.clickcrystals.gui.misc.animators;

import java.util.function.BooleanSupplier;

public class PollingAnimator extends Animator {
   private final BooleanSupplier poll;
   private boolean pollSuccess;

   public PollingAnimator(int length, BooleanSupplier poll, Animations.AnimationController animationController) {
      super(length, animationController);
      this.poll = poll;
      boolean bool = poll.getAsBoolean();
      this.pollSuccess = bool;
      this.setReversed(!bool);
   }

   public PollingAnimator(int length, BooleanSupplier poll) {
      this(length, poll, Animations.LINEAR);
   }

   @Override
   public double getProgress() {
      this.poll();
      return super.getProgress();
   }

   public void poll() {
      if (this.poll.getAsBoolean() && !this.pollSuccess) {
         this.pollSuccess = true;
         this.setReversed(false);
         this.reset();
      } else if (!this.poll.getAsBoolean() && this.pollSuccess) {
         this.pollSuccess = false;
         this.setReversed(true);
         this.reset();
      }
   }
}
