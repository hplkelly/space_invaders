/**
 * CIS120 HW09
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

/**
 * GameCourt: primary game logic for how different objects interact with one another. 
 */

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    // state of the game logic
	private Set<Pterodactyl> pterodactyls; // set of pterodactyl enemies
	private Player player; // the user, keyboard controlled
	private String name; // player name
	
	private int numLives = 5; // player's number of lives left
	private Weapon weapon; // player's weapon
	private int score = 0; // player's score, for hi scores
	
	// max of nine jewels on screen at any given moment
	private Jewel jewelArr[][] = new Jewel [3][3];
	
	public boolean playing = false; // whether the game is running
	
	private JLabel status; // current status text 

	// game constants
	public static final int COURT_WIDTH = 700;
	public static final int COURT_HEIGHT = 500;
	public static final int PLAYER_VELOCITY = 7;
	
	// update intervals for timers, in milliseconds
	public static final int INTERVAL = 35; // refresh screen for animation
	public static final int ADD_RENEMY_TIMER = 1300; // interval for adding basic, level one enemy
	public static final int ADD_GENEMY_TIMER = 3900; // interval for adding higher level enemies
	
	// score board
    final JLabel scoreBoard = new JLabel("   Score: 0   "); // start with score of 0
    public JLabel getScoreBoard() {
        return scoreBoard;
    }
    
    // life count
    final JLabel lifeCount = new JLabel("   Lives: 5   "); // start with 5 lives
    public JLabel getLifeCount() {
        return lifeCount;
    }
    
    // main timer for game 
    Timer timer = new Timer(INTERVAL, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            tick(); // performs all requisite actions for a single game timestep
        }
    });
    
    // timer for red pterodactyl appearances
    Timer redEnemyTimer = new Timer(ADD_RENEMY_TIMER, new ActionListener() {
        @Override
        // every timestep, add a new red pterodactyl to the set of enemies
        public void actionPerformed(ActionEvent e) {
            pterodactyls.add(new RedPterodactyl (COURT_WIDTH, COURT_HEIGHT)); 
        }
    }); 
    
    // timer for green pterodactyl appearances
    Timer greenEnemyTimer = new Timer(ADD_GENEMY_TIMER, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pterodactyls.add(new GreenPterodactyl (COURT_WIDTH, COURT_HEIGHT)); 
            // green enemies should shoot missiles
            for (Pterodactyl temp : pterodactyls) {
                if (temp instanceof GreenPterodactyl) {
                    ((GreenPterodactyl) temp).shootOne(temp.pos_x + (temp.width / 2), 
                            temp.pos_y + temp.height, COURT_WIDTH, COURT_HEIGHT);
                }
            }
        }
    }); 
    
    // timer for yellow pterodactyl appearances
    Timer yellowEnemyTimer = new Timer(ADD_GENEMY_TIMER, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pterodactyls.add(new YellowPterodactyl (COURT_WIDTH, COURT_HEIGHT)); 
            // yellow enemies should shoot two missiles
            for (Pterodactyl temp : pterodactyls) {
                if (temp instanceof YellowPterodactyl) {
                    ((YellowPterodactyl) temp).shootTwo(temp.pos_x, temp.pos_y + temp.height, 
                            COURT_WIDTH, COURT_HEIGHT);
                }
            }
            
            // life bonus jewels pop up with approx. 1/3 chance
            int rand = (int) (Math.random() * 10);
            if (rand <= 3) {
                double i = Math.random();
                double j = Math.random();
                jewelArr[(int) (i * 3)][(int) (j * 3)] = new Jewel(((int) (i * COURT_WIDTH)), 
                        ((int) (j * COURT_WIDTH)), COURT_WIDTH, COURT_HEIGHT);
            }
        }
    }); 
    
    // GameCourt constructor
	public GameCourt(JLabel status) {
		// creates border around the court area
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// prompt user for username
		name = JOptionPane.showInputDialog("Please enter your name.");
		
		// check if name is valid - name cannot contain ':' delimiter
		if (name.contains(":")) {
		    name = JOptionPane.showInputDialog("Illegal character: ':' - Please re-enter your "
		            + "name.");
		}
		
		timer.start(); // start game timer
		redEnemyTimer.start(); // start level one enemy timer
		
		// enable keyboard focus on the court area
		setFocusable(true);
		
		// key listener allows player 1.) to move when an arrow key is pressed or 2.) to shoot a 
		// missile when the spacebar is pressed
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					player.v_x = -PLAYER_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					player.v_x = PLAYER_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					player.v_y = PLAYER_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					player.v_y = -PLAYER_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE && weapon == null) {
				    weapon = new Weapon(player.pos_x + (player.width / 2), 
				            player.pos_y - player.height, 0, -5, COURT_WIDTH, COURT_HEIGHT);
				}
			}

			public void keyReleased(KeyEvent e) {
				player.v_x = 0;
				player.v_y = 0;
			}
		});

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
	    timer.restart();
	    redEnemyTimer.restart();
	    greenEnemyTimer.stop(); // reset difficulty
	    yellowEnemyTimer.stop(); // reset difficulty
	    
	    // delete all jewels
	    for (int i = 0; i < jewelArr.length; i++) {
	        for (int j = 0; j < jewelArr[0].length; j++) {
	            jewelArr[i][j] = null;
	        }
	    }
	    
		player = new Player(COURT_WIDTH, COURT_HEIGHT); 
		pterodactyls = new TreeSet<Pterodactyl>(); // wipe out old enemy set

		playing = true; // restart game
		numLives = 5; // reset number of lives to 5
		lifeCount.setText("   Lives: " + numLives + "   "); // reset lifeCount display
		
		score = 0; // reset score to 0
		scoreBoard.setText("   Score: " + score+ "   "); // reset scoreBoard display
		status.setText("Running...");

		// make sure this component has the keyboard focus
		requestFocusInWindow();
	}
	
	// check if any GameObj needs to be deleted based on collisions or position
	public void checkDeletion() {
	    // make a copy of the enemy set for iteration purposes
	    Set<Pterodactyl> temp = new TreeSet<Pterodactyl>();
	    for (Pterodactyl i : pterodactyls) {
	        temp.add(i);
	    }
	    
	    // check for all pterodactyl-user collisions
	    for (Pterodactyl j : temp) {
            if (playing && player.collides(j)) {
                numLives--; // lose a life!
                lifeCount.setText("   Lives: " + numLives + "   "); // update lifeCount board
                pterodactyls.remove(j); // remove pterodactyl from set
            }
            
            // remove pterodactyls if they move off of the court
            if (playing && j.pos_y >= COURT_HEIGHT - 40) {
                pterodactyls.remove(j);
            }
            if (playing && j.pos_x >= COURT_WIDTH - 40) {
                pterodactyls.remove(j);
            }
            if (playing && j.pos_x <= 0) {
                pterodactyls.remove(j);
            }
            
            // if user kills pterodactyl by using a missile, remove said pterodactyl
            if (playing && weapon != null && j.collides(weapon)) {
                score++; // increment score
                scoreBoard.setText("   Score: " + score + "   "); // update scoreBoard
                pterodactyls.remove(j); // remove destroyed pterodactyl
                weapon = null; // destroy weapon; user can now fire a new one
            }
            
            // check if a green enemy's missile has hit user
            if (playing && j instanceof GreenPterodactyl) {
                if (((GreenPterodactyl) j).weapon != null) {
                    if (player.collides(((GreenPterodactyl) j).weapon)) {
                        numLives--;
                        lifeCount.setText("   Lives: " + numLives + "   ");
                        ((GreenPterodactyl) j).weapon = null; // remove that enemy's weapon
                    } else if (((GreenPterodactyl) j).weapon.pos_y >= COURT_HEIGHT - 40) {
                        ((GreenPterodactyl) j).weapon = null; // off screen weapons are destroyed
                    }
                }
            }
            
            // check if a yellow enemy's missile has hit user
            if (playing && j instanceof YellowPterodactyl) {
                // check first weapon
                if (((YellowPterodactyl) j).weapon_one != null) {
                    if (player.collides(((YellowPterodactyl) j).weapon_one)) {
                        numLives--;
                        lifeCount.setText("   Lives: " + numLives + "   ");
                        ((YellowPterodactyl) j).weapon_one = null;
                    } else if (((YellowPterodactyl) j).weapon_one.pos_y >= COURT_HEIGHT) {
                        ((YellowPterodactyl) j).weapon_one = null;
                    }
                }
                
                // check second weapon
                if (((YellowPterodactyl) j).weapon_two != null) {
                    if (player.collides(((YellowPterodactyl) j).weapon_two)) {
                        numLives--;
                        lifeCount.setText("   Lives: " + numLives + "   ");
                        ((YellowPterodactyl) j).weapon_two = null;
                    } else if (((YellowPterodactyl) j).weapon_two.pos_y >= COURT_HEIGHT) {
                        ((YellowPterodactyl) j).weapon_two = null;
                    }
                }
            }
            
        }
	    
	    // check for jewel deletion (and numLives increases!)
	    for (int i = 0; i < jewelArr.length; i++) {
	        for (int j = 0; j < jewelArr[0].length; j++) {
	            if (jewelArr[i][j] != null) {
	                if (player.collides(jewelArr[i][j])) {
	                    numLives++; // life bonus!
	                    lifeCount.setText("   Lives: " + numLives + "   ");
	                    jewelArr[i][j] = null; // delete jewel
	                }
	            }
	        }
	    }
	    
	    // check if player's missile has reached end of screen 
	    if (playing && weapon != null && weapon.pos_y <= 0) {
	        weapon = null; // "delete" weapon
	    }
	}
	
	// returns set of strings; each string is a line from scores.txt, where each string contains 
    // information on username and score
    public Set<String> readHiScoreNamesAndScores() {
        Set<String> namesAndScores = new TreeSet<String>();
        
        try {
            BufferedReader in = new BufferedReader(new FileReader("scores.txt"));
            String nameAndScore;
            while ((nameAndScore = in.readLine()) != null) {
                namesAndScores.add(nameAndScore);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return namesAndScores;
    }
    
	/**
	 * This method is called every time the main timer defined in the constructor triggers.
	 */
	void tick() {
	    checkDeletion(); // should any object on the GameCourt be removed/set to null?
	    
	    // introduce green enemies if score >= 5
	    if (score >= 5) {
            greenEnemyTimer.start();
        }
	    
	    // introduce yellow enemies if score >= 10
	    if (score >= 10) {
	        yellowEnemyTimer.start();
	    }
	    
	    // move game objects!
		if (playing && numLives >= 0) {
			player.move(); // move player
			
			// if applicable, move player's missile
			if (weapon != null) {
			    weapon.move(); 
			}
			
			// move all enemies and their weapons
			for (Pterodactyl temp : pterodactyls) {
			    temp.move(); // move enemy
			    
			    // move green enemy weapons
			    if (temp instanceof GreenPterodactyl) {
			        if (((GreenPterodactyl) temp).weapon != null) {
			            ((GreenPterodactyl) temp).weapon.move();
			        }
			    // move yellow enemy weapons
			    } else if (temp instanceof YellowPterodactyl) {
			        if (((YellowPterodactyl) temp).weapon_one != null) {
			            ((YellowPterodactyl) temp).weapon_one.move();
			        }
			        if (((YellowPterodactyl) temp).weapon_two != null) {
                        ((YellowPterodactyl) temp).weapon_two.move();
                    }
			    }
			}
		    
			// end game (losing) conditions
		    if (numLives <= 0) {
		        playing = false;
		        status.setText("You lose!");
		        
		        // update scores file
	            Set<String> existingHiScores = new TreeSet<String>();
	            
	            // scores that are lower than current player's
	            Set<String> trailingScore = new TreeSet<String>(); 
	            
	            // deal with existing scores (not including current player's)
	            for (String cmp : readHiScoreNamesAndScores()) {
	                int colon = cmp.indexOf(':'); // delimiter
	                if (Integer.parseInt(cmp.substring(colon + 2, cmp.length())) >= score) {
	                    existingHiScores.add(cmp); // beat current player
	                } else {
	                     trailingScore.add(cmp); // lost to current player
	                }
	            }
	            
	            // if there are 4 or fewer people who have beaten the current player, current 
	            // player can now be added to high-scoring set
	            if (existingHiScores.size() < 5) {
	                existingHiScores.add("" + name + ": " + score);
	            }
	            
	            // trailing scores can now be added, if size permits
	            if (existingHiScores.size() < 5) {
	                for (String j : trailingScore) {
	                    if (existingHiScores.size() < 5) {
	                        existingHiScores.add(j);
	                    }
	                }
	            }
	            
	            // store high-scorers in scores.txt
	            boolean firstElt = true;
	            for (String s : existingHiScores) {
	                try {
	                    BufferedWriter out;
	                    if (firstElt) {
	                        // overwrite file
	                        out = new BufferedWriter(new FileWriter("scores.txt")); 
                        } else {
                            // append to end of file
                            out = new BufferedWriter(new FileWriter("scores.txt", true));
                            out.newLine();
                        }
                            out.append(s);
                            out.close();
                    } catch (IOException e) {
                        System.out.println("File not found");
                    } 
                    firstElt = false;
                }
	                        
	            // display hi scores 
	            String winners = "High Scores:" + "\n";
	            String[] theWinners = new String [existingHiScores.size()];
	            int i = 0; 
	            // read in scores into an array 
	            for (String s : existingHiScores) {
	                if (i < existingHiScores.size()) {
	                    theWinners[i] = s;
	                    i++;
	                } else {
	                    break;
	                }
	            }
	                        
	            // need to display winners in order: bubble sort on array
	            int counter = 0; // keeps track of number of passes that have been made
	            while (counter < theWinners.length) {
	                for (int j = 0; j < theWinners.length - 1; j++) {
	                    String temp = theWinners[j];
	                    int colon = temp.indexOf(':');
	                    int firstComp = Integer.parseInt(temp.substring(colon + 2, temp.length()));
	                    temp = theWinners[j + 1];
	                    colon = temp.indexOf(':');
	                    int secondComp = Integer.parseInt(temp.substring(colon + 2, temp.length()));
	                    
	                    // swap items in indices of necessary
	                    if (firstComp < secondComp) {
	                        theWinners[j + 1] = theWinners[j];
	                        theWinners[j] = temp;
	                    }                 
	                }
	                counter++;
	            }
	                     
	            for (int j = 0; j < theWinners.length; j++) {
	                winners = winners + theWinners[j] + "\n"; 
	            }
	            
	            // display winners
	            JOptionPane.showMessageDialog(null, winners);
		   }   
		      
			// update display
			repaint();
		}
	}

	// paint GameObjs
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
		
		// paint all enemies and their weapons
		for (Pterodactyl temp : pterodactyls) {
		    temp.draw(g); // draw enemy
		    
		    // draw enemy weapons
		    if (temp instanceof GreenPterodactyl) {
		        if (((GreenPterodactyl) temp).weapon != null) {
		            ((GreenPterodactyl) temp).weapon.draw(g);
		        }
		    } else if (temp instanceof YellowPterodactyl) {
		        if (((YellowPterodactyl) temp).weapon_one != null) {
                    ((YellowPterodactyl) temp).weapon_one.draw(g);
                }
		        if (((YellowPterodactyl) temp).weapon_two != null) {
                    ((YellowPterodactyl) temp).weapon_two.draw(g);
                }
		    }
		} 
		
		// draw jewels
		for (int i = 0; i < jewelArr.length; i++) {
            for (int j = 0; j < jewelArr[0].length; j++) {
                if (jewelArr[i][j] != null) {
                    jewelArr[i][j].draw(g);
                }
            }
        }
		
		player.draw(g); // re-draw player
		
		// draw player weapon, if necessary
		if (weapon != null) {
		    weapon.draw(g);
		}
	}
	
	// dimensions of screen
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
