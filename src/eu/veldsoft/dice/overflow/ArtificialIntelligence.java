package eu.veldsoft.dice.overflow;

/**
 * 
 * @author
 */
interface ArtificialIntelligence {
	/**
	 * 
	 * @param cells
	 * @param player
	 * @return
	 * @throws ImpossibleMoveException
	 */
	public int[] move(Cell cells[][], Cell.Type player) throws ImpossibleMoveException;
}
