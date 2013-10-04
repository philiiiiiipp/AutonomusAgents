package autonomousagents.test;

import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.util.Constants;
import autonomousagents.util.Random;

public class TestCompareSarsaQLearning
{
	public static void test()
	{
		double alpha = 0.1;

		XYSeriesCollection dataset = new XYSeriesCollection();

		// dataset.addSeries(averageSteps);
		// dataset.addSeries(averageLastSteps);
		dataset.addSeries(TestSarsa.generateDataSeries(0.1, 0.9));
		Random.resetRandom();
		dataset.addSeries(TestQLearning.generateSeries(0.1, 0.9));

		ApplicationFrame frame = new ApplicationFrame("Q-Learning with e-Greedy" + ", epsilon=" + Constants.EPSILON
				+ " alpha=" + alpha + " gamma=" + Constants.GAMMA);

		NumberAxis xax = new NumberAxis("Steps");
		NumberAxis yax = new NumberAxis(" Episodes");
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
