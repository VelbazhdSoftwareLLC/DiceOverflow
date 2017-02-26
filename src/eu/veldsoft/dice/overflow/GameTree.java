package eu.veldsoft.dice.overflow;

import javax.swing.tree.DefaultMutableTreeNode;

import eu.veldsoft.dice.overflow.model.Board;

/**
 * Game tree building program.
 * 
 * @author Todor Balabanov
 */
public class GameTree {

	/**
	 * Single entry point for the console application.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		System.out.println("Start ...");

		Board board = new Board();

		System.out.println(board);
		board.click(1, 1);
		board.next();
		System.out.println(board);
		board.click(3, 3);
		board.next();
		System.out.println(board);
		board.click(1, 1);
		board.next();
		System.out.println(board);
		board.click(3, 3);
		board.next();
		System.out.println(board);
		board.click(1, 1);
		board.next();
		System.out.println(board);
		board.click(3, 3);
		board.next();
		System.out.println(board);

		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(board.toBytes());

		System.out.println((new Board()).equals(new Board()));

		System.out.println("Finish ...");
	}
}
