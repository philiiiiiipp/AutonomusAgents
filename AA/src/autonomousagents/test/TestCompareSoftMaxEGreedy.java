package autonomousagents.test;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.predator.SoftmaxPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.JFreeChartHelper;
import autonomousagents.util.Random;

public class TestCompareSoftMaxEGreedy
{
	private static final int EPISODE_COUNT = 1750;

	/**
	 * Plots the difference between SoftMax and e-Greedy
	 */
	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();

		double alpha = 0.2;
		double gamma = 0.5;

		Random.resetRandom();
		List<Integer> stepList = TestSarsa.runSarsa(new EGreedyPolicy(), new PreyRandomPolicy(), alpha, gamma,
				EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createAverageDataseries(stepList, "Sarsa with e-Greedy, Alpha:" + alpha
				+ " Gamma:" + gamma, 100));

		Random.resetRandom();
		stepList = TestSarsa.runSarsa(new SoftmaxPolicy(), new PreyRandomPolicy(), alpha, gamma, EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createAverageDataseries(stepList, "Sarsa with SoftMax, Alpha:" + alpha
				+ " Gamma:" + gamma, 100));

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
