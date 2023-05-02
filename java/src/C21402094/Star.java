package C21402094;

import processing.core.PImage;

public class Star {
    freaks anmar = new freaks();

    float x, y, radius;
    float angle, rotationSpeed;
    float amplitude, smoothedAmplitude;
    float maxVibration = 10;
    float streakLength;
    float streakAngle;
    float bx, by;
    PImage bStars;
    
    
    public Star(float x, float y, float radius, float rotationSpeed, freaks anmar) 
    {
        this.angle = anmar.random(0, 360);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rotationSpeed = rotationSpeed;
        this.streakLength = anmar.random(10, 20);
        this.streakAngle = angle;
        this.anmar = anmar;
    }
    
    public Star(freaks anmar)
    {
        bStars = anmar.loadImage("starsBackground.jpg");
        this.anmar = anmar;
    }
    
    public void display(float amplitude) 
    {
        // update angle based on rotation speed and amplitude
        angle += rotationSpeed + freaks.map(amplitude, 0, 1, 0, (float) 0.5);
        
        // calculate position based on angle and radius
        float xPos = x + freaks.cos(freaks.radians(angle)) * radius;
        float yPos = y + freaks.sin(freaks.radians(angle)) * radius;
        
        calculateVibration(amplitude);
        
        // calculate streak angle based on direction of motion
        float dx = xPos - x;
        float dy = yPos - y;
        float direction = freaks.atan2(dy, dx);
        float streakAngle = direction - freaks.PI / 2;
        
        // draw streak
        float streakX = xPos + freaks.cos(streakAngle) * streakLength;
        float streakY = yPos + freaks.sin(streakAngle) * streakLength;
        anmar.stroke(255, 255, 255, 100);
        anmar.strokeWeight(3);
        anmar.line(xPos, yPos, streakX, streakY);
        
        
        anmar.pushMatrix();
        anmar.translate(x, y);
        anmar.rotate(freaks.radians(angle));
        anmar.translate(radius, 0);
    
        drawStar(0, 0, 6);
        
        anmar.popMatrix();
        
    }
    
    public void drawStar(float centerX, float centerY, float w)
    {
        anmar.stroke(255); 
        anmar.strokeWeight(2);
        
        // calculate the coordinates of the points of the star
        float radius;
        float outerRadius = w / 2;
        float innerRadius = (float) (outerRadius / 2.5);
        float angle = freaks.TWO_PI / 5;
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
            xPoints[i] = centerX + radius * freaks.cos(i * angle);
            yPoints[i] = centerY + radius * freaks.sin(i * angle);
        }
        
        // draw star
        anmar.beginShape();
        for (int i = 0; i < 10; i++) 
        {
            anmar.vertex(xPoints[i], yPoints[i]);
        }
        anmar.endShape(freaks.CLOSE);
    }
    
    public void backgroundStars()
    {
        anmar.imageMode(anmar.CORNER);
        anmar.image(bStars, 0, 0, anmar.width, anmar.height);
    }
    
    public void calculateVibration(float amplitude) 
    {
        smoothedAmplitude = freaks.lerp(smoothedAmplitude, amplitude, (float) 0.1);
        float vibration = freaks.map(smoothedAmplitude, 0, 1, 0, maxVibration);
        x += anmar.random(-vibration, vibration);
        y += anmar.random(-vibration, vibration);
    }
}