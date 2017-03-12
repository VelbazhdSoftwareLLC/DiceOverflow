/**
 * 
 */
package eu.veldsoft.dice.overflow.model.ai;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell.Type;
import eu.veldsoft.dice.overflow.model.Move;
import eu.veldsoft.dice.overflow.model.Util;

/**
 * Computer opponent based on Monte Carlo tree search.
 *
 * https://en.wikipedia.org/wiki/Monte_Carlo_tree_search
 * 
 * @author Todor Balabanov
 */
public class MonteCarloArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * Milliseconds used for move calculation.
	 */
	private int time = 0;

	/**
	 * Constructor with parameters.
	 * 
	 * @param time
	 *            Time for calculations.
	 */
	public MonteCarloArtificialIntelligence(int time) {
		super();

		this.time = time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] move(Board state, Type player) throws ImpossibleMoveException {
		super.move(state, player);

		Board board = new Board(state);
		Map<Move, Integer> counters = new HashMap<Move, Integer>();

		/*
		 * Calculate when to stop.
		 */
		long time = this.time + System.currentTimeMillis();

		/*
		 * Experiments are limited according to the available time.
		 */
		while (System.currentTimeMillis() < time) {
			/*
			 * Try to click all cells in the board.
			 */
			for (int i = 0; i < Board.COLS; i++) {
				for (int j = 0; j < Board.ROWS; j++) {
					if (board.click(i, j) == true) {
						board.next();

						/*
						 * Play until someone win.
						 */
						while (board.hasWinner() == false) {
							/*
							 * Select random cell to play.
							 */
							if (true == board.click(Util.PRNG.nextInt(Board.COLS), Util.PRNG.nextInt(Board.ROWS))) {
								/*
								 * Move to next player if the turn was valid.
								 */
								board.next();
							}
						}
						board.setGameOver();

						// TODO Use counters.
						Move move = new Move(i, j, true);
						if (counters.containsKey(move) == false) {
							counters.put(move, 0);
						}

						/*
						 * Calculate total score.
						 */
						Map<Type, Integer> score = board.score();
						int others = 0;
						for (Type key : score.keySet()) {
							others += score.get(key);
						}

						/*
						 * Others have current player score that is why it
						 * should be multiplied by two.
						 */
						counters.put(move, counters.get(move) + 2 * score.get(player) - others);

						/*
						 * Reinitialize the board for the next experiment.
						 */
						board = new Board(state);
					}
				}
			}
		}

		/*
		 * Select random valid cell.
		 */
		int x = -1;
		int y = -1;

		/*
		 * Evaluate the possible moves by finding the biggest number.
		 */
		int max = (Collections.max(counters.values()));
		for (Entry<Move, Integer> entry : counters.entrySet()) {
			if (entry.getValue() != max) {
				continue;
			}

			Move move = entry.getKey();
			x = move.getX();
			y = move.getY();

			/*
			 * There is no need to loop over the entire map.
			 */
			break;
		}

		return new int[] { x, y };
	}
}
