package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class ValueIteration
{

	public static double[][][][] evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		double[][][][] stateSpace = new double[11][11][11][11];
		double delta = 0;

		int counter = 0;
		do
		{
			counter += 1;
			delta = 0;
			for (State s : predatorPolicy.getPolicy().keySet())
			{
				if (s.isTerminal())
					continue;

				int predatorX = s.predatorPoint().getX();
				int predatorY = s.predatorPoint().getY();
				int preyX = s.preyPoint().getX();
				int preyY = s.preyPoint().getY();

				double v = stateSpace[predatorX][predatorY][preyX][preyY];

				stateSpace[predatorX][predatorY][preyX][preyY] = maximisation(
						s, stateSpace, predatorPolicy, preyPolicy);

				delta = Math.max(delta, Math.abs(v
						- stateSpace[predatorX][predatorY][preyX][preyY]));
			}

		} while (delta > Constants.THETA);

		System.out.println("Counter is at: " + counter);
		return stateSpace;
	}

	private static double maximisation(final State s,
			final double[][][][] stateSpace, final Policy predatorPolicy,
			final Policy preyPolicy)
	{

		double vStar = 0;
		Point newPredPosition = null;
		for (Action predatorAction : predatorPolicy.actionsForState(s))
		{
			newPredPosition = predatorAction.apply(s.predatorPoint());

			if (newPredPosition.equals(s.preyPoint()))
			{
				// catched, max reward
				return Constants.REWARD;
			}

			List<Action> possibleAction = preyPolicy.actionsForState(new State(
					newPredPosition, s.preyPoint()));

			double vSPrimeTotal = 0;
			Point newPreyPoint = null;
			for (Action a : possibleAction)
			{
				newPreyPoint = a.apply(s.preyPoint());
				vSPrimeTotal += a.getProbability()
						* (Constants.GAMMA * stateSpace[newPredPosition.getX()][newPredPosition
								.getY()][newPreyPoint.getX()][newPreyPoint
								.getY()]);
			}

			if (vStar < vSPrimeTotal)
				vStar = vSPrimeTotal;
		}

		return vStar;
	}
}
