package C21318233;

import ie.tudublin.*;

public class Headsadsathens extends Visual {

    Rain[] d = new Rain[400];
    float lerpedAverage = 0;
    float firstDrop = 25880;
    float secondDrop = 47000;
    float thirdDrop = 57700;

    float scale = 1;
    float xPan = 500;
    float yPan = 500;
    boolean zoomOut = true;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {

        // Start playing audio
        startMinim();
        loadAudio("heathens.mp3");
        getAudioPlayer().play();

        // Initialising rain drops at random x and y values
        for (int i = 0; i < d.length; i++) {
            d[i] = new Rain(this, random(0, width), random(-1000, 0));
        }
    }

    public void draw() {

        // Used for syncing the song to raindrops
        rainSync();

        // First drop of the song (Changes background)
        if (millis() >= firstDrop && millis() <= 46000) {
            background(47, 79, 79);

        } else if (millis() <= firstDrop) {
            background(0);
        }

        // For transition between first and second drop of song
        if (millis() <= secondDrop) {
            // Rain effect
            for (int i = 0; i < d.length; i++) {
                d[i].fall(lerpedAverage);
                d[i].show();
                d[i].bottom();
            }
            bouncingCircle();
        } else if (millis() >= secondDrop && millis() <= 47100) // Transition part
        {
            background(47, 79, 79);
        }

        translate(width / 2, height / 2);
        scale(scale);
        translate(-xPan, -yPan);
        
        // Second drop of the song (Start of rectangle visual)
        if (millis() >= secondDrop) {
            if (zoomOut == true)
            {
                rectVisual();
                scale /= 1.005;
            }
        }

        // Third drop of the song
        if (millis() >= thirdDrop) {
            background(47, 79, 79);

        }

    }

    public void bouncingCircle() {
        float halfheight = height / 2;
        float halfwidth = width / 2;

        // For syncing with song
        calculateAverageAmplitude();

        // Draws circle
        strokeWeight(3);

        // Changes color of circle on the first drop of the song
        if (millis() > 25895) {
            stroke(255);
            fill(47, 79, 79);
        } else {
            stroke(255, 0, 0);
            fill(0);
        }
        circle(halfwidth, halfheight, getSmoothedAmplitude() * 3000);
    }

    // For syncing song to rain
    public void rainSync() {

        float total = 0;
        for (int i = 0; i < getAudioBuffer().size(); i++) {
            total += abs(getAudioBuffer().get(i));
        }
        float average = total / (float) getAudioBuffer().size();
        lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    }

    // Rectangle Visual
    public void rectVisual() {
        stroke(random(50, 255));
        strokeWeight(15);
        rectMode(CENTER);
        rect(width / 2, height / 2, width, height);
    }
}