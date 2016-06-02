package eu.veldsoft.dice.overflow;

import eu.veldsoft.dice.overflow.Cell.Type;

/**
 * 
 * @author
 */
class RandomArtificialIntelligence implements ArtificialIntelligence {
	/**
	 * 
	 */
	@Override
	public int[] move(Cell[][] cells, Type player) throws ImpossibleMoveException {
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
