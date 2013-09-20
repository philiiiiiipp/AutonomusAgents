package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PolicyEvaluation
{
	private static final float REWARD = 10.0f;
	private static final float GAMMA = 0.8f;
	private static final float THETA = 0.00001f;

	public static float[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		float[][][][] valueMap = new float[11][11][11][11];
		float delta = 0;
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

				float v = valueMap[predatorX][predatorY][preyX][preyY];

				valueMap[predatorX][predatorY][preyX][preyY] = maximisation(s,
						valueMap, predatorPolicy, preyPolicy);

				delta = Math
						.max(delta, Math.abs(v
								- valueMap[predatorX][predatorY][preyX][preyY]));
			}
			i = i + 1;
		} while (delta > THETA);

		System.out.println(i);
		return valueMap;
	}

	private static float maximisation(final State s,
			final float[][][][] valueMap, final Policy predatorPolicy,
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
						* (reward(newPredPosition, newPreyPoint) + (GAMMA * valueMap[newPredPosition
								.getX()][newPredPosition.getY()][newPreyPoint
								.getX()][newPreyPoint.getY()]));
			}
		}

		return vPi;
	}

	private static float reward(final Point predator, final Point prey)
	{
		if (predator.equals(prey))
		{
			return REWARD;

		}

		return 0;
	}
}
