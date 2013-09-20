package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;
import autonomousagents.world.State;

/* Class that implements the Policy Evaluation algorithm */
public class PolicyEvaluation
{

	public static ValueMap evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		ValueMap valueMap = new ValueMap();
		double delta = 0;
		int i = 0;
		do
		{
			delta = 0;
			for (State s : predatorPolicy.getPolicy().keySet())
			{
				if (s.isTerminal())
					continue;

				double v = valueMap.getValueForState(s);

				valueMap.setValueForState(s,
						maximisation(s, valueMap, predatorPolicy, preyPolicy));

				delta = Math.max(delta,
						Math.abs(v - valueMap.getValueForState(s)));
			}
			i = i + 1;
		} while (delta > Constants.THETA);

		System.out.println(i);
		return valueMap;
	}

	private static float maximisation(final State s, final ValueMap valueMap,
			final Policy predatorPolicy, final Policy preyPolicy)
	{
		List<Action> actionList = predatorPolicy.actionsForState(s);
		float vPi = 0;
		Point newPredPosition = null;
		for (Action predatorAction : actionList)
		{
			newPredPosition = predatorAction.apply(s.predatorPoint());

			List<Action> possibleAction = preyPolicy.actionsForState(new State(
					newPredPosition, s.preyPoint()));

			Point newPreyPoint = null;
			for (Action preyAction : possibleAction)
			{
				newPreyPoint = preyAction.apply(s.preyPoint());
				vPi += predatorAction.getProbability()
						* preyAction.getProbability()
						* (reward(newPredPosition, newPreyPoint) + (Constants.GAMMA * valueMap
								.getValueForState(newPredPosition, newPreyPoint)));
			}
		}

		return vPi;
	}

	private static double reward(final Point predator, final Point prey)
	{
		if (predator.equals(prey))
			return Constants.REWARD;

		return 0;
	}
}
