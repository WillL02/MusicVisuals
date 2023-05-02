package C21402094;

public class blackHole {
    freaks anmar = new freaks();

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
        x = anmar.width/2;
        y = anmar.height/2;
        w = anmar.height/3;
        
        for (int i = 0; i < stars.length; i++) 
        {
            float rotationSpeed = anmar.random(0.01, 0.05);
            radius = anmar.random(432, 540);
            stars[i] = new Star(x, y, radius, rotationSpeed, anmar);
        }// end for
    }// end constructor

    public void displayBlackHole(int r2, int g2, int b2)
    {
        float spacer = freaks.map(anmar.ap.mix.level(), 0, 1, 10, 75); // map the amplitude of the sound to spacer
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
            shakeX = anmar.random(-amplitude*20, amplitude*20);
            shakeY = anmar.random(-amplitude*20, amplitude*20);
        } 
        else 
        {
            shakeX = 0;
            shakeY = 0;
        }
        
        anmar.strokeWeight(3);
        anmar.stroke(r2, g2, b2);
        
        for(int i = 0; i < 360; i+=2)
        {
            x2 = x + freaks.sin(freaks.radians(i)) * (spacer +radius/2);
            y2 = y + freaks.cos(freaks.radians(i)) * (spacer +radius/2);
            
            // wave effect
            float offset = freaks.map(amplitude, 0, 1, 0, 10);
            x2 += freaks.sin(freaks.radians(i*10 + anmar.millis()/100)) * offset;
            y2 += freaks.cos(freaks.radians(i*10 + anmar.millis()/100)) * offset;
            
            anmar.line(x2 + shakeX, y2 + shakeY, x + shakeX, y + shakeY);
        }// end for
        
        anmar.noStroke();
        anmar.fill(0);
        anmar.ellipse(x + shakeX, y + shakeY, radius + shakeX, radius + shakeX);
        
        
        if(radius < anmar.height * 0.9)
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


        for(int i = 0 ; i < anmar.ab.size() ; i ++)
        {
            total += freaks.abs(anmar.ab.get(i));
        }// end for
        
        amplitude = total / anmar.ab.size();
        smothedAmplitude = freaks.lerp(smothedAmplitude, amplitude, 0.1f);
    }// end function
    
}// end class
