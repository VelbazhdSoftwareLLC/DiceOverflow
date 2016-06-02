package eu.veldsoft.dice.overflow;

/**
 * 
 * @author Diana Dyulgerova
 */
class Board {
	/**
	 * 
	 */
	static final int ROWS = 5;

	/**
	 * 
	 */
	static final int COLS = 5;

	/**
	 * 
	 */
	private int turn = 0;

	/**
	 * 
	 */
	private boolean gameOver = false;

	/**
	 * 
	 */
	private Cell cells[][] = {
			{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
					new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
			{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.RED, 5), new Cell(Cell.Type.EMPTY, 0),
					new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
			{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
					new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
			{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
					new Cell(Cell.Type.BLUE, 5), new Cell(Cell.Type.EMPTY, 0) },
			{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
					new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) }, };

	/**
	 * 
	 * @param x
	 * @param y
	 */
	private void rise(int x, int y, Cell.Type type) {
		/*
		 * Check board bounds.
		 */
		if (x < 0 || y < 0 || x >= cells.length || y >= cells[x].length) {
			return;
		}

		cells[x][y].rise();
		cells[x][y].setType(type);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	private void flood(int x, int y) {
		/*
		 * Check board bounds.
		 */
		if (x < 0 || y < 0 || x >= cells.length || y >= cells[x].length) {
			return;
		}

		/*
		 * Nothing to flood.
		 */
		if (0 <= cells[x][y].getScore() && cells[x][y].getScore() <= 6) {
			return;
		}

		/*
		 * Flood.
		 */
		rise(x - 1, y, cells[x][y].getType());
		rise(x + 1, y, cells[x][y].getType());
		rise(x, y - 1, cells[x][y].getType());
		rise(x, y + 1, cells[x][y].getType());
		cells[x][y].drop(4);

		/*
		 * Check itself.
		 */
		flood(x, y);

		/*
		 * Recursive flood.
		 */
		flood(x - 1, y);
		flood(x + 1, y);
		flood(x, y - 1);
		flood(x, y + 1);
	}

	/**
	 * 
	 * @return
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * 
	 */
	public void setGameOver() {
		gameOver = true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * 
	 * @return
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * 
	 */
	public void next() {
		turn++;
	}

	/**
	 * 
	 */
	public void reset() {
		turn = 0;
		gameOver = false;

		cells = new Cell[][] {
				{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
						new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
				{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.RED, 5), new Cell(Cell.Type.EMPTY, 0),
						new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
				{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
						new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) },
				{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
						new Cell(Cell.Type.BLUE, 5), new Cell(Cell.Type.EMPTY, 0) },
				{ new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0),
						new Cell(Cell.Type.EMPTY, 0), new Cell(Cell.Type.EMPTY, 0) }, };
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return
	 */
	public boolean click(int x, int y) {
		/*
		 * Empty cells can not be clicked.
		 */
		if (cells[x][y].getType() == Cell.Type.EMPTY) {
			return false;
		}

		/*
		 * Player turn should match.
		 */
		if (turn % 2 != cells[x][y].getType().getId()) {
			return false;
		}

		cells[x][y].rise();
		flood(x, y);

		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasWinner() {
		Cell.Type found = null;

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].getType() == Cell.Type.EMPTY) {
					continue;
				}

				if (found == null) {
					found = cells[i][j].getType();
				} else if (cells[i][j].getType() != found) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	public Cell.Type winner() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; i < cells[j].length; j++) {
				if (cells[i][j].getType() != Cell.Type.EMPTY) {
					return cells[i][j].getType();
				}
			}
		}

		return Cell.Type.EMPTY;
	}
}
