package autonomousagents;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.agent.Agent;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.test.TestPolicyEvaluation;
import autonomousagents.test.TestPolicyIteration;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public class Main
{

	public static void main(final String[] args)
	{
		// TestValueIteration.test();
		// System.out.println();
		System.out.println("policy evaluation");
		TestPolicyEvaluation.test();

		oldMain();
		System.out.println();
		System.out.println("Policy Iteration");
		PredatorRandomPolicy pred = TestPolicyIteration.test();
		// oldMain();

		int iterations = 1000;

		ArrayList<Integer> results = new ArrayList<Integer>();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();
		for (int i = 0; i < iterations; i++)
		{

			Environment e = new Environment();
			Predator p = new Predator(new Point(0, 0), e, pred);
			Prey pr = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(p);
			e.addAgent(pr);

			results.add(stepper(e));
		}
		pprintStatistics(results);
	}

	public static void oldMain()
	{
		Predator p;
		Prey pr;

		ArrayList<Integer> results = new ArrayList<Integer>();

		int iterations = 100;

		long time1 = 0;

		long creationTime = 0;
		long stepTime = 0;

		PredatorRandomPolicy prPoly = new PredatorRandomPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();
		for (int i = 0; i < iterations; i++)
		{
			time1 = System.currentTimeMillis();
			Environment e = new Environment();
			p = new Predator(new Point(0, 0), e, prPoly);
			pr = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(p);
			e.addAgent(pr);

			creationTime += System.currentTimeMillis() - time1;

			time1 = System.currentTimeMillis();
			results.add(stepper(e));
			stepTime += System.currentTimeMillis() - time1;

		}
		System.out.println(creationTime / 1000.0f);
		System.out.println(stepTime / 1000.0f);
		pprintStatistics(results);

		// new VI();
	}

	public static void pprintStatistics(final ArrayList<Integer> scores)
	{
		double total = 0;
		double highest = Double.MIN_VALUE;
		for (int i : scores)
		{
			total += i;
			if (highest < i)
			{
				highest = i;
			}
		}
		double average = total / scores.size();

		double distanceSum = 0;
		for (int r : scores)
		{
			distanceSum += (r - average) * (r - average);
		}

		double median = scores.get(scores.size() / 2);
		if (scores.size() % 2 == 0)
		{
			median += scores.get(scores.size() / 2 + 1);
			median /= 2;
		}

		System.out.println("Median: " + median);
		System.out.println("Average: " + average);
		System.out.println("Deviation: "
				+ Math.sqrt(distanceSum / scores.size()));
		System.out.println("Highest: " + highest);

	}

	public static int stepper(final Environment environment)
	{
		int counterOfSteps = 0;
		List<Agent> agents = environment.getAgents();
		while (true)
		{
			counterOfSteps++;
			for (Agent agent : agents)
			{
				if (agent.step())
				{
					return counterOfSteps;
				}
			}
			// theState.pprint();
			// System.out.println();
		}
	}

}
