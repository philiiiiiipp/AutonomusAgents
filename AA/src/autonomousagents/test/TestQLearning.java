package autonomousagents.test;

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
	private static final int NUMBER_OF_EPISODES = 1000;
	private static final double alpha = 0.1d;

	public static void test()
	{

		double average = 0;
		Policy pred = new EGreedyPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();
		for (int i = 0; i < NUMBER_OF_EPISODES; ++i)
		{
			Environment e = new Environment();
			Predator p = new Predator(new Point(0, 0), e, pred);
			Prey prey = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(p);
			e.addAgent(prey);

			State s = e.getState();
			int counter = 0;
			do
			{
				counter++;
				Action a = pred.nextProbabalisticActionForState(e.getState());
				a.apply(p);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
					preyPoly.nextProbabalisticActionForState(e.getState()).apply(prey);

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue()
						+ alpha
						* (reward + Constants.GAMMA * (maximisation(pred.actionsForState(sPrime))) - a.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			printTable(pred);
			average += counter;
		}
		System.out.println(average / NUMBER_OF_EPISODES);
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

	private static void printTable(final Policy predatorPolicy)
	{
		for (int xPred = 0; xPred < 11; xPred++)
		{
			System.out.print(" | ");
			for (int yPred = 0; yPred < 11; yPred++)
			{
				Action a = predatorPolicy.nextProbabalisticActionForState(new State(new Point(xPred, yPred), new Point(
						5, 5)));

				System.out.print(a + " ");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}
}
