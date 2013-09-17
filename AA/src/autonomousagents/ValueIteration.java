package autonomousagents;

import java.util.List;

public class ValueIteration
{
	private final float[][][][] stateSpace = new float[11][11][11][11];

	private static final float REWARD = 1000.0f;
	private static final float GAMMA = 0.9f;

	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	private static final int STAY = 4;

	public float sweep()
	{
		float delta = 0;

		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				for (int xPrey = 0; xPrey < 11; xPrey++)
				{
					for (int yPrey = 0; yPrey < 11; yPrey++)
					{
						float valueForThisState = this.stateSpace[xPred][yPred][xPrey][yPrey];
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
		for (int xPred = 0; xPred < 11; xPred++)
		{
			System.out.println();
			for (int yPred = 0; yPred < 11; yPred++)
			{
				System.out.print(this.stateSpace[xPred][yPred][preyPosition
						.getX()][preyPosition.getY()] + " \t ");
			}
		}
	}

	private float maximisation(final Point predPosition,
			final Point preyPosition)
	{
		if (predPosition.equals(preyPosition))
		{
			// EndState
			return REWARD;
		}

		float vStar = 0;
		Point newPredPosition = null;
		for (int i = 0; i < 5; ++i)
		{
			switch (i)
			{
			case NORTH:
				newPredPosition = State.north(predPosition);
				break;
			case EAST:
				newPredPosition = State.east(predPosition);
				break;
			case SOUTH:
				newPredPosition = State.south(predPosition);
				break;
			case WEST:
				newPredPosition = State.west(predPosition);
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

			float vSPrimeTotal = 0;
			// first for the STAY policy of the prey
			vSPrimeTotal += 0.8 * (GAMMA * this.stateSpace[predPosition.getX()][predPosition
					.getY()][preyPosition.getX()][preyPosition.getY()]);

			// Now all the other possible moves
			List<Point> possiblesMoves = State.preyCanMoveTo(preyPosition,
					predPosition);
			for (Point p : possiblesMoves)
			{
				vSPrimeTotal += 1.0f
						/ possiblesMoves.size()
						* 0.2
						* (GAMMA * this.stateSpace[predPosition.getX()][predPosition
								.getY()][p.getX()][p.getY()]);
			}

			if (vStar < vSPrimeTotal)
				vStar = vSPrimeTotal;
		}

		return vStar;
	}
}
