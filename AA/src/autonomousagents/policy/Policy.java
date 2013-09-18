package autonomousagents.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import autonomousagents.State;
import autonomousagents.actions.Action;

public class Policy
{
	protected static Random RAND = new Random(2);

	protected Map<State, List<Action>> currentPolicy = new HashMap<State, List<Action>>();

	public Action nextProbabalisticActionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);
		float probability = RAND.nextFloat();

		for (int i = 0; i < actionList.size(); ++i)
		{
			probability -= actionList.get(i).getProbability();

			if (probability <= 0)
				return actionList.get(i);
		}

		return actionList.get(actionList.size() - 1);
	}

	public List<Action> actionsForState(final State s)
	{
		return this.currentPolicy.get(s);
	}

	public Map<State, List<Action>> getPolicy()
	{
		return this.currentPolicy;
	}
}
