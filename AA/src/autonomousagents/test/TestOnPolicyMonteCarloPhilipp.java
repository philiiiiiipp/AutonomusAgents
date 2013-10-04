package autonomousagents.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.Pair;
import autonomousagents.util.PrettyPrint;
import autonomousagents.util.Random;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOnPolicyMonteCarloPhilipp
{
	public static void test()
	{
		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		int counter = 0;

		Map<Pair<State, Action>, Pair<Double, Integer>> returns = new HashMap<Pair<State, Action>, Pair<Double, Integer>>();
		while (counter < 100000)
		{
			counter++;

			// (a)
			List<Pair<State, Action>> episode = generateEpisode(predatorPolicy, preyPolicy);
			// PrettyPrint.printEpisode(episode);

			// (b)

			for (int i = 0; i < episode.size(); ++i)
			{
				if (!returns.containsKey(episode.get(i)))
				{
					returns.put(episode.get(i), new Pair<Double, Integer>(0.0d, 0));
				}
				double r = Math.pow(Constants.GAMMA, episode.size() - (i + 1)) * Constants.REWARD;

				returns.get(episode.get(i)).setLeft(returns.get(episode.get(i)).getLeft() + r);
				returns.get(episode.get(i)).setRight(returns.get(episode.get(i)).getRight() + 1);
			}

			// (c)
			for (Pair<State, Action> pair : returns.keySet())
			{
				pair.getRight().setActionValue(returns.get(pair).getLeft() / returns.get(pair).getRight());
			}

		}

		// Test

		int stepCounter = 0;
		for (int i = 0; i < 1000; i++)
		{
			Environment e = new Environment();

			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);
			// Generate a random point, unequal to the prey position
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);

			e.addAgent(predator);
			e.addAgent(prey);

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

				s = e.getState();
				stepCounter++;
			}

		}
		System.out.println(stepCounter / 1000);
		PrettyPrint.printTable(predatorPolicy);
		System.out.println();

		State sTest = new State(new Point(5, 5), new Point(0, 0));
		PrettyPrint.printAction(predatorPolicy, sTest);
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

		Prey prey = new Prey(Random.randomPoint(), e, preyPolicy);
		// Generate a random point, unequal to the prey position
		Predator predator = new Predator(Random.randomPoint(prey.getPosition()), e, predatorPolicy);

		e.addAgent(predator);
		e.addAgent(prey);

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
