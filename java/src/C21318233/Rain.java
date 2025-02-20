package C21318233;

import C21423244.Monitor;

public class Rain {
    Monitor m;
    float x, y;

    
    //Rain constructor
    public Rain(Monitor m, float x, float y)
    {
        this.m = m;
        this.x = x;
        this.y = y;
    }

    //Determines the speed of the raindrop based on the amplitude
    public void fall(float amplitude) {
        y += Monitor.map(amplitude, 0, 1.0f, 4, 90.0f);
    }

    //Shows the raindrop
    public void show() {

        //Rain changes color on the first beat drop
        if (m.frameCount >= 1474 && m.frameCount < 6916)
        {
           m.stroke(103, 183, 209);
        }
        else if (m.frameCount < 1474)
        {
          m.stroke(255);  
        }
        else if (m.frameCount >= 6916)
        {
            m.stroke(255,0,0);
        }
        
        m.line(x, y, x, y + m.random(10,20));
    }

    //Once the raindrop hits the bottom, it resets to the top at a random y value
    public void bottom() {
        if (y > m.height) 
        {
            y = m.random(-200, 0);
        }
    }
}