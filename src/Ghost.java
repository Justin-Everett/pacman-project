public class Ghost extends character {

	private int xdif; // difference in ghost xpos and pacman xpos
	private int ydif; // difference in ghost ypos and pacman ypos
	private int oneRight;
	private int oneLeft;
	private int oneUp;
	private int oneDown;
	private int ord;
	private int old;
	private int oud;
	private int odd;
	private int[] vars = new int[4];
	private int state = 0;

	/**
	 * Creates a new ghost at position (xpos,ypos)
	 * 
	 * @param xpos starting x position of ghost
	 * @param ypos starting y position of ghost
	 */
	public Ghost(int xpos, int ypos) {
		this.setXPos(xpos);
		this.setYPos(ypos);
	}

	/**
	 * start deals with the ghost's starting moves, and does so by moving it up once if it is able to
	 */
	public void start() {
		if (checkOneUp()) {
			this.setYPos(this.getYPos() - 1);
		}
	}

	/**
	 * takes in an unsorted array of ints and sorts it in ascending order using
	 * bubble sort
	 * 
	 * @param x unsorted int array
	 * @return sorted int array in ascending order
	 */
	public int[] bubbleSort(int[] x) {
		int temp = 0;
		for (int i = 0; i < x.length - 1; i++) {
			for (int j = 0; j < x.length - i - 1; j++) {
				if (x[j] > x[j + 1]) {
					temp = x[j];
					x[j] = x[j + 1];
					x[j + 1] = temp;
				}
			}
		}
		return x;
	}

	/**
	 * 
	 * @param direction
	 * @param difference
	 * @return the distance between the ghost and pacman, by using pythagoras'
	 *         formula for distance in a triangle
	 */
	public int getDistance(int direction, int difference) {
		return (int) Math.sqrt(Math.pow(direction, 2) + Math.pow(difference, 2));
	}

	/**
	 * @return if the ghost is able to move one tile right
	 */
	public boolean checkOneRight() {
		if ((Gameplay.grid[this.getYPos()][this.getXPos() + 1] == 11
				|| Gameplay.grid[this.getYPos()][this.getXPos() + 1] == 0
				|| Gameplay.grid[this.getYPos()][this.getXPos() + 1] == 13)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return if the ghost is able to move one tile left
	 */
	public boolean checkOneLeft() {
		if ((Gameplay.grid[this.getYPos()][this.getXPos() - 1] == 11
				|| Gameplay.grid[this.getYPos()][this.getXPos() - 1] == 0
				|| Gameplay.grid[this.getYPos()][this.getXPos() - 1] == 13)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return if the ghost is able to move one tile up
	 */
	public boolean checkOneUp() {
		if ((Gameplay.grid[this.getYPos() - 1][this.getXPos()] == 11
				|| Gameplay.grid[this.getYPos() - 1][this.getXPos()] == 0
				|| Gameplay.grid[this.getYPos() - 1][this.getXPos()] == 12
				|| Gameplay.grid[this.getYPos() - 1][this.getXPos()] == 13)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return if the ghost is able to move one tile down
	 */
	public boolean checkOneDown() {
		if ((Gameplay.grid[this.getYPos() + 1][this.getXPos()] == 11
				|| Gameplay.grid[this.getYPos() + 1][this.getXPos()] == 0
				|| Gameplay.grid[this.getYPos() + 1][this.getXPos()] == 13)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * directPathfind completes one move of the movement algorithm the ghosts use
	 * this process is broken into several steps as such: compute the distance from
	 * pacman if the ghost were to move once in each direction sort this from lowest
	 * to highest move the ghost to the closest available move
	 */
	public void directPathfind() {
		this.xdif = Math.abs(Gameplay.pacman.getXPos() - this.getXPos());
		this.ydif = Math.abs(Gameplay.pacman.getYPos() - this.getYPos());
		// use one left/right/up/down to help decide which direction to move ghost, then
		this.oneRight = Math.abs(Gameplay.pacman.getXPos() - (this.getXPos() + 1));
		this.oneLeft = Math.abs(Gameplay.pacman.getXPos() - (this.getXPos() - 1));
		this.oneUp = Math.abs(Gameplay.pacman.getYPos() - (this.getYPos() - 1));
		this.oneDown = Math.abs(Gameplay.pacman.getYPos() - (this.getYPos() + 1));
		// get the distance between pacman and what the ghost's position would be in one
		// move in every direction
		this.ord = getDistance(this.oneRight, this.ydif);
		this.old = getDistance(this.oneLeft, this.ydif);
		this.oud = getDistance(this.oneUp, this.xdif);
		this.odd = getDistance(this.oneDown, this.xdif);
		// fill the vars array with the previously attained values
		this.vars[0] = ord;
		this.vars[1] = old;
		this.vars[2] = oud;
		this.vars[3] = odd;
		// sort the array from lowest to highest
		bubbleSort(this.vars);
		// compute a random number which will determine whether the ghost moves toward
		// pacman or randomly
		double x = Math.random();
		// if x is under 0.85 out of 1.00, loop through the array and move to the first
		// available adjacent square which is closest to pacman
		if (x < 0.85) {
			for (int i = 0; i < 4; i++) {
				if (this.vars[i] == this.ord) {
					if (checkOneRight()) {
						if (this.getYPos() == 12 && this.getXPos() == 22) {
							this.setXPos(1);
						}
						this.setXPos(this.getXPos() + 1);
						break;

					}
				} else if (this.vars[i] == this.old) {
					if (checkOneLeft()) {
						if (this.getYPos() == 12 && this.getXPos() == 2) {
							this.setXPos(23);
						}
						this.setXPos(this.getXPos() - 1);
						break;

					}
				} else if (this.vars[i] == this.oud) {
					if (checkOneUp()) {
						this.setYPos(this.getYPos() - 1);
						break;

					}
				} else if (this.vars[i] == this.odd) {
					if (checkOneDown()) {
						this.setYPos(this.getYPos() + 1);
						break;

					}
				}

			}
		} else {
			// if x is over the 0.85 chance, move randomly
			double g = Math.random();
			for (int i = 0; i < 4; i++) {
				if (g <= 0.25) {
					if (checkOneRight()) {
						if (this.getYPos() == 12 && this.getXPos() == 22) {
							this.setXPos(1);
						}
						this.setXPos(this.getXPos() + 1);
						break;

					}
				} else if (g > 0.25 && g <= 0.50) {
					if (checkOneLeft()) {
						if (this.getYPos() == 12 && this.getXPos() == 2) {
							this.setXPos(23);
						}
						this.setXPos(this.getXPos() - 1);
						break;

					}
				} else if (g > 0.50 && g <= 0.75) {
					if (checkOneUp()) {
						this.setYPos(this.getYPos() - 1);
						break;

					}
				} else if (g > 0.75) {
					if (checkOneDown()) {
						this.setYPos(this.getYPos() + 1);
						break;

					}
				}
			}
			if (this.state == 0 || this.state == 1 || this.state == 2) {
				this.state += 1;
			}
		}
	}
}