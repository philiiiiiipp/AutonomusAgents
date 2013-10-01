package autonomousagents.policy.predator;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.actions.EastAction;
import autonomousagents.actions.NorthAction;
import autonomousagents.actions.SouthAction;
import autonomousagents.actions.StayAction;
import autonomousagents.actions.WestAction;
import autonomousagents.policy.Policy;
import autonomousagents.util.Constants;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class EGreedyPolicy extends Policy
{
	public EGreedyPolicy()
	{
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				for (int xPrey = 0; xPrey < 11; xPrey++)
				{
					for (int yPrey = 0; yPrey < 11; yPrey++)
					{
						Point predPoint = new Point(xPred, yPred);
						Point preyPoint = new Point(xPrey, yPrey);

						State s = new State(predPoint, preyPoint);

						List<Action> actions = new ArrayList<Action>();
						// the Predator can move to each of the 5 directions
						// with equal probability
						actions.add(new NorthAction(1.0d / 5.0d));
						actions.add(new EastAction(1.0d / 5.0d));
						actions.add(new SouthAction(1.0d / 5.0d));
						actions.add(new WestAction(1.0d / 5.0d));
						actions.add(new StayAction(1.0d / 5.0d));

						this.currentPolicy.put(s, actions);
					}
				}
			}
		}
	}

	/**
	 * Returns the next action considering e-greedy
	 */
	@Override
	public Action nextProbabilisticActionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);
		double probability = RAND.nextDouble();
		int bestAction = 0;
		double highestActionValue = -1;

		for (int i = 0; i < actionList.size(); ++i)
		{
			Action a = actionList.get(i);
			if (a.getActionValue() > highestActionValue)
			{
				bestAction = i;
				highestActionValue = a.getActionValue();
			}
		}

		if (Constants.EPSILON > probability)
		{
			// find a random action
			return actionList.get(RAND.nextInt(actionList.size()));
		} else
		{
			return actionList.get(bestAction);
		}
	}

	@Override
	public String toString()
	{
		return "EGreedy";
	}
}
