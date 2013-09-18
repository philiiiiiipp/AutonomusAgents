package autonomousagents.policy.prey;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.Environment;
import autonomousagents.Point;
import autonomousagents.State;
import autonomousagents.actions.Action;
import autonomousagents.actions.EastAction;
import autonomousagents.actions.NorthAction;
import autonomousagents.actions.SouthAction;
import autonomousagents.actions.StayAction;
import autonomousagents.actions.WestAction;
import autonomousagents.policy.Policy;

public class PreyRandomPolicy extends Policy
{
	public PreyRandomPolicy()
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

						if (!Environment.north(preyPoint).equals(predPoint))
							actions.add(new NorthAction(0));

						if (!Environment.east(preyPoint).equals(predPoint))
							actions.add(new EastAction(0));

						if (!Environment.south(preyPoint).equals(predPoint))
							actions.add(new SouthAction(0));

						if (!Environment.west(preyPoint).equals(predPoint))
							actions.add(new WestAction(0));

						for (Action a : actions)
						{
							a.setProbability(1.0d / actions.size() * 0.2d);
						}

						actions.add(new StayAction(0.8d));

						this.currentPolicy.put(s, actions);
					}
				}
			}
		}
	}
}
