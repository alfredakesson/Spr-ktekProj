package sprakproj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.sweble.wikitext.engine.CompiledPage;
import org.sweble.wikitext.engine.Compiler;
import org.sweble.wikitext.engine.CompilerException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.utils.HtmlPrinter;
import org.sweble.wikitext.engine.utils.SimpleWikiConfiguration;
import org.sweble.wikitext.lazy.LinkTargetException;


public class testSweble {
	public static void main(String[] args) throws FileNotFoundException, IOException, LinkTargetException, CompilerException, JAXBException
	{
		String path = "test/Simple_page";
		int wrapCol = 80; //VET INTE RIKTIGT VAD DEN HÄR GÖR! 
				
		// Set-up a simple wiki configuration
		SimpleWikiConfiguration config = new SimpleWikiConfiguration(
				        "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
		
		// Instantiate a compiler for wiki pages
		Compiler compiler = new Compiler(config);
		
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, path + ".wikitext");
		
		PageId pageId = new PageId(pageTitle, -1);
		
		String wikitext = FileUtils.readFileToString(new File(path + ".wikitext"));
		
		// Compile the retrieved page
		CompiledPage cp = compiler.postprocess(pageId, wikitext, null);
		
		TextConverter p = new TextConverter(config, wrapCol, null);
		p.go(cp.getPage());
						
	}
		
	public void initSweble() throws FileNotFoundException, IOException, LinkTargetException, CompilerException, JAXBException
	{
		String path = "test/Simple_page";
		int wrapCol = 80; //VET INTE RIKTIGT VAD DEN HÄR GÖR! 
				
		// Set-up a simple wiki configuration
		SimpleWikiConfiguration config = new SimpleWikiConfiguration(
				        "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
		
		// Instantiate a compiler for wiki pages
		Compiler compiler = new Compiler(config);
		
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, path + ".wikitext");
		
		PageId pageId = new PageId(pageTitle, -1);
		
		String wikitext = FileUtils.readFileToString(new File(path + ".wikitext"));
		
		// Compile the retrieved page
		CompiledPage cp = compiler.postprocess(pageId, wikitext, null);
		
		TextConverter p = new TextConverter(config, wrapCol, null);
		p.go(cp.getPage());
	}
	
		
	
	static String run(File file, String fileTitle, boolean renderHtml) throws FileNotFoundException, IOException, LinkTargetException, CompilerException, JAXBException
	{
		// Set-up a simple wiki configuration
		SimpleWikiConfiguration config = new SimpleWikiConfiguration(
		        "classpath:/org/sweble/wikitext/engine/SimpleWikiConfiguration.xml");
		
		final int wrapCol = 80;
		
		// Instantiate a compiler for wiki pages
		Compiler compiler = new Compiler(config);
		
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, fileTitle);
		
		PageId pageId = new PageId(pageTitle, -1);
		
		String wikitext = FileUtils.readFileToString(file);
		
		// Compile the retrieved page
		CompiledPage cp = compiler.postprocess(pageId, wikitext, null);
		
		// Render the compiled page as HTML
		StringWriter w = new StringWriter();
		
		if (renderHtml)
		{
			HtmlPrinter p = new HtmlPrinter(w, pageTitle.getFullTitle());
			p.setCssResource("/org/sweble/wikitext/engine/utils/HtmlPrinter.css", "");
			p.setStandaloneHtml(true, "");
			p.go(cp.getPage());
			return w.toString();
		}
		else
		{
			TextConverter p = new TextConverter(config, wrapCol, null);
			return (String) p.go(cp.getPage());
		}
	}

}
