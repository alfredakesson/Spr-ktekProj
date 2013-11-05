package sprakproj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

import org.xml.sax.SAXException;



public class Startclass {
    static class DemoArticleFilter implements IArticleFilter {

        @Override
        public void process(WikiArticle page, Siteinfo siteinfo) throws SAXException {
        	
            // String to be scanned to find the pattern.
            String line = page.getText();
            String pattern = "född_datum \\s*=\\s*(.*)";

            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            // Now create matcher object.
            Matcher m = r.matcher(line);
            if (m.find( )) {
            	System.out.println(page.getTitle());
            	System.out.println("Found value: " + m.group(1) );
            }
            
        }


}

	public static void main(String[] args) {
    String bz2Filename = "../../svwiki-20131101-pages-articles.xml.bz2";
    System.out.println("hej");
    try {
            IArticleFilter handler = new DemoArticleFilter();
            WikiXMLParser wxp = new WikiXMLParser(bz2Filename, handler);
            wxp.parse();
    } catch (Exception e) {
            e.printStackTrace();
    }

	}

	
	/*
	 * född
	 * födelseår
	 * födelsedatum
	 * 
	 * 
	 * 
	 */
	
}
