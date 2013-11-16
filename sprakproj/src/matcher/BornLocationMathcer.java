package matcher;

import info.bliki.wiki.dump.WikiArticle;

public class BornLocationMathcer implements PossibleMatch {

	public BornLocationMathcer(){
		String stringBornLocationpattern1 = "[f|F]ödelseplats";
		String stringBornLocationpattern2 = "[f|F]ödelseland";
		String stringBornLocationpattern3 = "[f|F]ödelsestad";
		String stringBornLocationpattern4 = "[f|F]ödelseort";
		String stringBornLocationpattern5 = "[f|F]ödd_plats";
		
	}
	
/*  LOCATION
 * födelseland
 * födelseplats
 * födelsestad
 * födelseort
 * född_plats
 */ 
	
	@Override
	public void saveStringToDb(String wikiText, String wikiValue, String pageTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean foundPattern(String wikiText) {
		// TODO Auto-generated method stub
		return false;
	}

}
