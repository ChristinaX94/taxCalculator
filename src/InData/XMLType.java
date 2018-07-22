package InData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XMLType extends FileParser {
	private String[] prePart;

	XMLType(String title) {
		super(title);
	}

	public void readPersonInformation() throws IOException {
		for (int i = 0; i < 4; i++) {
			line = input.readLine();
			prePart = line.split("> ");
			part = prePart[1].split(" <");
			personInformation[i] = part[0];
		}
		line = input.readLine();
		while (!line.equals("<Receipts>")) {
			line = input.readLine();
		}
	}

	public void readReceiptInformation() throws IOException {
		receiptInformation = new String[4];
		for (int i = 0; i < 4; i++) {
			prePart = line.split("> ");
			part = prePart[1].split(" <");
			receiptInformation[i] = part[0];
			line = input.readLine();
		}
	}

	public void readCompanyInformation() throws IOException {
		companyInformation = new String[5];
		for (int i = 0; i < 5; i++) {
			prePart = line.split("> ");
			part = prePart[1].split(" <");
			companyInformation[i] = part[0];
			line = input.readLine();
		}
	}

	public void readFile() throws IOException {
		input = new BufferedReader(new FileReader(title));
		readPersonInformation();
		while (line != null) {
			line = input.readLine();
			if (line.equals("</Receipts>")) {
				return;
			}
			while (line.equals("")) { line = input.readLine(); }
			readAndStore();
		}
		input.close();
	}
}