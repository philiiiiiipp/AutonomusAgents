import java.util.ArrayList;

public class State
{

	private ArrayList<Agent> agents;

	public boolean isFree(final int x, final int y)
	{
		for (Agent a : this.agents)
		{
			if (a.isPresent(x, y))
			{
				return false;
			}
		}
		return true;
	}

}
