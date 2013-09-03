import java.util.Random;

public abstract class Agent
{

	protected int coord_x;
	protected int coord_y;

	protected static Random RAND = new Random(0);

	public Agent(final int x, final int y)
	{
		this.coord_x = x;
		this.coord_y = y;
	}

	public boolean isPresent(final int x, final int y)
	{
		return this.coord_x == x && this.coord_y == y;
	}

	public abstract boolean step();

}
