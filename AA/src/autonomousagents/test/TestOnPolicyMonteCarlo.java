package autonomousagents.test;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.Pair;
import autonomousagents.util.Probability;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOnPolicyMonteCarlo
{
	public void Evaluate()
	{
		List<Double> returnList = new ArrayList<Double>();

		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		while (true)
		{

			List<Pair<State, Action>> stateActionList = new ArrayList<Pair<State, Action>>();
			// generate an episode using the current policy
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();
			do
			{
				Action a = predatorPolicy.nextProbabilisticActionForState(s);
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}
				stateActionList.add(new Pair<State, Action>(s, a));
				s = e.getState();
			} while (!s.isTerminal());

			for (int i = 0; i < stateActionList.size(); ++i)
			{
				Pair<State, Action> sa = stateActionList.get(i);
				double r = 0;
				if (i == stateActionList.size() - 1)
				{
					r = Constants.REWARD;
				}
				double returnValue = Math.pow(Constants.GAMMA, i) * r;

				Action action = sa.getRight();
				action.setActionValue(returnValue);

				State state = sa.getLeft();
				double bestActionValue = maximisation(predatorPolicy.actionsForState(state));
				List<Probability<Action>> probabilityList = new ArrayList<Probability<Action>>();
				for (int j = 0; j < predatorPolicy.actionsForState(state).size(); ++j)
				{
					Action a = predatorPolicy.actionsForState(state).get(j);
					double ActionValue = a.getActionValue();
					double probability;
					if (bestActionValue != ActionValue)
					{
						probability = Constants.EPSILON / predatorPolicy.actionsForState(state).size();
					} else
					{
						probability = 1 - Constants.EPSILON + Constants.EPSILON
								/ predatorPolicy.actionsForState(state).size();
					}
					probabilityList.add(new Probability<Action>(a, probability));
					// predatorPolicy
				}
			}
		}
	}

	private double maximisation(final List<Action> actionList)
	{
		double highestActionValue = 0;
		for (Action a : actionList)
		{
			if (a.getActionValue() > highestActionValue)
				highestActionValue = a.getActionValue();
		}

		return highestActionValue;
	}

	private static double reward(final Point predator, final Point prey)
	{
		if (predator.equals(prey))
			return Constants.REWARD;

		return 0;
	}
}
