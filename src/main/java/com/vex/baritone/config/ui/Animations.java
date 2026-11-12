package com.vex.baritone.config.ui;

/**
 * Ported from ClickCrystals (Animations.java) - ease functions.
 */
public final class Animations {
    public static final Ease LINEAR = p -> p;
    public static final Ease EASE_IN = p -> p * p;
    public static final Ease EASE_OUT = p -> 1 - (1 - p) * (1 - p);
    public static final Ease EASE_IN_OUT = p -> p < 0.5 ? 2 * p * p : 1 - Math.pow(-2 * p + 2, 2) / 2;
    public static final Ease UPWARDS_BOUNCE = p -> (float) (Math.cos(4.5 * Math.PI * p) * Math.pow(1 - p, 2) * 0.4 + 0.6);
    public static final Ease ELASTIC_BOUNCE = p -> {
        double c4 = (2 * Math.PI) / 3;
        return p == 0 || p == 1 ? p : Math.pow(2, -10 * p) * Math.sin((p * 10 - 0.75) * c4) + 1;
    };

    private Animations() {
    }

    @FunctionalInterface
    public interface Ease {
        double apply(double p);
    }
}
