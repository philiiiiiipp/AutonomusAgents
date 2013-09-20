package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;
import autonomousagents.util.ValueMap;

public class PolicyIteration
{

	public static ValueMap evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		boolean stable = false;
		ValueMap valueMap = new ValueMap();
		int counter = 0;

		while (!stable)
		{
			counter++;
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		System.out.println("Policy Iteration: " + counter);
		return valueMap;
	}
}
