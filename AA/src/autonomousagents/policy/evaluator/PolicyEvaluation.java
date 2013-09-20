package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PolicyEvaluation
{

	public static double[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double[][][][] valueMap = new double[11][11][11][11];
		double delta = 0;
		int i = 0;
		do
		{
			delta = 0;
			for (State s : predatorPolicy.getPolicy().keySet())
			{
				if (s.isTerminal())
					continue;

				int predatorX = s.predatorPoint().getX();
				int predatorY = s.predatorPoint().getY();
				int preyX = s.preyPoint().getX();
				int preyY = s.preyPoint().getY();

				double v = valueMap[predatorX][predatorY][preyX][preyY];

				valueMap[predatorX][predatorY][preyX][preyY] = maximisation(s,
						valueMap, predatorPolicy, preyPolicy);

				delta = Math
						.max(delta, Math.abs(v
								- valueMap[predatorX][predatorY][preyX][preyY]));
			}
			i = i + 1;
		} while (delta > Constants.THETA);

		System.out.println(i);
		return valueMap;
	}

	private static double maximisation(final State s,
			final double[][][][] valueMap, final Policy predatorPolicy,
			final Policy preyPolicy)
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
						* (reward(newPredPosition, newPreyPoint) + (Constants.GAMMA * valueMap[newPredPosition
								.getX()][newPredPosition.getY()][newPreyPoint
								.getX()][newPreyPoint.getY()]));
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
