package autonomousagents;

import java.util.ArrayList;

public class Main
{

	public static void main(final String[] args)
	{
		State firstState = new State();
		Predator p = new Predator(new Point(0, 0), firstState);
		Prey pr = new Prey(new Point(5, 5), firstState);

		firstState.addAgent(p);
		firstState.addAgent(pr);

		float totNumber = 0;
		ArrayList<Integer> results = new ArrayList<Integer>();

		int iterations = 10000;
		int highest = 0;

		for (int i = 0; i < iterations; i++)
		{

			int thisTime = stepper(firstState);

			if (thisTime > highest)
			{
				highest = thisTime;
			}
			results.add(thisTime);
			totNumber += thisTime;

			firstState = new State();
			p = new Predator(new Point(0, 0), firstState);
			pr = new Prey(new Point(5, 5), firstState);

			firstState.addAgent(p);
			firstState.addAgent(pr);
		}

		double average = totNumber / iterations;
		System.out.println("Average: ");
		System.out.println(average);

		double distanceSum = 0;
		for (int r : results)
		{
			distanceSum += (r - average) * (r - average);
		}

		System.out.println("Deviation: ");
		System.out.println(Math.sqrt(distanceSum / results.size()));

		System.out.println("Highest: " + highest);

		// firstState.pprint();
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
