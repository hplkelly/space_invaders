import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// Jewels are game objects that appear on the GameCourt
public class Jewel extends GameObj {
    public static final String img_file = "jewel.png";
    public static final int SIZE = 20;
    public static final int INIT_VEL_X = 0; // jewels don't move
    public static final int INIT_VEL_Y = 0; // jewels don't move

    private static BufferedImage img;

    // constructor for new Jewel object
    public Jewel (int xPos, int yPos, int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, xPos, yPos, SIZE, SIZE, courtWidth,
                courtHeight);
        
        // try to get jewel image
        try {
            img = ImageIO.read(new File(img_file));
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
    }

    // display jewel on screen
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }
}
