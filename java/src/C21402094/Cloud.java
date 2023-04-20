package C21402094;

public class Cloud {

    freaks anmar = new freaks();

    float x, y, w, h, a, temp;
    int ep;
    float speed = 6;

    public Cloud(freaks anmar)
    {
        w = 200;
        h = anmar.random(65, 100);
        x = anmar.random(w/2, anmar.width-w/2);
        y = anmar.random(h/2, anmar.height-h/2);
        ep = (int)anmar.random(0, 2);
        this.anmar = anmar;
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
        
        anmar.noStroke();
        anmar.fill(250);
        anmar.rectMode(freaks.CENTER);
        anmar.ellipse(a, y-h/2, w/2, w/2);
        anmar.rect(x, y, w, h, h/2);
    }// end function

        
    public void moveCloud(boolean go)
    {
        if(y-h/2-w/2 > anmar.height && !go)
        {
            temp = -1 *(anmar.random(0 + h/2 + w/2, 170));
            y = temp;
        }// end if
        
        y = y + speed;
    }// end function

    
    public boolean overlaps(Cloud object)
    {
        float d = freaks.dist(x, y, object.x, object.y);
        
        return (d < (w/1.5) + (object.w/1.5));
    }// end function

}// end class