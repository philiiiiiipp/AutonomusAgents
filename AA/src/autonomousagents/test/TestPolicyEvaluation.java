package autonomousagents.test;

import autonomousagents.Point;
import autonomousagents.policy.evaluator.PolicyEvaluation;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;

public class TestPolicyEvaluation
{
	public static void test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		float stateSpace[][][][] = PolicyEvaluation.evaluate(predatorPolicy,
				preyPolicy);

		printStates(new Point(5, 5), stateSpace);
		printStates(new Point(2, 3), new Point(5, 4), stateSpace);
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

	private static void printStates(final Point predatorPosition,
			final Point preyPosition, final float[][][][] stateSpace)
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
