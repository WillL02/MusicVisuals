package C21318233;


public class Rain {
    Heathens h;
    float x, Y, yspeed;

    public Rain(Heathens h, float x, float y)
    {
        this.h = h;
        this.x = x;
        Y = y;
        yspeed = 0;
    }

    //Determines the speed of the raindrop based on the amplitude
    public void fall(float amplitude) {
        Y += Heathens.map(amplitude, 0, 1.0f, 1, 12.0f);
    }

    // public void fall(float amplitude) {
    //     float yspeed = h.random(2, 5);
    //     Y = Y + yspeed;
    // }

    //Shows the raindrop
    public void show() {
        h.stroke(255);
        h.line(x, Y, x, Y + 10);
    }

    //Once the raindrop hits the bottom, it resets to the top
    public void bottom() {
        if (Y > h.height) 
        {
            Y = h.random(-200, 0);
        }
    }
}