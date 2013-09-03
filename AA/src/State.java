import java.util.ArrayList;

public class State
{

	private static ArrayList<Agent> agents;

	public static boolean isFree(final int x, final int y)
	{
		for (Agent a : State.agents)
		{
			if (a.isPresent(x, y))
			{
				return false;
			}
		}
		return true;
	}

}
