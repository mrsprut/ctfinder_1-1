package org.tyaa.ctfinder.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
	
	public static String DirectToDiff(String _dateStringFrom, String _dateStringTo) throws ParseException {
		
		Date dateFrom = mDirectFormat.parse(_dateStringFrom);
		Date dateTo = mDirectFormat.parse(_dateStringTo);
		
		LocalDate localDateFrom =
				dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDateTo =
				dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		/*Duration duration = Duration.between(localDateTo, localDateFrom);
	    Long diff = Math.abs(duration.toDays());*/
		Integer diff = Period.between(localDateFrom, localDateTo).getDays();
	    
		return diff.toString();
	}
	
	public static String ReversedToDiff(String _dateStringFrom, String _dateStringTo) throws ParseException {
		
		Date dateFrom = mReversedFormat.parse(_dateStringFrom);
		Date dateTo = mReversedFormat.parse(_dateStringTo);
		
		LocalDate localDateFrom =
				dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDateTo =
				dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		//Duration duration = Duration.between(localDateTo, localDateFrom);
	    //Long diff = Math.abs(duration.toDays());
		Integer diff = Period.between(localDateFrom, localDateTo).getDays();
	    
		return diff.toString();
	}
}
