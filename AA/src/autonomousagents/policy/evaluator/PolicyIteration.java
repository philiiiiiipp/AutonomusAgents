package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;
import autonomousagents.world.State;

public class PolicyIteration
{
	private static final double REWARD = 10;
	private static final double GAMMA = 0.8f;
	private static final double THETA = 0.00000001f;

	public static double[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double[][][][] stateSpace = new double[11][11][11][11];
		double delta = 0;

		return stateSpace;
	}

	private static double maximisation(final State s,
			final double[][][][] stateSpace, final Policy preyPolicy)
	{
		double vStar = 0;
		return vStar;
	}
}
