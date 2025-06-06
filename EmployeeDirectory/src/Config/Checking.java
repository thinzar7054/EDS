package Config;

import java.util.Vector;

public class Checking {
	// Method to check if a string is empty or contains only whitespaces
	public static boolean isEmptyOrWhitespace(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber != null && phoneNumber.matches("\\d{10}");
	}

	public static boolean IsAllDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean IsContain(String s, Vector str) {
		for (int i = 0; i < str.size(); i++) {
			if (s.equals((String) str.elementAt(i))) {
				return true;
			}

		}
		return false;
	}

	public static boolean IsValidName(String input) {
		return input != null && input.matches("[a-zA-Z\\s]+");
	}

	public static boolean IsEmailFormat(String str) {
		boolean b = false;
		int dot = str.lastIndexOf(".");
		int at = str.indexOf("@");
		if ((dot < 0) || (at < 0) || (str.indexOf(" ") > 0)) {
			return b;
		}
		String st = str.substring(0, at);
		String st1 = str.substring(dot + 1);
		if (!st.trim().equals("") && (st1.equals("com"))) {
			b = true;
			return b;
		} else {
			return b;
		}

	}

	public static boolean isPhoneNo(String phone) {
		if (phone == null) {
			return false;
		}
		String phoneRegex = "^09\\d{9}$";
		return phone.matches(phoneRegex);
	}

	public static boolean isValidNRC(String nrc) {

		String nrcPattern = "\\d{1,2}/[A-Za-z]+\\([NA]\\)\\d{6}";

		return nrc.matches(nrcPattern);
	}
}