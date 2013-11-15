package matcher;

import info.bliki.wiki.dump.WikiArticle;

public interface PossibleMatch {

	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle);
	boolean foundPattern(String wikiText);
}