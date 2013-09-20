package autonomousagents.world;

/* The class Point presents properties and methods we 
 * use to encode the (x,y) position of an agent inside our grid */
public class Point
{
	private final int x;
	private final int y;

	// constructor for the Point class
	public Point(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	// returns the X coordinate of a point
	public int getX()
	{
		return this.x;
	}

	// returns the Y coordinate of a point
	public int getY()
	{
		return this.y;
	}

	// method that checks if two points are equal
	public boolean equals(final Point otherPoint)
	{
		return this.x == otherPoint.getX() && this.y == otherPoint.getY();
	}

	// method that prints the coordinates of a point
	public void pPrint()
	{
		System.out.println("X: " + this.x + " Y: " + this.y);
	}

}
