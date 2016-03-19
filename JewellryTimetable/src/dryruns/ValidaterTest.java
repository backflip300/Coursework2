package dryruns;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import processing.Validator;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidaterTest.
 */
public class ValidaterTest {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String args[]) throws IOException{
		File file = new File("U:/git/Coursework2/JewellryTimetable/Images/Timetable.jpg");
		 Desktop desktop = Desktop.getDesktop();
		 desktop.open(file);
		
	}
}
