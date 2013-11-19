package matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

import de.fau.cs.osr.ptk.common.ast.NodeList;

import sprakproj.Database;


public class BornDateMatcher extends DateMatcher implements PossibleMatch{
	private Database db;
	private Pattern bornPattern;

	public BornDateMatcher(){
		super();
		db = Database.getInstance();
		String stringBornpattern = "(^|\\W)([f|F]ödelsedatum|[f|F]ödelseår|[f|F]ödd|[f|F]ödd_datum|[f|F]ödd_år)(\\W|$)";
		//String stringBornpattern = "[f|F][ö|Ö][d|D].{1,15}\\s*";
		bornPattern = Pattern.compile(stringBornpattern);
	}

	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = bornPattern.matcher(wikiText);
		return m.find();
	}

	@Override
	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument n) {
		
			Matcher ym = yearPattern.matcher(wikiValue);
			Matcher dm = datePattern.matcher(wikiValue);
			Matcher dm2 = datePattern2.matcher(wikiValue);
			Matcher dm3 = datePattern3.matcher(wikiValue);
			
			String type = "bornDate";
			
			if (dm2.find()) {
				String date = dm2.group(1) + "-" + dm2.group(2) + "-" + dm2.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
				
				
			} else if (dm3.find()) {
				String date = dm3.group(1) + "-" + dm3.group(2) + "-" + dm3.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
			
				
			} else if (dm.find()) {
				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
					db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
					
				}

			} else if (ym.find()) {
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", ym.group(1), type);
				
			}else{

				//Agnes Carlsson
				DateConverter dc = new DateConverter(null, 80, pageTitle);
				dc.visit(n.getValue());
			}
	}
	
	public void saveDateConvertedString(String pageTitle, String dateString) {
		Matcher ym = yearPattern.matcher(dateString);
		Matcher dm = datePattern.matcher(dateString);
		Matcher dm2 = datePattern2.matcher(dateString);
		Matcher dm3 = datePattern3.matcher(dateString);

		String type = "bornDate";
		
		if (dm2.find()) {
			String date = dm2.group(1) + "-" + dm2.group(2) + "-" + dm2.group(3);
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
			
		} else if (dm3.find()) {
			String date = dm3.group(1) + "-" + dm3.group(2) + "-" + dm3.group(3);
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
			
		} else if (dm.find()) {
			int manad = convertMonthStringToNbr(dm.group(2));
			if (manad > 0) {
				String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", date, type);
			}

		} else if (ym.find()) {
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "bornDate", ym.group(1), type);
		
		} else{
			//dateNotInserted
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "dateNotInserted", dateString, "dateNotInserted");
		}
		if(dateString.length() > 11 || dateString.length() < 9){
			db.insertTriple(pageTitle.replaceAll(" ", "_"), "dateNotInserted", dateString, "dateNotInserted2");
		}


	}
}
