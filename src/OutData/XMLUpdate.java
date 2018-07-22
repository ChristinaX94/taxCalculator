package OutData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class XMLUpdate extends FileEditor {
	private String titleStart[] = { "<ReceiptID> ", "<Date> ", "<Kind> ",
			"<Amount> ", "<Company> ", "<Country> ", "<City> ", "<Street> ",
			"<Number> " };
	private String titleEnd[] = { " </ReceiptID>", " </Date>", " </Kind>",
			" </Amount>", " </Company>", " </Country>", " </City>",
			" </Street>", " </Number>" };

	public XMLUpdate(String title) {
		super(title);
	}

	public void addReceipt(String receipt[], String company[])
			throws IOException {
		File inputFile = new File(title);
		File tempFile = new File(title + "-temp");
		input = new BufferedReader(new FileReader(inputFile));
		text = new BufferedWriter(new FileWriter(tempFile));
		String currentLine = input.readLine();
		currentLine = reachPoint(currentLine);
		fillReceipt(text, receipt);
		fillCompany(text, company);
		inputFile.delete();
		placeFile(tempFile, currentLine);
	}

	public String reachPoint(String currentLine) throws IOException {
		while (!currentLine.equals("</Receipts>")) {
			text.write(currentLine);
			text.newLine();
			currentLine = input.readLine();
		}
		return currentLine;

	}

	public void fillReceipt(BufferedWriter text, String[] receipt)
			throws IOException {
		for (int i = 0; i < 4; i++) {
			text.newLine();
			text.write(titleStart[i] + receipt[i] + titleEnd[i]);
		}
	}

	public void fillCompany(BufferedWriter text, String[] company)
			throws IOException {
		for (int i = 0; i < 5; i++) {
			text.newLine();
			text.write(titleStart[i + 4] + company[i] + titleEnd[i + 4]);
		}
		text.newLine();
		text.newLine();
		text.write("</Receipts>");
		text.close();
		input.close();
	}

	public void deleteReceipt(String id, String path) throws IOException {
		File inputFile = new File(title);
		File tempFile = new File(title + "-temp");
		input = new BufferedReader(new FileReader(inputFile));
		text = new BufferedWriter(new FileWriter(tempFile));
		String currentLine = input.readLine();
		findAndRemove(currentLine, id);
		text.close();
		input.close();
		inputFile.delete();
		placeFile(tempFile, currentLine);
	}

	public String findAndRemove(String currentLine, String id)
			throws IOException {
		while (currentLine != null) {
			if (!currentLine.equals("<ReceiptID> " + id + " </ReceiptID>")) {
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
