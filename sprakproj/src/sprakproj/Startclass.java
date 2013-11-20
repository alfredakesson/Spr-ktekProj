package sprakproj;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;
import org.sweble.wikitext.engine.CompiledPage;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;
import org.xml.sax.SAXException;

public class Startclass implements IArticleFilter {
	private TextConverter p;
	int num = 0;

	
	public Startclass() {
	}
	
	public void runSweble(WikiArticle page, Siteinfo siteinfo) throws FileNotFoundException, IOException, LinkTargetException, CompilerException, JAXBException, SAXException
	{
		int wrapCol = 80;
				
		// Set-up a simple wiki configuration
		SimpleWikiConfiguration config = new SimpleWikiConfiguration(
				        "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
		
		// Instantiate a compiler for wiki pages
		Compiler compiler = new Compiler(config);
		
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, page.getTitle());	
		PageId pageId = new PageId(pageTitle, -1);
		String wikitext = page.getText();
		
		// Compile the retrieved page
		CompiledPage cp = compiler.postprocess(pageId, wikitext, null);
		p = new TextConverter(config, wrapCol, page.getTitle());
		p.go(cp.getPage());
		
		System.out.println(num++);
		
		//		throw new SAXException(); 

	}

	@Override
	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {
		
		try{
			runSweble(page, siteinfo);
		} catch(Exception e){
			//e.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) {
		String bz2Filename = "../../svwiki-20131101-pages-articles.xml.bz2";
		Startclass handler = new Startclass();
		try {
			WikiXMLParser wxp = new WikiXMLParser(bz2Filename, handler);
			wxp.parse();
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}
}
