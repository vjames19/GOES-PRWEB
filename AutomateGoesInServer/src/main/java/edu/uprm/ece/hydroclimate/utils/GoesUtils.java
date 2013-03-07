package edu.uprm.ece.hydroclimate.utils;

import java.util.Date;

public class GoesUtils {

	private static final int MILLIS = 1000;

	public static long convertSecondsToMillis(long seconds) {
		return MILLIS * seconds;
	}

	public static String stringFormatTime(String str, Date date) {
		// Had to escape the $ because of some weird thing with the matcher
		str = str.replaceAll("%t", "%1\\$t");
		return String.format(str, date);
	}

}
