package autonomousagents.util;

import java.text.DecimalFormat;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

/**
 * Class to create Python lists, in order to analyse the results
 * 
 */
public class PythonListCreator
{
	private static final double alpha = 0.1d;

	public static void createList()
	{
		DecimalFormat df = new DecimalFormat("#.0000");
		String pythonicList = "[";
		for (int i = 0; i < 60; i += 3)
		{
			pythonicList += "[";
			for (double j = 0; j < 0.9; j += 0.015)
			{
				Constants.QValue = i;
				Constants.EPSILON = j;

				double thisConfigurationsValue = testWithSpecificValues();

				pythonicList += df.format(thisConfigurationsValue) + ",";
			}
			pythonicList += "],";
		}
		pythonicList += "]";
		System.out.println(pythonicList);
	}

	public static double testWithSpecificValues()
	{
		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		double total = 0;
		for (int i = 0; i < Constants.NUMBER_OF_EPISODES; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPoly);

			e.addPredator(predator);
			e.addPrey(prey);

			State s = e.getState();
			//

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				Action a = predatorPolicy.nextProbabilisticActionForState(s);
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPoly.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue()
						+ alpha
						* (reward + Constants.GAMMA * maximisation(predatorPolicy.actionsForState(sPrime)) - a
								.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			if (Constants.NUMBER_OF_EPISODES - 100 <= i)
			{
				total += counter;
			}
		}

		return total / 100;
	}

	private static double maximisation(final List<Action> actionList)
	{
		double highestActionValue = 0;
		for (Action a : actionList)
		{
			if (a.getActionValue() > highestActionValue)
				highestActionValue = a.getActionValue();
		}

		return highestActionValue;
	}
}