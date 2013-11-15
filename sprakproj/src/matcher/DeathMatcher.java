package matcher;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sprakproj.Database;

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
	public void saveStringToDb(String wikiText, String wikiValue, String pageTitle) {
	}

	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = deathPattern.matcher(wikiText);
		return m.find();
	}

}
