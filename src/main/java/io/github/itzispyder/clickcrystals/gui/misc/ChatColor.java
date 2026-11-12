package io.github.itzispyder.clickcrystals.gui.misc;

public enum ChatColor {
   BLACK("§0", -16777216),
   DARK_BLUE("§1", -16777046),
   DARK_GREEN("§2", -16733696),
   DARK_AQUA("§3", -16733526),
   DARK_RED("§4", -5636096),
   DARK_PURPLE("§5", -5635926),
   ORANGE("§6", -22016),
   GRAY("§7", -5592406),
   DARK_GRAY("§8", -11184811),
   BLUE("§9", -11184641),
   GREEN("§a", -11141291),
   AQUA("§b", -11141121),
   RED("§c", -43691),
   PURPLE("§d", -43521),
   YELLOW("§e", -171),
   WHITE("§f", -1),
   OBFUSCATED("§k", 0),
   BOLD("§l", 0),
   STRIKETHROUGH("§m", 0),
   UNDERLINE("§n", 0),
   ITALIC("§o", 0),
   RESET("§r", 0);

   private final String mcColor;
   private final int hex;

   ChatColor(String mcColor, int hex) {
      this.mcColor = mcColor;
      this.hex = hex;
   }

   public String getName() {
      return this.name().toLowerCase();
   }

   public int getHex() {
      return this.hex;
   }

   @Override
   public String toString() {
      return this.mcColor;
   }
}
