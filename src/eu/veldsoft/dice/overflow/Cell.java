package eu.veldsoft.dice.overflow;

/**
 * 
 */
class Cell {
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
