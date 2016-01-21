package processing;

import java.util.ArrayList;

public class Validater {
	private boolean isValid;

	public Validater() {
		isValid = false;
	}

	public boolean vSimpleString(String toValidate) {
		return toValidate.matches("[a-zA-Z0-9]+");
	}

	public boolean vIntRange(int toValidate, int min, int max) {
		isValid = false;
		if (toValidate <= max && toValidate >= min) {
			isValid = true;
		}
		return isValid;

	}

	public boolean vtime(String toValidate) {
		isValid = false;
		int hours = 100;
		int mins = 1000;
		if (toValidate.length() == 5) {
			// System.out.println("test");
			if (toValidate.substring(0, 2).matches("[0-9]+")) {
				hours = Integer.parseInt((toValidate.substring(0, 2)));

			}
			if (toValidate.substring(3).matches("[0-9]+")) {

				mins = Integer.parseInt((toValidate.substring(3)));
			}
			// System.out.println(mins + "\t" + hours);
			if (mins < 60 && mins >= 0 && hours < 24 && hours >= 0) {
				isValid = true;

			}
		}
		return isValid;
	}

	public boolean vOnlyContainsNumbers(String toValidate) {
		isValid = false;
		if (toValidate.matches("[0-9]+")) {
			isValid = true;
		}
		return isValid;
	}

	public boolean enoughStock(ArrayList<String> products) {
		boolean Stocked = true;

		return Stocked;

	}
}
