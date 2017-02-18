package eu.veldsoft.dice.overflow;

/**
 * Describe single cell on the board.
 */
class Cell {
	//TODO Use it for the cell size.
	/**
	 * Size of the die on a particular cell.
	 * 
	 * @author Todor Balabanov
	 */
	enum Size {
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

		/**
		 * Size value of the cell.
		 */
		private int value = -1;
		
		/**
		 * Constructor.
		 * 
		 * @param value Size value of the cell.
		 */
		private Size(int value) {
			this.value = value;
		}
		
		/**
		 * Cell size getter.
		 * 
		 * @return Size of the cell.
		 */
		public int get() {
			return value;
		}
	};
	
	/**
	 * Type of the cell.
	 */
	enum Type {
		/**
		 * 
		 */
		EMPTY(-1), RED(0), BLUE(1);

		/**
		 * Get object by id.
		 * 
		 * @param id Identifier to search for.
		 * 
		 * @return The object or empty if the identifier was not found.
		 */
		static Type id(int id) {
			for (Type type : Type.values()) {
				if (type.getId() == id) {
					return type;
				}
			}

			return EMPTY;
		}

		/**
		 * Object indentifier.
		 */
		private int id = -1;

		/**
		 * Constructor.
		 * 
		 * @param id Object identifier.
		 */
		private Type(int id) {
			this.id = id;
		}

		/**
		 * Object identifier getter.
		 * 
		 * @return Identifier.
		 */
		public int getId() {
			return id;
		}
	}

	/**
	 * Cell type.
	 */
	private Type type;

	/**
	 * Cell size.
	 */
	private int size;

	/**
	 * Constructor.
	 * 
	 * @param type Cell type.
	 * @param size Cell size.
	 */
	public Cell(Type type, int size) {
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
	 * @param type Cell type.
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Cell size getter.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Cell size setter.
	 * 
	 * @param size Cell size.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Rise size of the die in the cell.
	 */
	public void rise() {
		size++;
	}

	/**
	 * Drop down the size of the die in the cell.
	 * 
	 * @param amount Amount to drop the size with.
	 */
	public void drop(int amount) {
		/*
		 * Negative numbers are illegal.
		 */
		if (amount < 0) {
			amount = 0;
		}

		size -= amount;

		/*
		 * Switch to empty cell.
		 */
		if (size < 1) {
			size = 0;
			type = Type.EMPTY;
		}
	}
}
