package com.rubinochat.utility;

import java.util.Calendar;
import java.util.Date;

public class Utility {

	public static String getDateString() {
		StringBuilder dateTime = new StringBuilder();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		if (hour.length() == 1) {
			hour = "0".concat(hour);
		}
		String minute = String.valueOf(calendar.get(Calendar.MINUTE));
		if (minute.length() == 1) {
			minute = "0".concat(minute);
		}
		return dateTime.append(hour).append(":").append(minute).toString();
	}
	
}
