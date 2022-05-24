package metaballs;

import java.awt.Color;
import java.util.ArrayList;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
//import processing.data.JSONObject;
//import processing.data.JSONArray;
//import processing.core.PFont;

public class App extends PApplet {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int TOPBAR = 80;

    public static final int FPS = 60;

    public PImage screenDisplay;

    public ArrayList<Metaball> allBalls;

    public double boundary;

    public Metaball closest;



    public App() {
        return;
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        // fullScreen();
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        
        frameRate(FPS);
        screenDisplay = new PImage(width, height);
        Metaball.setWindowHeight(height);
        Metaball.setWindowWidth(width);
        allBalls = new ArrayList<Metaball>();
        allBalls.add(new Metaball(500, 500, 10, 3));
        allBalls.add(new Metaball(100, 100, 4, 8));
        boundary = 0.03;


    }
	
    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        screenDisplay.loadPixels();
        double total;
        for (int i = 0; i < screenDisplay.pixels.length; i++){
            total = 0;
            for (Metaball ball : allBalls){
                total += ball.getValue(i%screenDisplay.pixelWidth, i/screenDisplay.pixelWidth);
            }

            if (total < boundary){
                screenDisplay.pixels[i] = java.awt.Color.HSBtoRGB((float)(total/boundary), 0.1f, 1);
            } else {
                screenDisplay.pixels[i] = color(150, 150, 150);
            }
        }
        screenDisplay.updatePixels();
        for (Metaball ball :allBalls){
            ball.updatePos();
        }
        this.image(screenDisplay, 0, 0);
    }

    public void mousePressed(){
        this.closest = allBalls.get(0);
        float minDist = distance(this.closest);
        for (Metaball ball : allBalls){
            float currentDist = distance(ball);
            if (currentDist < minDist){
                this.closest = ball;
                minDist = currentDist;
            }
        }
        if (minDist > 200){
            closest = null;
            return;
        }
        this.closest.setX(mouseX);
        this.closest.setY(mouseY);
        this.closest.setVelX(0);
        this.closest.setVelY(0);
    }

    public void mouseDragged(){
        if (this.closest == null){
            this.closest = allBalls.get(0);
            float minDist = distance(this.closest);
            for (Metaball ball : allBalls){
                float currentDist = distance(ball);
                if (currentDist < minDist){
                    this.closest = ball;
                    minDist = currentDist;
                }
            }
            if (minDist > 200){
                closest = null;
                return;
            }
            this.closest.setVelX(0);
            this.closest.setVelY(0);
        }
        
        this.closest.setX(mouseX);
        this.closest.setY(mouseY);
        
    }

    public void mouseReleased(){
        if (this.closest == null){
            return;
        }
        float randomAngle = this.random((float)Math.PI*2);
        this.closest.setVelX(5*Math.cos(randomAngle));
        this.closest.setVelY(5*Math.sin(randomAngle));

    }

    public void keyPressed(){
       if (this.keyCode == 78){
        allBalls.add(new Metaball(mouseX, mouseY, (int)this.random(5), (int)this.random(5)));
        boundary = 0.01*Math.sqrt(allBalls.size());
       }
   }

   public float distance(Metaball ball){
       return (float)Math.sqrt(Math.pow(ball.getX()-mouseX, 2) + Math.pow(ball.getY()-mouseY, 2));
   }


    public static void main(String[] args) {
        PApplet.main("metaballs.App");
    }
}
