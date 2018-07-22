package OutData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogXML extends Log {
	private String titlesBegin[] = { "<Name> ", "<AFM> ", "<Income> ",
			"<BasicTax> ", "<TaxIncrease> ", "<TotalTax> ",
			"<TotalReceiptsGathered> ", "<Entertainment> ", "<Basic> ",
			"<Travel> ", "<Health> ", "<Other> " };

	private String titlesEnd[] = { " </Name>", " </AFM>", " </Income>",
			" </BasicTax>", " </TaxIncrease>", " </TotalTax>",
			" </TotalReceiptsGathered>", " </Entertainment>", " </Basic>",
			" </Travel>", " </Health>", " </Other>" };

	public void createLog(String path) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(new File(path
				+ "LOG.xml")));
		for (int i = 0; i < 7; i++) {
			output.write(titlesBegin[i] + personBasicInformation[i]
					+ titlesEnd[i]);
			output.newLine();
		}
		for (int i = 0; i < 5; i++) {
			output.newLine();
			output.write(titlesBegin[i + 7] + personReceipts[i]
					+ titlesEnd[i + 7]);
		}
		output.close();
	}
}
