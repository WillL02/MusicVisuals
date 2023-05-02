package C21402094;

import C21423244.Monitor;
import processing.core.PImage;

public class Stickman {
    Monitor m;
    freaks anmar;

    PImage[] sticks = new PImage[18];
    PImage whiteStick;
    float x,y;
    float w, h;
    int i = 0;
    boolean done = false;
    float angle;
    float rotationSpeed = (float) 0.05;


    public Stickman(freaks anmar)
    { 
        this.anmar = anmar;
        this.m = anmar.m;
        x = anmar.m.height/2;
        y = anmar.m.width/2;
        angle = anmar.m.random(0, 360);
        for(int i = 0; i < sticks.length; i++)
        {
            sticks[i] = anmar.m.loadImage("stickman/"+i+".png");
        }// end for
        
        for(int i = 0; i < sticks.length; i++)
        {
            w = (float) (sticks[i].width);
            h = (float) (sticks[i].height);
        }// end for
        
        x = anmar.m.random(w/2, anmar.m.width-w/2);
        y = anmar.m.random(h/2, anmar.m.height-h/2);
        
        whiteStick = anmar.m.loadImage("stickman-white.png");
        
    }// end constructor
        
    void display()
    {
        anmar.m.imageMode(Monitor.CENTER); 
        anmar.m.image(sticks[i], x, y, w, h);
        
        // if index has not reached length and not made it to length yet
        if(i < sticks.length && !done)
        {
            i++;
        }// end if
        else
        {         
            i--;
        }// end else
        
        if(i == sticks.length-1 || i == 0)
        {
            done = !done;
        }// end if
    
    }// end function
        
    void displayWhite()
    {
        float centerRadius = anmar.m.height/3;
        float w = (float) (whiteStick.width * 0.06); 
        float h = (float) (whiteStick.height * 0.06);
        
        
        // Update angle based on rotation speed
        this.angle += rotationSpeed; 

        // Calculate position on curved circle based on black hole radius
        anmar.m.pushMatrix();
        anmar.m.translate(anmar.m.width / 2, anmar.m.height / 2);
        anmar.m.rotate(Monitor.radians(angle));
        anmar.m.translate(centerRadius, 0);
        
        anmar.m.imageMode(Monitor.CENTER); 
        anmar.m.image(whiteStick, 0, 0, w, h);
        
        anmar.m.popMatrix();
        anmar.m.resetMatrix();
        
        // update position and rotation of the star
        this.angle += rotationSpeed;
    }// end function
        
    
    void move()
    {
        if(i == 5 && done)
        {
            for(int i = 0; i < anmar.clouds.length; i++)
            {
                anmar.clouds[i].y+=10;
            }// end for
            
        }// end if
    }// end function

        
    boolean overlaps(Stickman object)
    {
        float d = Monitor.dist(x, y, object.x, object.y);
        
        return (d < (w/2) + (object.w/2) && d < (h/2) + (object.h/2));
    }// end function

}// end class
