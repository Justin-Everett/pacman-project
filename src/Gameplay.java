// importing libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	// initiate the timer and delay
	private Timer timer;
	private int delay = 50;

	// generate all images used to display the grid
	private ImageIcon tlc = new ImageIcon("img/CornerWalls.png");
	private ImageIcon top = new ImageIcon("img/TopWalls.png");
	private ImageIcon side = new ImageIcon("img/SideWalls.png");
	private ImageIcon food = new ImageIcon("img/Dots.png");
	private ImageIcon brc = new ImageIcon("img/brc.png");
	private ImageIcon blc = new ImageIcon("img/blc.png");
	private ImageIcon trc = new ImageIcon("img/trc.png");
	private ImageIcon b = new ImageIcon("img/b.png");
	private ImageIcon u = new ImageIcon("img/u.png");
	private ImageIcon r = new ImageIcon("img/r.png");
	private ImageIcon l = new ImageIcon("img/l.png");
	private ImageIcon bar = new ImageIcon("img/bar.png");

	// generate images for all the ghosts
	private ImageIcon red = new ImageIcon("img/redGhost.png");
	private ImageIcon blue = new ImageIcon("img/blueGhost.png");
	private ImageIcon pink = new ImageIcon("img/pinkGhost.png");
	private ImageIcon orange = new ImageIcon("img/orangeGhost.png");

	private ImageIcon life = new ImageIcon("img/Lives.png");

	/**
	 * images used to animate pacman
	 */
	private ImageIcon rightKeyFrame1 = new ImageIcon("img/rightKeyFrame1 (1).png");
	private ImageIcon rightKeyFrame2 = new ImageIcon("img/rightKeyFrame2.png");
	private ImageIcon leftKeyFrame1 = new ImageIcon("img/leftKeyFrame1.png");
	private ImageIcon leftKeyFrame2 = new ImageIcon("img/leftKeyFrame2.png");
	private ImageIcon upKeyFrame1 = new ImageIcon("img/upKeyFrame1.png");
	private ImageIcon upKeyFrame2 = new ImageIcon("img/upKeyFrame2.png");
	private ImageIcon downKeyFrame1 = new ImageIcon("img/downKeyFrame1.png");
	private ImageIcon downKeyFrame2 = new ImageIcon("img/downKeyFrame2.png");
	private ImageIcon keyFrame3 = new ImageIcon("img/keyFrame3.png");
	private ImageIcon startFrame = new ImageIcon("img/keyFrame3.png");

	private static int score;

	// booleans which represent which direction pacman is facing
	private static boolean left;
	private static boolean right;
	private static boolean up;
	private static boolean down;

	/*
	 * gameStates: gameState 0 represents the first frame of the game where pacman
	 * is stationary, gameStates 1-4 are used to animate pacman, gameState 6 is the
	 * game over screen, gameState 5 is the win screen
	 */
	public static int gameState = 0;

	private int lives = 2;

	/**
	 * create a new pacman object
	 */
	public static Pacman pacman = new Pacman();

	/**
	 * create four new ghost objects, positioned at specific points on the grid
	 */
	static Ghost inky = new Ghost(12, 12);
	static Ghost blinky = new Ghost(12, 12);
	static Ghost pinky = new Ghost(12, 12);
	static Ghost clyde = new Ghost(12, 12);

	/**
	 * grid is a two dimensional array which contains ints each int represents a
	 * different 25 pixel square image to be painted in that location within the
	 * grid when the program is displayed
	 */
	static int[][] grid = { { 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 6, 1, 1, 5, 0, 6, 1, 1, 5, 0, 2, 0, 6, 1, 1, 5, 0, 6, 1, 1, 5, 0, 2 },
			{ 2, 0, 2, 13, 13, 2, 0, 2, 13, 13, 2, 0, 2, 0, 2, 13, 13, 2, 0, 2, 13, 13, 2, 0, 2 },
			{ 2, 0, 4, 1, 1, 3, 0, 4, 1, 1, 3, 0, 2, 0, 4, 1, 1, 3, 0, 4, 1, 1, 3, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 8, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2 },
			{ 4, 1, 1, 1, 1, 5, 0, 10, 1, 1, 1, 13, 2, 13, 1, 1, 1, 9, 0, 6, 1, 1, 1, 1, 3 },
			{ 13, 13, 13, 13, 13, 2, 0, 2, 13, 13, 13, 13, 13, 13, 13, 13, 13, 2, 0, 2, 13, 13, 13, 13, 13 },
			{ 13, 13, 13, 13, 13, 2, 0, 2, 13, 6, 1, 12, 12, 12, 1, 5, 13, 2, 0, 2, 13, 13, 13, 13, 13 },
			{ 13, 13, 1, 1, 1, 3, 0, 2, 13, 2, 13, 13, 13, 13, 13, 2, 13, 2, 0, 4, 1, 1, 1, 13, 13 },
			{ 13, 13, 13, 13, 13, 13, 0, 13, 13, 2, 13, 13, 13, 13, 13, 2, 13, 13, 0, 13, 13, 13, 13, 13, 13 },
			{ 13, 13, 1, 1, 1, 5, 0, 2, 13, 2, 13, 13, 13, 13, 13, 2, 13, 2, 0, 6, 1, 1, 1, 13, 13 },
			{ 13, 13, 13, 13, 13, 2, 0, 2, 13, 4, 1, 1, 1, 1, 1, 3, 13, 2, 0, 2, 13, 13, 13, 13, 13 },
			{ 13, 13, 13, 13, 13, 2, 0, 2, 13, 13, 13, 13, 13, 13, 13, 13, 13, 2, 0, 2, 13, 13, 13, 13, 13 },
			{ 6, 1, 1, 1, 1, 3, 0, 2, 13, 1, 1, 1, 8, 1, 1, 1, 13, 2, 0, 4, 1, 1, 1, 1, 5 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 1, 1, 1, 5, 0, 1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1, 0, 6, 1, 1, 1, 0, 2 },
			{ 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2 },
			{ 10, 1, 1, 1, 0, 2, 0, 2, 0, 1, 1, 1, 8, 1, 1, 1, 0, 2, 0, 2, 0, 1, 1, 1, 9 },
			{ 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 1, 1, 1, 1, 1, 7, 1, 1, 1, 0, 2, 0, 1, 1, 1, 7, 1, 1, 1, 1, 1, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3 } };

	/**
	 * Gameplay creates a new Gameplay object, which adds a key listener, sets this
	 * component to focusable, and creates and starts a new timer
	 */
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	/**
	 * addScore increments the score by 5
	 */
	public static void addScore() {
		score = score + 5;
	}

	/**
	 * getCoords takes in an x and y value, converts them into a pixel position
	 * seperated by 25 pixels, then returns the values in an array
	 * 
	 * @param x is the x coordinate input
	 * @param y is the y coordinate input
	 * @return one dimensional array of x and y values
	 */
	public static int[] getCoords(int x, int y) {
		int pixelX = 25 * x + 100;
		int pixelY = 25 * y + 50;
		int[] position = { pixelX, pixelY };
		return position;
	}

	/**
	 * 
	 * @param x is the position array of object one
	 * @param y is the position array of object two
	 * @return true if object one is on top of object two, else return false
	 */
	public boolean checkTrue(int[] x, int[] y) {
		if (x[0] == y[0] && x[1] == y[1]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * checkPos checks whether pacman's position is currently the same as any of the
	 * ghosts' positions
	 */
	public void checkPos() {
		// if the score is equal to 1040 which is the max score, the game state is set
		// to 5 which is the win state
		if (score == 1040) {
			gameState = 5;
			repaint();
		}
		if (checkTrue(pacman.getPos(), (inky.getPos())) || checkTrue(pacman.getPos(), (blinky.getPos()))
				|| checkTrue(pacman.getPos(), (pinky.getPos())) || checkTrue(pacman.getPos(), (clyde.getPos()))) {
			if (lives == 0) {
				// if pacman is on a ghost and has zero lives remaining, the gamestate is set to
				// 6, which is the game over state
				gameState = 6;
				repaint();
			} else {
				// if pacman is on a ghost, it resets the life that he is currently on then
				// repaints
				startNewLife();
				repaint();
			}

		}
	}

	// setDirection takes in an int value and equates it to a direction, then sets
	// that direction to true and all the other directions to false
	public static void setDirection(int direction) {
		if (direction == 1) { // right
			down = false;
			up = false;
			left = false;
			right = true;
		} else if (direction == 2) { // left
			down = false;
			up = false;
			left = true;
			right = false;
		} else if (direction == 3) { // up
			down = false;
			up = true;
			left = false;
			right = false;
		} else if (direction == 4) { // down
			down = true;
			up = false;
			left = false;
			right = false;
		}

	}

	/**
	 * paint deals with displaying all of the graphics on the screen, and can be
	 * re-referenced via the built in repaint() method
	 */
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 1200, 770);
		if (gameState == 5) {
			// if the gameState is 5, the player has won so a win screen is shown
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 1200, 770);
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("You Win!!!", 450, 385);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.PLAIN, 30));
			g.drawString("Score: " + score, 510, 420);
		} else if (gameState == 6) {
			// if the gameState is 6, the player has lost so a game over screen is shown
			g.setColor(Color.RED);
			g.fillRect(0, 0, 1200, 770);
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("Game Over", 450, 385);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.PLAIN, 30));
			g.drawString("Score: " + score, 510, 420);
		} else {
			// loops through each index in grid, and paints a specific image depending on
			// the value of the int at that index
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					switch (grid[y][x]) {
					case 0:
						food.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 1:
						top.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 2:
						side.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 3:
						brc.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 4:
						blc.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 5:
						trc.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 6:
						tlc.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 7:
						b.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 8:
						u.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 9:
						l.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 10:
						r.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 11:
						g.setColor(Color.black);
						g.fillRect(getCoords(x, y)[0], getCoords(x, y)[1], 25, 25);
						break;
					case 12:
						bar.paintIcon(this, g, getCoords(x, y)[0], getCoords(x, y)[1]);
						break;
					case 13:
						g.setColor(Color.black);
						g.fillRect(getCoords(x, y)[0], getCoords(x, y)[1], 25, 25);
						break;
					}
				}
			}
			// fill a black rectangle to cover previous paints
			g.setColor(Color.black);
			g.fillRect(750, 620, 120, 50);

			// display the remaining lives
			switch (lives) {
			case 0:
				break;
			case 1:
				life.paintIcon(this, g, 750, 620);
				break;
			case 2:
				life.paintIcon(this, g, 750, 620);
				life.paintIcon(this, g, 810, 620);
				break;
			}
			// at the beginning of the game, pacman is painted at its original coordinates
			if (gameState == 0) {
				startFrame.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
						getCoords(pacman.getXPos(), pacman.getYPos())[1]);

			}
			// at the beginning of the game, pacman is painted at its original coordinates
			if (gameState == 0) {
				startFrame.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
						getCoords(pacman.getXPos(), pacman.getYPos())[1]);

			}

			// in game state 1, pacman is painted at it's current position facing the
			// correct direction, on the first frame of the 3 frame animation
			if (gameState == 1) {
				if (left) {
					leftKeyFrame1.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (right) {
					rightKeyFrame1.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (up) {
					upKeyFrame1.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (down) {
					downKeyFrame1.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				}
			}

			// in game state 2, pacman is painted in the second frame of its animation, 8
			// pixels ahead of the last tile in the correct direction
			if (gameState == 2) {
				if (right) {
					rightKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (left) {
					leftKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (up) {
					upKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (down) {
					downKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				}
			}

			// in game state 3, pacman is painted in the third frame of its animation, 8
			// pixels ahead of the last tile in the correct direction
			if (gameState == 3) {
				if (right) {
					keyFrame3.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (left) {
					keyFrame3.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (up) {
					keyFrame3.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (down) {
					keyFrame3.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				}
			}

			// in game state 4, pacman is painted in the fourth frame of its animation, 8
			// pixels ahead of the last tile in the correct direction
			if (gameState == 4) {
				if (right) {
					rightKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (left) {
					leftKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (up) {
					upKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				} else if (down) {
					downKeyFrame2.paintIcon(this, g, getCoords(pacman.getXPos(), pacman.getYPos())[0],
							getCoords(pacman.getXPos(), pacman.getYPos())[1]);
				}
			}

			// paint each ghost at their current x and y position
			blue.paintIcon(this, g, getCoords(inky.getXPos(), inky.getXPos())[0],
					getCoords(inky.getYPos(), inky.getYPos())[1]);
			red.paintIcon(this, g, getCoords(blinky.getXPos(), blinky.getXPos())[0],
					getCoords(blinky.getYPos(), blinky.getYPos())[1]);
			pink.paintIcon(this, g, getCoords(pinky.getXPos(), pinky.getXPos())[0],
					getCoords(pinky.getYPos(), pinky.getYPos())[1]);
			orange.paintIcon(this, g, getCoords(clyde.getXPos(), clyde.getXPos())[0],
					getCoords(clyde.getYPos(), clyde.getYPos())[1]);
			// display the score
			g.setColor(Color.black);
			g.fillRect(800, 100, 300, 150);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Score: " + String.valueOf(score), 850, 150);
		}
		// release the current graphics data to conserve memory
		g.dispose();
	}

	/*
	 * checks whether the value in the grid to the right of pacman is empty space or
	 * food, and returns true if so
	 */
	public boolean checkRight() {
		int rightPos = grid[pacman.getYPos()][pacman.getXPos() + 1];
		if (rightPos == 11 || rightPos == 0 || rightPos == 13 || rightPos == 14) {
			return true;
		} else
			return false;
	}

	/*
	 * checks whether the value in the grid to the left of pacman is empty space or
	 * food, and returns true if so
	 */
	public boolean checkLeft() {
		int leftPos = grid[pacman.getYPos()][pacman.getXPos() - 1];
		if (leftPos == 11 || leftPos == 0 || leftPos == 13 || leftPos == 14) {
			return true;
		} else
			return false;
	}

	/*
	 * checks whether the value in the grid below pacman is empty space or food, and
	 * returns true if so
	 */
	public boolean checkUp() {
		int upPos = grid[pacman.getYPos() - 1][pacman.getXPos()];
		if (upPos == 11 || upPos == 0 || upPos == 13 || upPos == 14) {
			return true;
		} else
			return false;
	}

	/*
	 * checks whether the value in the grid above pacman is empty space or food, and
	 * returns true if so
	 */
	public boolean checkDown() {
		int downPos = grid[pacman.getYPos() + 1][pacman.getXPos()];
		if (downPos == 11 || downPos == 0 || downPos == 13 || downPos == 14) {
			return true;
		} else
			return false;
	}

	/*
	 * if pacman can move right, pacman moves right, then the ghosts execute their
	 * algorithm, then checkPos checks if they have collided
	 */

	public void startRightCycle() {
		if (checkRight()) {
			pacman.moveRight();
			checkPos();
			pacman.setMoves(pacman.getMoves() + 1);
		}
	}

	/*
	 * if pacman can move left, pacman moves right, then the ghosts execute their
	 * algorithm, then checkPos checks if they have collided
	 */
	public void startLeftCycle() {
		if (checkLeft()) {
			pacman.moveLeft();
			checkPos();
			pacman.setMoves(pacman.getMoves() + 1);
		}
	}

	/*
	 * if pacman can move up, pacman moves right, then the ghosts execute their
	 * algorithm, then checkPos checks if they have collided
	 */
	public void startUpCycle() {
		if (checkUp()) {
			pacman.moveUp();
			checkPos();
			pacman.setMoves(pacman.getMoves() + 1);
		}
	}

	/*
	 * if pacman can move down, pacman moves right, then the ghosts execute their
	 * algorithm, then checkPos checks if they have collided
	 */
	public void startDownCycle() {
		if (checkDown()) {
			pacman.moveDown();
			checkPos();
			pacman.setMoves(pacman.getMoves() + 1);
		}
	}

	/**
	 * startGhosts deals with the starting movement sequence of each of the ghosts,
	 * by moving them one by one out of the starting area seperated by 5 or 10 moves
	 * each
	 */
	public void startGhosts() {
		if (blinky.getMoves() < 3 && pacman.getMoves() < 5) {
			blinky.start();
		}
		if (pacman.getMoves() >= 4) {
			blinky.directPathfind();
		}
		if (pacman.getMoves() >= 10 && pacman.getMoves() < 15 && pinky.getMoves() < 3) {
			pinky.start();
		}
		if (pacman.getMoves() >= 14) {
			pinky.directPathfind();
		}
		if (pacman.getMoves() >= 20 && pacman.getMoves() < 25 && inky.getMoves() < 3) {
			inky.start();
		}
		if (pacman.getMoves() >= 24) {
			inky.directPathfind();
		}
		if (pacman.getMoves() >= 30 && clyde.getMoves() < 3) {
			clyde.start();
		}
		if (pacman.getMoves() >= 34) {
			clyde.directPathfind();
		}
	}

	/**
	 * runGhostAlgorithm moves each ghost once
	 */
	public void runGhostAlgorithm() {
		inky.directPathfind();
		blinky.directPathfind();
		pinky.directPathfind();
		clyde.directPathfind();
	}

	/**
	 * moveGhosts chooses to either move all ghosts or to do the ghost starting
	 * sequence depending on the number of moves pacman has performed so far
	 */
	public void moveGhosts() {
		if (pacman.getMoves() < 34) {
			startGhosts();
		} else {
			runGhostAlgorithm();
		}
	}

	/**
	 * startNewLife starts a new pacman life if he has died, and therefore resets
	 * the gameState, ghost positions, pacman position, and decrements his lives by
	 * one
	 */
	public void startNewLife() {

		gameState = 0;

		pacman.setXPos(12);
		pacman.setYPos(19);
		pacman.resetMoves();

		inky.setXPos(12);
		inky.setYPos(12);
		inky.resetMoves();

		blinky.setXPos(12);
		blinky.setYPos(12);
		blinky.resetMoves();

		pinky.setXPos(12);
		pinky.setYPos(12);
		pinky.resetMoves();

		clyde.setXPos(12);
		clyde.setYPos(12);
		clyde.resetMoves();
		lives -= 1;
	}

	/**
	 * resetGrid goes through the entire grid and resets all of the empty squares
	 * back to food
	 */
	public void resetGrid() {
		for (int i = 0; i < grid.length - 1; i++) {
			for (int j = 0; j < grid[i].length - 1; j++) {
				if (grid[i][j] == 11) {
					grid[i][j] = 0;
				}
			}
		}
	}

	/**
	 * reset goes through pacman and all of the ghosts and resets their positions
	 * and moves, then resets the gamestate, grid, score, and lives
	 */
	public void reset() {
		gameState = 0;

		pacman.setXPos(12);
		pacman.setYPos(19);
		pacman.resetMoves();

		inky.setXPos(12);
		inky.setYPos(12);
		inky.resetMoves();

		blinky.setXPos(12);
		blinky.setYPos(12);
		blinky.resetMoves();

		pinky.setXPos(12);
		pinky.setYPos(12);
		pinky.resetMoves();

		clyde.setXPos(12);
		clyde.setYPos(12);
		clyde.resetMoves();

		score = 0;
		lives = 2;

		resetGrid();
	}

	/**
	 * setStartingState sets the gameState to 1 if it is currently equal to zero
	 */
	public void setStartingState() {
		if (gameState == 0) {
			gameState = 1;
		}
	}

	/**
	 * actionPerformed performs actions seperated by a delay which was defined at
	 * the creation of the timer
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// start the timer so that this function does not repeat until after the
		// designated delay
		timer.start();
		checkPos();
		// gameState changes that follow all handle delays between movements of pacman
		// and ghosts, as well as the animation of pacman
		if (gameState == 4) {
			gameState = 1;
			repaint();
		}
		if (gameState == 3) {
			gameState = 4;
			repaint();
		}
		if (gameState == 2) {
			gameState = 3;
			repaint();
		}
		if (gameState == 1) {
			// every 4th delay time, move pacman and the ghosts
			if (right && checkRight()) {
				startRightCycle();
			}
			if (left && checkLeft()) {
				startLeftCycle();
			}
			if (up && checkUp()) {
				startUpCycle();
			}
			if (down && checkDown()) {
				startDownCycle();
			}
			moveGhosts();
			gameState = 2;
			repaint();
		}
	}

	/**
	 * keyPressed listens for a key press, then performs actions based on which key
	 * was pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (gameState != 6) {
			// if the game is not over, check the key and set pacman to the corresponding direction 
			// and increment the gameState if it is zero so that pacman is not stuck in the beginning position
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				if (checkRight()) {
					setDirection(1);
					setStartingState();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				if (checkLeft()) {
					setDirection(2);
					setStartingState();
				}

			} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				if (checkUp()) {
					setDirection(3);
					setStartingState();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				if (checkDown()) {
					setDirection(4);
					setStartingState();
				}
			}

		} else if (gameState == 6 && e.getKeyCode() == KeyEvent.VK_SPACE || gameState == 5 && e.getKeyCode() == KeyEvent.VK_SPACE) {
			// if the game is over and the space key is pressed, reset the game
			reset();
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
