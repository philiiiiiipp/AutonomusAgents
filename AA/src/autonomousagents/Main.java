package autonomousagents;

import java.util.ArrayList;

public class Main
{

	public static void main(final String[] args)
	{
		// oldMain();

		// Looks to me like no matter what, once we cleared out all 0es its
		// converged
		float epsilon = 500;
		ValueIteration vi = new ValueIteration();

		while (vi.sweep() > epsilon)
		{
			// go on
		}

		vi.printStates(new Point(2, 5));
	}

	public static void oldMain()
	{
		State firstState;
		Predator p;
		Prey pr;

		ArrayList<Integer> results = new ArrayList<Integer>();

		int iterations = 10000;

		for (int i = 0; i < iterations; i++)
		{

			firstState = new State();
			p = new Predator(new Point(0, 0), firstState);
			pr = new Prey(new Point(5, 5), firstState);

			firstState.addAgent(p);
			firstState.addAgent(pr);

			results.add(stepper(firstState));

		}
		pprintStatistics(results);

		new VI();
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

	public static int stepper(final State theState)
	{
		int counterOfSteps = 0;
		ArrayList<Agent> agents = theState.getAgents();
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
