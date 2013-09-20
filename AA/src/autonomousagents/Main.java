package autonomousagents;

import autonomousagents.test.TestPolicyEvaluation;
import autonomousagents.test.TestPolicyIteration;
import autonomousagents.test.TestSimulator;
import autonomousagents.test.TestValueIteration;

public class Main
{

	public static void main(final String[] args)
	{
		TestValueIteration.test();
		TestPolicyEvaluation.test();
		TestPolicyIteration.test();
		TestSimulator.test();
	}
}
