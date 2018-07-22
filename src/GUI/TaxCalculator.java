package GUI;

import java.io.IOException;

class TaxCalculator {
	public static void main(String[] args) throws IOException {
		Window myWindow = new Window();
		myWindow.makeGui();
		myWindow.setVisible(true);
	}

}