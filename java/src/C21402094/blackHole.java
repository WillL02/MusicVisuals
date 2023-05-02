package C21402094;

import C21423244.Monitor;

public class blackHole {
    freaks anmar;
    Monitor m;

    float x2, y2, shakeX, shakeY;
    float radius;
    float amplitude;
    float smothedAmplitude;
    boolean ran;
    Star[] stars = new Star[50];
    float r2,g2,b2;
    float x,y,w;

    public blackHole(freaks anmar)
    {
        radius = (float) (w*1.5);
        ran = false;
        this.anmar = anmar;
        this.m = anmar.m;
        x = anmar.m.width/2;
        y = anmar.m.height/2;
        w = anmar.m.height/3;
        
        for (int i = 0; i < stars.length; i++) 
        {
            float rotationSpeed = anmar.random(0.01, 0.05);
            radius = anmar.m.random(432, 540);
            stars[i] = new Star(x, y, radius, rotationSpeed, anmar);
        }// end for
    }// end constructor

    public void displayBlackHole(int r2, int g2, int b2)
    {
        float spacer = Monitor.map(anmar.m.ap.mix.level(), 0, 1, 10, 75); // map the amplitude of the sound to spacer
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        
        
        if(!ran)
        {
            radius = w/4; // stops the radius from starting on a big radius and shrinking straight away 
        }

        
        calculateAverageAmplitude();
        
        // shake the screen when the beat is high
        if (amplitude > 0.5) 
        {
            shakeX = anmar.m.random(-amplitude*20, amplitude*20);
            shakeY = anmar.m.random(-amplitude*20, amplitude*20);
        } 
        else 
        {
            shakeX = 0;
            shakeY = 0;
        }
        
        anmar.m.strokeWeight(3);
        anmar.m.stroke(r2, g2, b2);
        
        for(int i = 0; i < 360; i+=2)
        {
            x2 = x + Monitor.sin(Monitor.radians(i)) * (spacer +radius/2);
            y2 = y + Monitor.cos(Monitor.radians(i)) * (spacer +radius/2);
            
            // wave effect
            float offset = Monitor.map(amplitude, 0, 1, 0, 10);
            x2 += Monitor.sin(Monitor.radians(i*10 + anmar.m.millis()/100)) * offset;
            y2 += Monitor.cos(Monitor.radians(i*10 + anmar.m.millis()/100)) * offset;
            
            anmar.m.line(x2 + shakeX, y2 + shakeY, x + shakeX, y + shakeY);
        }// end for
        
        anmar.m.noStroke();
        anmar.m.fill(0);
        anmar.m.ellipse(x + shakeX, y + shakeY, radius + shakeX, radius + shakeX);
        
        
        if(radius < anmar.m.height * 0.9)
        {
            radius = smothedAmplitude * 1000;
        }// end if
        
        for (int i = 0; i < stars.length; i++) 
        {
            stars[i].display(getSmoothedAmplitude());
        }// end for
        
        ran = true;
        
    }// end function
    
    
    public float getSmoothedAmplitude() 
    {
        return smothedAmplitude;
    }// end function
    
    
    public void calculateAverageAmplitude()
    {
        float total = 0;


        for(int i = 0 ; i < anmar.m.ab.size() ; i ++)
        {
            total += Monitor.abs(anmar.m.ab.get(i));
        }// end for
        
        amplitude = total / anmar.m.ab.size();
        smothedAmplitude = Monitor.lerp(smothedAmplitude, amplitude, 0.1f);
    }// end function
    
}// end class
