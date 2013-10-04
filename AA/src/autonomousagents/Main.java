package autonomousagents;

import autonomousagents.test.TestOnPolicyMonteCarloPhilipp;
import autonomousagents.test.TestQLearning;

public class Main
{

	public static void main(final String[] args)
	{
		// TestOnPolicyMonteCarlo.test();

		TestQLearning.test();
		// TestSarsa.test();
		// TestVariousQLearning.test();
		// TestVariousQLearning.test();
		// TestSarsa.test();

		// TestValueIteration.test();
		// TestPolicyEvaluation.test();
		// TestPolicyIteration.test();
		// TestSimulator.test();
		TestOnPolicyMonteCarloPhilipp.test();
		// TestQLearning.test();
		// TestSarsa.test();
	}
}
