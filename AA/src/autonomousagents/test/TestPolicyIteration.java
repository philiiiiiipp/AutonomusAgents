package autonomousagents.test;

import java.text.DecimalFormat;

import autonomousagents.policy.evaluator.PolicyIteration;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;

/**
 * Class used to test the correctness of the Policy Iteration algorithm
 * 
 */
public class TestPolicyIteration
{
	/**
	 * test method
	 * 
	 * @return
	 */
	public static PredatorRandomPolicy test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		ValueMap valueMap = PolicyIteration.evaluate(predatorPolicy, preyPolicy);

		printStates(new Point(5, 5), valueMap);

		return predatorPolicy;
	}

	/**
	 * method to print out the values of the states given the Prey position
	 * 
	 * @param preyPosition
	 * @param valueMap
	 */
	private static void printStates(final Point preyPosition, final ValueMap valueMap)
	{
		DecimalFormat df = new DecimalFormat("#.000000");
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				System.out.print(df.format(valueMap.getValueForState(new Point(xPred, yPred), preyPosition)) + " \t ");
			}
			System.out.println();
		}
	}
}
