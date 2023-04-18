package C21423244;

import java.util.concurrent.TimeUnit;

import C21318233.Heathens;
import C21318233.Rain;
import C21379483.Mouse;
import C21379483.SafeAndSound;
import ie.tudublin.*;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.*;
import ddf.minim.Minim;

public class Monitor extends Visual
{   
    PImage icon;
    PFont MS95;

    boolean computerStarted = false;
    boolean startDone = false;
    public int visualActive = 0;


    //MATE VARIABLES START HERE Declaring images 
    public Minim minim;
    public AudioPlayer song;
    public AudioBuffer ab;
    public FFT fft;

    public PImage windowsxp;
    public PImage windowsxp2;
    public PImage exit;

    //Declaring the 5 mouse curser images and making 12 mouse objects
    public PImage[] mice = new PImage[5];
    public Mouse[] mouses = new Mouse[12];

    //Program starts with 1 mouse and goes up to 12
    public int mouseNum = 1;
    public int maxMice = 12;
    
    public boolean alreadyStarted = false;
    SafeAndSound mate = new SafeAndSound(this);
    int count = 0;
    // MATE VARIABLES END


    // CRUZ VARIABLES START
    Heathens cruz = new Heathens(this);
    public Rain rain = new Rain(null, LINUX, Y);
    public Rain[] d = new Rain[400];
    // CRUZ VARIABLES END
    

    public void settings()
    {
        size(1920,1080,P2D);
    }

    public void setup()
    {
        LoadComputer();      
    }
    

    public void draw()
    {
        if(visualActive == 0) 
        {
            if(computerStarted == false) {
                drawComputer();
                loadingMode();
                
            } else {
                if(startDone == false) {
                    wait(0);
                }
                startDone = true;
                drawComputer();
                desktopMode();
            }
            
            
            //Plays audio once, needs fix
            if(count == 0) 
            {
                startMinim();
                loadAudio("startupSound95.mp3");
                getAudioPlayer().play();
                count++;
            }
            
            iconHover();
        } 
        else if(visualActive == 1)
        {
            mate.render();
        }
        else if(visualActive == 2)
        {
            cruz.render();
        }
        else if(visualActive == 3)
        {
            mate.render();
        }
    }

    public void iconHover() 
    {
        if(mouseX >= 531 && mouseX <= 622 && mouseY >= 86 && mouseY <= 166)
        {
            stroke(0,182,255);
            fill(49,182,255,65);
            rect(531, 86, 91, 50+30);
        }
 
        if(mouseX >= 531 && mouseX <= 622 && mouseY >= 176 && mouseY <= 256)
        {
            stroke(0,182,255);
            fill(49,182,255,65);
            rect(531, 176, 91, 50+30);
        }
 
        if(mouseX >= 531 && mouseX <= 622 && mouseY >= 266 && mouseY <= 345)
        {
            stroke(0,182,255);
            fill(49,182,255,65);
            rect(531, 266, 91, 50+30);
        }
        stroke(0);
    }

    public void drawComputer() 
    {
        float MonitorX = 480;
        float MonitorY = 35;
        float monitorSize =  (float) (width/1.9);
        float bezelSize = 45;

        float screenX = MonitorX+bezelSize;
        float screenY = 80;
        float screenSize =  (float) (monitorSize/1.1);

        float standX = (float) (screenX * 1.25);
        float standY = height-MonitorY;
        float standSize = (float) (screenX * 1.25);

        //background
        fill(176, 176, 176);
        square(0,0,width);
   
        // Table
        fill(130, 107, 82);
        quad(280, 1000, 1660, 1000, 1720, 1080,200, 1080);


        // Monitor
        fill(202, 201, 193);
        noStroke();
        rect(MonitorX,MonitorY,monitorSize,monitorSize,10);


        // Stand
        strokeWeight(2);
        stroke(0);
        fill(181, 178, 165);
        rect(standX,standY,standSize,standY);
    

        // Screen
        fill(0);
        noStroke();
        rect(screenX,screenY,screenSize,screenSize);
    }


    public void windowsLogo(float x,float y,float extent) 
    {
        float size = 20 * extent;
        float offset = 1* extent;

        // Red Square
        fill(255,0,0);
        quad(x,y, x+size,y+offset, x+size-offset,y+size+offset, x-offset,y+size);

        // Green Square
        fill(0,255,0);
        quad(x+size+(offset*2), y+offset,   x+(size*2)+(offset*2), y+(offset*2),    x+(size*2)+offset, y+size+(offset*2),    x+size+offset, y+size+offset);

        // Yellow Square
        fill(255, 255, 0);
        quad(x-offset, y+size+(offset*2),   x+size-offset ,y+size+(offset*3),   x+size-(offset*2), y+(size*2)+(offset*3),   x-(offset*2),y+(size*2)+(offset*2));

        // Blue Square
        fill(0,0,255);
        quad(x+size+offset, y+size+(offset*3),   x+(size*2)+offset, y+size+(offset*4),   x+(size*2), y+(size*2)+(offset*4),   x+size, y+(size*2)+(offset*3));
    }

