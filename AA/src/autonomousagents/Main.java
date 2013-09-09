package autonomousagents;

public class Main
{

	public static void main(final String[] args)
	{
		State firstState = new State();

		firstState.addAgent(new Predator(new Point(0, 0), firstState));
		firstState.addAgent(new Prey(new Point(5, 5), firstState));

		firstState.pprint();
	}

}
