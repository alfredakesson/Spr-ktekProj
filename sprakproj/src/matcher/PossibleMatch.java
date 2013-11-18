package matcher;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;




public interface PossibleMatch {

	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument templateArgument);
	boolean foundPattern(String wikiText);
}