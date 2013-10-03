package autonomousagents.util;

import autonomousagents.world.Point;

public class Random
{
	public static final java.util.Random RAND = new java.util.Random();

	/**
	 * Generate a random point, which is inside the boundaries of the GameField
	 * and unequal to the given point
	 * 
	 * @param notAvailable
	 * @return a random point, unequal to the given point
	 */
	public static Point randomPoint(final Point notAvailable)
	{
		int x = RAND.nextInt(GameField.XMAX);
		int y;
		if (x == notAvailable.getX())
		{
			// Y must be different
			y = RAND.nextInt(GameField.XMAX - 1);
			y = (notAvailable.getY() == y ? y + 1 : y);
		} else
		{
			y = RAND.nextInt(GameField.XMAX);
		}

		return new Point(x, y);
	}
}
