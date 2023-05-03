package C21318233;

import C21423244.Monitor;
import ie.tudublin.*;

public class Heathens {

    Monitor m;
    
    float lerpedAverage = 0;
    
    // float firstDrop = 1553;
    // float secondDrop = 2820;
    // float thirdDrop = 3462;
    float firstDrop = 1474;
    float secondDrop = 2746;
    float thirdDrop = 3391;
    float fourthDrop = 4195;
    float fifthDrop = 6916;
    float sixthDrop = 7240;

    float scale;
    boolean zoomOut;
    float backgroundCol;
    boolean reverse;


    public Heathens(Monitor m) 
    {
        this.m = m;
        this.scale = 1;
        zoomOut = true;
        backgroundCol = 0;
        reverse = false;
    }

    public void render() {

        System.out.println(m.frameCount);

        //Used for syncing the song to raindrops
        rainSync();

        
        // First drop of the song (Changes background)
        if (m.frameCount >= firstDrop && m.frameCount <= secondDrop) {
            m.background(47, 79, 79);

        } else if (m.frameCount <= firstDrop) {
            m.background(0);
        }

        // For transition between first and second drop of song
        if (m.frameCount <= secondDrop) {
            // Rain effect
            for (int i = 0; i < m.d.length; i++) {
                m.d[i].fall(lerpedAverage);
                m.d[i].show();
                m.d[i].bottom();
            }
            bouncingCircle();
        } else if (m.frameCount >= secondDrop && m.frameCount <= 2750) // Transition part
        {
           m.background(47, 79, 79);
        }

        
        m.translate(m.width / 2, m.height / 2);
        m.scale(scale);
        m.translate(-m.width / 2 , -m.height / 2);
    
        // Second drop of the song (Start of rectangle visual)
        if (m.frameCount >= secondDrop && m.frameCount <= fourthDrop) {
            if (zoomOut == true)
            {
                rectVisual();
                scale /= 1.005;
            }
        }

        // Third drop of the song
        if (m.frameCount >= thirdDrop && m.frameCount <= fourthDrop) {
            m.background(47, 79, 79);
            if (zoomOut == true)
            {
                m.rect(m.width/2, m.height/2, m.width, m.height);
                scale *= 1.005;
            }
            m.rect(m.width/2, m.height/2, m.width, m.height);
            scale *= 1.004;
            bouncingCircle();
        }

        //Fourth drop of the song
        if (m.frameCount >= fourthDrop && m.frameCount <= fifthDrop)
        {
            m.background(backgroundCol);
            bouncingCircle();
            for (int i = 0; i < m.d.length; i++) {
                m.d[i].fall(lerpedAverage);
                m.d[i].show();
                m.d[i].bottom();
            }
            if (backgroundCol == 255)
            {
                reverse = true;
            }
            else if (backgroundCol == 0)
            {
                reverse = false;
            }

            if (reverse == true)
            {
                backgroundCol--;
            }
            else 
            {
                backgroundCol++;
            }
        }

        //Fifth drop of the song
        if (m.frameCount >= fifthDrop)
        {
            m.background(0);
            m.strokeWeight(5);
            bouncingCircle();
            for (int i = 0; i < m.d.length; i++) {
                m.d[i].fall(lerpedAverage);
                m.d[i].show();
                m.d[i].bottom();
            }
            
        }
        //Sixth drop of the song
        if (m.frameCount >= sixthDrop && m.frameCount <= sixthDrop + 3)
        {
            m.background(255);
        }

        if (m.frameCount >= sixthDrop + 3)
        {
            exitIcon("Exit", m.width - 120, m.height - 120);
        }

        
    }

    public void bouncingCircle() {
        float halfheight = m.height / 2;
        float halfwidth = m.width / 2;

        // For syncing with song
        m.calculateAverageAmplitude();

        // Draws circle
        m.strokeWeight(3);

        // Changes color of circle on the first drop of the song
        if (m.frameCount >= firstDrop && m.frameCount <= fourthDrop) 
        {
            m.stroke(255);
            m.fill(47, 79, 79);
        } 
        else if (m.frameCount < firstDrop && m.frameCount <= fourthDrop) 
        {
            m.stroke(255, 0, 0);
            m.fill(0);
        }

        //Fourth Drop
        if (m.frameCount >= fourthDrop && m.frameCount <= fifthDrop)
        {
            m.stroke(2, 7, 93);
            m.fill(backgroundCol);
        }

        //Fifth Drop
        if (m.frameCount >= fifthDrop)
        {
            m.stroke(60, 0 , 8);
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
        float halfheight = m.height / 2;
        float halfwidth = m.width / 2;
        m.stroke(m.random(50, 255));
        m.strokeWeight(15);
        m.rect(halfwidth, halfheight, m.width, m.height);
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
            m.frameCount = 0;
            m.LoadComputer();
            }
        } else {
            m.cursor(Monitor.ARROW); // Default curser everywhere else
        }
    }

    
}