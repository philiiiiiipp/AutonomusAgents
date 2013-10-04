package autonomousagents.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.predator.GreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Pair;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOffPolicyMC
{
	private static final double alpha = 0.1d;

	public static void test()
	{
		Set<Pair<State, Action>> observedStateActions = new HashSet<Pair<State, Action>>();

		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();
		Policy behaviorPolicyPred = new EGreedyPolicy();
		Policy deterministicPolicyPred = new GreedyPolicy();

		int counter = 0;
		while (counter < 1000)
		{
			List<Pair<State, Action>> episodesOnPolicy = generateEpisode(behaviorPolicyPred, preyPolicy);
			List<Pair<State, Action>> episodesOffPolicy = generateEpisode(deterministicPolicyPred, preyPolicy);
			int latestTime = -99;
			for (int i = episodesOnPolicy.size(); i >= 0; i--)
			{
				Action on = episodesOnPolicy.get(i).getRight();
				Action off = episodesOffPolicy.get(i).getRight();
				if (on != off)
				{
					latestTime = i;
					break;
				}
			}
		}

	}

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
