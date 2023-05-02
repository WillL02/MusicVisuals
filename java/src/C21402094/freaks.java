package C21402094;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import ie.tudublin.*;

public class freaks extends Visual{
    float angle = 0, i;
    float lerpedAverage = 0;
    float beatThreshold = 0.5f;

    boolean ran = false;
    int startTime, startTime2;
    int r = 135, g = 206, b = 235;

    Cloud[] clouds = new Cloud[5];
    Stickman[] stickmen = new Stickman[16];

    blackHole bh;
    Star s;

    Minim minim;
    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer ab;

    public void settings() {
        size(1344, 756, P2D);
    }

    public void setup() {
        
        bh = new blackHole(this);
        s = new Star(this);

        minim = new Minim(this);
        ap = minim.loadFile("TimmyTrumpet.mp3", 2048);
        ap.play();
        ab = ap.mix;

        for (int i = 0; i < stickmen.length; i++) {
            stickmen[i] = new Stickman(this);
        }

        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud(this);
        }

        frameRate(60);
        frameCount = 0;

        i = 1;
    }// end function

    public void draw() {
        System.out.println(frameCount);
        if (frameCount >= 960) // if 16 seconds have passed
        {
            if (i < height / 30) {
                loadPixels();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        float temp = y / i;

                        if (temp < 4) {
                            temp = 0;
                        }
                        r = (int) freaks.map(temp, 0, height / 4, 0, 135);
                        g = (int) freaks.map(temp, 0, height / 4, 0, 206);
                        b = (int) freaks.map(temp, 0, height / 4, 0, 235);

                        pixels[x + y * width] = color(r, g, b);
                    }
                }
                updatePixels();

                // adjusts the speed of transition to space
                i += 0.5;

            } // end if
            else {
                background(0);
                s.backgroundStars();

                if (frameCount >= 3190 && frameCount < 6612)// if 53.2 seconds have passed
                {
                    bh.displayBlackHole(251, 139, 35);// turn orange
                    stickmen[0].displayWhite();
                }

                else if (frameCount >= 6612 && frameCount < 7718) {
                    bh.displayBlackHole(0, 0, 255); // blue
                    stickmen[0].displayWhite();
                }

                else if (frameCount >= 7718) {
                    bh.displayBlackHole(255, 0, 0); // red
                    stickmen[0].displayWhite();
                } // end if
                else {
                    if (frameCount >= 10200) {
                        bh.displayBlackHole(1, 1, 1);
                    } // end if
                    else {
                        bh.displayBlackHole(255, 255, 255);
                    } // end else

                } // end else

            } // end outer else

        } // end if
        else // if 15.5 seconds havent passed yet
        {
            background(r, g, b);// sky blue background

            // when the clouds go off screen they dont come back
            if (frameCount >= 900) {
                ran = true;
            }

            for (int i = 0; i < clouds.length; i++) {
                clouds[i].displayCloud();
                clouds[i].moveCloud(ran);
            }

            for (int i = 0; i < clouds.length; i++) {
                for (int j = 0; j < clouds.length; j++) {
                    // if they overlap move their position
                    if (clouds[i].overlaps(clouds[j]) && i != j) {
                        float dx = clouds[i].x - clouds[j].x;
                        float dy = clouds[i].y - clouds[j].y;
                        float angle = freaks.atan2(dy, dx);
                        float distance = (float) ((clouds[i].w / 1.5) + (clouds[j].w / 1.5)
                                - freaks.dist(clouds[i].x, clouds[i].y, clouds[j].x, clouds[j].y));
                        float newX = clouds[i].x + freaks.cos(angle) * distance;
                        float newY = clouds[i].y + freaks.sin(angle) * distance;
                        clouds[i].x = newX;
                        clouds[i].y = newY;

                    } // end if

                } // end inner for

            } // end outer for

            // Stickmen
            for (int i = 0; i < stickmen.length; i++) {
                stickmen[i].display();
                stickmen[i].move();
            }

            for (int i = 0; i < stickmen.length; i++) {
                for (int j = 0; j < stickmen.length; j++) {

                    if (stickmen[i].overlaps(stickmen[j]) && i != j) {
                        float tx = stickmen[i].x - stickmen[j].x;
                        float ty = stickmen[i].y - stickmen[j].y;
                        float angle = freaks.atan2(ty, tx);
                        float distance = (float) ((stickmen[i].w / 1.5) + (stickmen[j].w / 1.5)
                                - freaks.dist(stickmen[i].x, stickmen[i].y, stickmen[j].x, stickmen[j].y));
                        float newX = stickmen[i].x + freaks.cos(angle) * distance;
                        float newY = stickmen[i].y + freaks.sin(angle) * distance;
                        stickmen[i].x = newX;
                        stickmen[i].y = newY;
                    } // end if

                } // end inner for

            } // end outer for

        } // else
    }// end function

    public float random(double d, double e) {
        return 0;
    }

}
