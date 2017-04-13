package com.meidiandian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	/**
	 * make the date into the except time
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {

		String defaultTime = "2017-1-1 12:12:12";

		if (date != null && !StringUtils.isEmpty(format)) {

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String time = sdf.format(date);

			return time;
		} else {
			return defaultTime;
		}

	}
	
	/**
	 * 将字符串形式的日期转换为Date
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date formatToDate(String dateString) throws ParseException {
		
		Date returnDate = new Date();
		
		if (!StringUtils.isEmpty(dateString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			returnDate = sdf.parse(dateString);
		}
		
		return returnDate;
	}
}
