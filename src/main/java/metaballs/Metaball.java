package metaballs;

public class Metaball {

    private double x, y, velx, vely;

    private static int windowWIDTH, windowHEIGHT;

    public Metaball(int x, int y, int velx, int vely) {
        this.x = x;
        this.y = y;
        this.velx = velx;
        this.vely = vely;
    }

    public double getValue(int xOth, int yOth){

        return 1/(Math.sqrt(Math.pow((x-xOth),2) + Math.pow((y-yOth), 2)));

    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setVelX(double vel){
        this.velx = vel;
    }

    public void setVelY(double vel){
        this.vely = vel;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public static void setWindowHeight(int height){
        windowHEIGHT = height;
    }

    public static void setWindowWidth(int width){
        windowWIDTH = width;
    }

    public void updatePos(){
        this.x += this.velx;
        this.y += this.vely;
        if (this.x < 0 || this.x > windowWIDTH){
            this.velx *= -1;
            this.x += this.velx*2;
        }
        if (this.y < 0 || this.y > windowHEIGHT){
            this.vely*= -1;
            this.y += this.vely*2;
        }

    }
}
