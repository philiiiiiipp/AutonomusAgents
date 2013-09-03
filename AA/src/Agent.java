import java.util.Random;

public abstract class Agent
{

	protected Point point;

	protected static Random RAND = new Random(0);

	public Agent(final Point p)
	{
		this.point = p;
	}

	public boolean isPresent(final int x, final int y)
	{
		return this.point.getX() == x && this.point.getY() == y;
	}

	public abstract boolean step();

}
