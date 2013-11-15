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
}
