package autonomousagents.test;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestQLearning
{
	/**
	 * Run Q-Learning
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 * @param alpha
	 * @param gamme
	 * @param episodeCount
	 * @return a list, containing the number of steps used for catching the prey
	 *         after each episode
	 */
	public static List<Integer> runQLearning(final Policy predatorPolicy, final Policy preyPolicy, final double alpha,
			final double gamma, final int episodeCount)
	{
		List<Integer> stepList = new ArrayList<Integer>();

		for (int i = 0; i < episodeCount; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();

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
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * maximisation(predatorPolicy.actionsForState(sPrime)) - a.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			stepList.add(counter);
		}

		return stepList;
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
