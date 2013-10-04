package autonomousagents.test;

import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestOffPolicyMC
{
	private static final double alpha = 0.1d;

	public static void test()
	{
		Policy predatorPolicy = new EGreedyPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		Environment e = new Environment();

		Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
		Prey prey = new Prey(new Point(5, 5), e, preyPoly);

		e.addAgent(predator);
		e.addAgent(prey);

		State s = e.getState();
	}
}
