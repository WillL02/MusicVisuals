package C21379483;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.*;
import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.Minim;

public class SafeAndSound extends PApplet {

    PImage windowsxp;

    Minim minim;
    AudioPlayer song;
    AudioBuffer ab;
    FFT fft;

    int x; // Circle spin
    int radius = 400; // Radius in pixels of the circle

    float angle;

    public void setup() {
        size(1920, 1280, P3D);
        // fullScreen(P3D);
        windowsxp = loadImage("windowsxp.png");

        colorMode(HSB);
        background(0);

        minim = new Minim(this);

        song = minim.loadFile("song.mp3", 1024);

        song.play();

        ab = song.mix;

        fft = new FFT(song.bufferSize(), song.sampleRate());

        x = 0;

        smooth();

        rectMode(CENTER);
    }

    public void draw() {
        if (frameCount < 300) { // Should be 300
            startLoad();
        } else {
            loadPicture();

            // White circle starts here

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
        if (frameCount > 500) {
            for (int y = 0; y < 100; y++) {
                stroke(255 - y % 10);
                strokeWeight(10);
                fill(255 - y * 10, 255 - y, 255 - y);
                scale(0.95);
                rotate(radians(angle));
                rect(0, 0, 580, 580);
            }
            float total = 0;
            for (int i = 0; i < ab.size(); i++) {
                total += abs(ab.get(i));
            }
            float average = total / (float) ab.size();
            float lerpedAverage = 0;
            lerpedAverage = lerp(lerpedAverage, average, 0.1f);
            angle += map(lerpedAverage, 0, 1.0f, 0, 5.5f);
        }
    }

void startLoad() {
  //image(windowsxp,0,0);
  for (int i = 0; i < 10000; i++) {
    float x = random(width);
    float y = random(height);
    color c = windowsxp.get(int(x), int(y));
    fill(c, 25);
    noStroke();
    ellipse(x, y, 6, 6);
  }
}

    void loadPicture() {
        image(windowsxp, 0, 0);
    }
}
