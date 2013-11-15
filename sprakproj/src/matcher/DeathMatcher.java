package matcher;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sprakproj.Database;
import info.bliki.wiki.dump.WikiArticle;

public class DeathMatcher extends DateMatcher implements PossibleMatch {
	private Database db;
	private Pattern deathPattern;
	public LinkedList<String> res;
	
	
	public DeathMatcher(){
		super();
		db = Database.getInstance();
		String stringDeathpattern = "[d|D][ö|Ö][d|D].{1,15}\\s*";
		deathPattern = Pattern.compile(stringDeathpattern);
	}
	
	@Override
	public void saveStringToDb(WikiArticle page) {
		Matcher m = deathPattern.matcher(page.getText());
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
				
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "död",
						date);
				
			} else if (dm3.find()) {
				res.add(page.getTitle() + dm3.group(1));
				String date = dm3.group(1) + "-" + dm3.group(2) + "-"
						+ dm3.group(3);
				
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "död",
						date);
				
			} else if (dm.find()) {

				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					res.add(page.getTitle() + m.group(1));
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);

					db.insertTriple(page.getTitle().replaceAll(" ", "_"),
							"död", date);
					

				}

			} else if (ym.find()) {
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "död",
						ym.group(1));


			}
			else{
				if(!dateS.equals("==")){
					System.out.println(dateS);					
				}
			}

		}
	

	}

	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = deathPattern.matcher(wikiText);
		return m.find();
	}

}
