package C21379483;

import processing.core.PImage;

public class Mouse {

   private SafeAndSound safeAndSound;

   private float x, y, size, rot;

   private PImage mouse;

   public Mouse(PImage tempIMG, float tempD, SafeAndSound safeAndSound) {
      this.safeAndSound = safeAndSound;
      x = 0;
      rot = 0;
      // Randomly choose between two ranges for y
      float yRange = safeAndSound.random(2) < 0.5 ? safeAndSound.random(0, 300)
            : safeAndSound.random(safeAndSound.height - 300, safeAndSound.height - 100);
      y = yRange;

      size = tempD;
      mouse = tempIMG;
   }

   public void move(float lerpedAverage) {
      x += SafeAndSound.map(lerpedAverage, 0, 1.0f, 0, 20.0f);
      y = y + safeAndSound.random(-2, 2);
   }

   public void display() {
      safeAndSound.pushMatrix();
      safeAndSound.stroke(0);
      safeAndSound.translate(x + size / 2, y + size / 2); // move to the center of the image
      safeAndSound.rotate(SafeAndSound.radians(rot)); // rotate the image
      safeAndSound.image(mouse, -size / 2, -size / 2, size, size); // draw the image centered at (0, 0)
      safeAndSound.popMatrix();
   }

   public void side() {
      if (x > safeAndSound.width) {
         // Create a new instance of Mouse with new random values
         float newSize = safeAndSound.random(75, 100); // generate a new random size
         Mouse newMouse = new Mouse(mouse, newSize, safeAndSound); // create a new mouse with random size and position
         x = -newSize; // reset the x position of the current mouse
         y = newMouse.y; // set the y position of the current mouse to match the new mouse
         size = newSize; // set the size of the current mouse to match the new mouse
      }
   }

   public void rotates(float lerpedAverage) {
      rot += SafeAndSound.map(lerpedAverage, 0, 1.0f, 0, 20.0f);
   }

   public float getX() {
      return x;
   }

   public float getY() {
      return y;
   }

   public float getSize() {
      return size;
   }
}
