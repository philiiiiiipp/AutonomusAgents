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
		while (!stable)
		{
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		return valueMap;
	}
}
