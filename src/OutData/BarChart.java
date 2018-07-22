package OutData;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import Calculations.TaxPayer;

public class BarChart extends JFrame {
	public BarChart(TaxPayer person) {
		super("Tax analysis in $");
		JFreeChart barChart = ChartFactory.createBarChart(person.getName(),
				"Tax Category", "Amount",
				createDataset(person.getTax(), person.getBasicTax()),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 360));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset(double totalTax, double basicTax) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(basicTax, "Amount", "Basic Tax");
		dataset.setValue(totalTax - basicTax, "Amount", "Tax Increase");
		dataset.setValue(totalTax, "Amount", "Total Tax");
		return dataset;
	}
}