package autonomousagents.test;

import autonomousagents.Point;
import autonomousagents.policy.evaluator.ValueIteration;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;

public class TestValueIteration
{
	public static void test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		float stateSpace[][][][] = ValueIteration.evaluate(predatorPolicy,
				preyPolicy);

		printStates(new Point(5, 5), stateSpace);
	}

	private static void printStates(final Point preyPosition,
			final float[][][][] stateSpace)
	{
		for (int xPred = 0; xPred < 11; xPred++)
		{
			System.out.println();
			for (int yPred = 0; yPred < 11; yPred++)
			{
				System.out
						.print(stateSpace[xPred][yPred][preyPosition.getX()][preyPosition
								.getY()] + " \t ");
			}
		}
	}
}
