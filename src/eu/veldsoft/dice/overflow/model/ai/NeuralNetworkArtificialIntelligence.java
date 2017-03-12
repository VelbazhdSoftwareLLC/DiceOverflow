package eu.veldsoft.dice.overflow.model.ai;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

import eu.veldsoft.dice.overflow.model.Board;
import eu.veldsoft.dice.overflow.model.Cell;
import eu.veldsoft.dice.overflow.model.Cell.Type;

/**
 * Artificial intelligence based on artificial neural network.
 * 
 * @author Todor Balabanov
 */
public class NeuralNetworkArtificialIntelligence extends AbstractArtificialIntelligence {
	// TODO Load net object from a file or database.
	/**
	 * Artificial neural network object.
	 */
	private BasicNetwork network = new BasicNetwork();

	/**
	 * Constructor without parameters.
	 */
	public NeuralNetworkArtificialIntelligence() {
		super();
		network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 25));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 26));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 25));
		network.getStructure().finalizeStructure();
		network.reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] move(Board state, Type player) throws ImpossibleMoveException {
		super.move(state, player);

		Cell cells[][] = state.getCells();

		/*
		 * Use ANN for move generation. The information from the board cells
		 * should be normalized according ANN input capabilities.
		 * 
		 * https://en.wikipedia.org/wiki/Activation_function
		 */
		double lowHigh[] = { Double.MIN_VALUE, Double.MAX_VALUE };
		network.getActivation(0).activationFunction(lowHigh, 0, 2);
		double middle = (lowHigh[0] + lowHigh[1]) / 2;

		double input[] = new double[Board.COLS * Board.ROWS];
		for (int i = 0, k = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++, k++) {
				if (cells[i][j].getType() == Cell.Type.EMPTY) {
					/*
					 * Empty cells are encoded with the middle value.
					 */
					input[k] = middle;
				} else {
					/*
					 * There are 6 values for each die and 0 if the cell is
					 * empty.
					 */
					double value = cells[i][j].getSize().value() / Cell.Size.values().length - 1;

					if (cells[i][j].getType() == Cell.Type.RED) {
						input[k] = middle - (middle - lowHigh[0]) * value;
					}
					if (cells[i][j].getType() == Cell.Type.BLUE) {
						input[k] = middle + (lowHigh[1] - middle) * value;
					}
				}
			}
		}

		MLData output = network.compute(new BasicMLData(input));

		int x = -1;
		int y = -1;
		double max = 0;
		for (int i = 0, k = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++, k++) {
				if (max < output.getData()[k] && cells[i][j].getType() == player) {
					x = i;
					y = j;
				}
			}
		}

		return new int[] { x, y };
	}
}
