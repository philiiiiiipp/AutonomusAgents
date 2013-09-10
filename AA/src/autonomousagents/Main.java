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
		for (int i = 0; i < 10000; i++)
		{
			totNumber += stepper(firstState);

			firstState = new State();
			p = new Predator(new Point(0, 0), firstState);
			pr = new Prey(new Point(5, 5), firstState);

			firstState.addAgent(p);
			firstState.addAgent(pr);
		}

		System.out.println(totNumber / 10000);
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
