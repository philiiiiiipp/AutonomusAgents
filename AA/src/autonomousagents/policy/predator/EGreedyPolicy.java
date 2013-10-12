package autonomousagents.policy.predator;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.util.Constants;
import autonomousagents.util.Random;
import autonomousagents.world.State;

public class EGreedyPolicy extends GreedyPolicy
{

	/**
	 * Returns the next action considering e-greedy
	 */
	@Override
	public Action nextProbabilisticActionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);
		// System.out.println(this.currentPolicy.get(s));
		double probability = Random.RAND.nextDouble();

		if (Constants.EPSILON > probability)
		{
			// find a random action
			return actionList.get(Random.RAND.nextInt(actionList.size()));
		} else
		{
			return super.nextProbabilisticActionForState(s);
		}
	}

	@Override
	public String toString()
	{
		return "EGreedy";
	}
}
