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
import autonomousagents.util.GameField;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class GreedyPolicy extends Policy
{

	public GreedyPolicy()
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

	public GreedyPolicy(final int numberOfPredators)
	{
		List<Point> possiblePoints = new ArrayList<Point>();
		for (int i = 0; i < 11; i++)
		{
			for (int j = 0; j < 11; j++)
			{
				possiblePoints.add(new Point(i, j));
			}
		}

		// / calculate all subsets of size numberOfPredators
		List<List<Point>> subsets = getSubSets(possiblePoints, numberOfPredators);

		for (List<Point> pList : subsets)
		{
			for (int x = 0; x < GameField.XMAX; ++x)
			{
				for (int y = 0; y < GameField.YMAX; y++)
				{

					State s = new State(pList, new Point(x, y));
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

	private static List<List<Point>> getSubSets(final List<Point> possiblePoints, final int numberOfPredators)
	{
		List<List<Point>> subsets = new ArrayList<List<Point>>();
		for (Point p : possiblePoints)
		{
			List<Point> subsubSet = new ArrayList<Point>();
			subsubSet.add(p);
			subsets.add(subsubSet);
		}

		for (int i = 0; i < numberOfPredators - 1; i++)
		{
			List<List<Point>> newsubsets = new ArrayList<List<Point>>();
			for (int subI = 0; subI < subsets.size(); subI++)
			{
				List<Point> subsubSet = subsets.get(subI);

				for (int point = 0; point < possiblePoints.size(); point++)
				{
					List<Point> newSubSet = new ArrayList<Point>(subsubSet);
					newSubSet.add(possiblePoints.get(point));

					newsubsets.add(newSubSet);
				}
			}
			subsets = newsubsets;
		}

		return subsets;
	}

	/**
	 * Returns the next action considering greedy
	 */
	@Override
	public Action nextProbabilisticActionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);
		// System.out.println(actionList);

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

		return actionList.get(bestAction);
	}

	@Override
	public String toString()
	{
		return "Greedy";
	}
}
