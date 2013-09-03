import java.util.ArrayList;

public class State
{

	private ArrayList<Agent> agents;
	private static final int XMAX = 10;
	private static final int YMAX = 10;
	private static final int XMIN = 0;
	private static final int YMIN = 0;

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

	public static Point north(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == YMIN ? YMAX : pointToTranslate
				.getY() - 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == YMAX ? YMIN : pointToTranslate
				.getY() + 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == XMAX ? XMIN : pointToTranslate
				.getX() + 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}

	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == XMIN ? XMAX : pointToTranslate
				.getX() + 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}

	public ArrayList<Point> neighbors(final Point currentPoint)
	{
		ArrayList<Point> neighbors;

	}
}
