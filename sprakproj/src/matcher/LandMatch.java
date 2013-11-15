package matcher;

import java.util.regex.Pattern;

import sprakproj.Database;
import info.bliki.wiki.dump.WikiArticle;

public class LandMatch implements PossibleMatch {
	
	private Database db;
	private Pattern landPattern;

	public LandMatch() {
		db = Database.getInstance();
		String stringBornpattern = "land\\s*";
		landPattern = Pattern.compile(stringBornpattern);	}

	@Override
	public void saveStringToDb(WikiArticle page) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean foundPattern(String wikiText) {
		// TODO Auto-generated method stub
		return false;
	}

}
