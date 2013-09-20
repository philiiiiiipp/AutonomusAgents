package autonomousagents.policy.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PolicyImprovement
{
	private static final float REWARD = 10.0f;
	private static final float GAMMA = 0.8f;
	private static final float THETA = 0.00001f;

	/**
	 * Improving the predator policy
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 */
	public static void improve(final Policy predatorPolicy,
			final Policy preyPolicy, final float[][][][] valueMap)
	{
		boolean policyStable = true;

		for (State s : predatorPolicy.getPolicy().keySet())
		{
			if (s.isTerminal())
				continue;

			List<Action> actions = predatorPolicy.actionsForState(s);
		}

	}

	private static List<Action> maximisation(final State s,
			final double[][][][] valueMap, final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double maxV = 0;
		Map<Action, Double> resultMap = new HashMap<Action, Double>();

		Point newPredPosition = null;
		for (Action predatorAction : predatorPolicy.actionsForState(s))
		{
			double stateSum = 0;
			newPredPosition = predatorAction.apply(s.predatorPoint());

			List<Action> possibleAction = preyPolicy.actionsForState(new State(
					newPredPosition, s.preyPoint()));

			Point newPreyPoint = null;
			for (Action preyAction : possibleAction)
			{
				newPreyPoint = preyAction.apply(s.preyPoint());

				stateSum += preyAction.getProbability()
						* (reward(newPredPosition, newPreyPoint) + (GAMMA * valueMap[newPredPosition
								.getX()][newPredPosition.getY()][newPreyPoint
								.getX()][newPreyPoint.getY()]));
			}

			if (maxV < stateSum)
				maxV = stateSum;

			resultMap.put(predatorAction, stateSum);
		}

		List<Action> resultList = new ArrayList<Action>();
		for (Action a : resultMap.keySet())
		{
			if (resultMap.get(a) < maxV)
				continue;

			resultList.add(a);
		}

		return resultList;
	}

	private static float reward(final Point predator, final Point prey)
	{
		if (predator.equals(prey))
			return REWARD;

		return 0;
	}
}
