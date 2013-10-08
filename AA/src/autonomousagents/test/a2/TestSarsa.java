package autonomousagents.test.a2;

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

public class TestSarsa
{

	/**
	 * Run the SARSA algorithmn
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 * @param alpha
	 * @param gamma
	 * @param episodeCount
	 * @return a list, containing the number of steps used for catching the prey
	 *         after each episode
	 */
	public static List<Integer> runSarsa(final Policy predatorPolicy, final Policy preyPolicy, final double alpha,
			final double gamma, final int episodeCount)
	{
		List<Integer> stepsCount = new ArrayList<Integer>();

		for (int i = 0; i < episodeCount; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addPredator(predator);
			e.addPrey(prey);

			State s = e.getState();
			Action a = predatorPolicy.nextProbabilisticActionForState(s);

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				Action aPrime = predatorPolicy.nextProbabilisticActionForState(sPrime);

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * aPrime.getActionValue() - a.getActionValue()));

				s = sPrime;
				a = aPrime;
			} while (!s.isTerminal());

			stepsCount.add(counter);
		}

		return stepsCount;
	}
}
