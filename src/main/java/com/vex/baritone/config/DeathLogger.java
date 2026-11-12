package com.vex.baritone.config;

import com.vex.baritone.api.utils.Helper;
import com.vex.baritone.api.utils.IPlayerContext;

/**
 * Prints your death coordinates to chat. Hooked into
 * WaypointBehavior.onPlayerDeath via ASM.
 */
public final class DeathLogger {

    private DeathLogger() {
    }

    public static void report(IPlayerContext ctx) {
        try {
            if (!FeatureConfig.deathCoordinates(ctx)) {
                return;
            }
            var pos = ctx.playerFeet();
            Helper.HELPER.logDirect(String.format("You died at %d, %d, %d", pos.x, pos.y, pos.z));
        } catch (Throwable t) {
            // never let a death-time log break anything
        }
    }
}
