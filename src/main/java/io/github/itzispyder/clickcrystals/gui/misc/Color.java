package io.github.itzispyder.clickcrystals.gui.misc;

import io.github.itzispyder.clickcrystals.util.MathUtils;

public class Color {
   public static final Color WHITE = new Color(-1);
   public static final Color LIGHT_GRAY = new Color(-4144960);
   public static final Color GRAY = new Color(-8355712);
   public static final Color DARK_GRAY = new Color(-12566464);
   public static final Color BLACK = new Color(-16777216);
   public static final Color NONE = new Color(0);
   public static final Color BROWN = new Color(-8367872);
   public static final Color RED = new Color(-65536);
   public static final Color ORANGE = new Color(-32768);
   public static final Color YELLOW = new Color(-256);
   public static final Color GREEN = new Color(-16744448);
   public static final Color LIME = new Color(-8323328);
   public static final Color BLUE = new Color(-16776961);
   public static final Color AQUA = new Color(-16723713);
   public static final Color MAGENTA = new Color(-2096897);
   public static final Color PURPLE = new Color(-7921665);
   private final int r;
   private final int g;
   private final int b;
   private final int a;
   private final int hex;

   public static Color parse(String color) {
      color = color.trim().toLowerCase();
      Color result = BLACK;
      if (color.isEmpty()) {
         return result;
      }

      if (color.startsWith("#")) {
         color = color.substring(1);
         int len = color.length();
         if (len != 6 && len != 8) {
            return result;
         }

         int hex = Integer.parseUnsignedInt(color, 16);
         int a = len == 8 ? hex >> 24 & 0xFF : 255;
         int r = hex >> 16 & 0xFF;
         int g = hex >> 8 & 0xFF;
         int b = hex & 0xFF;
         return new Color(a, r, g, b);
      } else {
         switch (color) {
            case "white":
               result = WHITE;
               break;
            case "light_gray":
            case "light-gray":
               result = LIGHT_GRAY;
               break;
            case "gray":
               result = GRAY;
               break;
            case "dark_gray":
            case "dark-gray":
               result = DARK_GRAY;
               break;
            case "brown":
               result = BROWN;
               break;
            case "red":
               result = RED;
               break;
            case "orange":
               result = ORANGE;
               break;
            case "yellow":
               result = YELLOW;
               break;
            case "green":
               result = GREEN;
               break;
            case "lime":
               result = LIME;
               break;
            case "blue":
               result = BLUE;
               break;
            case "aqua":
               result = AQUA;
               break;
            case "magenta":
               result = MAGENTA;
               break;
            case "purple":
               result = PURPLE;
               break;
            case "none":
               result = NONE;
         }

         return result;
      }
   }

   private Color(int hex, int a, int r, int g, int b) {
      this.hex = hex;
      this.a = MathUtils.clamp(a, 0, 255);
      this.r = MathUtils.clamp(r, 0, 255);
      this.g = MathUtils.clamp(g, 0, 255);
      this.b = MathUtils.clamp(b, 0, 255);
   }

   public Color(int hex) {
      this(hex, hex >> 24 & 0xFF, hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF);
   }

   public Color() {
      this(0);
   }

   public Color(int a, int r, int g, int b) {
      this.a = MathUtils.clamp(a, 0, 255);
      this.r = MathUtils.clamp(r, 0, 255);
      this.g = MathUtils.clamp(g, 0, 255);
      this.b = MathUtils.clamp(b, 0, 255);
      this.hex = this.a << 24 | this.r << 16 | this.g << 8 | this.b;
   }

   public Color(float a, float r, float g, float b) {
      this((int)(a * 255.0F), (int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F));
   }

   public Color(double a, double r, double g, double b) {
      this((float)a, (float)r, (float)g, (float)b);
   }

   public Color(java.awt.Color awtColor) {
      this(awtColor.getAlpha(), awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
   }

   public int getHex() {
      return this.hex;
   }

   public int getHexOpaque() {
      return 0xFF000000 | this.r << 16 | this.g << 8 | this.b;
   }

   public int getHexCustomAlpha(int alpha) {
      return alpha << 24 | this.r << 16 | this.g << 8 | this.b;
   }

   public int getHexCustomAlpha(float alpha) {
      return this.getHexCustomAlpha((int)(alpha * 255.0F));
   }

   public int getHexCustomAlpha(double alpha) {
      return this.getHexCustomAlpha((int)(alpha * 255.0));
   }

   public Color withAlpha(int a) {
      return new Color(a, this.r, this.g, this.b);
   }

   public Color withRed(int r) {
      return new Color(this.a, r, this.g, this.b);
   }

   public Color withBlue(int g) {
      return new Color(this.a, this.r, g, this.b);
   }

   public Color withGreen(int b) {
      return new Color(this.a, this.r, this.g, b);
   }

   public int getAlpha() {
      return this.a;
   }

   public int getRed() {
      return this.r;
   }

   public int getGreen() {
      return this.g;
   }

   public int getBlue() {
      return this.b;
   }

   public float getAlphaF() {
      return this.a / 255.0F;
   }

   public float getRedF() {
      return this.r / 255.0F;
   }

   public float getGreenF() {
      return this.g / 255.0F;
   }

   public float getBlueF() {
      return this.b / 255.0F;
   }

   public Color lerp(Color color, float delta) {
      delta = (float)MathUtils.clamp(delta, 0.0, 1.0);
      float iDelta = 1.0F - delta;
      int a = (int)(this.a * delta + color.a * iDelta);
      int r = (int)(this.r * delta + color.r * iDelta);
      int g = (int)(this.g * delta + color.g * iDelta);
      int b = (int)(this.b * delta + color.b * iDelta);
      return new Color(a, r, g, b);
   }

   public Color brighter() {
      return new Color(this.a, this.r + 20, this.g + 20, this.b + 20);
   }

   public Color brighter(int step) {
      return new Color(this.a, this.r + 20 * step, this.g + 20 * step, this.b + 20 * step);
   }

   public Color darker() {
      return new Color(this.a, this.r - 20, this.g - 20, this.b - 20);
   }

   public Color darker(int step) {
      return new Color(this.a, this.r - 20 * step, this.g - 20 * step, this.b - 20 * step);
   }

   public java.awt.Color toAwtColor() {
      return new java.awt.Color(this.r, this.g, this.b, this.a);
   }

   @Override
   public String toString() {
      return "#" + Integer.toHexString(this.hex).toUpperCase();
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof Color c) ? false : c.a == this.a && c.r == this.r && c.g == this.g && c.b == this.b && c.hex == this.hex;
   }

   public static int lerp(int colorFrom, int colorTo, double delta) {
      double iDelta = 1.0 - delta;
      int af = colorFrom >> 24 & 0xFF;
      int rf = colorFrom >> 16 & 0xFF;
      int gf = colorFrom >> 8 & 0xFF;
      int bf = colorFrom & 0xFF;
      int at = colorTo >> 24 & 0xFF;
      int rt = colorTo >> 16 & 0xFF;
      int gt = colorTo >> 8 & 0xFF;
      int bt = colorTo & 0xFF;
      int a = (int)(af * iDelta + at * delta);
      int r = (int)(rf * iDelta + rt * delta);
      int g = (int)(gf * iDelta + gt * delta);
      int b = (int)(bf * iDelta + bt * delta);
      return a << 24 | r << 16 | g << 8 | b;
   }
}
