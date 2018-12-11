package eu.veldsoft.dice.overflow.model.ai;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell;
import eu.veldsoft.dice.overflow.model.Cell.Type;
import eu.veldsoft.dice.overflow.model.Util;

/**
 * Computer opponent based on random search.
 *
 * https://en.wikipedia.org/wiki/Random_search
 * 
 * @author Todor Balabanov
 */
public class RandomArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] move(Board state, Type player) throws ImpossibleMoveException {
		super.move(state, player);

		Cell cells[][] = state.getCells();

		/*
		 * Select random valid cell.
		 */
		int x = -1;
		int y = -1;
		do {
			x = Util.PRNG.nextInt(cells.length);
			y = Util.PRNG.nextInt(cells[x].length);
		} while (cells[x][y].getType() != player);

		return new int[] { x, y };
	}
}
