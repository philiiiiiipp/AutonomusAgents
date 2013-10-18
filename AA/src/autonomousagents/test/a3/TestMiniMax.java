package autonomousagents.test.a3;

import java.util.List;

import scpsolver.problems.LPSolution;
import scpsolver.problems.LPWizard;
import autonomousagents.agent.Agent;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.SoftmaxPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.QTable;
import autonomousagents.util.ValueTable;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public class TestMiniMax
{

	private static final int EPISODE_COUNT = 250000;

	/**
	 * Plot the difference between SARSA, Q-Learning and On-/Off-Policy Monte
	 * Carlo
	 */
	public static void test()
	{
		runMiniMax(new SoftmaxPolicy(), new PreyRandomPolicy(), 1000);
	}

	private static List<Integer> runMiniMax(final Policy predatorPolicy, final Policy preyPolicy, final int episodeCount)
	{
		QTable predatorQ = new QTable();
		ValueTable predatorV = new ValueTable();

		QTable preyQ = new QTable();
		ValueTable preyV = new ValueTable();

		for (int step = 0; step < 1000; step++)
		{
			// Initialise s
			Environment e = new Environment();
			e.addPrey(new Prey(new Point(5, 5), e, preyPolicy));
			e.addPredator(new Predator(new Point(0, 0), e, predatorPolicy));

			int stepCount = 0;
			do
			{
				stepCount++;
				for (Agent a : e.getAgents())
				{
					a.step();
					if (e.getState().isTerminal())
						break;
				}

			} while (e.getState().isTerminal());

			System.out.println(stepCount);
		}

		LPWizard lpw = new LPWizard();
		lpw.plus("x1", 5.0).plus("x2", 10.0);
		lpw.setMinProblem(true);
		lpw.addConstraint("c1", 8, "<=").plus("x1").plus("x2", 2);
		// lpw.addConstraint("c2", 4, "<=").plus("x2", 4.0);
		lpw.addConstraint("c3", 1, "=").plus("x1").plus("x2");

		LPSolution solution = lpw.solve();
		System.out.println(solution);

		return null;
	}
}
