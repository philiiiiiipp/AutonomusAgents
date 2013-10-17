package autonomousagents.test.a3;

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
import autonomousagents.policy.predator.GreedyPolicy;
import autonomousagents.policy.predator.PredatorRandomPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.JFreeChartHelper;
import autonomousagents.util.Random;

public class TestCompareAll
{
	private static final int EPISODE_COUNT = 1750;

	/**
	 * Plot the difference between SARSA, Q-Learning and On-/Off-Policy Monte
	 * Carlo
	 */
	public static void test()
	{

		XYSeriesCollection dataset = new XYSeriesCollection();

		Random.resetRandom();
		List<Integer> stepList = TestOnPolicyMonteCarlo.runOnPolicyMonteCarlo(new EGreedyPolicy(),
				new PreyRandomPolicy(), EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createDataseries(stepList, "MonteCarlo OnPolicy"));

		Constants.QValue = 0;
		Random.resetRandom();
		stepList = TestOffPolicyMonteCarlo.runOffPolicyMonteCarlo(new PredatorRandomPolicy(), new GreedyPolicy(),
				new PreyRandomPolicy(), EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createDataseries(stepList, "MonteCarlo OffPolicy with Random"));

		Random.resetRandom();
		stepList = TestOffPolicyMonteCarlo.runOffPolicyMonteCarlo(new EGreedyPolicy(), new GreedyPolicy(),
				new PreyRandomPolicy(), EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createDataseries(stepList, "MonteCarlo OffPolicy with e-Greedy"));
		Constants.QValue = 15;

		double alpha = 0.1;
		double gamma = 0.9;

		Random.resetRandom();
		stepList = TestQLearning.runQLearning(new EGreedyPolicy(), new PreyRandomPolicy(), alpha, gamma, EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createDataseries(stepList, "Q-Learning with Alpha:" + alpha + " Gamma:"
				+ gamma));

		Random.resetRandom();
		stepList = TestSarsa.runSarsa(new EGreedyPolicy(), new PreyRandomPolicy(), alpha, gamma, EPISODE_COUNT);
		dataset.addSeries(JFreeChartHelper.createDataseries(stepList, "Sarsa with Alpha:" + alpha + " Gamma:" + gamma));

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
