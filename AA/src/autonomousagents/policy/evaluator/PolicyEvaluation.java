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
		float[][][][] stateSpace = new float[11][11][11][11];
		float delta = 0;
		int i = 0;
		do
		{
			delta = 0;
			for (State s : predatorPolicy.getPolicy().keySet())
			{
				int predatorX = s.predatorPoint().getX();
				int predatorY = s.predatorPoint().getY();
				int preyX = s.preyPoint().getX();
				int preyY = s.preyPoint().getY();
				System.out.printf("%d, %d, %d, %d\n", predatorX, predatorY,
						preyX, preyY);

				float v = stateSpace[predatorX][predatorY][preyX][preyY];

				stateSpace[predatorX][predatorY][preyX][preyY] = maximisation(
						s, stateSpace, predatorPolicy, preyPolicy);

				delta = Math.max(delta, Math.abs(v
						- stateSpace[predatorX][predatorY][preyX][preyY]));
			}
			i = i + 1;
		} while (delta > THETA);

		System.out.println(i);
		return stateSpace;
	}

	private static float maximisation(final State s,
			final float[][][][] stateSpace, final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		if (s.predatorPoint().equals(s.preyPoint()))
		{
			// Undefined
			return 0;
		}
		List<Action> actionList = predatorPolicy.actionsForState(s);
		float VPi = 0;
		Point newPredPosition = null;
		for (Action predatorAction : actionList)
		{
			newPredPosition = predatorAction.apply(s.predatorPoint());

			List<Action> possibleAction = preyPolicy.actionsForState(new State(
					newPredPosition, s.preyPoint()));
			float Value = 0;
			Point newPreyPoint = null;
			for (Action preyAction : possibleAction)
			{
				newPreyPoint = preyAction.apply(s.preyPoint());
				if (newPredPosition.equals(s.preyPoint()))
				{
					Value += preyAction.getProbability()
							* (REWARD + (GAMMA * stateSpace[newPredPosition
									.getX()][newPredPosition.getY()][newPreyPoint
									.getX()][newPreyPoint.getY()]));
				} else
				{
					Value += preyAction.getProbability()
							* (GAMMA * stateSpace[newPredPosition.getX()][newPredPosition
									.getY()][newPreyPoint.getX()][newPreyPoint
									.getY()]);
				}

			}
			VPi += predatorAction.getProbability() * Value;
		}

		return VPi;
	}
}
