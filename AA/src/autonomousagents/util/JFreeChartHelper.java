package autonomousagents.util;

import java.util.List;

import org.jfree.data.xy.XYSeries;

public class JFreeChartHelper
{

	/**
	 * Summing up all data values and plots them with their given position
	 * 
	 * @param data
	 * @param name
	 * @return
	 */
	public static XYSeries createDataseries(final List<Integer> data, final String name)
	{
		XYSeries steps = new XYSeries(name);
		int totalCount = 0;

		for (int i = 0; i < data.size(); ++i)
		{
			totalCount += data.get(i);
			steps.add(i, totalCount);
		}

		return steps;
	}

	/**
	 * Creates a data series, averaging over the average amount of datapoints
	 * 
	 * @param data
	 * @param name
	 * @param average
	 * @return
	 */
	public static XYSeries createAverageDataseries(final List<Integer> data, final String name, final int average)
	{
		XYSeries steps = new XYSeries(name);
		int totalCount = 0;
		for (int i = 0; i < data.size(); ++i)
		{
			totalCount += data.get(i);

			if (i != 0 && i % average == 0)
			{
				steps.add(i, (double) totalCount / average);
				totalCount = 0;
			}
		}

		return steps;
	}

	/**
	 * Creates a data series, averaging over the average amount of datapoints
	 * 
	 * @param data
	 * @param name
	 * @param average
	 * @return
	 */
	public static XYSeries createWinningDataseries(final List<Integer> data, final String name, final int average)
	{
		XYSeries steps = new XYSeries(name);
		int totalCount = 0;
		for (int i = 0; i < data.size(); ++i)
		{
			totalCount += data.get(i);
			steps.add(i, totalCount);
		}

		return steps;
	}

	/**
	 * Creates a data series, averaging over the average amount of datapoints
	 * 
	 * @param data
	 * @param name
	 * @param average
	 * @return
	 */
	public static XYSeries createLoosingDataseries(final List<Integer> data, final String name, final int average)
	{
		XYSeries steps = new XYSeries(name);
		int totalCount = 0;
		for (int i = 0; i < data.size(); ++i)
		{
			totalCount += (data.get(i) == 0 ? 1 : 0);
			steps.add(i, totalCount);
		}

		return steps;
	}
}
