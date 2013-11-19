package matcher;

import java.util.regex.Pattern;

import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.preprocessor.Template;
import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;

import de.fau.cs.osr.ptk.common.ast.AstNode;
import de.fau.cs.osr.ptk.common.ast.NodeList;
import de.fau.cs.osr.ptk.common.ast.StringContentNode;

import sprakproj.TextConverter;

public class DateConverter extends TextConverter {
	private StringBuilder dateStringBuilder;
	private String pageTitle;

	public DateConverter(SimpleWikiConfiguration config, int wrapCol,
			String pageTitle) {
		super(config, wrapCol, pageTitle);
		
		dateStringBuilder = new StringBuilder();
		this.pageTitle = pageTitle;
	}

	public void visit(Template n)
	{
		if(pageTitle.equals("Agnes Carlsson")){
			System.out.println("kommer in");
		}
		if(pageTitle.equals("Agnes_Carlsson")){
			System.out.println("kommer in");
		}
		
		visit(n.getArgs());
		insertTriple();
		
		if(pageTitle.equals("Agnes Carlsson")){
			System.out.println("går ut");
		}
		if(pageTitle.equals("Agnes_Carlsson")){
			System.out.println("går ut");
		}
	}
	
	public void visit(TemplateArgument n)
	{
		dateStringBuilder.append(getText(n.getValue()));
		dateStringBuilder.append("-");
	}	
	
	
	
	
	
	private void insertTriple() {
		BornDateMatcher bornDateMatcher = new BornDateMatcher();
		bornDateMatcher.saveDateConvertedString(pageTitle, dateStringBuilder.toString());
		
		if(pageTitle.equals("Agnes_Carlsson")){
			System.out.println("datum hittat=\t" + dateStringBuilder.toString());
		}
		
		
		//Pattern datePattern4;
		//String stringDatePatter43 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";
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
	
	
}
