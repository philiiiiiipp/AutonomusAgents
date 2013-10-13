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

		for (int i = 0; i < 11; i++)
		{
			for (int j = 0; j < 11; j++)
			{
				Point prey = new Point(i, j);
				for (List<Point> set : subsets)
				{
					// Test if prey is not on one of the predators
					boolean possible = true;
					for (Point p : set)
					{
						if (p.equals(prey))
						{
							possible = false;
							break;
						}
					}
					if (!possible)
						continue;

					State s = new State(set, prey);
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
		assert (numberOfPredators < 4);

		List<List<Point>> subsets = new ArrayList<List<Point>>();
		// generate subsets
		for (int i = 0; i < possiblePoints.size(); i++)
		{
			List<Point> thisSubset = new ArrayList<Point>();
			thisSubset.add(possiblePoints.get(i));
			if (numberOfPredators == 1)
				continue;
			for (int j = 0; j < possiblePoints.size(); j++)
			{
				thisSubset.add(possiblePoints.get(j));
				if (numberOfPredators == 2)
					continue;
				for (int k = 0; k < possiblePoints.size(); k++)
				{
					thisSubset.add(possiblePoints.get(k));
				}
			}
			subsets.add(thisSubset);
		}
		// Filter subsets for doubles
		for (int i = 0; i < subsets.size() - 1; i++)
		{
			for (int j = i; j < subsets.size(); j++)
			{
				if (subsets.get(i) == subsets.get(j))
				{
					subsets.remove(j);
				}
			}
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
