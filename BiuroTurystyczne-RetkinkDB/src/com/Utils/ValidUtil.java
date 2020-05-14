package com.Utils;

public class ValidUtil {
	public static boolean isDoubleInstance(String line) {

		try {
			Double.parseDouble(line);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public static boolean isIntInstance(String line) {
		try {
			Integer.parseInt(line);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

}