    public void LoadComputer() 
    {
        drawComputer();
        startMinim();
        loadAudio("fanBackground.mp3");
        getAudioPlayer().play();
        MS95 = createFont("W95FA.otf", 128);
        visualActive = 0;
        alreadyStarted = false;
        rectMode(CORNER);
        colorMode(RGB);
    }

    public void LoadSafeAndSound() 
    {
        colorMode(HSB);
        background(0);

        minim = new Minim(this);
        song  = minim.loadFile("song.mp3", 1024);
        windowsxp = loadImage("windowsxp.png");
        windowsxp2 = loadImage("windowsxp2.png");
        song.play();
        ab = song.mix;
        fft = new FFT(song.bufferSize(), song.sampleRate());

        smooth();

        rectMode(CENTER);


        // Load mouse curser images
        for (int i = 0; i < mice.length; i++) {
            int y = i + 1;
           mice[i] = loadImage("mouse" + y + ".png");
        }
  
        //Creating mouse objects, with random size between 85,130
        //And passing this file onto Mouse class using "this"
        for (int i = 0; i < mouses.length; i++) {
            int index = (int) (random(0, mice.length));
            mouses[i] = new Mouse(mice[index], random(85, 130), this);
        }
    }

    public void LoadHeathens() 
    {
        // Start playing audio
        startMinim();
        loadAudio("heathens.mp3");
        getAudioPlayer().play();

        // Initialising rain drops at random x and y values
        for (int i = 0; i < d.length; i++) {
            d[i] = new Rain(this, random(0, width), random(-1000, 0));
        }
    }


    public void loadingMode() 
    {
        float screenX = 526;
        float screenY = 81;
        float screenSize = 916;
        fill(10,10,10);
        rect(screenX,screenY,screenSize,screenSize);
        
        wait(1);

        windowsLogo(850, 200,6);
        fill(255);
        textSize(80);
        text("Loading...", 800,800);
        computerStarted = true;    
           
    }

    public void desktopMode() 
    {
        float screenX = 526;
        float screenY = 81;
        float screenSize = 916;

        float taskbarHeight = 35;

        float taskbarStartHeight = 25;
        float taskbarStartWidth = 65;

        float taskbarTimeHeight = 25;
        float taskbarTimeWidth = 100;

        // Desktop Background
        fill(0,128,128);
        rect(screenX,screenY,screenSize,screenSize);

        //Taskbar
        fill(192,192,192);
        rect(screenX, screenSize+screenY-taskbarHeight, screenSize, taskbarHeight);

        //Taskbar Start
        fill(192,192,192);
        stroke(0);
        rect(screenX+5, screenSize+screenY-30, taskbarStartWidth, taskbarStartHeight);
        windowsLogo(screenX+10, screenSize+screenY-25, (float) 0.3);
        
        fill(0);
        textFont(MS95);
        textSize(20);
        // Font Bold Effect
        text("Start", screenX+30, screenSize+screenY-10);
        text("Start", screenX+31, screenSize+screenY-10);
        text("Start", screenX+29, screenSize+screenY-10);

        //Taskbar Time
        fill(192,192,192);
        rect(screenSize+screenX-taskbarTimeWidth-5, screenSize+screenY-30, taskbarTimeWidth, taskbarTimeHeight);

        // Obtains time from computer
        int m = minute();  // Values from 0 - 59
        int h = hour();    // Values from 0 - 23
        String time =  ""; // AM or PM value
        
        // IF time should be PM or AM
        if(h > 12) 
        {
            time = " PM";
        } else {
            time = " AM";
        } // end IF
         
        String curTime = String.format("%02d:%02d "+ time, h, m);
        fill(0);
        
        // Print Taskbar TIme
        text(curTime, screenSize+screenX-taskbarTimeWidth+5, screenSize+screenY-10);

        // Icons
        desktopIcon("Visual1.exe","icon1.png", screenX+20, screenY+10);
        desktopIcon("Visual2.exe", "icon2.png", screenX+20, screenY+100);
        desktopIcon("Visual3.exe", "icon3.png", screenX+20, screenY+190);
    }

    public void desktopIcon(String text, String imgName, float x, float y) 
    {
        icon = loadImage(imgName);
        image(icon, x, y);

        //Text
        fill(255);
        text(text,x-15, y+70);
    }

    public void mousePressed() {
       if(mouseX >= 531 && mouseX <= 622 && mouseY >= 86 && mouseY <= 166)
       {
        if(alreadyStarted == false) {
            rect(531, 86, 91, 50+30);
            LoadSafeAndSound();
            visualActive = 1;
            alreadyStarted = true;
        }
       }

       if(mouseX >= 531 && mouseX <= 622 && mouseY >= 176 && mouseY <= 256)
       {
        if(alreadyStarted == false) {
            rect(531, 176, 91, 50+30);
            LoadHeathens();
            visualActive = 2;
            alreadyStarted = true;
        }
       }

       if(mouseX >= 531 && mouseX <= 622 && mouseY >= 266 && mouseY <= 345)
       {
        rect(531, 266, 91, 50+30);
       }
    }

    private void wait(int waitSeconds) 
    {
        //Times out loading screen
        try {
            TimeUnit.SECONDS.sleep(waitSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
