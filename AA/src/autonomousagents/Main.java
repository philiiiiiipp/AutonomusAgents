package autonomousagents;

import autonomousagents.test.TestQLearning;
import autonomousagents.test.TestSarsa;
import autonomousagents.test.TestVariousQLearning;

public class Main
{

	public static void main(final String[] args)
	{
		TestQLearning.test();
		TestSarsa.test();
		TestVariousQLearning.test();

		// TestValueIteration.test();
		// TestPolicyEvaluation.test();
		// TestPolicyIteration.test();
		// TestSimulator.test();
	}
}
