package matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;


import sprakproj.Database;

public class BornLocationMatcher implements PossibleMatch {

	private Database db;
	private Pattern landPattern;
	private Pattern getLandPattern;
	private Pattern getLandWithoutBracketPattern;

	public BornLocationMatcher() {
		db = Database.getInstance();
		String stringLandpattern = "(^|\\W)(födelseplats|födelseort|födelsestad|födelseland|född_plats)(\\W|$)";
		String getLand = "\\[\\[(.+?)(\\]\\]|\\|)";
		String getLandWithoutBracket = "[A-ZÅÄÖØÆ]\\w+";//matcha åäö!!!och andra danska ön osv...
		landPattern = Pattern.compile(stringLandpattern);
		getLandPattern = Pattern.compile(getLand);
		getLandWithoutBracketPattern = Pattern.compile(getLandWithoutBracket);
	}

	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = landPattern.matcher(wikiText);
		return m.find();
	}

	@Override
	public void saveStringToDb(String wikiName, String wikiValue,
			String pageTitle, TemplateArgument tArg) {
		wikiValue = wikiValue.trim();
		boolean found = true;
		System.out.println("------------------------");
		//System.out.println(wikiName);
		//System.out.println(wikiValue);
		Matcher m = getLandPattern.matcher(wikiValue);
		while (m.find()) {
			found = false;
			//System.out.println(m.group(1));
		}
		if (found) {
			Matcher m2 = getLandWithoutBracketPattern.matcher(wikiValue);
int i = 0;
			while (m2.find()) {//ändrade tilll while här
i++;
				System.out.println(m2.group(0));
				if(i>1){
					System.out.println("wikiname=\t"+wikiName);//okej vårat ast-träd failar här!
					System.out.println("wikival=\t"+wikiValue);
					System.out.println(pageTitle);	//  Benedikta Ebbesdotter      
													//	födelseplats = Knardrup på Själland i Danmark 
													//	HÄR BLIR DET FEL!!!!
					System.exit(1);
				}
			}
		}
		System.out.println(pageTitle);
		//System.out.println("------------------------");
	
	}

	@Override
	public void insertError(String pageTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean saveDateConvertedString(String pageTitle, String dateInput,
			String type) {
		// TODO Auto-generated method stub
		return false;
	}

}
