package autonomousagents.test.a3;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.agent.Agent;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.predator.RandomPolicy;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public class TestNewSimulator
{
	private static final int ITERATIONS = 100;

	/**
	 * Run the simulator ITERATIONS times, and print the Median, Average,
	 * Deviation and the highest number of moves it took the predator to catch
	 * the prey.
	 */
	public static void test()
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		RandomPolicy pred = new RandomPolicy();
		for (int i = 0; i < ITERATIONS; i++)
		{
			Environment e = new Environment();
			Predator firstPred = new Predator(new Point(1, 0), e, pred);
			Predator secondPred = new Predator(new Point(4, 1), e, pred);
			Predator thirdPred = new Predator(new Point(3, 2), e, pred);

			Prey pr = new Prey(new Point(5, 5), e, pred);

			e.addPredator(firstPred);
			e.addPredator(secondPred);
			e.addPredator(thirdPred);

			e.addPrey(pr);
			results.add(stepper(e));
		}

		pprintStatistics(results);
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
		System.out.println("Deviation: " + Math.sqrt(distanceSum / scores.size()));
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
		}
	}
}
