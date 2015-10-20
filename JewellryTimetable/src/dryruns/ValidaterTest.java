package dryruns;

import processing.Validater;

public class ValidaterTest {

	
	public static void main(String args[]){
		
		String time = "";
		String time2 = "25:00";
		Validater valid = new Validater();
		if(valid.vtime(time) == true){
			System.out.println("got through");
		}else{
			System.out.println("mareeee");
		}
		
		
	}
}
