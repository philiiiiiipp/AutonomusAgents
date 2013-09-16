package autonomousagents;

public class ValueIteration
{
	private final float[][][][] stateSpace = new float[11][11][11][11];

	enum actions
	{
		NORTH, EAST, SOUTH, WEST, STAY
	}

	public void sweep()
	{
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				for (int xPrey = 0; xPrey < 11; xPrey++)
				{
					for (int yPrey = 0; yPrey < 11; yPrey++)
					{

					}
				}
			}
		}
	}

	private float maximisation(final Point predPosition,
			final Point preyPosition)
	{
		Point newPredPosition;
		for (int i = 0; i < 5; ++i)
		{
			switch (i)
			{
			case 0:
				newPredPosition = State.north(predPosition);
				break;
			case 1:
				newPredPosition = State.east(predPosition);
			case 2:
				newPredPosition = State.south(predPosition);
			case 3:
				newPredPosition = State.west(predPosition);
			default:
				break;
			}
		}
	}

	private float reward(final Point preyPosition, final Point predatorPosition)
	{
		if (preyPosition.equals(predatorPosition))
		{
			return 1.0f;
		} else
		{
			return 0;
		}
	}

}
