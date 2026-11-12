package com.vex.baritone.config.ui;

/**
 * Ported from ClickCrystals (Color.java), trimmed to what the ported UI uses.
 */
public class Color {
    public final int r;
    public final int g;
    public final int b;
    public final int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int hex) {
        this((hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF, (hex >> 24) & 0xFF);
    }

    public Color(int hex, int alpha) {
        this((hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF, alpha);
    }

    public int getHex() {
        return (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
    }

    public int getHexCustomAlpha(double alpha) {
        return ((int) (alpha * 255) << 24) | (this.r << 16) | (this.g << 8) | this.b;
    }

    public Color darker(int factor) {
        return new Color(this.r / factor, this.g / factor, this.b / factor, this.a);
    }

    public static int lerp(int start, int end, double p) {
        Color startC = new Color(start);
        Color endC = new Color(end);
        int r = (int) (startC.r + (endC.r - startC.r) * p);
        int g = (int) (startC.g + (endC.g - startC.g) * p);
        int b = (int) (startC.b + (endC.b - startC.b) * p);
        int a = (int) (startC.a + (endC.a - startC.a) * p);
        return new Color(r, g, b, a).getHex();
    }
}
