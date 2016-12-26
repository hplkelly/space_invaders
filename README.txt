=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: hungp
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: after the user wins 10 points, jewels begin to appear on the screen at each 
  timestep with probability approximately 1/3. These jewels are stored in a 2D array. Since there 
  can be several jewels on the screen at once (but no more than 9), it makes sense to store them 
  within some type of data structure. And, because the jewels do not move and because the 
  locations of the jewels can be modeled in a grid-like network, this is appropriate. Iterating 
  over the 2D array is not time-costly because there are only 9 indices in total. 

  2. Collections: the game contains a set of Pterodactyls (enemies), which are added to the set 
  as appropriate when a given timer hits the appropriate interval. These Pterodactyls are removed 
  when player-enemy collisions occur, when they are hit by a missile fired by the player, or when 
  their x- or y- positions exceed the court width or court height. Each enemy either exists in the 
  game or doesn't, so there is no need to use a Map structure to model this; as such, a Set is 
  appropriate in intuitively and efficiently iterating over each existing enemy.  

  3. I/O: my game prompts the user to input their name at the outset of the game. The user's name
  may later be stored in a scores.txt file that is formatted as follows:
  	Ada: 5
  	Bob: 2
  	Charles: 1
  	
  	- where the ':' is a delimiter. As such, when the user's name is prompted at the beginning of
  	the game, if the inputted name contains a colon, the user is prompted to input another String.
  	
  	When the user loses, if their score is a top 5 score, then their name gets written to the file,
  	and the scores get displayed in a pop-up (the pop-up pops up regardless). 
  	
  4. Inheritance/Subtyping + Dynamic Dispatch: given that different types of enemies have different
  types of similar functionality, I decided to use dynamic dispatch. The basic Pterodactyl (which 
  is red) can only do one thing: move vertically downward. The next level Pterodactyl (green) moves
  differently (diagonally downward and to the right). More importantly, it can shoot missiles, 
  which is a capability that the red pterodactyl does not have. The last sub-type of Pterodactyl, 
  the YellowPterodactyl, also moves differently, and it shoots out two missiles simultaneously. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  	- Game.java: contains a class with a simple main method; gets the window up and running; pops 
  	up instructions for user; sets up basic displays and buttons
  	- GameCourt.java: the main playing stage of the game. Here, GameObjs appear, and the user 
  	plays within the court
  	- GameObj: an object in the game that appears on the GameCourt; they can move and interact with
  	each other (with collisions)
  		- Pterodactyl.java: the enemy class. They can move and collide with the user, which 
  		decreases the user's number of lives
  			- RedPterodactyl.java: the basic pterodactyl. They can only move and collide.
  			- GreenPterodactyl.java: the middle pterodactyl. They can move diagonally and shoot 
  			missiles.
  			- YellowPterodactyl.java: the last pterodactyl. They can move diagonally (but in a 
  			different direction from the GreenPterodactyls) and can shoot two missiles 
  			simultaneously.
  		- Jewel: a GameObj. When one is eaten by the Player, the Player gains a life.
  		- Player: the user's on screen representation. The Player can move within the court's 
  		bounds and can shoot one missile at a time. When these missiles collide with an enemy, said 
  		enemy gets removed from the game, and the user's score increases.
  		- Weapon: these can be fired by the Player upon a spacebar press. They can also be fired by
  		select sub-types of Pterodactyls. When an enemy's weapon hits the player, the player loses
  		a life. When the player's weapon hits an enemy, that enemy dies, and the player's score 
  		increases by 1.
  	
  	
- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I found it difficult to work with layout and the aesthetic aspect of designing my game. Of 
  course, I can work to improve the basic aesthetics (button layout, image files) of the game in 
  the future, but finding good image files (transparent background, etc.) - though perhaps not a 
  huge concern - was a bit frustrating.
  
  In terms of actually implementing the game logic, I didn't find any part - beyond the usual, 
  "expected" bugs - particularly frustrating, and issues that I had were usually resolved in office
  hours.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  Every class's fields are either private or package-private. Bounds are placed within relevant 
  classes to protect what the fields can be reset to (for example, the Player cannot fly off the 
  screen; controls for the player's position are set in the class). 
  
  If given the chance to refactor, I might separate the IO functionality into a separate class, as 
  it is currently contained in GameCourt.java


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

	Images:
	- bird.png: from "birdclipart.com" 
	
	- red_pterodactyl.png: from "pinterest.com/jsmdragongirl/how-to-draw-dragons/"
	
	- green_pterodactyl.png: from "http://photobucket.com/images/small%20flying%20green%20dragon"
	
	- yellow_pterodactyl.png: from "claytonkashuba.com"
	
	- jewel.png: from "clipartpanda.com"
