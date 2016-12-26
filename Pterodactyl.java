import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/* Pterodactyl enemies are game objects. Because pterodactyls will be stored in a set in the 
 * GameCourt, the pterodactyl class must implement Comparable<Pterodactyl> */
public class Pterodactyl extends GameObj implements Comparable<Pterodactyl> {
    private static final int SIZE = 60; // height and width of the enemy
    private static final int INIT_POS_Y = 20; // standard beginning y-position for all pterodactyls
    private BufferedImage img; // picture of pterodactyl
    
    public Pterodactyl(int initialVelocityX, int initialVelocityY, int courtWidth, int courtHeight, 
            String img_file) {
        
        // initialize velocity, position, size, etc.
        // initial x-position is randomized for every pterodactyl
        super(initialVelocityX, initialVelocityY, (int) Math.floor(Math.random() * 
                (courtWidth - 40)), INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        
        // store pterodactyl picture
        try {
            img = ImageIO.read(new File(img_file)); 
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
    }
    
    // helper method: returns name of image file; for debugging only
    public String getImageName() {
        return img.toString();
    }
    
    // how to draw a pterodactyl object
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }
    
    // simulates pterodactyl's movement; standard pterodactyl has y-directional movement only
    public void move() {
        this.pos_y += v_y;
    }
    
    // implementing Comparable<Pterodactyl>
    @Override
    public int compareTo(Pterodactyl p) {
        if (this.pos_x > p.pos_x) {
            return 1;
        } else if (this.pos_x < p.pos_x) {
            return -1;
        } else {
            return 0;
        }
    }
}