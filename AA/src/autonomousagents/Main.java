package autonomousagents;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;

public class Main
{

	public static void main(final String[] args)
	{
		oldMain();

		// Looks to me like no matter what, once we cleared out all 0es its
		// converged

		// State s = new State();
		// s.addAgent(new Predator(new Point(8, 3), s, new RandomPolicy()));
		// s.addAgent(new Prey(new Point(1, 5), s));

		// float epsilon = 500;
		// ValueIteration vi = new ValueIteration();
		//
		// while (vi.sweep() > epsilon)
		// {
		// // go on
		// }
		//
		// vi.printStates(new Point(5, 5));
	}

	public static void oldMain()
	{
		State firstState;
		Predator p;
		Prey pr;

		ArrayList<Integer> results = new ArrayList<Integer>();

		int iterations = 1000;

		for (int i = 0; i < iterations; i++)
		{
			Environment e = new Environment();
			p = new Predator(new Point(0, 0), e, new PredatorRandomPolicy());
			pr = new Prey(new Point(5, 5), e, new PreyRandomPolicy());

			e.addAgent(p);
			e.addAgent(pr);

			results.add(stepper(e));

		}
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
