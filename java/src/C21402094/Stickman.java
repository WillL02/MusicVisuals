package C21402094;

import processing.core.PImage;

public class Stickman {
    freaks anmar = new freaks();

    PImage[] sticks = new PImage[18];
    PImage whiteStick;
    float x = anmar.height/2, y = anmar.width/2;
    float w, h;
    int i = 0;
    boolean done = false;
    float angle = anmar.random(0, 360);
    float rotationSpeed = (float) 0.05;


    public Stickman(freaks anmar)
    { 
        this.anmar = anmar;
        for(int i = 0; i < sticks.length; i++)
        {
            sticks[i] = anmar.loadImage("stickman/"+i+".png");
        }// end for
        
        for(int i = 0; i < sticks.length; i++)
        {
            w = (float) (sticks[i].width * 0.2);
            h = (float) (sticks[i].height * 0.2);
        }// end for
        
        x = anmar.random(w/2, anmar.width-w/2);
        y = anmar.random(h/2, anmar.height-h/2);
        
        whiteStick = anmar.loadImage("stickman-white.png");
        
    }// end constructor
        
    void display()
    {
        anmar.imageMode(freaks.CENTER); 
        anmar.image(sticks[i], x, y, w, h);
        
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
        float centerRadius = anmar.height/3;
        float w = (float) (whiteStick.width * 0.06); 
        float h = (float) (whiteStick.height * 0.06);
        
        
        // Update angle based on rotation speed
        this.angle += rotationSpeed; 

        // Calculate position on curved circle based on black hole radius
        anmar.pushMatrix();
        anmar.translate(anmar.width / 2, anmar.height / 2);
        anmar.rotate(freaks.radians(angle));
        anmar.translate(centerRadius, 0);
        
        anmar.imageMode(freaks.CENTER); 
        anmar.image(whiteStick, 0, 0, w, h);
        
        anmar.popMatrix();
        anmar.resetMatrix();
        
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
        float d = freaks.dist(x, y, object.x, object.y);
        
        return (d < (w/2) + (object.w/2) && d < (h/2) + (object.h/2));
    }// end function

}// end class
