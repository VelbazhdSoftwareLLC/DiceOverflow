package eu.veldsoft.dice.overflow.model;

import java.io.Serializable;

/**
 * Describe single cell on the board.
 */
@SuppressWarnings("serial")
public class Cell implements Serializable {
	// TODO Use it for the cell size.
	/**
	 * Size of the die on a particular cell.
	 * 
	 * @author Todor Balabanov
	 */
	public enum Size {
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

		/**
		 * Size value of the cell.
		 */
		private int id = -1;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            Size value of the cell.
		 */
		private Size(int id) {
			this.id = id;
		}

		/**
		 * Cell size getter.
		 * 
		 * @return Size of the cell.
		 */
		public int getId() {
			return id;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "" + (id == 0 ? " " : id);
		}
	};

	/**
	 * Type of the cell.
	 */
	public enum Type {
		EMPTY(-1, " "), RED(0, "R"), BLUE(1, "B");

		/**
		 * Get object by id.
		 * 
		 * @param id
		 *            Identifier to search for.
		 * 
		 * @return The object or empty if the identifier was not found.
		 */
		public static Type id(int id) {
			for (Type type : Type.values()) {
				if (type.getId() == id) {
					return type;
				}
			}

			return EMPTY;
		}

		/**
		 * Object identifier.
		 */
		private int id = -1;

		/**
		 * Symbols combination to be used in to string printing.
		 */
		private String symbol = "";

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            Object identifier.
		 * @param symbol
		 *            Symbols used in to string printing.
		 */
		private Type(int id, String symbol) {
			this.id = id;
			this.symbol = symbol;
		}

		/**
		 * Object identifier getter.
		 * 
		 * @return Identifier.
		 */
		public int getId() {
			return id;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "" + symbol;
		}
	}

	/**
	 * Cell type.
	 */
	private Type type;

	/**
	 * Cell size.
	 */
	private Size size;

	/**
	 * When the size of the cell is more than the maximum available.
	 */
	private int overflowing = 0;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Cell type.
	 * @param size
	 *            Cell size.
	 */
	public Cell(Type type, Size size) {
		this.type = type;
		this.size = size;
	}

	/**
	 * Cell type getter.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Cell type setter.
	 * 
	 * @param type
	 *            Cell type.
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Cell size getter.
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * Cell size setter.
	 * 
	 * @param size
	 *            Cell size.
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/**
	 * Rise the size.
	 */
	public void riseUp() {
		overflowing++;
	}

	/**
	 * F Decrease the size.
	 */
	public void riseDown() {
		overflowing--;
		if (overflowing < 0) {
			overflowing = 0;
		}
	}

	/**
	 * Rise size of the die in the cell.
	 */
	public void rise() {
		switch (size) {
		case ZERO:
			size = Size.ONE;
			break;
		case ONE:
			size = Size.TWO;
			break;
		case TWO:
			size = Size.THREE;
			break;
		case THREE:
			size = Size.FOUR;
			break;
		case FOUR:
			size = Size.FIVE;
			break;
		case FIVE:
			size = Size.SIX;
			break;
		case SIX:
			size = Size.ZERO;
			riseUp();
			break;
		}
	}

	/**
	 * Drop down the size of the die in the cell.
	 * 
	 * @param amount
	 *            Amount to drop the size with.
	 */
	public void drop(int amount) {
		/*
		 * Negative numbers are illegal.
		 */
		if (amount < 0) {
			amount = 0;
			return;
		}

		for (int a = 0; a < amount; a++) {
			switch (size) {
			case ONE:
				size = Size.ZERO;
				break;
			case TWO:
				size = Size.ONE;
				break;
			case THREE:
				size = Size.TWO;
				break;
			case FOUR:
				size = Size.THREE;
				break;
			case FIVE:
				size = Size.FOUR;
				break;
			case SIX:
				size = Size.FIVE;
				break;
			default:
				riseDown();
				if (overflowing == 0 && size == Size.ZERO) {
					size = Size.SIX;
				}
				break;
			}
		}

		/*
		 * Switch to empty cell.
		 */
		if (size.getId() <= 0 && overflowing <= 0) {
			size = Size.ZERO;
			type = Type.EMPTY;
			overflowing = 0;
		}
	}

	/**
	 * Overflowing flag getter.
	 * 
	 * @return Zero if does not overflowing, something greater than zero
	 *         otherwise.
	 */
	public int overflowing() {
		return overflowing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + overflowing;
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(object instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) object;
		if (overflowing != other.overflowing) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + type + "" + size + ")";
	}
}
