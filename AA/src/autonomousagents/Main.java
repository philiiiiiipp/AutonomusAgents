package autonomousagents;

import java.util.ArrayList;

public class Main
{

	public static void main(final String[] args)
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

		// firstState.pprint();
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

		System.out.print("Median: ");
		System.out.println(median);

		System.out.print("Average: ");
		System.out.println(average);

		System.out.print("Deviation: ");
		System.out.println(Math.sqrt(distanceSum / scores.size()));

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
