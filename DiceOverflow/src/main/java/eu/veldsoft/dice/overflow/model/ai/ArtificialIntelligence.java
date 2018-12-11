package eu.veldsoft.dice.overflow.model.ai;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell;

/**
 * Interface which each computer opponent class needs to implement.
 * 
 * @author Todor Balabanov
 */
public interface ArtificialIntelligence {
	/**
	 * At each game turn each player should do a single move. The move is
	 * coordinates of a cell on the board.
	 * 
	 * @param state
	 *            Cells on the board.
	 * 
	 * @param player
	 *            Current player reference.
	 * 
	 * @return Move coordinates.
	 * 
	 * @throws ImpossibleMoveException
	 *             If the move is not possible at all rise an exception.
	 */
	public int[] move(Board state, Cell.Type player) throws ImpossibleMoveException;
}
