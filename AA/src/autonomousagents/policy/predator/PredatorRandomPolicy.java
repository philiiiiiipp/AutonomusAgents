package autonomousagents.policy.predator;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.Point;
import autonomousagents.State;
import autonomousagents.actions.Action;
import autonomousagents.actions.EastAction;
import autonomousagents.actions.NorthAction;
import autonomousagents.actions.SouthAction;
import autonomousagents.actions.StayAction;
import autonomousagents.actions.WestAction;
import autonomousagents.policy.Policy;

public class PredatorRandomPolicy extends Policy
{
	public PredatorRandomPolicy()
	{
		for (int xPred = 0; xPred < 11; xPred++)
		{
			for (int yPred = 0; yPred < 11; yPred++)
			{
				for (int xPrey = 0; xPrey < 11; xPrey++)
				{
					for (int yPrey = 0; yPrey < 11; yPrey++)
					{
						List<Point> points = new ArrayList<Point>();
						Point predPoint = new Point(xPred, yPred);
						points.add(predPoint);

						Point preyPoint = new Point(xPrey, yPrey);
						points.add(preyPoint);

						State s = new State(points);

						List<Action> actions = new ArrayList<Action>();
						actions.add(new NorthAction(1.0f / 5.0f));
						actions.add(new EastAction(1.0f / 5.0f));
						actions.add(new SouthAction(1.0f / 5.0f));
						actions.add(new WestAction(1.0f / 5.0f));
						actions.add(new StayAction(1.0f / 5.0f));

						this.currentPolicy.put(s, actions);
					}
				}
			}
		}
	}
}
