package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PolicyImprovement
{
	private static final float REWARD = 10.0f;
	private static final float GAMMA = 0.1f;
	private static final float THETA = 0.00001f;

	public static void improve(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double vStar = 0;
		float[][][][] stateSpace = new float[11][11][11][11];
		boolean policyStable = true;

		for (State s : predatorPolicy.getPolicy().keySet())
		{
			List<Action> actionList = predatorPolicy.actionsForState(s);
			Point newPredPosition = null;
			for (Action predatorAction : actionList)
			{
				newPredPosition = predatorAction.apply(s.predatorPoint());
				if (newPredPosition.equals(s.preyPoint()))
				{
					// catched, max reward
					// return REWARD;
				}

				List<Action> possibleAction = preyPolicy
						.actionsForState(new State(newPredPosition, s
								.preyPoint()));
				double vSPrimeTotal = 0;
				Point newPreyPoint = null;
				for (Action a : possibleAction)
				{
					newPreyPoint = a.apply(s.preyPoint());
					vSPrimeTotal += a.getProbability()
							* (GAMMA * stateSpace[newPredPosition.getX()][newPredPosition
									.getY()][newPreyPoint.getX()][newPreyPoint
									.getY()]);
				}

				if (vStar < vSPrimeTotal)
					vStar = vSPrimeTotal;
			}
		}
	}

	private static double maximisation(final State s,
			final double[][][][] stateSpace, final Policy preyPolicy)
	{
		double vStar = 0;
		return vStar;
	}
}
