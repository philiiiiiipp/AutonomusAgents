package autonomousagents.test;

import autonomousagents.policy.evaluator.PolicyEvaluation;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.world.Point;

public class TestPolicyEvaluation
{
	public static void test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		double valueMap[][][][] = PolicyEvaluation.evaluate(predatorPolicy,
				preyPolicy);

		printStates(new Point(5, 5), valueMap);
		printStates(new Point(0, 0), new Point(5, 5), valueMap);
		printStates(new Point(2, 3), new Point(5, 4), valueMap);
		printStates(new Point(2, 10), new Point(10, 0), valueMap);
		printStates(new Point(10, 10), new Point(0, 0), valueMap);
	}

	private static void printStates(final Point preyPosition,
			final double[][][][] stateSpace)
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

	private static void printStates(final Point predatorPosition,
			final Point preyPosition, final double[][][][] stateSpace)
	{
		System.out.println();
		System.out
				.println("Predator("
						+ predatorPosition.getX()
						+ ", "
						+ predatorPosition.getY()
						+ "),"
						+ "Prey("
						+ preyPosition.getX()
						+ ", "
						+ preyPosition.getY()
						+ ") is "
						+ stateSpace[predatorPosition.getX()][predatorPosition
								.getY()][preyPosition.getX()][preyPosition
								.getY()]);
	}
}
