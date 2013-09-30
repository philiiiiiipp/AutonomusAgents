package autonomousagents.test;

import java.text.DecimalFormat;
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

public class TestQLearning
{
	private static final int NUMBER_OF_EPISODES = 1000000;
	private static final double alpha = 0.1d;

	public static void test()
	{

		double average = 0;
		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		System.out.println(predatorPolicy.getPolicy().keySet().size());

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
				Action a = predatorPolicy.nextProbabalisticActionForState(s);
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPoly.nextProbabalisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue()
						+ alpha
						* (reward + Constants.GAMMA * maximisation(predatorPolicy.actionsForState(sPrime)) - a
								.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			// System.out.println(counter);
			// printTable(predatorPolicy);
			if (i >= NUMBER_OF_EPISODES - 1000)
				average += counter;
		}
		System.out.println(average / 1000);
		printAction(predatorPolicy);
		printTable(predatorPolicy);
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

	private static void printAction(final Policy predatorPolicy)
	{

		DecimalFormat df = new DecimalFormat("#.000000");

		State s = new State(new Point(5, 4), new Point(5, 5));

		for (Action a : predatorPolicy.actionsForState(s))
		{
			System.out.println(a.getActionValue() + " " + a);
		}

		for (int yPred = 0; yPred < 11; yPred++)
		{
			System.out.print(" | ");
			for (int xPred = 0; xPred < 11; xPred++)
			{
				s = new State(new Point(xPred, yPred), new Point(5, 5));

				// if (s.isTerminal())
				// {
				// System.out.print(df.format(0.0) + ".\t");
				// continue;
				// }
				Action a = predatorPolicy.actionWithHighestValue(s);
				System.out.print(+xPred + ":" + yPred + " " + df.format(a.getActionValue()) + a + "\t");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}

	private static void printTable(final Policy predatorPolicy)
	{
		for (int yPred = 0; yPred < 11; yPred++)
		{
			System.out.print(" | ");
			for (int xPred = 0; xPred < 11; xPred++)
			{
				State s = new State(new Point(xPred, yPred), new Point(5, 5));

				if (s.isTerminal())
				{
					System.out.print("# ");
					continue;
				}

				Action a = predatorPolicy.actionWithHighestValue(s);
				System.out.print(a + " ");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}
}
