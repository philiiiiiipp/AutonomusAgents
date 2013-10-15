package autonomousagents.policy.prey;

import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.actions.StayAction;
import autonomousagents.policy.predator.GreedyPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.Random;
import autonomousagents.world.State;

public class EGreedyPolicyWithTrip extends GreedyPolicy
{
	public EGreedyPolicyWithTrip(final int size)
	{
		super(size);
	}

	/**
	 * Returns the next action considering e-greedy
	 */
	@Override
	public Action nextProbabilisticActionForState(final State s)
	{
		List<Action> actionList = this.currentPolicy.get(s);

		double tripProbability = Random.RAND.nextDouble();

		if (tripProbability < 0.2)
		{
			return new StayAction(1.0d / 5.0d);
		}

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
		return "EGreedyWithTrip";
	}
}
