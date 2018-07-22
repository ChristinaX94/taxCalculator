package OutData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogTXT extends Log {
	private String titles[] = { "Name: ", "AFM: ", "Income: ", "Basic Tax: ",
			"Tax Increase: ", "Total Tax: ", "TotalReceiptsGathered: ",
			"Entertainment: ", "Basic: ", "Travel: ", "Health: ", "Other: " };

	public void createLog(String path) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(new File(path
				+ "LOG.txt")));
		for (int i = 0; i < 7; i++) {
			output.write(titles[i] + personBasicInformation[i]);
			output.newLine();
		}
		for (int i = 0; i < 5; i++) {
			output.newLine();
			output.write(titles[i + 7] + personReceipts[i]);
		}
		output.close();
	}
}