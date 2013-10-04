package autonomousagents.test;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Random;

public class TestCompareAll
{
	public static void test()
	{

		XYSeriesCollection dataset = new XYSeriesCollection();
		final int episodeCount = 1750;

		Random.resetRandom();
		List<Integer> stepList = TestOnPolicyMonteCarlo.runOnPolicyMonteCarlo(new EGreedyPolicy(),
				new PreyRandomPolicy(), episodeCount);
		dataset.addSeries(createDataseries(stepList, "MonteCarlo"));

		Random.resetRandom();
		stepList = TestQLearning.runQLearning(new EGreedyPolicy(), new PreyRandomPolicy(), 0.1, 0.9, episodeCount);
		dataset.addSeries(createDataseries(stepList, "Q-Learning with Alpha:0.1 Beta:0.9"));

		Random.resetRandom();
		stepList = TestSarsa.runSarsa(new EGreedyPolicy(), new PreyRandomPolicy(), 0.1, 0.9, episodeCount);
		dataset.addSeries(createDataseries(stepList, "Sarsa with Alpha:0.1 Beta:0.9"));

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

	private static XYSeries createDataseries(final List<Integer> data, final String name)
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
}
