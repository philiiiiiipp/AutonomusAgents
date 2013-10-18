package autonomousagents.test.a3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.util.JFreeChartHelper;
import autonomousagents.world.Point;

public class TestMultipleRLearning
{
	private static final int EPISODE_COUNT = 7000;

	/**
	 * Plot the performance of multiple Q-learning
	 */
	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();

		double alpha = 0.1;
		double gamma = 0.1;

		List<Point> predatorPoints = new ArrayList<Point>();
		predatorPoints.add(new Point(5, 5));
		predatorPoints.add(new Point(5, 4));
		// predatorPoints.add(new Point(5, 3));
		Point preyPoint = new Point(0, 0);

		List<Integer> stepList = MultipleRLearning.runRLearning(EPISODE_COUNT, alpha, gamma, predatorPoints, preyPoint);
		dataset.addSeries(JFreeChartHelper.createWinningDataseries(stepList, "Predator", 1));

		System.out.println("Done");

		dataset.addSeries(JFreeChartHelper.createLoosingDataseries(stepList, "Prey", 1));

		ApplicationFrame frame = new ApplicationFrame("");

		NumberAxis xax = new NumberAxis("Episodes");
		NumberAxis yax = new NumberAxis("Wins");
		XYSplineRenderer a = new XYSplineRenderer();
		a.setBaseShapesVisible(false);
		a.setSeriesPaint(3, Color.RED);
		XYPlot xyplot = new XYPlot(dataset, xax, yax, a);

		JFreeChart chart = new JFreeChart(xyplot);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.setContentPane(chartPanel);
		frame.pack();
		frame.setVisible(true);
	}
}
