package sprakproj;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

import org.xml.sax.SAXException;

public class Startclass implements IArticleFilter {

	private Pattern r;
	public LinkedList<String> res;
	public int num = 0;

	public Startclass() {
		String pattern = "[f|F]öd.{1,10} \\s*=\\s*(.*)";
		r = Pattern.compile(pattern);
		res = new LinkedList<String>();
	}

	@Override
	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {
		Matcher m = r.matcher(page.getText());
		if (m.find()) {
			res.add(page.getTitle() + m.group(1));
		}
		if(num > 10000){
			throw new SAXException();
		}
		num++;

	}

	public static void main(String[] args) {
		String bz2Filename = "../../svwiki-20131101-pages-articles.xml.bz2";
		System.out.println("hej");
		Startclass handler = new Startclass();
		try {
			WikiXMLParser wxp = new WikiXMLParser(bz2Filename, handler);
			wxp.parse();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(handler.res.size());
			for (String theRes : handler.res) {
				System.out.println(theRes);
				
			}
		}

	}

	/*
	 * född födelseår födelsedatum
	 */

}
