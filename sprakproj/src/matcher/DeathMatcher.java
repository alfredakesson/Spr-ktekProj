package matcher;

import java.util.LinkedList;
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean foundPattern(String wikiText) {
		// TODO Auto-generated method stub
		return false;
	}

}
