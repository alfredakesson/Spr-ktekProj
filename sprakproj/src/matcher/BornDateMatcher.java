package matcher;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;


public class BornDateMatcher extends DateMatcher implements PossibleMatch{
	private Pattern bornPattern;

	public BornDateMatcher(){
		super();
		String stringBornpattern = "(^|\\W)([f|F]ödelsedatum|[f|F]ödelseår|[f|F]ödd|[f|F]ödd_datum|[f|F]ödd_år|[F|f]datum|[f|F]öd)(\\W|$)";
		bornPattern = Pattern.compile(stringBornpattern);
	}

	@Override
	public boolean foundPattern(String wikiText) {
		Matcher m = bornPattern.matcher(wikiText);
		return m.find();
	}

	@Override
	public void saveStringToDb(String wikiName, String wikiValue, String pageTitle, TemplateArgument n) throws FileNotFoundException, JAXBException {
		
			Matcher ym = yearPattern.matcher(wikiValue);
			Matcher dm = datePattern.matcher(wikiValue);
			Matcher dm2 = datePattern2.matcher(wikiValue);
			Matcher dm3 = datePattern3.matcher(wikiValue);
			
			String type = "bornDate";
			
			//TMP
			Matcher bcMatcher;
			Matcher acMatcher;
			bcMatcher = bcPattern.matcher(wikiValue);
			acMatcher = acPattern.matcher(wikiValue);
			
			try{
				
			if(bcMatcher.find()){
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "kristus", bcMatcher.group(0), "kristus");		
			}
			if(acMatcher.find()){
				db.insertTriple(pageTitle.replaceAll(" ", "_"), "kristus", acMatcher.group(0), "kristus");
			}
			} catch(Exception e){
				
			}
			//END OF TMP
			
			
			
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
				DateConverter dc = new DateConverter(null, 80, pageTitle, "bornDate");
				dc.start(n);
			}
	}
	
}
