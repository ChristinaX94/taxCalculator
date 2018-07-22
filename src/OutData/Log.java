package OutData;

import java.io.IOException;

public abstract class Log {
	protected String personBasicInformation[] = new String[7];
	protected int personReceipts[] = new int[5];

	public abstract void createLog(String path) throws IOException;

	public void setPersonInformation(String information[]) {
		personBasicInformation = information;
	}

	public void setReceiptInformation(int receipts[]) {
		personReceipts = receipts;
	}
}
