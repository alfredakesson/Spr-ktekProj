package countOccurencesOfTypes;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;

import javax.xml.bind.JAXBException;

import org.sweble.wikitext.engine.CompiledPage;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.engine.Page;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;
import org.xml.sax.SAXException;

public class Sweble implements IArticleFilter {

	public static void main(String[] args) {
		String bz2Filename = "../../svwiki-20131101-pages-articles.xml.bz2";
		// System.out.println("hej");
		Sweble handler = new Sweble();
		try {
			WikiXMLParser wxp = new WikiXMLParser(bz2Filename, handler);
			wxp.parse();
		} catch (Exception e) {
			e.printStackTrace();
			// for (String theRes : handler.res) {
			// System.out.println(theRes);
			//
			// }
		}

	}

	private int num;

	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {

		SimpleWikiConfiguration config = null;
		try {
			config = new SimpleWikiConfiguration(
					"classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		final int wrapCol = 80;

		// Instantiate a compiler for wiki pages
		Compiler compiler = new Compiler(config);

		// Retrieve a page
		PageTitle pageTitle = null;
		try {
			pageTitle = PageTitle.make(config, page.getTitle());
		} catch (LinkTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		PageId pageId = new PageId(pageTitle, -1);

		String wikitext = page.getText();

		// Compile the retrieved page
		CompiledPage cp = null;
		try {
			cp = compiler.postprocess(pageId, wikitext, null);
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		// Render the compiled page as HTML

		Page cccp = cp.getPage();
		TypeCounter tc = new TypeCounter(config, wrapCol);
		tc.go(cccp);
		// if (num > 10000) {
		// throw new SAXException();
		// }
		System.out.println(num);
		num++;

		// HtmlPrinter p = new HtmlPrinter(w, pageTitle.getFullTitle());
		// p.setCssResource("/org/sweble/wikitext/engine/utils/HtmlPrinter.css",
		// "");
		// p.setStandaloneHtml(true, "");
		// p.go(cp.getPage());
		// System.out.println(w.toString());
		// throw new SAXException();
	}
	

}
