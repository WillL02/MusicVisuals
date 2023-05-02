package C21402094;

import C21423244.Monitor;
import processing.core.PImage;

public class Star {
    freaks anmar;
    Monitor m;

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
        this.m = anmar.m;
        this.angle = anmar.m.random(0, 360);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rotationSpeed = rotationSpeed;
        this.streakLength = anmar.m.random(10, 20);
        this.streakAngle = angle;
        this.anmar = anmar;
    }
    
    public Star(freaks anmar)
    {
        bStars = anmar.m.loadImage("starsBackground.jpg");
        this.anmar = anmar;
    }
    
    public void display(float amplitude) 
    {
        // update angle based on rotation speed and amplitude
        angle += rotationSpeed + Monitor.map(amplitude, 0, 1, 0, (float) 0.5);
        
        // calculate position based on angle and radius
        float xPos = x + Monitor.cos(Monitor.radians(angle)) * radius;
        float yPos = y + Monitor.sin(Monitor.radians(angle)) * radius;
        
        calculateVibration(amplitude);
        
        // calculate streak angle based on direction of motion
        float dx = xPos - x;
        float dy = yPos - y;
        float direction = Monitor.atan2(dy, dx);
        float streakAngle = direction - Monitor.PI / 2;
        
        // draw streak
        float streakX = xPos + Monitor.cos(streakAngle) * streakLength;
        float streakY = yPos + Monitor.sin(streakAngle) * streakLength;
        anmar.m.stroke(255, 255, 255, 100);
        anmar.m.strokeWeight(3);
        anmar.m.line(xPos, yPos, streakX, streakY);
        
        
        anmar.m.pushMatrix();
        anmar.m.translate(x, y);
        anmar.m.rotate(Monitor.radians(angle));
        anmar.m.translate(radius, 0);
    
        drawStar(0, 0, 6);
        
        anmar.m.popMatrix();
    
    }
    
    public void drawStar(float centerX, float centerY, float w)
    {
        anmar.m.stroke(255); 
        anmar.m.strokeWeight(2);
        
        // calculate the coordinates of the points of the star
        float radius;
        float outerRadius = w / 2;
        float innerRadius = (float) (outerRadius / 2.5);
        float angle = Monitor.TWO_PI / 5;
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
            xPoints[i] = centerX + radius * Monitor.cos(i * angle);
            yPoints[i] = centerY + radius * Monitor.sin(i * angle);
        }
        
        // draw star
        anmar.m.beginShape();
        for (int i = 0; i < 10; i++) 
        {
            anmar.m.vertex(xPoints[i], yPoints[i]);
        }
        anmar.m.endShape(Monitor.CLOSE);
    }
    
    public void backgroundStars()
    {
        anmar.m.imageMode(anmar.m.CORNER);
        anmar.m.image(bStars, 0, 0, anmar.m.width, anmar.m.height);
    }
    
    public void calculateVibration(float amplitude) 
    {
        smoothedAmplitude = Monitor.lerp(smoothedAmplitude, amplitude, (float) 0.1);
        float vibration = Monitor.map(smoothedAmplitude, 0, 1, 0, maxVibration);
        x += anmar.m.random(-vibration, vibration);
        y += anmar.m.random(-vibration, vibration);
    }
}