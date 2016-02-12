package dryruns;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import processing.Validater;

public class ValidaterTest {

	
	public static void main(String args[]) throws IOException{
		File file = new File("U:/git/Coursework2/JewellryTimetable/Images/Timetable.jpg");
		 Desktop desktop = Desktop.getDesktop();
		 desktop.open(file);
		
	}
}
