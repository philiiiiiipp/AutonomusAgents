package autonomousagents.test;

import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.util.Random;

public class TestCompareSarsaQLearning
{
	/**
	 * Plot the difference between SARSA and Q-Learning
	 */
	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();

		Random.resetRandom();
		dataset.addSeries(TestSarsa.generateDataSeries(0.1, 0.1));
		Random.resetRandom();
		dataset.addSeries(TestQLearning.generateSeries(0.1, 0.1));

		ApplicationFrame frame = new ApplicationFrame("");

		NumberAxis xax = new NumberAxis("Episodes");
		NumberAxis yax = new NumberAxis("Steps");
		XYSplineRenderer a = new XYSplineRenderer();
		a.setBaseShapesVisible(false);
		a.setSeriesPaint(2, Color.ORANGE);
		a.setSeriesPaint(3, Color.BLACK);
		XYPlot xyplot = new XYPlot(dataset, xax, yax, a);

		JFreeChart chart = new JFreeChart(xyplot);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.setContentPane(chartPanel);
		frame.pack();
		frame.setVisible(true);
	}
}
