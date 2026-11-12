package io.github.itzispyder.clickcrystals.gui;

public interface Positionable {
   int getX();

   void setX(int var1);

   int getY();

   void setY(int var1);

   int getWidth();

   void setWidth(int var1);

   int getHeight();

   void setHeight(int var1);

   default Positionable.Dimension getDimensions() {
      return new Positionable.Dimension(this.getX(), this.getY(), this.getWidth(), this.getHeight());
   }

   default void setDimensions(Positionable.Dimension dim) {
      this.setX(dim.x);
      this.setY(dim.y);
      this.setWidth(dim.width);
      this.setHeight(dim.height);
   }

   default Positionable.Dimension getCenter() {
      return new Positionable.Dimension(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, 0, 0);
   }

   class Dimension {
      public int x;
      public int y;
      public int width;
      public int height;

      public Dimension() {
         this(0, 0, 0, 0);
      }

      public Dimension(int x, int y, int width, int height) {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
      }

      public int getX() {
         return this.x;
      }

      public Positionable.Dimension setX(int x) {
         this.x = x;
         return this;
      }

      public int getY() {
         return this.y;
      }

      public Positionable.Dimension setY(int y) {
         this.y = y;
         return this;
      }

      public int getWidth() {
         return this.width;
      }

      public Positionable.Dimension setWidth(int width) {
         this.width = width;
         return this;
      }

      public int getHeight() {
         return this.height;
      }

      public Positionable.Dimension setHeight(int height) {
         this.height = height;
         return this;
      }
   }
}
