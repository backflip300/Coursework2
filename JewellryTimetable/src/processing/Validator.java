package processing;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Validater.
 */
public class Validator {

	/** The is valid specifies wheter an input is valid. */
	private boolean isValid;

	/**
	 * Instantiates a new validater.
	 */
	public Validator() {
		isValid = false;
	}

	/**
	 * V simple string validates if input is a simple string containing only
	 * letters and numbers.
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if valid
	 */
	public boolean vSimpleString(String toValidate) {
		return toValidate.matches("[a-zA-Z0-9]+");
	}

	/**
	 * V int range determines if a integer is between 2 values.
	 *
	 * @param toValidate
	 *            the number to validate
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return true, if successful
	 */
	public boolean vIntRange(int toValidate, int min, int max) {
		isValid = false;
		if (toValidate <= max && toValidate >= min) {
			isValid = true;
		}
		return isValid;

	}

	/**
	 * Vtime determines if a string is in the correct format for the timetable.
	 * 
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if successful
	 */
	public boolean vtime(String toValidate) {
		isValid = false;
		int hours = 100;
		int mins = 1000;
		if (toValidate.length() == 5) {
			if (toValidate.substring(0, 2).matches("[0-9]+")) {
				hours = Integer.parseInt((toValidate.substring(0, 2)));

			}
			if (toValidate.substring(3).matches("[0-9]+")) {

				mins = Integer.parseInt((toValidate.substring(3)));
			}
			if (mins < 60 && mins >= 0 && hours < 24 && hours >= 0) {
				isValid = true;

			}
		}
		return isValid;
	}

	/**
	 * V only contains numbers determines if a string only contains numbers.
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if successful
	 */
	public boolean vOnlyContainsNumbers(String toValidate) {
		isValid = false;
		if (toValidate.matches("[0-9]+")) {
			isValid = true;
		}
		return isValid;
	}

}
