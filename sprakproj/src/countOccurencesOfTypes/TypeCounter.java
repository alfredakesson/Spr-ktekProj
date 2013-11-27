package countOccurencesOfTypes;

/**
 * Copyright 2011 The Open Source Research Group,
 *                University of Erlangen-Nürnberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.sweble.wikitext.engine.Page;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.utils.EntityReferences;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;
import org.sweble.wikitext.lazy.encval.IllegalCodePoint;
import org.sweble.wikitext.lazy.parser.Bold;
import org.sweble.wikitext.lazy.parser.ExternalLink;
import org.sweble.wikitext.lazy.parser.HorizontalRule;
import org.sweble.wikitext.lazy.parser.ImageLink;
import org.sweble.wikitext.lazy.parser.InternalLink;
import org.sweble.wikitext.lazy.parser.Italics;
import org.sweble.wikitext.lazy.parser.MagicWord;
import org.sweble.wikitext.lazy.parser.Paragraph;
import org.sweble.wikitext.lazy.parser.Section;
import org.sweble.wikitext.lazy.parser.Url;
import org.sweble.wikitext.lazy.parser.Whitespace;
import org.sweble.wikitext.lazy.parser.XmlElement;
import org.sweble.wikitext.lazy.preprocessor.TagExtension;
import org.sweble.wikitext.lazy.preprocessor.Template;
import org.sweble.wikitext.lazy.preprocessor.TemplateArgument;
import org.sweble.wikitext.lazy.preprocessor.TemplateParameter;
import org.sweble.wikitext.lazy.preprocessor.XmlComment;
import org.sweble.wikitext.lazy.utils.XmlCharRef;
import org.sweble.wikitext.lazy.utils.XmlEntityRef;

import sprakproj.Database;
import de.fau.cs.osr.ptk.common.AstVisitor;
import de.fau.cs.osr.ptk.common.ast.AstNode;
import de.fau.cs.osr.ptk.common.ast.NodeList;
import de.fau.cs.osr.ptk.common.ast.StringContentNode;
import de.fau.cs.osr.ptk.common.ast.Text;


/**
 * A visitor to convert an article AST into a pure text representation. To
 * better understand the visitor pattern as implemented by the Visitor class,
 * please take a look at the following resources:
 * <ul>
 * <li>{@link http://en.wikipedia.org/wiki/Visitor_pattern} (classic pattern)</li>
 * <li>{@link http://www.javaworld.com/javaworld/javatips/jw-javatip98.html}
 * (the version we use here)</li>
 * </ul>
 * 
 * The methods needed to descend into an AST and visit the children of a given
 * node <code>n</code> are
 * <ul>
 * <li><code>dispatch(n)</code> - visit node <code>n</code>,</li>
 * <li><code>iterate(n)</code> - visit the <b>children</b> of node
 * <code>n</code>,</li>
 * <li><code>map(n)</code> - visit the <b>children</b> of node <code>n</code>
 * and gather the return values of the <code>visit()</code> calls in a list,</li>
 * <li><code>mapInPlace(n)</code> - visit the <b>children</b> of node
 * <code>n</code> and replace each child node <code>c</code> with the return
 * value of the call to <code>visit(c)</code>.</li>
 * </ul>
 */
public class TypeCounter
        extends
            AstVisitor
{
	private final SimpleWikiConfiguration config;
	private StringBuilder sb;
	

	private Database db = Database.getInstance();
	
	// =========================================================================
	
	public TypeCounter(SimpleWikiConfiguration config, int wrapCol)
	{
		this.config = config;
	}


	@Override
	protected boolean before(AstNode node)
	{
		// This method is called by go() before visitation starts
		sb = new StringBuilder();
		return super.before(node);
	}
	
	@Override
	protected Object after(AstNode node, Object result)
	{
		
		// This method is called by go() after visitation has finished
		// The return value will be passed to go() which passes it to the caller
		return sb.toString();
	}
	
	// =========================================================================
	
	public void visit(AstNode n)
	{
		// Fallback for all nodes that are not explicitly handled below

	}
	
	public void visit(NodeList n)
	{
		iterate(n);
	}
	
	public void visit(Page p)
	{
		iterate(p.getContent());
	}
	
	public void visit(Text text)
	{
		//System.out.println(text.getContent());
	}
	
	public void visit(Whitespace w)
	{
	}
	
	public void visit(Bold b)
	{
		iterate(b.getContent());
	}
	
	public void visit(Italics i)
	{
		iterate(i.getContent());
	}
	
	public void visit(XmlCharRef cr)
	{

	}
	
	public void visit(XmlEntityRef er)
	{
		String ch = EntityReferences.resolve(er.getName());
		if (ch == null)
		{

		}
		else
		{

		}
	}
	
	public void visit(Url url)
	{

	}
	
	public void visit(ExternalLink link)
	{

	}
	
	public void visit(InternalLink link)
	{
		try
		{
			PageTitle page = PageTitle.make(config, link.getTarget());
			if (page.getNamespace().equals(config.getNamespace("Category")))
				return;
		}
		catch (LinkTargetException e)
		{
		}
		

		if (link.getTitle().getContent() == null
		        || link.getTitle().getContent().isEmpty())
		{

		}
		else
		{
			iterate(link.getTitle());
		}

	}
	
	public void visit(Section s)
	{

		
		iterate(s.getTitle());
		
		
		iterate(s.getBody());
		
	
	}
	
	public void visit(Paragraph p)
	{
		iterate(p.getContent());

	}
	
	public void visit(HorizontalRule hr)
	{
		
	}
	
	public void visit(XmlElement e)
	{
		if (e.getName().equalsIgnoreCase("br"))
		{
	
		}
		else
		{
			iterate(e.getBody());
		}
	}
	
	// =========================================================================
	// Stuff we want to hide
	
	public void visit(ImageLink n)
	{
	}
	
	public void visit(IllegalCodePoint n)
	{
	}
	
	public void visit(XmlComment n)
	{
	}
	
	public void visit(Template n)
	{
		visit(n.getArgs());
	}
	
	
	
	public void visit(TemplateArgument n)
	{

//TOM	
		//System.out.println(n.getValue());
		//System.out.println("hej");
		String nam = getText(n.getName());
		String val = getText(n.getValue());
		
		db.insertTTriple(nam.trim(), "un", val.trim());
		
		//System.out.println(getText(n.getName()));
		//System.out.println(getText(n.getValue()));
		//visit(n.getValue());
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

	public void visit(TemplateParameter n)
	{

	
		System.out.println("HEJ");
	}
	
	public void visit(TagExtension n)
	{
	}
	
	public void visit(MagicWord n)
	{
	}
	
	// =========================================================================
	
	
}

