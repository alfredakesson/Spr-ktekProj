package matcher;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.preprocessor.Template;
import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

import sprakproj.TextConverter;
import de.fau.cs.osr.ptk.common.ast.AstNode;
import de.fau.cs.osr.ptk.common.ast.NodeList;
import de.fau.cs.osr.ptk.common.ast.StringContentNode;

public class DateConverter extends TextConverter {
	private StringBuilder dateStringBuilder;
	private String pageTitle, savedString, type;
	
	PossibleMatch bornDateMatcher;

	public DateConverter(SimpleWikiConfiguration config, int wrapCol,
			String pageTitle, String type) {
		super(config, wrapCol, pageTitle);
		
		dateStringBuilder = new StringBuilder();
		this.pageTitle = pageTitle;
		this.type = type;
		if(type.equals("bornDate")){
			bornDateMatcher = new BornDateMatcher();			
		}
		else {
			bornDateMatcher = new DeathDateMatcher();
		}
	}

	
	public void start(TemplateArgument n) {
		
		saveNodeListAsString(n);
		try{
			visit(n.getValue());
		} catch(Exception e){
			//För att kunna parsa texten ändå fångar vi dryga exceptions här!
			//Eftersom ja inte orkar kolla på alla jävla fel så låter jag den bara fånga skiten
		}
		if(!insertTriple(dateStringBuilder.toString())){
			findDateInSavedString();
		}
		
	}
	
	
	@Override
	public void visit(NodeList n)
	{
		iterate(n);
	}

	
	@Override
	public void visit(Template n)
	{
		visit(n.getArgs());
	}
	

	private void findDateInSavedString() {
		ArrayList<String> tmpRes = new ArrayList<String>();
		String stringTextPattern = "\\[Text\\((.*?)\\)\\]";
		String stringYearPattern = "\"(\\d{1,4})\"";
		
		Pattern textPattern = Pattern.compile(stringTextPattern);
		Pattern yearPattern = Pattern.compile(stringYearPattern);
		
		Matcher mText =  textPattern.matcher(savedString);
		Matcher mYear;
		
		while(mText.find()){
			String foundText = mText.group(1);
			tmpRes.add(foundText);
		}
		boolean append = false;
		StringBuilder dateBuilder = new StringBuilder(); 
		for(String s : tmpRes){
			
			mYear = yearPattern.matcher(s);
			if(mYear.find()){
				append = true; 
			}
			if(append){
				dateBuilder.append(mYear.group(1));
				dateBuilder.append("-");	
			}
		}
		if(!insertTriple(dateBuilder.toString())){
			bornDateMatcher.insertError(pageTitle);
		}

	}


	@Override
	public void visit(TemplateArgument n)
	{
		dateStringBuilder.append(getText(n.getValue()));
		dateStringBuilder.append("-");
	}	
	

	
	private boolean insertTriple(String dateInput) {
		return bornDateMatcher.saveDateConvertedString(pageTitle, dateInput, type);
	}
	
	private String getText(NodeList name) {
		StringBuilder stb = new StringBuilder();
		for (AstNode astNode : name) {
			if(astNode.isNodeType(AstNode.NT_TEXT)){
				StringContentNode stringContentNode = (StringContentNode) astNode;
				stb.append(stringContentNode.getContent());
			}
		}		
		return stb.toString();
	}

	private void saveNodeListAsString(AstNode node){
		 StringBuilder emergencyBuilder = new StringBuilder();
		 if (node != null)
	     {
	             for (AstNode n : node){
	            	 emergencyBuilder.append(n);
	             }
	                  
	     }
	     savedString = emergencyBuilder.toString();
	}
	
	
}
