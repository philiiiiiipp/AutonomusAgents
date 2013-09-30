package autonomousagents.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import autonomousagents.actions.Action;
import autonomousagents.world.State;

/**
 * Class that encodes the policy for an agent
 * 
 */
public class Policy
{
	protected static Random RAND = new Random();

	protected Map<State, List<Action>> currentPolicy = new HashMap<State, List<Action>>();

	/**
	 * returns a random action, given their possibilities in state s
	 * 
	 * @param s
	 * @return
	 */
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

	public Action actionWithHighestValue(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);

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

	/**
	 * returns the list of actions that can be taken by the agent in a given
	 * State s
	 * 
	 * @param s
	 * @return
	 */
	public List<Action> actionsForState(final State s)
	{
		return this.currentPolicy.get(s);
	}

	/**
	 * returns the current policy as a mapping of states to actions
	 * 
	 * @return
	 */
	public Map<State, List<Action>> getPolicy()
	{
		return this.currentPolicy;
	}
}
