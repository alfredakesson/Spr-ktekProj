package matcher;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;




public interface PossibleMatch {

	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument templateArgument) throws FileNotFoundException, JAXBException;
	boolean foundPattern(String wikiText);
}