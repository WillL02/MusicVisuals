package C21379483;

import processing.core.PApplet;
import processing.core.PImage;


public class Mouse extends PApplet{
   float x, y, size, xspeed, rot;
   
   PImage mouse;
   
   Mouse(PImage tempIMG, float tempD) {
      x = 0;
      rot = 0;
      // Randomly choose between two ranges for y
      float yRange = random(2) < 0.5 ? random(0, 300) : random(height-300, height-100);
      y = yRange;
      
      size = tempD;
      mouse = tempIMG;
   }
   
   void move(float lerpedAverage) {
      x += map(lerpedAverage, 0, 1.0f, 0, 20.0f);
      y = y + random(-2, 2);
   }
   
   void display() {
      pushMatrix();
      stroke(0);
      translate(x + size / 2, y + size / 2); // move to the center of the image
      rotate(radians(rot)); // rotate the image
      image(mouse, -size / 2, -size / 2, size, size); // draw the image centered at (0, 0)
      popMatrix();
   }
   
   void side() {
      if (x > width) {
         // Create a new instance of Mouse with new random values
         float newSize = random(75, 100); // generate a new random size
         Mouse newMouse = new Mouse(mouse, newSize); // create a new mouse with random size and position
         x = -newSize; // reset the x position of the current mouse
         y = newMouse.y; // set the y position of the current mouse to match the new mouse
         size = newSize; // set the size of the current mouse to match the new mouse
      }
   }
   
   void rotates(float lerpedAverage) {
      rot += map(lerpedAverage, 0, 1.0f, 0, 20.0f);
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
