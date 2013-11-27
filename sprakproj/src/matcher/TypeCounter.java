package matcher;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

import sprakproj.Database;

public class TypeCounter implements PossibleMatch {
	Database db;
	
	public TypeCounter(){
		db = Database.getInstance();
	}

	@Override
	public void saveStringToDb(String wikiName, String wikiValue,
			String pageTitle, TemplateArgument templateArgument) {
			db.insertTriple(wikiName.trim(), pageTitle, wikiValue, "typeCounter");
			
		

	}

	@Override
	public boolean foundPattern(String wikiText) {
		// TODO Auto-generated method stub
		return true;
	}

}
