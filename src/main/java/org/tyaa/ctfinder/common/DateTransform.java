package org.tyaa.ctfinder.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTransform {

	private static DateFormat mDirectFormat =
			new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
	private static DateFormat mReversedFormat =
			new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	
	public static String DirectToReversed(String _dateString) throws ParseException {
		
		Date date = mDirectFormat.parse(_dateString);
		return mReversedFormat.format(date);
	}
	
	public static String ReversedToDirect(String _dateString) throws ParseException {
		
		Date date = mReversedFormat.parse(_dateString);
		return mDirectFormat.format(date);
	}
}
