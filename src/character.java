
public class character{
	// this class houses the common methods between the ghost and pacman classes
	private int xpos;
	private int ypos;
	private int moves = 0;

	/**
	 * @return returns the x and y position in the form of an array
	 */
	public int[] getPos() {
		int[] result = { this.xpos, this.ypos };
		return result;
	}

	/**
	 * @return the x position of this object
	 */
	public int getXPos() {
		return this.xpos;
	}

	/**
	 * @return the y position of this object
	 */
	public int getYPos() {
		return this.ypos;
	}

	/**
	 * sets the x position of this object to the input int x
	 * @param x
	 */
	public void setXPos(int x) {
		this.xpos = x;
	}

	/**
	 * sets the y position of this object to the input int y
	 * @param y
	 */
	public void setYPos(int y) {
		this.ypos = y;
	}

	/**
	 * @return the amount of moves made by this object
	 */
	public int getMoves() {
		return this.moves;
	}
	/**
	 * sets the move count of this object
	 * @param moves
	 */
	public void setMoves(int moves) {
		this.moves = moves;
	}

	/**
	 * resets the move count of this object to zero
	 */
	public void resetMoves() {
		moves = 0;
	}
}
