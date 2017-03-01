package eu.veldsoft.dice.overflow.model;

/**
 * Single game move. The objects should be immutable.
 * 
 * @author Todor Balabanov
 */
public class Move {
	/**
	 * X coordinate of the move.
	 */
	private int x;

	/**
	 * Y coordinate of the move.
	 */
	private int y;

	/**
	 * Is the move valid flag.
	 */
	private boolean valid;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param x
	 *            X coordinate of the move.
	 * @param y
	 *            Y coordinate of the move.
	 * @param valid
	 *            Is the move valid.
	 */
	public Move(int x, int y, boolean valid) {
		super();

		this.x = x;
		this.y = y;
		this.valid = valid;
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param move
	 *            Move coordinates.
	 * @param valid
	 *            Is the move valid.
	 */
	public Move(Move move, boolean valid) {
		this(move.x, move.y, valid);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (valid ? 1231 : 1237);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof Move)) {
			return false;
		}
		Move other = (Move) object;
		if (valid != other.valid) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
}
