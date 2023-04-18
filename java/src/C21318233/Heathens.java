package C21318233;

import C21423244.Monitor;
import ie.tudublin.*;

public class Heathens {

    Monitor m;
    
    float lerpedAverage = 0;
    float firstDrop = 25880;
    float secondDrop = 47000;
    float thirdDrop = 57700;

    float scale = 1;
    float xPan = 500;
    float yPan = 500;
    boolean zoomOut = true;


    public Heathens(Monitor m) 
    {
        this.m = m;
    }

    public void render() {

        // exitIcon("Exit", m.width - 120, m.height - 120);
        // exitIconHover();

        // Used for syncing the song to raindrops
        rainSync();

        // First drop of the song (Changes background)
        if (m.millis() >= firstDrop && m.millis() <= 46000) {
            m.background(47, 79, 79);

        } else if (m.millis() <= firstDrop) {
            m.background(0);
        }

        // For transition between first and second drop of song
        if (m.millis() <= secondDrop) {
            // Rain effect
            for (int i = 0; i < m.d.length; i++) {
                m.d[i].fall(lerpedAverage);
                m.d[i].show();
                m.d[i].bottom();
            }
            bouncingCircle();
        } else if (m.millis() >= secondDrop && m.millis() <= 47100) // Transition part
        {
           m.background(47, 79, 79);
        }

        m.translate(m.width / 2, m.height / 2);
        m.scale(scale);
        m.translate(-xPan, -yPan);
        
        // Second drop of the song (Start of rectangle visual)
        if (m.millis() >= secondDrop) {
            if (zoomOut == true)
            {
                rectVisual();
                scale /= 1.005;
            }
        }

        // Third drop of the song
        if (m.millis() >= thirdDrop) {
            m.background(47, 79, 79);

        }
        exitIcon("Exit", m.width - 120, m.height - 120);
        exitIconHover();
    }

    public void bouncingCircle() {
        float halfheight = m.height / 2;
        float halfwidth = m.width / 2;

        // For syncing with song
        m.calculateAverageAmplitude();

        // Draws circle
        m.strokeWeight(3);

        // Changes color of circle on the first drop of the song
        if (m.millis() > 25895) {
            m.stroke(255);
            m.fill(47, 79, 79);
        } else {
            m.stroke(255, 0, 0);
            m.fill(0);
        }
        m.circle(halfwidth, halfheight, m.getSmoothedAmplitude() * 3000);
    }

    // For syncing song to rain
    public void rainSync() {

        float total = 0;
        for (int i = 0; i < m.getAudioBuffer().size(); i++) {
            total += m.abs(m.getAudioBuffer().get(i));
        }
        float average = total / (float) m.getAudioBuffer().size();
        lerpedAverage = m.lerp(lerpedAverage, average, 0.1f);
    }

    // Rectangle Visual
    public void rectVisual() {
        m.stroke(m.random(50, 255));
        m.strokeWeight(15);
        m.rectMode(m.CENTER);
        m.rect(m.width / 2, m.height / 2, m.width, m.height);
    }

    void exitIcon(String text, float x, float y) {
        m.exit = m.loadImage("exit.png");
        m.image(m.exit, x, y);
        m.fill(0, 408, 612);
        m.textSize(25);
        m.text(text, x + 8, y + 77);
      }
    
      void exitIconHover() {
        if (m.mouseX > m.width - 140 && m.mouseX < m.width - 20 && m.mouseY > m.height - 130 && m.mouseY < m.height - 20) {
          m.cursor(Monitor.HAND); // Changes the curser when hovering over the icon
          m.noStroke();
          m.fill(49, 182, 255, 65);
          m.rect(m.width - 92, m.height - 85, 100, 100);
          // Exits the program if EXIT icon is pressed
          if (m.mousePressed) {
            m.song.pause();
            m.minim.stop();
            m.LoadComputer();

          }
        } else {
          m.cursor(Monitor.ARROW); // Default curser everywhere else
        }
      }
}