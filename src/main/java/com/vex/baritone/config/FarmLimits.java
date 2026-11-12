package com.vex.baritone.config;

/**
 * Hard limits for the farm process.
 */
public final class FarmLimits {

    public static final int MAX_FARM_CHUNKS = 12;
    public static final int MAX_FARM_RANGE_BLOCKS = 192;

    private FarmLimits() {
    }

    public static int clampRange(int range) {
        return Math.min(range, MAX_FARM_RANGE_BLOCKS);
    }
}
