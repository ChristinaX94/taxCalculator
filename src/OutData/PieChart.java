package OutData;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import Calculations.TaxPayer;

public class PieChart extends JFrame {
	public PieChart(TaxPayer person) {
		super(person.getName() + ": Receipts");
		JFreeChart chart = ChartFactory.createPieChart(person.getName()
				+ ": Receipts", createDataset(person), true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 360));
		setContentPane(chartPanel);
	}

	private static PieDataset createDataset(TaxPayer person) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Entertainment: " + person.getReceiptInformation()[0],
				person.getReceiptInformation()[0]);
		dataset.setValue("Basic: " + person.getReceiptInformation()[1],
				person.getReceiptInformation()[1]);
		dataset.setValue("Travel: " + person.getReceiptInformation()[2],
				person.getReceiptInformation()[2]);
		dataset.setValue("Health: " + person.getReceiptInformation()[3],
				person.getReceiptInformation()[3]);
		dataset.setValue("Other: " + person.getReceiptInformation()[4],
				person.getReceiptInformation()[4]);
		return dataset;
	}
}