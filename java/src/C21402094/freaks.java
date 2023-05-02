package C21402094;


import C21423244.Monitor;
import ie.tudublin.*;

public class freaks {
    Monitor m;

    Cloud[] clouds = new Cloud[5];
    Stickman[] stickmen = new Stickman[16];

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
        m.frameCount = 900;

        m.i = 1; 

    }

    public void render() {
        System.out.println(m.frameCount);
        if (m.frameCount >= 960) // if 16 seconds have passed
        {
            if (m.i < m.height / 30) {
                m.loadPixels();
                for (int x = 0; x < m.width; x++) {
                    for (int y = 0; y < m.height; y++) {
                        float temp = y / m.i;

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
                m.i += 2;

            } // end if
            else {
                m.background(0);
                s.backgroundStars();

                if (m.frameCount >= 3190 && m.frameCount < 6612)// if 53.2 seconds have passed
                {
                    bh.displayBlackHole(251, 139, 35);// turn orange
                    stickmen[0].displayWhite();
                }

                else if (m.frameCount >= 6612 && m.frameCount < 7718) {
                    bh.displayBlackHole(0, 0, 255); // blue
                    stickmen[0].displayWhite();
                }

                else if (m.frameCount >= 7718) {
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


















            // m.background(0);
            // s.backgroundStars();

            // if (m.frameCount >= 3190 && m.frameCount < 6612)// if 53.2 seconds have passed
            // {
            //     bh.displayBlackHole(251, 139, 35);// turn orange
            //     stickmen[0].displayWhite();
            // }

            // else if (m.frameCount >= 6612 && m.frameCount < 7718) {
            //     bh.displayBlackHole(0, 0, 255); // blue
            //     stickmen[0].displayWhite();
            // }

            // else if (m.frameCount >= 7718) {
            //     bh.displayBlackHole(255, 0, 0); // red
            //     stickmen[0].displayWhite();
            // } // end if
            // else {
            //     if (m.frameCount >= 10200) {
            //         bh.displayBlackHole(1, 1, 1);
            //     } // end if
            //     else {
            //         bh.displayBlackHole(255, 255, 255);
            //     } // end else

            // } // end else

    }

    public float random(double d, double e) {
        return 0;
    }
}
