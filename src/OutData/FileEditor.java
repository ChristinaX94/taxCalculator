package OutData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileEditor {
	protected String title;
	protected BufferedReader input;
	protected BufferedWriter text;

	public FileEditor(String title) {
		this.title = title;
	}

	public abstract void addReceipt(String receipt[], String company[])
			throws IOException;

	public abstract void deleteReceipt(String id, String path)
			throws IOException;

	public abstract void fillReceipt(BufferedWriter text, String receipt[])
			throws IOException;

	public abstract void fillCompany(BufferedWriter text, String company[])
			throws IOException;

	public abstract String findAndRemove(String currentLine, String id)
			throws IOException;

	public void placeFile(File tempFile, String currentLine) throws IOException {
		File finalFile = new File(title);
		BufferedReader input = new BufferedReader(new FileReader(tempFile));
		BufferedWriter text = new BufferedWriter(new FileWriter(finalFile));
		while ((currentLine = input.readLine()) != null) {
			text.write(currentLine);
			text.newLine();
		}
		text.close();
		input.close();
		tempFile.delete();
	}
}
