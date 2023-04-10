package C21318233;

import ie.tudublin.*;

public class Heathens extends Visual {
    Rain[] d = new Rain[200];
    float lerpedAverage = 0;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {

        startMinim();
        loadAudio("heathens.mp3");
        getAudioPlayer().play();

        //Initialising rain drops at random y values
        for (int i = 0; i < d.length; i++) {
            d[i] = new Rain(this, random(0, width), random(-1000, 0));
        }
    }

    public void draw() {
        //Used for syncing song to raindrops
        calculation();

        background(0);
        
        //Rain functions
        for (int i = 0; i < d.length; i++) {
            d[i].fall(lerpedAverage);
            d[i].show();
            d[i].bottom();
        }
        
        circleGroup();

    }

    public void circleGroup() {
        calculateAverageAmplitude();
        float halfheight = height / 2;
        float halfwidth = width / 2;

        stroke(255, 0, 0);
        strokeWeight(3);
        fill(0);
        circle(halfwidth, halfheight, getSmoothedAmplitude() * 2000);
    }

    
    public void calculation() {
        float total = 0;
        for (int i = 0; i < getAudioBuffer().size(); i++) {
            total += abs(getAudioBuffer().get(i));
        }
        float average = total / (float) getAudioBuffer().size();
        lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    }
}