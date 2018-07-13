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
	
	public static String DirectToReversed(String _dateString) throws ParseException {
		
		DateFormat directFormat =
				new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		DateFormat reversedFormat =
				new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = directFormat.parse(_dateString);
		return reversedFormat.format(date);
	}
	
	public static String ReversedToDirect(String _dateString) throws ParseException {
		
		DateFormat directFormat =
				new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		DateFormat reversedFormat =
				new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = reversedFormat.parse(_dateString);
		return directFormat.format(date);
	}
	
	public static String DirectToDiff(String _dateStringFrom, String _dateStringTo) throws ParseException {
		
		DateFormat directFormat =
				new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date dateFrom = directFormat.parse(_dateStringFrom);
		Date dateTo = directFormat.parse(_dateStringTo);
		
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
		
		DateFormat reversedFormat =
				new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateFrom = reversedFormat.parse(_dateStringFrom);
		Date dateTo = reversedFormat.parse(_dateStringTo);
		
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
