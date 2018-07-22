package OutData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class OutDataFactory {
	public FileEditor file;
	public Log log;

	public FileEditor openFile(String title) throws IOException {
		BufferedReader input = null;
		input = new BufferedReader(new InputStreamReader(new FileInputStream(
				title), Charset.forName("UTF-8")));
		if (title.contains("xml") || input.read() == '<') {
			file = new XMLUpdate(title);
		} else if (title.contains("txt")) {
			file = new TXTUpdate(title);
		}
		input.close();
		return file;
	}

	public Log prepareFile(String type) throws IOException {
		if (type.equals("xml")) {
			log = new LogXML();
		}
		if (type.equals("txt")) {
			log = new LogTXT();
		}
		return log;
	}
}
