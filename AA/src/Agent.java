import java.util.Random;

public abstract class Agent
{

	protected Point currentPosition;

	protected static Random RAND = new Random(0);

	public Agent(final Point p)
	{
		this.currentPosition = p;
	}

	public boolean isPresent(final int x, final int y)
	{
		return this.currentPosition.getX() == x
				&& this.currentPosition.getY() == y;
	}

	public abstract boolean step();

}
