package matcher;


public interface PossibleMatch {

	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle);
	boolean foundPattern(String wikiText);
}