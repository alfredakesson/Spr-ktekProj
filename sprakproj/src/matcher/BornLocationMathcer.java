package matcher;

import info.bliki.wiki.dump.WikiArticle;

public class BornLocationMathcer implements PossibleMatch {

	
/*  LOCATION
 * födelseland
 * födelseplats
 * födelsestad
 * födelseort
 * född_plats
 */ 
	
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
