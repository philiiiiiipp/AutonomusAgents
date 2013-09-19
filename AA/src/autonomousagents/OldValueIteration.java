package autonomousagents;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public class OldValueIteration
{
	private final double[][][][] stateSpace = new double[11][11][11][11];

	private static final double REWARD = 10.0d;
	private static final double GAMMA = 0.8d;
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	private static final int STAY = 4;

	public double sweep()
	{
		double delta = 0;

		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				for (int xPrey = 0; xPrey < 11; xPrey++)
				{
					for (int yPrey = 0; yPrey < 11; yPrey++)
					{
						if (xPred == xPrey && yPred == yPrey)
							continue;

						double valueForThisState = this.stateSpace[xPred][yPred][xPrey][yPrey];
						this.stateSpace[xPred][yPred][xPrey][yPrey] = maximisation(
								new Point(xPred, yPred),
								new Point(xPrey, yPrey));

						delta = Math.max(delta, Math.abs(valueForThisState
								- this.stateSpace[xPred][yPred][xPrey][yPrey]));
					}
				}
			}
		}

		return delta;
	}

	public void printStates(final Point preyPosition)
	{
		DecimalFormat df = new DecimalFormat("#.000000");
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				System.out.print(df
						.format(this.stateSpace[xPred][yPred][preyPosition
								.getX()][preyPosition.getY()])
						+ " \t ");
			}
			System.out.println();
		}
	}

	private double maximisation(final Point predPosition,
			final Point preyPosition)
	{
		if (predPosition.equals(preyPosition))
		{
			// EndState
			return REWARD;
		}

		double vStar = 0;
		Point newPredPosition = null;
		for (int i = 0; i < 5; ++i)
		{
			switch (i)
			{
			case NORTH:
				newPredPosition = Environment.north(predPosition);
				break;
			case EAST:
				newPredPosition = Environment.east(predPosition);
				break;
			case SOUTH:
				newPredPosition = Environment.south(predPosition);
				break;
			case WEST:
				newPredPosition = Environment.west(predPosition);
				break;
			case STAY:
				newPredPosition = predPosition;
				break;
			default:
				break;
			}

			if (newPredPosition.equals(preyPosition))
			{
				// catched, max reward
				return REWARD;
			}

			double vSPrimeTotal = 0;
			// first for the STAY policy of the prey
			vSPrimeTotal += 0.8d * (GAMMA * this.stateSpace[newPredPosition
					.getX()][newPredPosition.getY()][preyPosition.getX()][preyPosition
					.getY()]);

			// Now all the other possible moves
			List<Point> possiblesMoves = new ArrayList<Point>();

			Point newPoint = Environment.north(preyPosition);
			if (!newPoint.equals(newPredPosition))
				possiblesMoves.add(newPoint);

			newPoint = Environment.east(preyPosition);
			if (!newPoint.equals(newPredPosition))
				possiblesMoves.add(newPoint);

			newPoint = Environment.south(preyPosition);
			if (!newPoint.equals(newPredPosition))
				possiblesMoves.add(newPoint);

			newPoint = Environment.west(preyPosition);
			if (!newPoint.equals(newPredPosition))
				possiblesMoves.add(newPoint);

			for (Point p : possiblesMoves)
			{
				vSPrimeTotal += 1.0d
						/ possiblesMoves.size()
						* 0.2d
						* (GAMMA * this.stateSpace[newPredPosition.getX()][newPredPosition
								.getY()][p.getX()][p.getY()]);
			}

			if (vStar < vSPrimeTotal)
				vStar = vSPrimeTotal;
		}

		return vStar;
	}
}
