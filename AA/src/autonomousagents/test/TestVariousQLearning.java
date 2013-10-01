package autonomousagents.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestVariousQLearning
{
	private static final int NUMBER_OF_EPISODES = 800;
	private static final double alpha = 0.1d;

	public static void test()
	{
		ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < 60; i += 5)
		{
			values.add(new ArrayList<Double>());
			for (double j = 0; j < 0.9; j += 0.03)
			{
				Constants.QValue = i;
				Constants.EPSILON = j;

				double thisConfigurationsValue = testWithSpecificValues();

				values.get(values.size() - 1).add(thisConfigurationsValue);
			}
		}

		pprint(values);
	}

	public static double testWithSpecificValues()
	{
		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		double total = 0;
		for (int i = 0; i < NUMBER_OF_EPISODES; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(predator);
			e.addAgent(prey);

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

			if (NUMBER_OF_EPISODES - 100 <= i)
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

	private static void pprint(final ArrayList<ArrayList<Double>> values)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		for (ArrayList<Double> row : values)
		{
			for (double v : row)
			{
				System.out.print(df.format(v) + "\t");
			}
			System.out.println();
		}
	}
}