package autonomousagents;

import autonomousagents.test.TestQLearning;
<<<<<<< HEAD
=======
import autonomousagents.test.TestSarsa;
import autonomousagents.test.TestVariousQLearning;
>>>>>>> 5802596ea206390ae0bbda1a30c5972f1f6c1897

public class Main
{

	public static void main(final String[] args)
	{
<<<<<<< HEAD
		// TestOnPolicyMonteCarlo.test();

		TestQLearning.test();
		// TestSarsa.test();
		// TestVariousQLearning.test();
=======
		TestVariousQLearning.test();

		TestQLearning.test();
		TestSarsa.test();
>>>>>>> 5802596ea206390ae0bbda1a30c5972f1f6c1897
		// TestValueIteration.test();
		// TestPolicyEvaluation.test();
		// TestPolicyIteration.test();
		// TestSimulator.test();
	}
}
