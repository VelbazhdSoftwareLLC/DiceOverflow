/**
 * 
 */
package eu.veldsoft.dice.overflow.model.ai;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell;
import eu.veldsoft.dice.overflow.model.Cell.Type;

/**
 * Abstract computer opponent.
 * 
 * @author Todor Balabanov
 */
public abstract class AbstractArtificialIntelligence implements ArtificialIntelligence {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] move(Board state, Type player) throws ImpossibleMoveException {
		Cell cells[][] = state.getCells();

		/*
		 * Check for available moves.
		 */
		boolean found = false;
		loops: for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].getType() == player) {
					found = true;
					break loops;
				}
			}
		}
		if (found == false) {
			throw new ImpossibleMoveException();
		}

		return new int[] {};
	}

}
