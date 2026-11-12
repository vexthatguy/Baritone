package com.vex.baritone.config.ui;

import java.util.function.Supplier;

/**
 * Ported from ClickCrystals (PollingAnimator.java), time-based, no scheduler.
 */
public class PollingAnimator implements Animator {
    private final long lengthMs;
    private final Supplier<Boolean> stateSupplier;
    private final Animations.Ease ease;
    private long startTime = Long.MIN_VALUE;
    private boolean last;

    public PollingAnimator(long lengthMs, Supplier<Boolean> stateSupplier, Animations.Ease ease) {
        this.lengthMs = lengthMs;
        this.stateSupplier = stateSupplier;
        this.ease = ease;
        this.last = stateSupplier.get();
    }

    @Override
    public double getAnimation() {
        boolean current = this.stateSupplier.get();
        if (current != this.last) {
            this.last = current;
            this.startTime = System.currentTimeMillis();
        }
        if (this.startTime == Long.MIN_VALUE) {
            return this.last ? 1.0 : 0.0;
        }
        long elapsed = System.currentTimeMillis() - this.startTime;
        double raw = Math.min(1.0, Math.max(0.0, (double) elapsed / this.lengthMs));
        double eased = this.ease.apply(raw);
        return this.last ? eased : 1.0 - eased;
    }
}
