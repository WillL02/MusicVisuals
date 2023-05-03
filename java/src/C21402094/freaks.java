package C21402094;

import C21423244.Monitor;


public class freaks {
    float angle = 0;
    float lerpedAverage = 0;
    float beatThreshold = 0.5f;

    boolean ran = false;
    int startTime, startTime2;

    Monitor m;
    float i;

    Cloud[] clouds = new Cloud[5];
    Stickman[] stickmen = new Stickman[30];

    public int r = 135;
    public int g = 206;
    public int b = 235;

    blackHole bh;
    Star s;


    public freaks(Monitor m) 
    {
        this.m = m;
        bh = new blackHole(this);
        s = new Star(this);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud(this);
        }

        for (int i = 0; i < stickmen.length; i++) {
            stickmen[i] = new Stickman(this);
        }

        m.frameRate(60);
        m.frameCount = 0;
        i = 1;
    }


    public void render() {
        
        if (m.frameCount >= 960) // if 16 seconds have passed
        {
            if (i < m.height / 30) {
                m.loadPixels();
                for (int x = 0; x < m.width; x++) {
                    for (int y = 0; y < m.height; y++) {
                        float temp = y / i;

                        if (temp < 4) {
                            temp = 0;
                        }
                        r = (int) Monitor.map(temp, 0, m.height / 4, 0, 135);
                        g = (int) Monitor.map(temp, 0, m.height / 4, 0, 206);
                        b = (int) Monitor.map(temp, 0, m.height / 4, 0, 235);

                        m.pixels[x + y * m.width] = m.color(r, g, b);
                    }
                }
                m.updatePixels();

                // adjusts the speed of transition to space
                i += 3;

            } // end if
            else {
                m.background(0);
                s.backgroundStars();
                exitIcon("Exit", m.width - 120, m.height - 120);
                exitIconHover();

                if (m.frameCount >= 3309 && m.frameCount < 6748)// if 53.2 seconds have passed
                {
                    bh.displayBlackHole(251, 139, 35);// turn orange
                    stickmen[0].displayWhite();
                }

                else if (m.frameCount >= 6748 && m.frameCount < 7828) {
                    bh.displayBlackHole(0, 0, 255); // blue
                    stickmen[0].displayWhite();
                }

                else if (m.frameCount >= 7828) {
                    bh.displayBlackHole(255, 0, 0); // red
                    stickmen[0].displayWhite();
                } // end if
                else {
                    if (m.frameCount >= 10200) {
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
            m.background(r, g, b);// sky blue background
            exitIcon("Exit", m.width - 120, m.height - 120);
            exitIconHover();

            // when the clouds go off screen they dont come back
            if (m.frameCount >= 900) {
                m.ran = true;
            }

            for (int i = 0; i < clouds.length; i++) {
                clouds[i].displayCloud();
                clouds[i].moveCloud(m.ran);
            }

            for (int i = 0; i < clouds.length; i++) {
                for (int j = 0; j < clouds.length; j++) {
                    // if they overlap move their position
                    if (clouds[i].overlaps(clouds[j]) && i != j) {
                        float dx = clouds[i].x - clouds[j].x;
                        float dy = clouds[i].y - clouds[j].y;
                        float angle = Monitor.atan2(dy, dx);
                        float distance = (float) ((clouds[i].w / 1.5) + (clouds[j].w / 1.5)
                                - Monitor.dist(clouds[i].x, clouds[i].y, clouds[j].x, clouds[j].y));
                        float newX = clouds[i].x + Monitor.cos(angle) * distance;
                        float newY = clouds[i].y + Monitor.sin(angle) * distance;
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
                        float angle = Monitor.atan2(ty, tx);
                        float distance = (float) ((stickmen[i].w / 1.5) + (stickmen[j].w / 1.5)
                                - Monitor.dist(stickmen[i].x, stickmen[i].y, stickmen[j].x, stickmen[j].y));
                        float newX = stickmen[i].x + Monitor.cos(angle) * distance;
                        float newY = stickmen[i].y + Monitor.sin(angle) * distance;
                        stickmen[i].x = newX;
                        stickmen[i].y = newY;
                    } // end if

                } // end inner for

            } // end outer for

        } // else

    }

    void exitIcon(String text, float x, float y) {
        m.exit = m.loadImage("exit.png");
        m.image(m.exit, x, y);
        m.fill(255, 0,0);
        m.textSize(25);
        m.text(text, x+8, y + 77);
      }

      void exitIconHover() {
        if (m.mouseX > m.width - 140 && m.mouseX < m.width - 20 && m.mouseY > m.height - 130 && m.mouseY < m.height - 20) {
          m.cursor(Monitor.HAND); // Changes the curser when hovering over the icon
          m.noStroke();
          m.fill(49, 182, 255, 65);
          m.rect(m.width - 92, m.height - 85, 100, 100);
          // Exits the program if EXIT icon is pressed
          if (m.mousePressed) {

            m.ap.pause();
            m.minim.stop();
            m.frameCount = 0;
            m.cursor(Monitor.ARROW); // Default curser everywhere else
            m.LoadComputer();
           
          }
        } else {
          m.cursor(Monitor.ARROW); // Default curser everywhere else
        }
      }

    public float random(double d, double e) {
        return 0;
    }
}
