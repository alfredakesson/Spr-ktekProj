package matcher;

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
		visit(n.getArgs());
		insertTriple();
	}
	
	
	
	private void insertTriple() {
		BornDateMatcher bornDateMatcher = new BornDateMatcher();
		bornDateMatcher.saveDateConvertedString(pageTitle, dateStringBuilder.toString());
	}

	public void visit(TemplateArgument n)
	{
		dateStringBuilder.append(getText(n.getValue()));
		dateStringBuilder.append("-");
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
