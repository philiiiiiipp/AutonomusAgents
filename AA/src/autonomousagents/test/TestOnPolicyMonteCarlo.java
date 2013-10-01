package autonomousagents.test;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.Pair;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOnPolicyMonteCarlo
{
	public void Evaluate()
	{
		List<Double> returnList = new ArrayList<Double>();

		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPolicy = new PreyRandomPolicy();

		while (true)
		{
			List<Pair<State, Action>> stateActionList = new ArrayList<Pair<State, Action>>();
			// generate an episode using the current policy
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();

			Action a = predatorPolicy.nextProbabilisticActionForState(s);
			a.apply(predator);

			// Reward from this action
			double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

			if (!e.getState().isTerminal())
			{
				preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
			}
			stateActionList.add(new Pair<State, Action>(s, a));

			// append R to the list of returns
			// returnList.add(reward);
			// the action value of action a will be te average over all the
			// returns
			// a.setActionValue(actionValue);
		}
	}
}
