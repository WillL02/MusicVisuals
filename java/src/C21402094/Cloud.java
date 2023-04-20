package C21402094;

import ie.tudublin.*;

public class Cloud {
    float x, y, w, h, a, temp;
    int ep;
    float speed = 6;

    public Cloud()
    {
        w = 200;
        h = random(65, 100);
        x = random(w/2, width-w/2);
        y = random(h/2, height-h/2);
        ep = (int)random(0, 2);
    }// end functions
    
    public void displayCloud()
    {  
        if(ep == 1)
        {
            a = x - w/10;
        }// end if
        else
        {
            a = x + w/10;
        }// end else
        
        noStroke();
        fill(250);
        rectMode(CENTER);
        ellipse(a, y-h/2, w/2, w/2);
        rect(x, y, w, h, h/2);
    }// end function

        
    public void moveCloud(boolean go)
    {
        if(y-h/2-w/2 > height && !go)
        {
            temp = -1 *(random(0 + h/2 + w/2, 170));
            y = temp;
        }// end if
        
        y = y + speed;
    }// end function

    
    public boolean overlaps(Cloud object)
    {
        float d = dist(x, y, object.x, object.y);
        
        return (d < (w/1.5) + (object.w/1.5));
    }// end function

}// end class