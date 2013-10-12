package autonomousagents.test;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.predator.EGreedyPolicyWithTrip;
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
	public static List<Integer> runQLearning(final int episodeCount, final double alpha, final double gamma,
			final List<Point> predatorPoints, final Point preyPoint)
	{
		List<Integer> stepList = new ArrayList<Integer>();
		List<Policy> policyList = new ArrayList<Policy>();

		for (int i = 0; i < predatorPoints.size(); i++)
		{
			policyList.add(new EGreedyPolicy());
		}
		// System.out.println(policyList.size());

		Policy preyPolicy = new EGreedyPolicyWithTrip();

		for (int i = 0; i < episodeCount; ++i)
		{
			// Initialise s
			Environment e = new Environment();

			List<Predator> predatorList = new ArrayList<Predator>();
			for (int j = 0; j < predatorPoints.size(); j++)
			{
				Predator p = new Predator(predatorPoints.get(j), e, policyList.get(j));
				predatorList.add(p);
				e.addPredator(p);
			}

			Prey prey = new Prey(preyPoint, e, preyPolicy);
			e.addPrey(prey);

			State s = e.getState();

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				List<Action> predatorActions = new ArrayList<Action>();

				for (int predatorX = 0; predatorX < predatorList.size(); predatorX++)
				{
					Policy predatorPolicyX = policyList.get(predatorX);
					// System.out.println(s);
					Action predatorActionXAction = predatorPolicyX.nextProbabilisticActionForState(s);
					predatorActions.add(predatorActionXAction);
					predatorActionXAction.apply(predatorList.get(predatorX));
				}

				Action preyAction = preyPolicy.nextProbabilisticActionForState(s);
				preyAction.apply(prey);

				// Reward from this action
				double predatorReward = (e.getState().isTerminal() ? 10 : 0);
				double preyReward = (e.getState().isTerminal() ? -10 : 0);

				State sPrime = e.getState();

				for (int predAction = 0; predAction < predatorActions.size(); predAction++)
				{
					Action predatorActionX = predatorActions.get(predAction);

					predatorActionX.setActionValue(predatorActionX.getActionValue()
							+ alpha
							* (predatorReward
									+ gamma
									* maximisationPredator(predAction, predatorList, prey, policyList, preyPolicy,
											sPrime) - predatorActionX.getActionValue()));
				}

				preyAction
						.setActionValue(preyAction.getActionValue()
								+ alpha
								* (preyReward + gamma
										* maximisationPrey(predatorList, prey, policyList, preyPolicy, sPrime) - preyAction
											.getActionValue()));
				s = sPrime;
			} while (!s.isTerminal());

			stepList.add(counter);
		}

		return stepList;
	}

	private static double maximisationPredator(final int currentPredatorIndex, final List<Predator> predatorList,
			final Prey prey, final List<Policy> policyList, final Policy preyPolicy, final State s)
	{
		List<Action> actionList = policyList.get(currentPredatorIndex).actionsForState(s);
		for (int i = 0; i < predatorList.size(); i++)
		{
			if (i != currentPredatorIndex)
			{
				policyList.get(i).nextProbabilisticActionForState(s).apply(predatorList.get(i));
			}
		}
		preyPolicy.nextProbabilisticActionForState(s).apply(prey);
		double highestActionValue = 0;
		for (Action action : actionList)
		{
			if (action.getActionValue() > highestActionValue)
			{
				highestActionValue = action.getActionValue();
			}
		}
		// System.out.println(highestActionValue);
		return highestActionValue;
	}

	private static double maximisationPrey(final List<Predator> predatorList, final Prey prey,
			final List<Policy> policyList, final Policy preyPolicy, final State s)
	{
		List<Action> actionList = preyPolicy.actionsForState(s);
		for (int i = 0; i < predatorList.size(); i++)
		{
			policyList.get(i).nextProbabilisticActionForState(s).apply(predatorList.get(i));
		}

		double highestActionValue = 0;
		for (Action action : actionList)
		{
			if (action.getActionValue() > highestActionValue)
			{
				highestActionValue = action.getActionValue();
			}
		}

		return highestActionValue;
	}
}
