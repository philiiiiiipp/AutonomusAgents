package autonomousagents;

import autonomousagents.test.TestCompareSoftMaxEGreedy;
import autonomousagents.test.TestQLearning;

public class Main
{

	public static void main(final String[] args)
	{
		// TestOnPolicyMonteCarlo.test();
		TestCompareSoftMaxEGreedy.test();
		// TestQLearning.test();
		// TestSarsa.test();
		// TestVariousQLearning.test();
		// TestVariousQLearning.test();
		// TestSarsa.test();

		TestQLearning.test();
	}
}
