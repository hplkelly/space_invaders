/*
 * CIS120 HW09
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Game Main class: specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
	    // instructions window - game play, etc.
	    JOptionPane.showMessageDialog(null, 
	            "Help Polly get home safely! \n\n"
	          + "Dodge the incoming pterodactyls by pressing the up, down, left, and right keys \n"
	          + "on your keyboard in order to move. If Polly collides with a pterodactyl, she \n"
	          + "loses a life. \n\n"
	          + "Beware of the more dangerous green and yellow pterodactyls that begin to \n"
	          + "arrive when you gain a sufficient number of points - they can shoot missiles. \n"
	          + "The green pterodactyl shoots a single missile; the yellow, two. And if Polly \n"
	          + "gets hit by a missile, she'll lose a life. \n\n"
	          + "You can destroy incoming pterodactyls by pressing the spacebar, which launches \n"
	          + "a missile. Note, however, that Polly can only launch a single missile at a time \n"
	          + "- after all, she's a small bird - so aim carefully, and tread wisely. \n\n"
	          + "Finally, sometimes, jewels will pop up on the screen. Eat these quickly - they \n"
	          + "may disappear after a few seconds! Each jewel eaten gives Polly an extra life, \n"
	          + "but once Polly loses all of her lives, the game is lost! \n\n"
	          + "Have a safe flight!");
	          
		// top-level frame, where game components live
		final JFrame frame = new JFrame("PTERRATTACK");
		frame.setLocation(300, 300);

		// status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		
		// display status
		final JLabel status = new JLabel("Running...");
		status_panel.add(status);

		// main playing area
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);
		
		// create control panel
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);
		
		// reset button
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		control_panel.add(reset); // add reset button to control panel

		// quit button
		final JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // exit game safely
            }
        });
        control_panel.add(quit); // add quit button to control panel
        
        // game stats 
        final JPanel game_stats_panel = new JPanel();
        frame.add(game_stats_panel, BorderLayout.EAST);
        
        // vertical layout
        game_stats_panel.setLayout(new BoxLayout(game_stats_panel, BoxLayout.Y_AXIS));
		game_stats_panel.add(court.getScoreBoard()); 
		game_stats_panel.add(court.getLifeCount());
		
		// put frame on screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// start game
		court.reset();
	}
	
	/*
	 * main method to start and run game; initializes the GUI elements
	 * specified in Game  
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
