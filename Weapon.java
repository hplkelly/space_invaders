import java.awt.*;

// weapons are objects of the game that appear on the GameCourt
public class Weapon extends GameObj {
    private static final int width = 5;
    private static final int height = 30;
    
    // constructor for Weapon object
    public Weapon(int initPosX, int initPosY, int velocityX, int velocityY, int courtWidth, 
            int courtHeight) {
        super (velocityX, velocityY, initPosX, initPosY, width, height, courtWidth, courtHeight);
    }
    
    // weapons should move across the GameCourt
    public void move() {
        this.pos_x += v_x;
        this.pos_y += v_y;
    }
    
    // method for making weapon appear on screen
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(pos_x, pos_y, width, height); 
       
    } 
}
