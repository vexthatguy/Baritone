package io.github.itzispyder.clickcrystals.modules;

public interface Toggleable {
   void setEnabled(boolean var1);

   boolean isEnabled();

   default void toggle() {
      this.setEnabled(!this.isEnabled());
   }
}
