package C21402094;

import ie.tudublin.*;

public class Stickman {
    PImage[] sticks = new PImage[18];
    PImage whiteStick;
    float x = height/2, y = width/2;
    float w, h;
    int i = 0;
    boolean done = false;
    float angle = random(0, 360);
    float rotationSpeed = 0.05;


    public Stickman()
    { 
        for(int i = 0; i < sticks.length; i++)
        {
            sticks[i] = loadImage("stickman/"+i+".png");
        }// end for
        
        for(int i = 0; i < sticks.length; i++)
        {
            w = sticks[i].width * 0.2;
            h = sticks[i].height * 0.2;
        }// end for
        
        x = random(w/2, width-w/2);
        y = random(h/2, height-h/2);
        
        whiteStick = loadImage("stickman-white.png");
        
    }// end constructor
        
    void display()
    {
        imageMode(CENTER); 
        image(sticks[i], x, y, w, h);
        
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
        float centerRadius = height/3;
        float w = whiteStick.width * 0.06; 
        float h = whiteStick.height * 0.06;
        
        
        // Update angle based on rotation speed
        this.angle += rotationSpeed; 

        // Calculate position on curved circle based on black hole radius
        pushMatrix();
        translate(width / 2, height / 2);
        rotate(radians(angle));
        translate(centerRadius, 0);
        
        imageMode(CENTER); 
        image(whiteStick, 0, 0, w, h);
        
        popMatrix();
        resetMatrix();
        
        // update position and rotation of the star
        this.angle += rotationSpeed;
    }// end function
        
    
    void move()
    {
        if(i == 5 && done)
        {
            for(int i = 0; i < clouds.length; i++)
            {
                clouds[i].y+=10;
            }// end for
            
        }// end if
    }// end function

        
    boolean overlaps(Stickman object)
    {
        float d = dist(x, y, object.x, object.y);
        
        return (d < (w/2) + (object.w/2) && d < (h/2) + (object.h/2));
    }// end function

}// end class
