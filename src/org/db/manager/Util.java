package org.db.manager;

public class Util {
	public static boolean startsWith(String str, String[] arr) {
		for (String prefix : arr) {
			if (str.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	public static String dashs(int count) {
		return repeateChar('-', count);
	}

	public static String repeateChar(char ch, int count) {
		char[] chArr = new char[count];
		for (int i = 0; i < count; i++) {
			chArr[i] = ch;
		}

		return new String(chArr);
	}
}
