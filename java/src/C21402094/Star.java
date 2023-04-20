package C21402094;

import ie.tudublin.*;

public class Star {
    float x, y, radius;
    float angle, rotationSpeed;
    float amplitude, smoothedAmplitude;
    float maxVibration = 10;
    float streakLength;
    float streakAngle;
    float bx, by;
    PImage bStars;
    
    
    public Star(float x, float y, float radius, float rotationSpeed) 
    {
        this.angle = random(0, 360);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rotationSpeed = rotationSpeed;
        this.streakLength = random(10, 20);
        this.streakAngle = angle;
    }
    
    public Star()
    {
        bStars = loadImage("starsBackground.jpg");
    }
    
    public void display(float amplitude) 
    {
        // update angle based on rotation speed and amplitude
        angle += rotationSpeed + map(amplitude, 0, 1, 0, 0.5);
        
        // calculate position based on angle and radius
        float xPos = x + cos(radians(angle)) * radius;
        float yPos = y + sin(radians(angle)) * radius;
        
        calculateVibration(amplitude);
        
        // calculate streak angle based on direction of motion
        float dx = xPos - x;
        float dy = yPos - y;
        float direction = atan2(dy, dx);
        float streakAngle = direction - PI / 2;
        
        // draw streak
        float streakX = xPos + cos(streakAngle) * streakLength;
        float streakY = yPos + sin(streakAngle) * streakLength;
        stroke(255, 255, 255, 100);
        strokeWeight(3);
        line(xPos, yPos, streakX, streakY);
        
        
        pushMatrix();
        translate(x, y);
        rotate(radians(angle));
        translate(radius, 0);
    
        drawStar(0, 0, 6);
        
        popMatrix();
        
    }
    
    public void drawStar(float centerX, float centerY, float w)
    {
        stroke(255); 
        strokeWeight(2);
        
        // calculate the coordinates of the points of the star
        float radius;
        float outerRadius = w / 2;
        float innerRadius = outerRadius / 2.5;
        float angle = TWO_PI / 5;
        float[] xPoints = new float[10];
        float[] yPoints = new float[10];
        for (int i = 0; i < 10; i++) 
        {
            if (i % 2 == 0) 
            {
                radius = outerRadius;
            } 
            else 
            {
                radius = innerRadius;
            }
            xPoints[i] = centerX + radius * cos(i * angle);
            yPoints[i] = centerY + radius * sin(i * angle);
        }
        
        // draw star
        beginShape();
        for (int i = 0; i < 10; i++) 
        {
            vertex(xPoints[i], yPoints[i]);
        }
        endShape(CLOSE);
    }
    
    public void backgroundStars()
    {
        image(bStars, 0, 0);
    }
    
    public void calculateVibration(float amplitude) 
    {
        smoothedAmplitude = lerp(smoothedAmplitude, amplitude, 0.1);
        float vibration = map(smoothedAmplitude, 0, 1, 0, maxVibration);
        x += random(-vibration, vibration);
        y += random(-vibration, vibration);
    }
}