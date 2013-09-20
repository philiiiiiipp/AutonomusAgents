package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;
import autonomousagents.util.ValueMap;

/* Class that implements the Policy Iteration algorithm */
public class PolicyIteration
{

	public static ValueMap evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		boolean stable = false;
		ValueMap valueMap = new ValueMap();
		while (!stable)
		{
			// first step - policy evaluation
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			// second step - policy improvement
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		return valueMap;
	}
}
