package autonomousagents.test;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.util.Constants;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class MultipleQLearning
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
	public static List<Integer> runQLearning()
	{
		List<Integer> stepList = new ArrayList<Integer>();

		for (int i = 0; i < 100; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Policy predatorPolicy = new EGreedyPolicy();
			Policy preyPolicy = new EGreedyPolicy();

			Predator predator1 = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addAgent(predator1);
			e.addAgent(prey);

			State s = e.getState();
			double alpha = 0.1;
			double gamma = 0.1;

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;

				Action predatorAction1 = predatorPolicy.nextProbabilisticActionForState(s);
				predatorAction1.apply(predator1);
				Action preyAction1 = preyPolicy.nextProbabilisticActionForState(s);
				preyAction1.apply(prey);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				State sPrime = e.getState();

				predatorAction1
						.setActionValue(predatorAction1.getActionValue()
								+ alpha
								* (reward + gamma * maximisation(predator1, prey, predatorPolicy, preyPolicy, sPrime) - predatorAction1
										.getActionValue()));
				preyAction1
						.setActionValue(preyAction1.getActionValue()
								+ alpha
								* (reward + gamma
										* maximisationPrey(prey, predator1, preyPolicy, predatorPolicy, sPrime) - preyAction1
											.getActionValue()));
				s = sPrime;
			} while (!s.isTerminal());

			stepList.add(counter);
			System.out.println(counter);
		}

		return stepList;
	}

	private static double maximisation(final Predator predator1, final Prey prey, final Policy predatorPolicy,
			final Policy preyPolicy, final State s)
	{
		List<Action> actionList = predatorPolicy.actionsForState(s);
		Action preyAction = preyPolicy.nextProbabilisticActionForState(s);
		preyAction.apply(prey);

		double highestActionValue = 0;
		for (Action a : actionList)
		{
			if (a.getActionValue() > highestActionValue)
				highestActionValue = a.getActionValue();
		}

		return highestActionValue;
	}

	private static double maximisationPrey(final Prey prey, final Predator predator1, final Policy predatorPolicy,
			final Policy preyPolicy, final State s)
	{
		List<Action> actionList = preyPolicy.actionsForState(s);
		Action predatorAction = predatorPolicy.nextProbabilisticActionForState(s);
		predatorAction.apply(predator1);

		double highestActionValue = 0;
		for (Action a : actionList)
		{
			if (a.getActionValue() > highestActionValue)
				highestActionValue = a.getActionValue();
		}

		return highestActionValue;
	}
}
