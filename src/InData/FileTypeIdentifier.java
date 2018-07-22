package InData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileTypeIdentifier {
	public String title;
	public FileParser file;

	public FileTypeIdentifier(String title) {
		this.title = title;
	}

	public FileParser recogniseFile() throws IOException {
		BufferedReader input = null;
		input = new BufferedReader(new InputStreamReader(new FileInputStream(
				title), Charset.forName("UTF-8")));
		if (title.contains("xml") || input.read() == '<') {
			file = new XMLType(title);
		} else if (title.contains("txt")) {
			file = new TXTType(title);
		}
		input.close();
		return file;
	}
}
