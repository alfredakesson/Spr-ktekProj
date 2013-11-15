package sprakproj;

import info.bliki.wiki.dump.WikiArticle;

public interface PossibleMatch {

	public void saveStringToDb(WikiArticle page);
	boolean foundPattern(String wikiText);
}