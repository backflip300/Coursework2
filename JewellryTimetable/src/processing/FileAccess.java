package processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The Class FileAccess.
 */
public class FileAccess {

	/** The Location of the file. */
	private Path Location;

	/**
	 * Instantiates a new file access.
	 *
	 * @param Location
	 *            the location of the file
	 */
	public FileAccess(Path Location) {
		this.Location = Location;
	}

	/**
	 * O read file data gives out arraylist of textfile as objects.
	 *
	 * @return the array list containing the textfile as objects
	 */
	public ArrayList<Object> oReadFileData() {
		ArrayList<Object> fileData = new ArrayList<Object>();
		Charset charset = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(Location, charset)) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileData.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}

	/**
	 * S read file data out arraylist of textfile as strings.
	 *
	 * @return the array list containing the textfile as strings
	 */
	public ArrayList<String> sReadFileData() {
		ArrayList<String> fileData = new ArrayList<String>();
		Charset charset = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(Location, charset)) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileData.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}

	/**
	 * S write file data writes to the end of the file.
	 *
	 * @param toWrite
	 *            the text to write to the end of the file
	 */
	public void sWriteFileData(String toWrite) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Location.toString(), true))) {
			writer.write(toWrite);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * S editline edits a line in the text file.
	 *
	 * @param toEdit
	 *            the line of text to replace the old line with
	 * @param line
	 *            the line to change.
	 */
	public void sEditline(String toEdit, int line) {
		// get old data
		ArrayList<String> oldData = new ArrayList<String>();
		oldData = sReadFileData();
		// edit line
		oldData.remove(line);
		oldData.add(line, toEdit);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Location.toString(), false))) {
			writer.write("");
			for (int i = 0; i < oldData.size(); i++) {
				writer.write(oldData.get(i));
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * S remove line of text from file.
	 *
	 * @param line
	 *            the line to remove
	 */
	public void sRemoveLine(int line) {
		ArrayList<String> oldData = new ArrayList<String>();
		oldData = sReadFileData();
		// edit line
		oldData.remove(line);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Location.toString(), false))) {
			writer.write("");
			for (int i = 0; i < oldData.size(); i++) {
				writer.write(oldData.get(i));
				writer.newLine();
			}
			writer.write("");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}

}
