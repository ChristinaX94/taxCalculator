package InData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class FileParser {
	public String title;
	public String type;
	protected BufferedReader input;
	protected String line = "";
	protected String[] part;
	protected String[] receiptInformation;
	protected String[] companyInformation;
	protected String[] personInformation = new String[4];
	protected HashMap<String[], String[]> receiptAndCompanyList = new HashMap<String[], String[]>();

	FileParser(String title) {
		this.title = title;
	}

	abstract public void readFile() throws IOException;

	abstract public void readReceiptInformation() throws IOException;

	abstract public void readCompanyInformation() throws IOException;

	abstract public void readPersonInformation() throws IOException;

	public void readAndStore() throws IOException {
		readReceiptInformation();
		readCompanyInformation();
		receiptAndCompanyList.put(receiptInformation, companyInformation);
	}

	public String[] getPersonInformation() {
		return personInformation;
	}

	public HashMap getReceiptAndCompanyList() {
		return receiptAndCompanyList;
	}

}
