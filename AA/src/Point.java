public class Point
{
	final int x;
	final int y;

	public Point(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public boolean equals(final Point otherPoint)
	{
		return this.x == otherPoint.x && this.x == otherPoint.y;
	}
}
