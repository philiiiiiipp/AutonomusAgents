package autonomousagents.test.a1;

import java.text.DecimalFormat;

import autonomousagents.policy.evaluator.ValueIteration;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;

/**
 * Class used to test the correctness of the Value Iteration algorithm
 * 
 */
public class TestValueIteration
{
	/**
	 * test method
	 */
	public static void test()
	{
		PredatorRandomPolicy predatorPolicy = new PredatorRandomPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		ValueMap stateSpace = ValueIteration.evaluate(predatorPolicy, preyPolicy);

		printStates(new Point(5, 5), stateSpace);
	}

	/**
	 * method to print out the values for the states given the Prey positions
	 * 
	 * @param preyPosition
	 * @param stateSpace
	 */
	private static void printStates(final Point preyPosition, final ValueMap stateSpace)
	{
		DecimalFormat df = new DecimalFormat("#.000000");
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				System.out
						.print(df.format(stateSpace.getValueForState(new Point(xPred, yPred), preyPosition)) + " \t ");
			}
			System.out.println();
		}
	}
}
