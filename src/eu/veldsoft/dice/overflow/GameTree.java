package eu.veldsoft.dice.overflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.tree.DefaultMutableTreeNode;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell.Type;
import eu.veldsoft.dice.overflow.model.Move;

/**
 * Game tree building program.
 * 
 * @author Todor Balabanov
 */
public class GameTree {
	/**
	 * Pseudo-random number generator.
	 */
	private static final Random PRNG = new Random();

	/**
	 * Initial board object.
	 */
	private static Board board = new Board();

	/**
	 * Tree structure which is holding the serialized version of the board.
	 */
	private static DefaultMutableTreeNode tree = new DefaultMutableTreeNode(board);

	/**
	 * Monte Carlo evaluation of specific game board state.
	 * 
	 * @param state
	 *            Node in the game tree.
	 * @param time
	 *            Milliseconds to be used for Monte Carlo experiments.
	 * @return Evaluation for each possible move.
	 */
	private static Map<Move, Integer> monteCarlo(Board state, long time) {
		Map<Move, Integer> counters = new HashMap<Move, Integer>();

		Board board = new Board(state);

		/*
		 * Calculate when to stop.
		 */
		time += System.currentTimeMillis();

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
							if (true == board.click(PRNG.nextInt(Board.COLS), PRNG.nextInt(Board.ROWS))) {
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

						Map<Type, Integer> score = board.score();
						counters.put(move, counters.get(move) + (score.get(Type.RED) - score.get(Type.BLUE)));

						/*
						 * Reinitialize the board for the next experiment.
						 */
						board = new Board(state);
					}
				}
			}
		}

		return counters;
	}

	/**
	 * Building of the game tree.
	 * 
	 * @param root
	 *            Root of the tree.
	 */
	private static void build(DefaultMutableTreeNode root) {
		Board board = new Board((Board) root.getUserObject());

		/*
		 * Leaf of the tree game. It is the end of the recursive calls.
		 */
		if (board.hasWinner() == true) {
			board.setGameOver();
			return;
		}

		/*
		 * Try to click all cells in the board.
		 */
		for (int i = 0; i < Board.COLS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				/*
				 * Keep track of the successful moves.
				 */
				if (board.click(i, j) == true) {
					board.next();
					DefaultMutableTreeNode node;
					root.add(node = new DefaultMutableTreeNode(board));

					// TODO Check for node existence in order to escape graph
					// loops.

					/*
					 * Call the building of a subtree. The root of the subtree
					 * is the child of the current node.
					 */
					build(node);

					board = new Board((Board) root.getUserObject());
				}
			}
		}
	}

	/**
	 * Single entry point for the console application.
	 * 
	 * @param args
	 *            Command line arguments.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Start ...");

		board.click(1, 1);
		board.next();
		board.click(3, 3);
		board.next();
		board.click(1, 1);
		board.next();
		board.click(3, 3);
		board.next();
		System.err.println(board);

		// build(tree);
		Map<Move, Integer> evaluation = monteCarlo(board, 5000);
		System.err.println(evaluation);

		System.out.println("Finish ...");
	}
}
