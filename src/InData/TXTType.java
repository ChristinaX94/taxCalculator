package InData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TXTType extends FileParser {

	TXTType(String title) {
		super(title);
	}

	public void readPersonInformation() throws IOException {
		for (int i = 0; i < 4; i++) {
			line = input.readLine();
			part = line.split(": ");
			personInformation[i] = part[1];
		}
		line = input.readLine();
		while (!line.equals("Receipts:")) {
			line = input.readLine();
		}
		line = input.readLine();
	}

	public void readReceiptInformation() throws IOException {
		receiptInformation = new String[4];
		for (int i = 0; i < 4; i++) {
			part = line.split(": ");
			receiptInformation[i] = part[1];
			line = input.readLine();
		}
	}

	public void readCompanyInformation() throws IOException {
		companyInformation = new String[5];
		for (int i = 0; i < 5; i++) {
			part = line.split(": ");
			companyInformation[i] = part[1];
			line = input.readLine();
		}
	}

	public void readAndStore() throws IOException {
		readReceiptInformation();
		readCompanyInformation();
		receiptAndCompanyList.put(receiptInformation, companyInformation);
	}

	public void readFile() throws IOException {
		input = new BufferedReader(new FileReader(title));
		readPersonInformation();
		while (line != null) {
			while (line.equals("")) {
				line = input.readLine();
				if (line == null) { break;}
			}
			if (line != null) { readAndStore(); }
		}
		input.close();
	}
}
