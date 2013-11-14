package sprakproj;

import info.bliki.wiki.dump.WikiArticle;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BornMatcher implements PossibleMatch {
	private Database db;
	
	private Pattern bornPattern;
	private Pattern yearPattern;
	private Pattern datePattern;
	private Pattern datePattern2;
	private Pattern datePattern3;
	
	public LinkedList<String> res;
	
	public BornMatcher(){
		String stringBornpattern = "[f|F][ö|Ö][d|D].{1,15}\\s*";
		bornPattern = Pattern.compile(stringBornpattern);		
	}	
	
	public BornMatcher(boolean matchWasFound){
		db = Database.getInstance();
		initRegex();
	}
	
	private void initRegex(){
		String stringBornpattern = "[f|F][ö|Ö][d|D].{1,15}\\s*";
		String stringYearPattern = "(\\d{1,4})";
		String stringDatePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})\\D{1,30}(\\d{1,4})";
		String stringDatePattern2 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";
		String stringDatePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";

		bornPattern = Pattern.compile(stringBornpattern);
		yearPattern = Pattern.compile(stringYearPattern);
		datePattern = Pattern.compile(stringDatePattern);
		datePattern2 = Pattern.compile(stringDatePattern2);
		datePattern3 = Pattern.compile(stringDatePattern3);
	}
	
	
	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = bornPattern.matcher(wikiText);
		return m.find();
	}

	@Override
	public void saveStringToDb(WikiArticle page) {
		Matcher m = bornPattern.matcher(page.getText());
		while (m.find()) {
			String dateS = m.group(1);
			Matcher ym = yearPattern.matcher(dateS);
			Matcher dm = datePattern.matcher(dateS);
			Matcher dm2 = datePattern2.matcher(dateS);
			Matcher dm3 = datePattern3.matcher(dateS);
			if (dm2.find()) {
				res.add(page.getTitle() + dm2.group(1));
				String date = dm2.group(1) + "-" + dm2.group(2) + "-"
						+ dm2.group(3);
				
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						date);
				
			} else if (dm3.find()) {
				res.add(page.getTitle() + dm3.group(1));
				String date = dm3.group(1) + "-" + dm3.group(2) + "-"
						+ dm3.group(3);
				
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						date);
				
			} else if (dm.find()) {

				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					res.add(page.getTitle() + m.group(1));
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);

					db.insertTriple(page.getTitle().replaceAll(" ", "_"),
							"född", date);
					

				}

			} else if (ym.find()) {
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						ym.group(1));


			}
			else{
				if(!dateS.equals("==")){
					System.out.println(dateS);					
				}
			}

		}
		
	}
	
	
	private int convertMonthStringToNbr(String s) {
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

}
