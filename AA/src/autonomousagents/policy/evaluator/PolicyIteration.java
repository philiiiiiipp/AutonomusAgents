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

		int counter = 0;
		ValueMap valueMap = new ValueMap();
		while (!stable)
		{
<<<<<<< HEAD
			counter += 1;
=======
			// first step - policy evaluation
>>>>>>> c9483f04bd851331fe086980da28670e4c333906
			valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);
			// second step - policy improvement
			stable = PolicyImprovement.improve(predatorPolicy, preyPolicy,
					valueMap);
		}

		System.out.println("Counter is at: " + counter);
		return valueMap;
	}
}
