package eu.veldsoft.dice.overflow.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import eu.veldsoft.dice.overflow.model.Cell.Size;
import eu.veldsoft.dice.overflow.model.Cell.Type;

/**
 * Board object.
 * 
 * @author Diana Dyulgerova
 */
@SuppressWarnings("serial")
public class Board implements Serializable {
	/**
	 * Height size in rows.
	 */
	public static final int ROWS = 5;

	/**
	 * Width size in columns.
	 */
	public static final int COLS = 5;

	/**
	 * Initial board state static factory function.
	 * 
	 * @return Initial board state.
	 */
	private static Cell[][] initial() {
		Cell result[][] = {
				{ new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO),
						new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO) },
				{ new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.RED, Size.FIVE), new Cell(Type.EMPTY, Size.ZERO),
						new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO) },
				{ new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO),
						new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO) },
				{ new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO),
						new Cell(Type.BLUE, Size.FIVE), new Cell(Type.EMPTY, Size.ZERO) },
				{ new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO),
						new Cell(Type.EMPTY, Size.ZERO), new Cell(Type.EMPTY, Size.ZERO) }, };

		return result;
	}

	/**
	 * Turn counter.
	 */
	private int turn = 0;

	/**
	 * Game over flag.
	 */
	private boolean gameOver = false;

	/**
	 * Cells on the board.
	 */
	private Cell cells[][] = initial();

	/**
	 * Rise size of the particular cell.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
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
	 * Flood color if there is overflow of a cell.
	 * 
	 * @param x
	 *            X coordinate.
	 * 
	 * @param y
	 *            Y coordinate.
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
		if (cells[x][y].overflowing() <= 0) {
			// TODO Find better way to search for flooding conditions.
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
	 * Turn getter.
	 * 
	 * @return Turn number.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Game over flag setter.
	 */
	public void setGameOver() {
		gameOver = true;
	}

	/**
	 * Game over flag getter.
	 * 
	 * @return Game over state.
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Cells getter.
	 * 
	 * @return Array with board cells.
	 */
	public Cell[][] getCells() {
		// TODO Do a deep copy.
		return cells;
	}

	/**
	 * Move to next turn.
	 */
	public void next() {
		turn++;
	}

	/**
	 * Initialize the game in the starting conditions.
	 */
	public void reset() {
		turn = 0;
		gameOver = false;

		cells = initial();
	}

	/**
	 * Handle player's click.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 * 
	 * @return True if the click was valid move, false otherwise.
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
		if (Cell.Type.play(turn) != cells[x][y].getType()) {
			return false;
		}

		cells[x][y].rise();
		flood(x, y);

		return true;
	}

	/**
	 * Check for winner on the board.
	 * 
	 * @return True if there is a winner, false otherwise.
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
	 * Provide winner type.
	 * 
	 * @return Winner type.
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

	/**
	 * Evaluate scores of different players.
	 * 
	 * @return Counters with the success of the different players on the board.
	 */
	public Map<Type, Integer> score() {
		Map<Type, Integer> result = new HashMap<Type, Integer>();

		/*
		 * Initialize counters.
		 */
		for (Type type : Type.values()) {
			result.put(type, 0);
		}

		/*
		 * Score players.
		 */
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; i < cells[j].length; j++) {
				Type type = cells[i][j].getType();
				int score = cells[i][j].getSize().value();

				result.put(type, score + result.get(type));
			}
		}

		return result;
	}

	/**
	 * Convert board to bytes array.
	 * 
	 * @return The game board as bites.
	 */
	public byte[] toBytes() {
		byte bytes[] = {};

		try {
			ByteArrayOutputStream out = null;
			(new ObjectOutputStream(out = new ByteArrayOutputStream())).writeObject(this);
			out.flush();
			bytes = out.toByteArray();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}

	/**
	 * Convert bytes to a board object.
	 * 
	 * @param bytes
	 *            Array of bytes representing a board.
	 */
	public void fromBytes(byte[] bytes) {
		Board board = this;

		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
			board = (Board) in.readObject();
			in.close();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.turn = board.turn;
		this.gameOver = board.gameOver;
		this.cells = board.cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(cells);
		result = prime * result + (gameOver ? 1231 : 1237);
		result = prime * result + turn;
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
		if (!(object instanceof Board)) {
			return false;
		}
		Board other = (Board) object;
		if (!Arrays.deepEquals(cells, other.cells)) {
			return false;
		}
		if (gameOver != other.gameOver) {
			return false;
		}
		if (turn != other.turn) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String result = "";

		result += "Turn:\t" + turn;
		result += "\n";
		result += "Over:\t" + gameOver;
		result += "\n";

		result += Arrays.deepToString(cells).replaceAll("], \\[", "\n").replaceAll(",", "").replaceAll("\\[", "")
				.replaceAll("]", "");

		return result;
	}
}
