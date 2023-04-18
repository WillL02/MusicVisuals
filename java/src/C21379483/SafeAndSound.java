package C21379483;

import C21423244.Monitor;

import static C21423244.Monitor.*;


public class SafeAndSound {

  // Declaring Variables
  Monitor m;

  //Program starts with 1 mouse and goes up to 12
  int mouseNum = 1;
  int maxMice = 12;

  //The rotation used for all the objects in the program
  float angle, lerpedAverage = 0;


  public SafeAndSound(Monitor m) 
  {
    this.m = m;
  }

  public void render() {
    //Start of the program
    if (m.frameCount < 245) {
      startLoad();
    } else { // Load-up is done, now actual visual starts
      loadPicture();
      exitIcon("Exit", m.width - 120, m.height - 120);
      exitIconHover();

      if (m.song.isPlaying()) { // Makes it so when the song finishes, they dissapear
        m.pushMatrix();
        mouseFunct();
        m.popMatrix();
      }

      dancingCircle();
      if (m.frameCount > 245 && m.song.isPlaying()) {
        dancingRectangle();
      }
    }
  }

  void startLoad() {
    //Load in effect, with 10,000 dots drawing a second
    for (int i = 0; i < 10000; i++) {
      float x = m.random(m.width);
      float y = m.random(m.height);
      int c = m.windowsxp.get((int) x, (int) y);
      m.fill(c, 25);
      m.noStroke();
      m.ellipse(x, y, 6, 6);
      
    }
  }

  void loadPicture() {
    //Loads the background for the main part of the program
    m.image(m.windowsxp2, 0, 0);
  }

  void mouseFunct() {
    //Gets the rotation and movement for the mouse
    float total = 0;
    for (int i = 0; i < m.ab.size(); i++) {
      total += abs(m.ab.get(i));
    }
    float average = total / (float) m.ab.size();
    lerpedAverage = lerp(lerpedAverage, average, 0.1f);

    // Mouse images functionality added
    for (int i = 0; i < mouseNum; i++) {
      m.mouses[i].move(lerpedAverage);
      m.mouses[i].rotates(lerpedAverage);
      m.mouses[i].display();
      m.mouses[i].side();
    }
    // Adds a new mouse every 100 frames until there's 12, so they don't spawn at once
    if (m.frameCount % 100 == 0 && mouseNum < maxMice) {
      mouseNum++;
    }
  }

  void dancingRectangle() {
    for (int y = 0; y < 100; y++) {
      m.stroke(255 - y % 10);
      m.strokeWeight(10);
      m.fill(255 - y * 10, 255 - y, 255 - y);
      m.scale(0.95f);
      m.rotate(radians(angle));
      m.rect(0, 0, 580, 580);
    }
    float total = 0;
    for (int i = 0; i < m.ab.size(); i++) {
      total += abs(m.ab.get(i));
    }
    float average = total / (float) m.ab.size();
    lerpedAverage = lerp(lerpedAverage, average, 0.1f);
    angle += map(lerpedAverage, 0, 1.0f, 0, 1.5f);
  }

  void dancingCircle() {
    int x = 0; // Circle spin
    int radius = 400; // Radius in pixels of the circle
    // Circle starts here

    m.translate(m.width / 2, m.height / 2);
    // Audio Visualization
    m.fft.forward(m.song.mix);
    float bands = m.fft.specSize();

    for (int i = 0; i < bands * 2; i++) {

      // Starting positions of line
      float start_x = radius * cos(PI * (i + x) / bands);
      float start_y = radius * sin(PI * (i + x) / bands);

      // Draw line based on sound
      float c = map(i, 0, m.ab.size(), 0, 255);
      m.stroke(c, 255, 255);
      m.strokeWeight(5);
      if (i < bands) {
        // Line based on band length
        m.line(start_x, start_y, start_x + m.fft.getBand(i) * 7 * cos(PI * (i + x) / bands),
            start_y + m.fft.getBand(i) * 7 * sin(PI * (i + x) / bands));
      } else {
        // Line based on frequency
        m.line(start_x, start_y, start_x + m.fft.getFreq(i) * 5 * cos(PI * (i + x) / bands),
            start_y + m.fft.getFreq(i) * 5 * sin(PI * (i + x) / bands));
      }
    }
  }

  void exitIcon(String text, float x, float y) {
    m.exit = m.loadImage("exit.png");
    m.image(m.exit, x, y);
    m.fill(0, 408, 612);
    m.textSize(25);
    m.text(text, x + 8, y + 77);
  }

  void exitIconHover() {
    if (m.mouseX > m.width - 140 && m.mouseX < m.width - 20 && m.mouseY > m.height - 130 && m.mouseY < m.height - 20) {
      m.cursor(Monitor.HAND); // Changes the curser when hovering over the icon
      m.noStroke();
      m.fill(49, 182, 255, 65);
      m.rect(m.width - 92, m.height - 85, 100, 100);
      // Exits the program if EXIT icon is pressed
      if (m.mousePressed) {
        m.song.pause();
        m.minim.stop();
        m.frameCount = 0;
        m.cursor(Monitor.ARROW); // Default curser everywhere else
        m.LoadComputer();
       
      }
    } else {
      m.cursor(Monitor.ARROW); // Default curser everywhere else
    }
    // If the moving mouse hovers over the exit icon, it draws retangle too
    for (int i = 0; i < maxMice; i++) {
      float mouseXPos = m.mouses[i].getX();
      float mouseYPos = m.mouses[i].getY();
      float mouseSize = m.mouses[i].getSize();

      if (mouseXPos + mouseSize > m.width - 140 && mouseXPos + mouseSize < m.width - 20
          && mouseYPos + mouseSize > m.height - 130 && mouseYPos + mouseSize <m. height - 20) {
        m.noStroke();
        m.fill(49, 182, 255, 65);
        m.rect(m.width - 92, m.height - 85, 100, 100);
      }
    }
  }
}
