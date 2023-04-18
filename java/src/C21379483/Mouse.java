package C21379483;

import C21423244.Monitor;
import processing.core.PImage;

public class Mouse {

   private float x, y, size, rot;

   private PImage mouse;
   private Monitor m;

   public Mouse(PImage tempIMG, float tempD, Monitor m) {
      this.m = m;
      x = 0;
      rot = 0;
      // Randomly choose between two ranges for y
      float yRange = m.random(2) < 0.5 ? m.random(0, 300)
            : m.random(m.height - 300, m.height - 100);
      y = yRange;

      size = tempD;
      mouse = tempIMG;
   }

   public void move(float lerpedAverage) {
      x += Monitor.map(lerpedAverage, 0, 1.0f, 0, 20.0f);
      y = y + m.random(-2, 2);
   }

   public void display() {
      m.pushMatrix();
      m.stroke(0);
      m.translate(x + size / 2, y + size / 2); // move to the center of the image
      m.rotate(Monitor.radians(rot)); // rotate the image
      m.image(mouse, -size / 2, -size / 2, size, size); // draw the image centered at (0, 0)
      m.popMatrix();
   }

   public void side() {
      if (x > m.width) {
         // Create a new instance of Mouse with new random values
         float newSize = m.random(75, 100); // generate a new random size
         Mouse newMouse = new Mouse(mouse, newSize, m); // create a new mouse with random size and position
         x = -newSize; // reset the x position of the current mouse
         y = newMouse.y; // set the y position of the current mouse to match the new mouse
         size = newSize; // set the size of the current mouse to match the new mouse
      }
   }

   public void rotates(float lerpedAverage) {
      rot += Monitor.map(lerpedAverage, 0, 1.0f, 0, 20.0f);
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
