package autonomousagents.test;

import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.predator.SoftmaxPolicy;
import autonomousagents.util.Random;

public class TestCompareSoftMaxEGreedy
{
	/**
	 * Plots the difference between SoftMax and e-Greedy
	 */
	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();

		dataset.addSeries(TestQLearning.generateSeries(0.2, 0.5, new EGreedyPolicy()));
		Random.resetRandom();
		dataset.addSeries(TestQLearning.generateSeries(0.2, 0.5, new SoftmaxPolicy()));

		ApplicationFrame frame = new ApplicationFrame("");

		NumberAxis xax = new NumberAxis("Episodes");
		NumberAxis yax = new NumberAxis("Steps");
		XYSplineRenderer a = new XYSplineRenderer();
		// a.setBaseShapesVisible(false);
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
