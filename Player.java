import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObj {
    private static final String img_file = "bird.png";
    private static final int SIZE = 40;
    private static final int INIT_VEL_X = 0; // no initial movement
    private static final int INIT_VEL_Y = 0; // no initial movement
    private int courtWidth = -1; 
    private int courtHeight = -1;
    private static BufferedImage img;
    
    // constructor for Player object
    public Player(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, courtWidth / 2, courtHeight / 2, SIZE, SIZE, courtWidth,
                courtHeight);
        this.courtWidth = courtWidth; // bounds
        this.courtHeight = courtHeight; // bounds
        
     // get bird picture
        try {
            img = ImageIO.read(new File(img_file));
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
    }

    // player has bounded movement
    public void move() {
        // left and right bounds
        if (pos_x + v_x <= courtWidth - SIZE && pos_x + v_x >= 0) {
            this.pos_x += v_x;
        }
        
        // top and bottom bounds
        if (pos_y + v_y <= courtHeight - SIZE && pos_y + v_y >= 0) {
            this.pos_y += v_y;
        }
    }
    
    // draws player on screen
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }  
}
