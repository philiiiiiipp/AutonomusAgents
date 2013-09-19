package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.Environment;
import autonomousagents.Point;
import autonomousagents.State;
import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Direction;

public class PolicyEvaluation
{
	private static final float REWARD = 0.0f;
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

		float VPi = 0;
		Point newPredPosition = null;
		for (int i = 0; i < 5; ++i)
		{
			switch (i)
			{
			case Direction.NORTH:
				newPredPosition = Environment.north(s.predatorPoint());
				break;
			case Direction.EAST:
				newPredPosition = Environment.east(s.predatorPoint());
				break;
			case Direction.SOUTH:
				newPredPosition = Environment.south(s.predatorPoint());
				break;
			case Direction.WEST:
				newPredPosition = Environment.west(s.predatorPoint());
				break;
			case Direction.STAY:
				newPredPosition = s.predatorPoint();
				break;
			default:
				break;
			}

			if (newPredPosition.equals(s.preyPoint()))
			{
				// catched, max reward
				return REWARD;
			}

			List<Action> possibleAction = preyPolicy.actionsForState(new State(
					newPredPosition, s.preyPoint()));
			float Value = 0;
			Point newPreyPoint = null;
			for (Action a : possibleAction)
			{
				newPreyPoint = a.apply(s.preyPoint());
				Value += a.getProbability()
						* (-1 + GAMMA
								* stateSpace[newPredPosition.getX()][newPredPosition
										.getY()][newPreyPoint.getX()][newPreyPoint
										.getY()]);
			}
			VPi += (1.0f / 5.0f) * Value;
		}
		return VPi;
	}
}
