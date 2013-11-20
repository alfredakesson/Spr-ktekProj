package matcher;

import java.util.regex.Pattern;

public abstract class DateMatcher {
	protected Pattern yearPattern;
	protected Pattern datePattern;
	protected Pattern datePattern2;
	protected Pattern datePattern3;
	
	
	public DateMatcher(){
		initRegex();
	}
	
	private void initRegex(){

		String stringYearPattern = "(\\d{1,4})";
		String stringDatePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})\\D{1,30}(\\d{1,4})";
		String stringDatePattern2 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";
		String stringDatePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		

		
		yearPattern = Pattern.compile(stringYearPattern);
		datePattern = Pattern.compile(stringDatePattern);
		datePattern2 = Pattern.compile(stringDatePattern2);
		datePattern3 = Pattern.compile(stringDatePattern3);
	}
	
	
	protected int convertMonthStringToNbr(String s) {
		s = s.toLowerCase();
		if (s.equals("januari")) {
			return 1;
		} else if (s.equals("februari")) {
			return 2;
		} else if (s.equals("mars")) {
			return 3;
		} else if (s.equals("april")) {
			return 4;
		} else if (s.equals("maj")) {
			return 5;
		} else if (s.equals("juni")) {
			return 6;
		} else if (s.equals("juli")) {
			return 7;
		} else if (s.equals("augusti")) {
			return 8;
		} else if (s.equals("september")) {
			return 9;
		} else if (s.equals("oktober")) {
			return 10;
		} else if (s.equals("november")) {
			return 11;
		} else if (s.equals("december")) {
			return 12;
		} else {
			return -1;
		}
	}
		/*
		 * OLD PATTERN: 
	 	String stringYearPattern = "(\\d{1,4})";
		String stringDatePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})\\D{1,30}(\\d{1,4})";
		String stringDatePattern2 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";
		String stringDatePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		*/
	 
}
