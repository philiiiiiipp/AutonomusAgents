package autonomousagents.policy.evaluator;

import autonomousagents.policy.Policy;
import autonomousagents.util.ValueMap;

/**
 * Class that implements the Policy Iteration algorithm
 * 
 * @author philipp
 * 
 */
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
			// first step - policy evaluation
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			// second step - policy improvement
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		System.out.println("Policy Iteration: " + counter);
		return valueMap;
	}
}
