package matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sprakproj.Database;

public abstract class DateMatcher {
	protected Pattern yearPattern;
	protected Pattern datePattern;
	protected Pattern datePattern2;
	protected Pattern datePattern3;
	protected Database db;
	
	public DateMatcher(){
		initRegex();
		db = Database.getInstance();
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
	
	public boolean saveDateConvertedString(String pageTitle, String dateString, String type) {
		Matcher ym = yearPattern.matcher(dateString);
		Matcher dm = datePattern.matcher(dateString);
		Matcher dm2 = datePattern2.matcher(dateString);
		Matcher dm3 = datePattern3.matcher(dateString);

		
		if (dm2.find()) {
			String date = dm2.group(1) + "-" + dm2.group(2) + "-" + dm2.group(3);
			db.insertTriple(pageTitle.replaceAll(" ", "_"), type, date, type);
			return true;
			
		} else if (dm3.find()) {
			String date = dm3.group(1) + "-" + dm3.group(2) + "-" + dm3.group(3);
			db.insertTriple(pageTitle.replaceAll(" ", "_"), type, date, type);
			return true;
			
		} else if (dm.find()) {
			int manad = convertMonthStringToNbr(dm.group(2));
			if (manad > 0) {
				String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), type, date, type);
				return true;
			}

		} else if (ym.find()) {
			db.insertTriple(pageTitle.replaceAll(" ", "_"), type, ym.group(1), type);
			return true;
		
		} else{
			//dateNotInserted
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "dateNotInserted", type, "dateNotInserted");
			
		}


		return false;
	}
		/*
		 * OLD PATTERN: 
	 	String stringYearPattern = "(\\d{1,4})";
		String stringDatePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})\\D{1,30}(\\d{1,4})";
		String stringDatePattern2 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";
		String stringDatePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		*/
	 
}
