// second-level pterodactyls: diagonal movement; single missile capability
public class GreenPterodactyl extends Pterodactyl {
    private static final String img_file = "green_pterodactyl.png";
    private static final int INIT_VEL_X = 3; // pterodactyl has x-directional movement
    private static final int INIT_VEL_Y = 3; // pterodactyl flies downwards
    Weapon weapon; // package private; second-level pterodactyls have weapons
    
    // constructor for GreenPterodactyl
    public GreenPterodactyl(int courtWidth, int courtHeight) {
       super(INIT_VEL_X, INIT_VEL_Y, courtWidth, courtHeight, img_file);  
    } 
    
    // second-level pterodactyl can shoot one missile 
    public void shootOne(int x, int y, int courtWidth, int courtHeight) {
        // weapon speed is greater than speed of Pterodactyl objects
        weapon = new Weapon(x, y, 0, 5, courtWidth, courtHeight);
    }
    
    // helper method; returns this GreenPterodactyl's weapon; for debugging only
    public Weapon getWeapon() {
        return weapon;
    }
    
    // non-standard movement
    @Override
    public void move() {
        this.pos_x += v_x;
        this.pos_y += v_y;
    }
}
