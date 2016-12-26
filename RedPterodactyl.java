// first-level pterodactyls: basic movement, no missiles
public class RedPterodactyl extends Pterodactyl {
    private static final String img_file = "red_pterodactyl.png";
    private static final int INIT_VEL_X = 0; // pterodactyl flies straight down
    private static final int INIT_VEL_Y = 3; 
    
    // construct the object, feeding in arguments to the super class constructor
    public RedPterodactyl(int courtWidth, int courtHeight) {
       super(INIT_VEL_X, INIT_VEL_Y, courtWidth, courtHeight, img_file);  
    } 
    
    // level 1 pterodactyl: standard, downward movement; no x-directional movement
    @Override
    public void move() {
        this.pos_y += v_y;
    }
}
