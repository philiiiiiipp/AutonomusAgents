package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;
import autonomousagents.util.ValueMap;

public class PolicyIteration
{

	public static ValueMap evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		boolean stable = false;

		int counter = 0;
		ValueMap valueMap = new ValueMap();
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
