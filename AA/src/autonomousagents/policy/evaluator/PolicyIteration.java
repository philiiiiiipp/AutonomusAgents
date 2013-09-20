package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;

public class PolicyIteration
{

	public static double[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		boolean stable = false;
		double[][][][] valueMap = null;
		int counter = 0;
		while (!stable)
		{
			counter += 1;
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		System.out.println("Counter is at: " + counter);
		return valueMap;
	}
}
