package autonomousagents.policy.predator;

import autonomousagents.actions.Action;
import autonomousagents.actions.EastAction;
import autonomousagents.actions.NorthAction;
import autonomousagents.actions.SouthAction;
import autonomousagents.actions.StayAction;
import autonomousagents.actions.WestAction;
import autonomousagents.policy.Policy;
import autonomousagents.util.Random;
import autonomousagents.world.State;

public class RandomPolicy extends Policy
{
	@Override
	public Action nextProbabilisticActionForState(final State s)
	{
		// List<Action> actionList = this.currentPolicy.get(s);

		int probability = Random.RAND.nextInt(5);
		switch (probability)
		{
		case 0:
			return new NorthAction(0);
		case 1:
			return new EastAction(0);
		case 2:
			return new SouthAction(0);
		case 3:
			return new WestAction(0);
		case 4:
			return new StayAction(0);

		}
		return new NorthAction(0);
	}
}
