package autonomousagents;

import java.text.DecimalFormat;

import autonomousagents.util.GameField;

public class VI
{

	public VI()
	{
		double lambda = 0.5;

		int columns = GameField.XMAX - GameField.XMIN + 1;
		int rows = GameField.YMAX - GameField.YMIN + 1;

		double[][] values1 = new double[rows][columns];
		double[][] values2 = new double[rows][columns];

		values1[5][5] = 1;

		double[][] readingGrid = values1;
		double[][] writingGrid = values2;

		for (int _ = 0; _ < 20; _++)
		{
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					double high = getHighestNeigbor(readingGrid, i, j);

					if (high != readingGrid[i][j])
					{
						high *= lambda;
					}
					writingGrid[i][j] = high;
				}
			}

			double[][] temp = readingGrid;
			readingGrid = writingGrid;
			writingGrid = temp;
		}
		VI.pprintValues(readingGrid);
	}

	public static double getHighestNeigbor(final double[][] values,
			final int x, final int y)
	{
		double highest = values[x][y];
		int i = (x + 1) % 11;
		int j = y;
		highest = Math.max(highest, values[i][j]);

		i = Math.abs((x - 1)) % 11;
		highest = Math.max(highest, values[i][j]);

		i = x;
		j = (y + 1) % 11;
		highest = Math.max(highest, values[i][j]);

		j = Math.abs((y - 1)) % 11;
		highest = Math.max(highest, values[i][j]);

		return highest;
	}

	public static void pprintValues(final double[][] values)
	{
		DecimalFormat df = new DecimalFormat("#.###");
		for (double[] row : values)
		{
			String acc = "";
			for (double entry : row)
			{
				String s = df.format(entry);
				if (!s.contains("."))
				{
					s = s.concat(".");
				}
				while (s.length() < 5)
				{
					s = s.concat("0");
				}
				acc = acc.concat(s + "  ");
			}
			System.out.println(acc);
			System.out.println();
		}
	}
}
