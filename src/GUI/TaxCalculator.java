//Christina Trialoni
//This is a simple Tax Calculator, a project as part of a University course
//Uses jfreechart library for barcharts and piecharts (http://www.jfree.org/index.html)

package GUI;

import java.io.IOException;

class TaxCalculator {
	public static void main(String[] args) throws IOException {
		Window myWindow = new Window();
		myWindow.makeGui();
		myWindow.setVisible(true);
	}

}