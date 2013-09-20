package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;

public class PolicyIteration
{

	public static double[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		boolean stable = false;
		double[][][][] valueMap = null;
		while (!stable)
		{
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		return valueMap;
	}
}
