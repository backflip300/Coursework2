package processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileAccess {
	Path Location;

	public FileAccess(Path Location) {
		this.Location = Location;
	}

	public ArrayList<Object> oReadFileData() {
		ArrayList<Object> fileData = new ArrayList<Object>();
		Charset charset = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(Location, charset)) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileData.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileData;
	}

	public ArrayList<String> sReadFileData() {
		ArrayList<String> fileData = new ArrayList<String>();
		Charset charset = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(Location, charset)) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileData.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileData;
	}

	public void sWriteFileData(String toWrite) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				Location.toString(), true))) {

			writer.write(toWrite);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sEditline(String toedit, int line) {
		// get old data
		ArrayList<String> oldData = new ArrayList<String>();
		oldData = sReadFileData();
		// edit line
		oldData.remove(line);
		oldData.add(line, toedit);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				Location.toString(), false))) {
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

	public void sRemoveLine(int line) {
		ArrayList<String> oldData = new ArrayList<String>();
		oldData = sReadFileData();
		// edit line
		oldData.remove(line);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				Location.toString(), false))) {
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
