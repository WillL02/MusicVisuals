package C21379483;

import ie.tudublin.*;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.*;
import processing.core.PImage;
import ddf.minim.Minim;

public class SafeAndSound extends Visual {

  Minim minim;
  AudioPlayer song;
  AudioBuffer ab;
  FFT fft;

  PImage windowsxp;
  PImage windowsxp2;
  PImage exit;
  PImage[] mice = new PImage[5];
  Mouse[] mouses = new Mouse[12];

  int mouseNum = 1;
  int maxMice = 12;

  float angle, lerpedAverage = 0;

  public void settings() {
    size(1920, 1280, P2D);
  }

  public void setup() {
    colorMode(HSB);
    background(0);

    minim = new Minim(this);
    song = minim.loadFile("song.mp3", 1024);
    windowsxp = loadImage("windowsxp.png");
    windowsxp2 = loadImage("windowsxp2.png");
    song.play();
    ab = song.mix;
    fft = new FFT(song.bufferSize(), song.sampleRate());

    smooth();

    rectMode(CENTER);

    // Load mouse curser images
    for (int i = 0; i < mice.length; i++) {
      int y = i + 1;
      mice[i] = loadImage("mouse" + y + ".png");
    }

    for (int i = 0; i < mouses.length; i++) {
      int index = (int) (random(0, mice.length));
      mouses[i] = new Mouse(mice[index], random(85, 130), this);
    }
  }

  public void draw() {
    if (frameCount < 245) {
      startLoad();
    } else {
      loadPicture();
      exitIcon("Exit", width - 120, height - 120);
      exitIconHover();

      if (song.isPlaying()) {
        pushMatrix();
        mouseFunct();
        popMatrix();
      }

      dancingCircle();

    }
    if (frameCount > 245 && song.isPlaying()) {
      dancingRectangle();
    }
  }

  void startLoad() {
    // image(windowsxp,0,0);
    for (int i = 0; i < 10000; i++) {
      float x = random(width);
      float y = random(height);
      int c = windowsxp.get((int) x, (int) y);
      fill(c, 25);
      noStroke();
      ellipse(x, y, 6, 6);
    }
  }

  void loadPicture() {
    image(windowsxp2, 0, 0);
  }

  void mouseFunct() {
    float total = 0;
    for (int i = 0; i < ab.size(); i++) {
      total += abs(ab.get(i));
    }
    float average = total / (float) ab.size();
    lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    // Mouse images functionality added
    for (int i = 0; i < mouseNum; i++) {
      mouses[i].move(lerpedAverage);
      mouses[i].rotates(lerpedAverage);

      mouses[i].display();

      mouses[i].side();
    }

    if (frameCount % 100 == 0 && mouseNum < maxMice) {
      mouseNum++;
    }
  }

  void dancingRectangle() {
    for (int y = 0; y < 100; y++) {
      stroke(255 - y % 10);
      strokeWeight(10);
      fill(255 - y * 10, 255 - y, 255 - y);
      scale(0.95f);
      rotate(radians(angle));
      rect(0, 0, 580, 580);
    }
    float total = 0;
    for (int i = 0; i < ab.size(); i++) {
      total += abs(ab.get(i));
    }
    float average = total / (float) ab.size();
    lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    angle += map(lerpedAverage, 0, 1.0f, 0, 1.5f);
  }

  void dancingCircle() {
    int x = 0; // Circle spin
    int radius = 400; // Radius in pixels of the circle
    // Circle starts here

    translate(width / 2, height / 2);
    // Audio Visualization
    fft.forward(song.mix);
    float bands = fft.specSize();

    for (int i = 0; i < bands * 2; i++) {

      // Starting positions of line
      float start_x = radius * cos(PI * (i + x) / bands);
      float start_y = radius * sin(PI * (i + x) / bands);

      // Draw line based on sound
      float c = map(i, 0, ab.size(), 0, 255);
      stroke(c, 255, 255);
      strokeWeight(5);
      if (i < bands) {
        // Line based on band length
        line(start_x, start_y, start_x + fft.getBand(i) * 7 * cos(PI * (i + x) / bands),
            start_y + fft.getBand(i) * 7 * sin(PI * (i + x) / bands));
      } else {
        // Line based on frequency
        line(start_x, start_y, start_x + fft.getFreq(i) * 5 * cos(PI * (i + x) / bands),
            start_y + fft.getFreq(i) * 5 * sin(PI * (i + x) / bands));
      }
    }
  }

  void exitIcon(String text, float x, float y) {
    exit = loadImage("exit.png");
    image(exit, x, y);
    fill(0, 408, 612);
    textSize(25);
    text(text, x + 8, y + 77);
  }

  void exitIconHover() {
    if (mouseX > width - 140 && mouseX < width - 20 && mouseY > height - 130 && mouseY < height - 20) {
      cursor(HAND);
      noStroke();
      fill(49, 182, 255, 65);
      rect(width - 92, height - 85, 100, 100);

      if (mousePressed) {
        song.pause();
        minim.stop();
        exit();
      }
    } else {
      cursor(ARROW);
    }

    for (int i = 0; i < maxMice; i++) {
      float mouseXPos = mouses[i].getX();
      float mouseYPos = mouses[i].getY();
      float mouseSize = mouses[i].getSize();

      if (mouseXPos + mouseSize > width - 140 && mouseXPos + mouseSize < width - 20
          && mouseYPos + mouseSize > height - 130 && mouseYPos + mouseSize < height - 20) {
        noStroke();
        fill(49, 182, 255, 65);
        rect(width - 92, height - 85, 100, 100);
      }
    }
  }
}
