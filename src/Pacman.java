
public class Pacman extends character {
	
	/**
	 * Construct a pacman object with initial x and y positions of (19, 12)
	 */
	public Pacman() {
		this.setYPos(19);
		this.setXPos(12);
	}

	/**
	 * Changes the grid at pacman's current square to be empty, making it look as if pacman has eaten the food
	 */
	public void changeGrid() {
		int thisGrid = Gameplay.grid[this.getYPos()][this.getXPos()];
		if (thisGrid == 0 || thisGrid == 14) {
			Gameplay.addScore();
			Gameplay.grid[this.getYPos()][this.getXPos()] = 11;
		}
	}

	/**
	 * moveRight checks whether the value in the array 'grid' to the right of
	 * pacman's current position is equal to 0 or 11, corresponding to empty space
	 * or food, and if so, pacman's x position is incremented by 1 and his direction
	 * is set to right
	 */
	public void moveRight() {
		if (this.getYPos() == 12 && this.getXPos() == 22) {
			this.setXPos(1);
		}
		this.setXPos(this.getXPos() + 1);
		this.changeGrid();
		Gameplay.setDirection(1);
	}

	/**
	 * moveLeft checks whether the value in the array 'grid' to the left of pacman's
	 * current position is equal to 0 or 11, corresponding to empty space or food,
	 * and if so, pacman's x position is decremented by 1 and his direction is set
	 * to left
	 */
	public void moveLeft() {
		if (this.getYPos() == 12 && this.getXPos() == 2) {
			this.setXPos(23);
		}
		this.setXPos(this.getXPos() - 1);
		this.changeGrid();
		Gameplay.setDirection(2);
	}

	/**
	 * moveUp checks whether the value in the array 'grid' above pacman's current
	 * position is equal to 0 or 11, corresponding to empty space or food, and if
	 * so, pacman's y position is decremented by 1 and his direction is set to up
	 */
	public void moveUp() {
		this.setYPos(this.getYPos() - 1);
		this.changeGrid();
		Gameplay.setDirection(3);
	}

	/**
	 * moveDown checks whether the value in the array 'grid' below pacman's current
	 * position is equal to 0 or 11, corresponding to empty space or food, and if
	 * so, pacman's y position is incremented by 1 and his direction is set to down
	 */
	public void moveDown() {
		this.setYPos(this.getYPos() + 1);
		this.changeGrid();
		Gameplay.setDirection(4);
	}
}
