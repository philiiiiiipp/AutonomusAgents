package autonomousagents.policy.evaluator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.util.ValueMap;
import autonomousagents.world.Point;
import autonomousagents.world.State;

/* Class that implements the Value Iteration algorithm */
public class ValueIteration
{
	public static ValueMap evaluate(final Policy predatorPolicy,
			final Policy preyPolicy)
	{
		ValueMap stateSpace = new ValueMap();
		double delta = 0;
		int counter = 0;
		do
		{
			counter++;
			delta = 0;
			for (State s : predatorPolicy.getPolicy().keySet())
			{
				if (s.isTerminal())
					continue;

				double v = stateSpace.getValueForState(s);

				stateSpace
						.setValueForState(
								s,
								maximisation(s, stateSpace, predatorPolicy,
										preyPolicy));

				delta = Math.max(delta,
						Math.abs(v - stateSpace.getValueForState(s)));
			}

		} while (delta > Constants.THETA);

		System.out.println("ValueIteration: " + counter);
		return stateSpace;
	}

	private static double maximisation(final State s,
			final ValueMap stateSpace, final Policy predatorPolicy,
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
						* (Constants.GAMMA * stateSpace.getValueForState(
								newPredPosition, newPreyPoint));
			}

			if (vStar < vSPrimeTotal)
				vStar = vSPrimeTotal;
		}

		return vStar;
	}
}
