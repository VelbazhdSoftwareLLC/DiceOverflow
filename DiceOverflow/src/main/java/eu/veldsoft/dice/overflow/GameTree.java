package eu.veldsoft.dice.overflow;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell;
import eu.veldsoft.dice.overflow.model.Cell.Type;
import eu.veldsoft.dice.overflow.model.Move;
import eu.veldsoft.dice.overflow.model.Util;

/**
 * Game tree building program.
 * 
 * @author Todor Balabanov
 */
public class GameTree {
	/**
	 * Initial board object.
	 */
	private static Board board = new Board();

	/**
	 * Tree structure which is holding the serialised version of the board.
	 */
	private static TreeNode<Board> tree = new ArrayMultiTreeNode<>(board);

	/**
	 * Keep a set of all generated moves.
	 */
	private static Set<Board> visited = new HashSet<Board>();

	/**
	 * Monte Carlo evaluation of specific game board state.
	 * 
	 * @param state
	 *            Node in the game tree.
	 * @param type
	 *            Who is playing on this turn.
	 * @param time
	 *            Milliseconds to be used for Monte Carlo experiments.
	 * @return Evaluation for each possible move.
	 */
	private static Map<Move, Integer> monteCarlo(Board state, Cell.Type type, long time) {
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
							if (true == board.click(Util.PRNG.nextInt(Board.COLS), Util.PRNG.nextInt(Board.ROWS))) {
								/*
								 * Move to next player if the turn was valid.
								 */
								board.next();
							}
						}
						board.setGameOver();

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
						counters.put(move, counters.get(move) + 2 * score.get(type) - others);

						/*
						 * Reinitialise the board for the next experiment.
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
	private static void build(TreeNode<Board>  root) {
		Board board = new Board(root.data());

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

					/*
					 * Do not proceed an existing states in the game tree.
					 */
					if (visited.contains(board) == true) {
						board = new Board(root.data());
						continue;
					}

					/*
					 * Add tree node for this particular move.
					 */
					TreeNode<Board> node;
					root.add(node = new ArrayMultiTreeNode<>(board));
					visited.add(board);

					/*
					 * Call the building of a subtree. The root of the subtree
					 * is the child of the current node.
					 */
					build(node);

					/*
					 * Prepare for new move check.
					 */
					board = new Board(root.data());
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

		build(tree);

		// Map<Move, Integer> evaluation = monteCarlo(board, Type.RED, 5000);
		// System.err.println(evaluation);

		System.out.println("Finish ...");
	}
}
