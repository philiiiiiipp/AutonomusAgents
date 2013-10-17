package autonomousagents.test.a3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.GreedyPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.Pair;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOffPolicyMonteCarlo
{

	/**
	 * Run OffPolicy Monte Carlo
	 * 
	 * @param behaviorPolicyPredator
	 * @param deterministicPolicyPredator
	 * @param preyPolicy
	 * @param episodeCount
	 * @return a list, containing the number of steps used for catching the prey
	 *         after each episode
	 */
	public static List<Integer> runOffPolicyMonteCarlo(final Policy behaviorPolicyPredator,
			final GreedyPolicy deterministicPolicyPredator, final Policy preyPolicy, final int episodeCount)
	{
		List<Integer> resultList = new ArrayList<Integer>();

		int outerCounter = 0;
		while (outerCounter < episodeCount)
		{
			// (a)
			List<Pair<State, Action>> episodesOnPolicy = generateEpisode(behaviorPolicyPredator, preyPolicy);

			// (b)
			int tao = 0;
			for (int i = episodesOnPolicy.size() - 1; i >= 0; i--)
			{
				Pair<State, Action> current = episodesOnPolicy.get(i);
				Action a = deterministicPolicyPredator.nextProbabilisticActionForState(current.getLeft());

				if (!a.isEqual(current.getRight()))
				{
					tao = i;
					break;
				}
			}

			// (c)
			HashSet<Pair<State, Action>> done = new HashSet<Pair<State, Action>>();
			for (int t = tao; t < episodesOnPolicy.size(); t++)
			{
				Pair<State, Action> current = episodesOnPolicy.get(t);
				if (done.contains(current))
					continue;

				double w = createW(episodesOnPolicy, t, behaviorPolicyPredator);
				double returnT = Math.pow(Constants.GAMMA, episodesOnPolicy.size() - (t + 1)) * Constants.REWARD;

				current.getRight().setNumerator(current.getRight().getNumerator() + w * returnT);
				current.getRight().setDenominator(current.getRight().getDenominator() + w);

				current.getRight().setActionValue(
						current.getRight().getNumerator() / current.getRight().getDenominator());

				done.add(current);
			}

			// (d)
			for (State s : behaviorPolicyPredator.getPolicy().keySet())
			{
				List<Action> actionList = new ArrayList<Action>();
				Action newAction = behaviorPolicyPredator.actionWithHighestValue(s);

				actionList.add(newAction);
				deterministicPolicyPredator.getPolicy().put(s, actionList);
			}

			// Test
			Environment e = new Environment();

			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);
			Predator predator = new Predator(new Point(0, 0), e, deterministicPolicyPredator);

			e.addPredator(predator);
			e.addPrey(prey);

			int counter1 = 0;
			State s = e.getState();
			while (!s.isTerminal())
			{
				counter1++;
				Action a = deterministicPolicyPredator.nextProbabilisticActionForState(s);
				a.apply(predator);

				// If the current state is terminal, the prey is not moving once
				// more, since its already eaten.
				if (!e.getState().isTerminal())
				{
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				s = e.getState();
			}
			resultList.add(counter1);
			outerCounter++;
		}

		return resultList;
	}

	private static double createW(final List<Pair<State, Action>> episodesOnPolicy, final int t, final Policy piPrime)
	{
		double w = 1;
		for (int k = t; k < episodesOnPolicy.size(); k++)
		{
			Action aHihgest = piPrime.actionWithHighestValue(episodesOnPolicy.get(k).getLeft());
			boolean isHighest = aHihgest.isEqual(episodesOnPolicy.get(k).getRight());
			double probability = -1;

			if (isHighest)
			{
				probability = 1 - Constants.EPSILON + 1.0 / 5 * Constants.EPSILON;
			} else
			{
				probability = 1.0 / 5 * Constants.EPSILON;
			}

			w *= 1.0d / probability;
		}

		return w;
	}

	private static List<Pair<State, Action>> generateEpisode(final Policy predatorPolicy, final Policy preyPolicy)
	{
		List<Pair<State, Action>> episode = new ArrayList<Pair<State, Action>>();

		Environment e = new Environment();

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
