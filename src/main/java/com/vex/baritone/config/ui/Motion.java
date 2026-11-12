package com.vex.baritone.config.ui;

/**
 * Small frame-rate independent smoothing helpers. Everything the UI animates
 * goes through here so motion feels consistent regardless of FPS: exponential
 * approach for values that should settle, easing curves for one-shot moments.
 */
public final class Motion {
    private Motion() {
    }

    /** Frame delta in seconds, clamped so a hitch can never jump the UI. */
    public static double delta(long lastFrameNanos) {
        if (lastFrameNanos <= 0L) {
            return 0.016;
        }
        double dt = (System.nanoTime() - lastFrameNanos) / 1_000_000_000.0;
        return Math.max(0.0, Math.min(0.05, dt));
    }

    /**
     * Exponential approach towards {@code target}. {@code speed} is roughly
     * "how fast it converges": 14 is snappy, 8 is soft.
     */
    public static double approach(double current, double target, double speed, double dt) {
        if (dt <= 0.0) {
            return current;
        }
        double factor = 1.0 - Math.exp(-speed * dt);
        double next = current + (target - current) * factor;
        return Math.abs(target - next) < 0.0005 ? target : next;
    }

    public static double easeOutCubic(double t) {
        double p = clamp01(t);
        return 1.0 - Math.pow(1.0 - p, 3.0);
    }

    public static double easeInOutCubic(double t) {
        double p = clamp01(t);
        return p < 0.5 ? 4.0 * p * p * p : 1.0 - Math.pow(-2.0 * p + 2.0, 3.0) / 2.0;
    }

    public static double clamp01(double value) {
        return value < 0.0 ? 0.0 : Math.min(value, 1.0);
    }

    public static double clamp(double value, double min, double max) {
        return value < min ? min : Math.min(value, max);
    }

    public static int round(double value) {
        return (int) Math.round(value);
    }
}
