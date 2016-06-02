package eu.veldsoft.dice.overflow;

import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import eu.veldsoft.dice.overflow.Cell.Type;

/**
 * 
 * @author
 */
class NeuralNetworkArtificialIntelligence implements ArtificialIntelligence {
	/**
	 * 
	 */
	// TODO Load net object from a file or database.
	private MultiLayerPerceptron net = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 25, 26, 25);

	/**
	 * 
	 */
	@Override
	public int[] move(Cell[][] cells, Type player) throws ImpossibleMoveException {
		int x = -1;
		int y = -1;

		/*
		 * Check for available moves.
		 */
		boolean found = false;
		loops: for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].getType() == player) {
					x = i;
					y = j;
					found = true;
					break loops;
				}
			}
		}
		if (found == false) {
			throw new ImpossibleMoveException();
		}

		/*
		 * Use ANN for move generation.
		 */
		double input[] = new double[Board.COLS * Board.ROWS];
		for (int i = 0, k = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++, k++) {
				if (cells[i][j].getType() == Cell.Type.EMPTY) {
					input[k] = 0.5;
				} else {
					double value = 0.5D * cells[i][j].getScore() / 6D;

					if (cells[i][j].getType() == Cell.Type.RED) {
						input[k] = 0.5 - value;
					}
					if (cells[i][j].getType() == Cell.Type.BLUE) {
						input[k] = 0.5 + value;
					}
				}
			}
		}

		net.setInput(input);
		net.calculate();
		double output[] = net.getOutput();

		double max = 0;
		for (int i = 0, k = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++, k++) {
				if (max < output[k] && cells[i][j].getType() == player) {
					x = i;
					y = j;
				}
			}
		}

		return new int[] { x, y };
	}
}
