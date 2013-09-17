package autonomousagents.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import autonomousagents.State;
import autonomousagents.actions.Action;

public class Policy
{
	Random rand = new Random(2);

	protected Map<State, List<Action>> currentPolicy = new HashMap<State, List<Action>>();

	public Action actionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);
		float probability = this.rand.nextFloat();

		for (int i = 0; i < actionList.size(); ++i)
		{
			probability -= actionList.get(i).getProbability();

			if (probability <= 0)
				return actionList.get(i);
		}

		return actionList.get(actionList.size() - 1);
	}
}
