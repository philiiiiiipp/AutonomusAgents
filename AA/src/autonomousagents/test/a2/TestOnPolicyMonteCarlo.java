package autonomousagents.test.a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.util.Pair;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOnPolicyMonteCarlo
{
	/**
	 * Running a fixed amount of episodes with onpolicy monte carlo.
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 * @return a list, containing the number of steps used for catching the prey
	 *         after each episode
	 */
	public static List<Integer> runOnPolicyMonteCarlo(final Policy predatorPolicy, final Policy preyPolicy,
			final int episodeCount)
	{
		List<Integer> resultList = new ArrayList<Integer>();

		Map<Pair<State, Action>, Pair<Double, Integer>> returns = new HashMap<Pair<State, Action>, Pair<Double, Integer>>();
		Set<Pair<State, Action>> observedStateActions = new HashSet<Pair<State, Action>>();

		int counter = 0;
		while (counter < episodeCount)
		{
			observedStateActions.clear();

			// (a)
			List<Pair<State, Action>> episode = generateEpisode(predatorPolicy, preyPolicy);

			// (b)
			for (int i = 0; i < episode.size(); ++i)
			{
				if (!returns.containsKey(episode.get(i)))
				{
					returns.put(episode.get(i), new Pair<Double, Integer>(0.0d, 0));
				}

				if (!observedStateActions.contains(episode.get(i)))
				{
					observedStateActions.add(episode.get(i));

					double r = Math.pow(Constants.GAMMA, episode.size() - (i + 1)) * Constants.REWARD;

					returns.get(episode.get(i)).setLeft(returns.get(episode.get(i)).getLeft() + r);
					returns.get(episode.get(i)).setRight(returns.get(episode.get(i)).getRight() + 1);
				}
			}

			// (c)
			for (Pair<State, Action> pair : returns.keySet())
			{
				pair.getRight().setActionValue(returns.get(pair).getLeft() / returns.get(pair).getRight());
			}

			resultList.add(episode.size());

			counter++;
		}

		return resultList;
	}

	/**
	 * Generate an episode using the given policy
	 * 
	 * @return a list of state/action pairs, resembling the episode
	 */
	private static List<Pair<State, Action>> generateEpisode(final Policy predatorPolicy, final Policy preyPolicy)
	{
		List<Pair<State, Action>> episode = new ArrayList<Pair<State, Action>>();

		Environment e = new Environment();

		// Prey prey = new Prey(Random.randomPoint(), e, preyPolicy);
		// Generate a random point, unequal to the prey position
		// Predator predator = new
		// Predator(Random.randomPoint(prey.getPosition()), e, predatorPolicy);

		Prey prey = new Prey(new Point(5, 5), e, preyPolicy);
		Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);

		e.addPredator(predator);
		e.addPrey(prey);

		State s = e.getState();
		while (!s.isTerminal())
		{
			Action a = predatorPolicy.nextProbabilisticActionForState(s);
			a.apply(predator);

			// If the current state is terminal, the prey is not moving once
			// more, since its already eaten.
			if (!e.getState().isTerminal())
			{
				preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
			}

			episode.add(new Pair<State, Action>(s, a));
			s = e.getState();
		}

		return episode;
	}
}
