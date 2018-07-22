package OutData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TXTUpdate extends FileEditor {
	private String titles[] = { "Receipt ID: ", "Date: ", "Kind: ", "Amount: ",
			"Company: ", "Country: ", "City: ", "Street: ", "Number: " };

	public TXTUpdate(String title) {
		super(title);
	}

	public void addReceipt(String receipt[], String company[])
			throws IOException {
		FileWriter input = new FileWriter(title, true);
		BufferedWriter text = new BufferedWriter(input);
		text.newLine();
		fillReceipt(text, receipt);
		fillCompany(text, company);
		text.close();
		input.close();
	}

	public void fillReceipt(BufferedWriter text, String receipt[])
			throws IOException {
		for (int i = 0; i < 4; i++) {
			text.newLine();
			text.write(titles[i] + receipt[i]);
		}
	}

	public void fillCompany(BufferedWriter text, String company[])
			throws IOException {
		for (int i = 0; i < 5; i++) {
			text.newLine();
			text.write(titles[i + 4] + company[i]);
		}
	}

	public void deleteReceipt(String id, String path) throws IOException {
		File inputFile = new File(title);
		File tempFile = new File(title + "-temp");
		input = new BufferedReader(new FileReader(inputFile));
		text = new BufferedWriter(new FileWriter(tempFile));
		String currentLine = input.readLine();
		currentLine = findAndRemove(currentLine, id);
		text.close();
		input.close();
		inputFile.delete();
		placeFile(tempFile, currentLine);
	}

	public String findAndRemove(String currentLine, String id)
			throws IOException {
		while (currentLine != null) {
			if (!currentLine.equals("Receipt ID: " + id)) {
				text.write(currentLine);
				text.newLine();
				currentLine = input.readLine();
			} else {
				for (int i = 0; i < 10; i++) { currentLine = input.readLine(); }
			}
		}
		return currentLine;
	}
}
