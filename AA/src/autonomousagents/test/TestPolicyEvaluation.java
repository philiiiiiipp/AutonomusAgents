package autonomousagents.test;

import java.text.DecimalFormat;

import autonomousagents.policy.evaluator.PolicyEvaluation;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;

/**
 * Class used to test the correctness of the Policy Evaluation algorithm
 * 
 */
public class TestPolicyEvaluation
{
	/**
	 * test method
	 */
	public static void test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		ValueMap valueMap = PolicyEvaluation.evaluate(predatorPolicy, preyPolicy);

		printStates(new Point(5, 5), valueMap);
		printStates(new Point(0, 0), new Point(5, 5), valueMap);
		printStates(new Point(2, 3), new Point(5, 4), valueMap);
		printStates(new Point(2, 10), new Point(10, 0), valueMap);
		printStates(new Point(10, 10), new Point(0, 0), valueMap);
	}

	/**
	 * method used to print out the values of the states given the Prey position
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

	/**
	 * method to print out the values of the states given the Predator and Prey
	 * position
	 * 
	 * @param predatorPosition
	 * @param preyPosition
	 * @param stateSpace
	 */
	private static void printStates(final Point predatorPosition, final Point preyPosition, final ValueMap stateSpace)
	{
		System.out.println();
		System.out.println("Predator(" + predatorPosition.getX() + ", " + predatorPosition.getY() + ")," + "Prey("
				+ preyPosition.getX() + ", " + preyPosition.getY() + ") is "
				+ stateSpace.getValueForState(predatorPosition, preyPosition));
	}
}
