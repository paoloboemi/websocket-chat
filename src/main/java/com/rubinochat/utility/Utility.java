package com.rubinochat.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	
	private static SimpleDateFormat hourMinuteDateFormat = new SimpleDateFormat("HH:mm");

	public static String getDateString() {
		Date date = new Date();
		return hourMinuteDateFormat.format(date);
	}
	
}
