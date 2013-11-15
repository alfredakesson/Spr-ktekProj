package matcher;

import info.bliki.wiki.dump.WikiArticle;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sprakproj.Database;


public class BornMatcher extends DateMatcher implements PossibleMatch{
	private Database db;
	private Pattern bornPattern;

	
	public LinkedList<String> res;
	
	public BornMatcher(){
		super();
		db = Database.getInstance();
		String stringBornpattern = "[f|F][ö|Ö][d|D].{1,15}\\s*";
		bornPattern = Pattern.compile(stringBornpattern);
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

}
