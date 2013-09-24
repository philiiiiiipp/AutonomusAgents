package autonomousagents.policy.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PolicyImprovement
{
	/**
	 * Improving the predator policy, return true if the policy is stable
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 */
	public static boolean improve(final Policy predatorPolicy, final Policy preyPolicy, final ValueMap valueMap)
	{
		boolean policyStable = true;

		for (State s : predatorPolicy.getPolicy().keySet())
		{
			if (s.isTerminal())
				continue;

			List<Action> actions = predatorPolicy.actionsForState(s);
			predatorPolicy.getPolicy().put(s, maximisation(s, valueMap, predatorPolicy, preyPolicy));

			if (!isEquals(predatorPolicy.getPolicy().get(s), actions))
			{
				policyStable = false;
			}
		}

		return policyStable;
	}

	/**
	 * Returns if two actions lists are equivalent.
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	private static boolean isEquals(final List<Action> l1, final List<Action> l2)
	{
		if (l1.size() != l2.size())
			return false;

		for (Action a : l1)
		{
			if (!l2.contains(a))
				return false;
		}
		return true;
	}

	/**
	 * Calculates the argmax of the axions for a specific policy
	 * 
	 * @param s
	 * @param valueMap
	 * @param predatorPolicy
	 * @param preyPolicy
	 * @return
	 */
	private static List<Action> maximisation(final State s, final ValueMap valueMap, final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double maxV = 0;
		Map<Action, Double> resultMap = new HashMap<Action, Double>();

		Point newPredPosition = null;
		for (Action predatorAction : predatorPolicy.actionsForState(s))
		{
			double stateSum = 0;
			newPredPosition = predatorAction.apply(s.predatorPoint());

			List<Action> possibleAction = preyPolicy.actionsForState(new State(newPredPosition, s.preyPoint()));

			Point newPreyPoint = null;
			for (Action preyAction : possibleAction)
			{
				newPreyPoint = preyAction.apply(s.preyPoint());

				stateSum += preyAction.getProbability()
						* (reward(newPredPosition, newPreyPoint) + (Constants.GAMMA * valueMap.getValueForState(
								newPredPosition, newPreyPoint)));
			}

			if (maxV < stateSum)
				maxV = stateSum;

			resultMap.put(predatorAction, stateSum);
		}

		List<Action> resultList = new ArrayList<Action>();
		for (Action a : resultMap.keySet())
		{
			// Compare the values for the actions and take care of the floating
			// point / rounding error
			if (Math.abs(resultMap.get(a) - maxV) > Constants.FLOATING_EPSILON)
			{
				continue;
			}

			resultList.add(a);
		}

		for (Action a : resultList)
		{
			a.setProbability(1d / resultList.size());
		}

		return resultList;
	}

	private static double reward(final Point predator, final Point prey)
	{
		if (predator.equals(prey))
			return Constants.REWARD;

		return 0;
	}
}
