package C21318233;

import ie.tudublin.*;

public class Heathens extends Visual {

    Rain[] d = new Rain[400];
    float lerpedAverage = 0;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {

        //Start playing audio
        startMinim();
        loadAudio("heathens.mp3");
        getAudioPlayer().play();

        //Initialising rain drops at random x and y values
        for (int i = 0; i < d.length; i++) {
            d[i] = new Rain(this, random(0, width), random(-1000, 0));
        }
    }

    public void draw() {

        //Used for syncing the song to raindrops
        rainSync();

        //First drop of the song (Changes background)
        if (millis() > 25895)
        {
            background(47,79,79);
        }
        else
        {
            background(0);
        }
        
        //Rain effect
        for (int i = 0; i < d.length; i++) {
            d[i].fall(lerpedAverage);
            d[i].show();
            d[i].bottom();
        }
        
        bouncingCircle();

        //Second drop of the song (Start of line visual)
        if (millis() > 47000)
        {
            stroke(255,0,0);
            line(0,0, 500, 500); //For testing purposes
        }

    }

    public void bouncingCircle() {
        float halfheight = height / 2;
        float halfwidth = width / 2;
        
        //For syncing with song
        calculateAverageAmplitude();

        //Draws circle
        strokeWeight(3);

        //Changes color of circle on the first drop of the song
        if (millis() > 25895)
        {
            stroke(255);
            fill(47,79,79);
        }
        else
        {
            stroke(255,0,0);
            fill(0);
            
        }

        circle(halfwidth, halfheight, getSmoothedAmplitude() * 2000);
    }

    
    public void rainSync() {
        //For syncing song
        float total = 0;
        for (int i = 0; i < getAudioBuffer().size(); i++) 
        {
            total += abs(getAudioBuffer().get(i));
        }
        float average = total / (float) getAudioBuffer().size();
        lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    }

    public void lineVisual() {
        
    }
}