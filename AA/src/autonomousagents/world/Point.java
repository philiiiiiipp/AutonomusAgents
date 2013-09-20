package autonomousagents.world;

/**
 * The class Point presents properties and methods we use to encode the (x,y)
 * position of an agent inside our grid
 */
public class Point
{
	private final int x;
	private final int y;

	/**
	 * constructor for the Point class
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Point(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * get the X coordinate of a point
	 * 
	 * @return int
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * get the Y coordinate of a point
	 * 
	 * @return int
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * checks if the current point is equal to another Point
	 * 
	 * @param otherPoint
	 *            - the point with which we want to check for equality
	 * @return boolean
	 */
	public boolean equals(final Point otherPoint)
	{
		return this.x == otherPoint.getX() && this.y == otherPoint.getY();
	}

	/**
	 * method that prints the X and Y coordinates of a point
	 */
	public void pPrint()
	{
		System.out.println("X: " + this.x + " Y: " + this.y);
	}

}
