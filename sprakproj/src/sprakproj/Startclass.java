package sprakproj;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

import org.xml.sax.SAXException;



public class Startclass {
    static class DemoArticleFilter implements IArticleFilter {

        @Override
        public void process(WikiArticle page, Siteinfo siteinfo) throws SAXException {
                System.out.println("----------------------------------------");
                System.out.println(page.getId());
                System.out.println(page.getRevisionId());
                System.out.println(page.getTitle());
                System.out.println("----------------------------------------");
                System.out.println(page.getText());
        }


}

	public static void main(String[] args) {
    String bz2Filename = "/Users/alfredakesson/Downloads/svwiki-20131101-pages-articles.xml.bz2";
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
