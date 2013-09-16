package autonomousagents;

public class ValueIteration
{
	private final float[][][][] stateSpace = new float[11][11][11][11];

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
