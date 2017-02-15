package eu.veldsoft.dice.overflow;

/**
 * 
 */
class Cell {
	//TODO Use it for the cell size.
	/**
	 * 
	 * @author Todor Balabanov
	 */
	enum Size {
		EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

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
	 * 
	 */
	enum Type {
		/**
		 * 
		 */
		EMPTY(-1), RED(0), BLUE(1);

		/**
		 * 
		 * @param id
		 * @return
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
		 * 
		 */
		private int id = -1;

		/**
		 * 
		 * @param id
		 */
		private Type(int id) {
			this.id = id;
		}

		/**
		 * 
		 * @return
		 */
		public int getId() {
			return id;
		}
	}

	/**
	 * 
	 */
	private Type type;

	/**
	 * 
	 */
	private int score;

	/**
	 * 
	 */
	public Cell(Type type, int score) {
		this.type = type;
		this.score = score;
	}

	/**
	 * 
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 
	 */
	public void rise() {
		score++;
	}

	/**
	 * 
	 * @param amount
	 */
	public void drop(int amount) {
		/*
		 * Negative numbers are illegal.
		 */
		if (amount < 0) {
			amount = 0;
		}

		score -= amount;

		/*
		 * Switch to empty cell.
		 */
		if (score < 1) {
			score = 0;
			type = Type.EMPTY;
		}
	}
}
