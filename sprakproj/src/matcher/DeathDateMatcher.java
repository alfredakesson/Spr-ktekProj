package matcher;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

import sprakproj.Database;

public class DeathDateMatcher extends DateMatcher implements PossibleMatch {
	private Database db;
	private Pattern deathPattern;
	public LinkedList<String> res;
	
	
	public DeathDateMatcher(){
		super();
		db = Database.getInstance();
		String stringDeathpattern = "[d|D][ö|Ö][d|D].{1,15}\\s*";
		deathPattern = Pattern.compile(stringDeathpattern);
	}
	
	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = deathPattern.matcher(wikiText);
		return m.find();
	}
	
	@Override
	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument tArg) {
		
			Matcher ym = yearPattern.matcher(wikiValue);
			Matcher dm = datePattern.matcher(wikiValue);
			Matcher dm2 = datePattern2.matcher(wikiValue);
			Matcher dm3 = datePattern3.matcher(wikiValue);
			
			String type = "deathDate";
			
			if (dm2.find()) {
				String date = dm2.group(1) + "-" + dm2.group(2) + "-" + dm2.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
			
			} else if (dm3.find()) {
				String date = dm3.group(1) + "-" + dm3.group(2) + "-" + dm3.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
			
				
			} else if (dm.find()) {
				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
					db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
				
				}

			} else if (ym.find()) {
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", ym.group(1), type);
	
			}
		
	}

}
