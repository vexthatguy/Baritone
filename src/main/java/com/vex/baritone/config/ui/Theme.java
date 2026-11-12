package com.vex.baritone.config.ui;

/**
 * Flat, dense palette in the spirit of utilitarian mod UIs: near-black
 * surfaces, one-pixel borders, a single blue accent. No gradients or glow.
 */
public final class Theme {
    private Theme() {
    }

    /** Dimmed backdrop behind the window. */
    public static final int OVERLAY = 0xC2000000;
    /** Window surface. */
    public static final int PANEL = 0xFF0E0F12;
    public static final int PANEL_BORDER = 0xFF2A2D34;
    /** Title strip. */
    public static final int HEADER = 0xFF121317;
    /** Inset surfaces (unused by the flat layout, kept for notices). */
    public static final int SURFACE = 0xFF0B0C0F;
    public static final int SURFACE_BORDER = 0xFF24272E;
    /** Rows. */
    public static final int ROW_HOVER = 0xFF17191E;
    public static final int ROW_SEPARATOR = 0xFF1F2228;
    /** Navigation. */
    public static final int NAV_HOVER = 0xFF14161A;
    public static final int NAV_SELECTED = 0xFF1A1D24;
    /** Accent. */
    public static final int ACCENT = 0xFF4A9EFF;
    public static final int ACCENT_SOFT = 0x334A9EFF;
    public static final int ACCENT_DEEP = 0xFF2E6FBF;
    /** Status. */
    public static final int GOOD = 0xFF57D38C;
    public static final int WARN = 0xFFE3A64A;
    /** Typography. */
    public static final int TEXT = 0xFFE8EAEE;
    public static final int TEXT_DIM = 0xFF9BA2AE;
    public static final int TEXT_FAINT = 0xFF6A7280;
    /** Toggle. */
    public static final int TRACK_OFF = 0xFF30343C;
    public static final int KNOB = 0xFFFFFFFF;
    /** Chips / logo backing. */
    public static final int CHIP = 0xFF181B21;
}
