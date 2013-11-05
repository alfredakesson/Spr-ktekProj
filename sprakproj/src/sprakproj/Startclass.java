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
	private Pattern yr;
	private Pattern dr;

	public Startclass() {
		String pattern = "[f|F]öd.{1,10} \\s*=\\s*(.*)";
		String yearPattern = "\\[\\[\\s{0,3}(\\d{4})\\s{0,3}\\]\\]"; //"\\[\\[\\s{1,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\]\\]//\\s{0,3}\\[\\[(\\d{4})\\]\\]";
		String datePattern = "\\[\\[\\s{0,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\s{0,3}\\]\\]";
		r = Pattern.compile(pattern);
		yr = Pattern.compile(yearPattern);
		dr = Pattern.compile(datePattern);
		
		res = new LinkedList<String>();
	}

	@Override
	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {
		Matcher m = r.matcher(page.getText());
		if (m.find()) {
			
			
			String dateS = m.group(1);
			Matcher ym = yr.matcher(dateS);
			
			if(ym.find()){
				res.add(page.getTitle() + m.group(1));
				System.out.print(page.getTitle()+ " föddes " + ym.group(1));
				Matcher dm = dr.matcher(dateS);
				if(dm.find()){
					System.out.print(" månad " + dm.group(2) + " dag " + dm.group(1));
				}
				System.out.println();
				
			}

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
//			for (String theRes : handler.res) {
//				System.out.println(theRes);
//				
//			}
		}

	}

	/*
	 * född födelseår födelsedatum
	 * 
	 * 
	 *[[5 oktober]] [[1785]]
	 *{{Ålder|1973|6|6}}
	 */

}
