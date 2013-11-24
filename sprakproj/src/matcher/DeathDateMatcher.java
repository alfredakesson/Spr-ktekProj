package matcher;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBException;
import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

public class DeathDateMatcher extends DateMatcher implements PossibleMatch {
	private Pattern deathPattern;
	
	
	public DeathDateMatcher(){
		super();
		String stringDeathpattern = "(^|\\W)([d|D]öd|[D|d]öd_datum|[D|d]ödsdatum|[D|d]öd_år|[D|d]datum|[D|d]ödsdag)(\\W|$)";
		//String stringDeathpattern = "[d|D][ö|Ö][d|D].{1,15}\\s*"; //MAYBE THE OLD PATTTERN WAS BETTER?!
		deathPattern = Pattern.compile(stringDeathpattern);
	}
	
	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = deathPattern.matcher(wikiText);
		return m.find();
	}
	
	@Override
	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument n) throws FileNotFoundException, JAXBException {		
			Matcher ym = yearPattern.matcher(wikiValue);
			Matcher dm = datePattern.matcher(wikiValue);
			Matcher dm2 = datePattern2.matcher(wikiValue);
			Matcher dm3 = datePattern3.matcher(wikiValue);
			
			String type = "deathDate";
			
			if (dm2.find()) {
				String date = dm2.group(1) + "-" + dm2.group(2) + "-" + dm2.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
			
			} else if (dm3.find()) {
				String date = dm3.group(1) + "-" + dm3.group(2) + "-" + dm3.group(3);
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
			
				
			} else if (dm.find()) {
				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
					db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", date, type);
				
				}

			} else if (ym.find()) {
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "deathDate", ym.group(1), type);
	
			}
			else{
				DateConverter dc = new DateConverter(null, 80, pageTitle, "deathDate");
				dc.start(n);
			}
		
	}

}
