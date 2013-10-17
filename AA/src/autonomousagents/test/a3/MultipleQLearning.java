package autonomousagents.test.a3;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.EGreedyPolicyWithTrip;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;
import autonomousagents.world.State.TerminalStates;

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
			policyList.add(new EGreedyPolicy(predatorPoints.size()));
		}
		// System.out.println(policyList.size());

		Policy preyPolicy = new EGreedyPolicyWithTrip(predatorPoints.size());

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
			// int counter = 0;
			do
			{
				// counter++;
				List<Action> predatorActions = new ArrayList<Action>();

				for (int predatorX = 0; predatorX < predatorList.size(); predatorX++)
				{
					Policy predatorPolicyX = policyList.get(predatorX);

					Action predatorActionXAction = predatorPolicyX.nextProbabilisticActionForState(s);
					predatorActions.add(predatorActionXAction);
					predatorActionXAction.apply(predatorList.get(predatorX));
				}

				Action preyAction = preyPolicy.nextProbabilisticActionForState(s);
				preyAction.apply(prey);

				// Reward from this action
				TerminalStates state = e.getState().getTerminalState();
				double predatorReward = 0;
				double preyReward = 0;

				if (state == TerminalStates.PREDATOR_WINS)
				{
					predatorReward = 10;
					preyReward = -10;
				} else if (state == TerminalStates.PREY_WINS)
				{
					predatorReward = -10;
					preyReward = 10;
				}

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
		}

		for (int step = 0; step < 1000; step++)
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

			do
			{
				for (Predator p : predatorList)
				{
					p.step();
				}

				prey.step();
				s = e.getState();
			} while (!s.isTerminal());

			stepList.add((s.getTerminalState() == TerminalStates.PREDATOR_WINS) ? 1 : 0);
		}

		return stepList;
	}

	private static double maximisationPredator(final int currentPredatorIndex, final List<Predator> predatorList,
			final Prey prey, final List<Policy> policyList, final Policy preyPolicy, final State s)
	{

		List<Point> newPredatorPoints = new ArrayList<Point>();
		for (int i = 0; i < predatorList.size(); i++)
		{
			if (i != currentPredatorIndex)
			{
				newPredatorPoints.add(policyList.get(i).nextProbabilisticActionForState(s)
						.apply((predatorList.get(i).getPosition())));
			} else
			{
				newPredatorPoints.add(predatorList.get(i).getPosition());
			}
		}

		Point preyPoint = preyPolicy.nextProbabilisticActionForState(s).apply(prey.getPosition());

		List<Action> actionList = policyList.get(currentPredatorIndex).actionsForState(
				new State(newPredatorPoints, preyPoint));
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
		List<Point> newPredatorPoints = new ArrayList<Point>();
		for (int i = 0; i < predatorList.size(); i++)
		{
			newPredatorPoints.add(policyList.get(i).nextProbabilisticActionForState(s)
					.apply((predatorList.get(i).getPosition())));
		}

		List<Action> actionList = preyPolicy.actionsForState(new State(newPredatorPoints, prey.getPosition()));
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
