package C21402094;

float x = width/2, y = height/2, w = height/3;
float x2, y2, shakeX, shakeY;
float radius;
float amplitude;
float smothedAmplitude;
boolean ran;
Star[] stars = new Star[50];

public class blackHole {

    public blackHole()
    {
        radius = w*1.5;
        ran = false;
        
        for (int i = 0; i < stars.length; i++) 
        {
            float rotationSpeed = random(0.01, 0.05);
            float radius = random(this.radius * 1.2, this.radius * 1.5);
            stars[i] = new Star(x, y, radius, rotationSpeed);
        }// end for
    }// end constructor

    public void displayBlackHole(int r2, int g2, int b2)
    {
        float spacer = map(ap.mix.level(), 0, 1, 10, 75); // map the amplitude of the sound to spacer
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
            shakeX = random(-amplitude*20, amplitude*20);
            shakeY = random(-amplitude*20, amplitude*20);
        } 
        else 
        {
            shakeX = 0;
            shakeY = 0;
        }
        
        strokeWeight(3);
        stroke(r2, g2, b2);
        
        for(int i = 0; i < 360; i+=2)
        {
            x2 = x + sin(radians(i)) * (spacer +radius/2);
            y2 = y + cos(radians(i)) * (spacer +radius/2);
            
            // wave effect
            float offset = map(amplitude, 0, 1, 0, 10);
            x2 += sin(radians(i*10 + millis()/100)) * offset;
            y2 += cos(radians(i*10 + millis()/100)) * offset;
            
            line(x2 + shakeX, y2 + shakeY, x + shakeX, y + shakeY);
        }// end for
        
        noStroke();
        fill(0);
        ellipse(x + shakeX, y + shakeY, radius + shakeX, radius + shakeX);
        
        
        if(radius < height * 0.9)
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


        for(int i = 0 ; i < ab.size() ; i ++)
        {
            total += abs(ab.get(i));
        }// end for
        
        amplitude = total / ab.size();
        smothedAmplitude = PApplet.lerp(smothedAmplitude, amplitude, 0.1f);
    }// end function
    
}// end class
