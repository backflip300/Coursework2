package processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class FileAccess.
 */
public class FileAccess {
	
	/** The Location. */
	private Path Location;

	/**
	 * Instantiates a new file access.
	 *
	 * @param Location the location
	 */
	public FileAccess(Path Location) {
		this.Location = Location;
	}

	/**
	 * O read file data.
	 *
	 * @return the array list
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
	 * S read file data.
	 *
	 * @return the array list
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
	 * S write file data.
	 *
	 * @param toWrite the to write
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
	 * S editline.
	 *
	 * @param toedit the toedit
	 * @param line the line
	 */
	public void sEditline(String toedit, int line) {
		// get old data
		ArrayList<String> oldData = new ArrayList<String>();
		oldData = sReadFileData();
		// edit line
		oldData.remove(line);
		oldData.add(line, toedit);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Location.toString(), false))) {
			writer.write("");
			for (int i = 0; i < oldData.size(); i++) {
				writer.write(oldData.get(i));
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * S remove line.
	 *
	 * @param line the line
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
