// third-level pterodactyls: diagonal movement; double missile capability
public class YellowPterodactyl extends Pterodactyl {
    private static final String img_file = "yellow_pterodactyl.png";
    private static final int INIT_VEL_X = -3; // diagonal movement; leftwards
    private static final int INIT_VEL_Y = 3; 
    
    // third-level pterodactyls have two-missile capacity
    Weapon weapon_one;
    Weapon weapon_two;
    
    // YellowPterodactyl constructor
    public YellowPterodactyl(int courtWidth, int courtHeight) {
       super(INIT_VEL_X, INIT_VEL_Y, courtWidth, courtHeight, img_file);  
    } 
    
    // third-levels can simultaneously shoot two missiles
    public void shootTwo(int x, int y, int courtWidth, int courtHeight) {
        weapon_one = new Weapon(x + this.width / 2, y, 0, 5, courtWidth, courtHeight);
        weapon_two = new Weapon(x - this.width / 2, y, 0, 5, courtWidth, courtHeight); 
    }
    
    // non-standard movement
    @Override
    public void move() {
        this.pos_x += v_x;
        this.pos_y += v_y;
    }
}
