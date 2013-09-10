package autonomousagents;

import java.util.ArrayList;

public class Main
{

	public static void main(final String[] args)
	{
		State firstState = new State();

		firstState.addAgent(new Predator(new Point(0, 0), firstState));
		firstState.addAgent(new Prey(new Point(5, 5), firstState));

		System.out.println(stepper(firstState));
		firstState.pprint();
	}

	public static int stepper(final State theState)
	{
		int counterOfSteps = 0;
		ArrayList<Agent> agents = theState.getAgents();

		for (Agent agent : agents)
		{
			counterOfSteps++;

			if (agent.step())
			{
				return counterOfSteps;
			}
		}
		return -1;
	}

}
