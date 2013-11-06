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
	private Pattern dr2;
	private Pattern dr3;

	public Startclass() {
		String pattern = "[f|F]öd.{1,10} \\s*=\\s*(.*)";
		String yearPattern = "(\\d{4})";// "\\[\\[\\s{0,3}(\\d{4})\\s{0,3}\\]\\]"; //"\\[\\[\\s{1,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\]\\]//\\s{0,3}\\[\\[(\\d{4})\\]\\]";
		String datePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})"; //"\\[\\[\\s{0,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\s{0,3}\\]\\]";
		String datePattern2 = "\\{\\{.{0,10}\\|\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\}\\}";
		String datePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		r = Pattern.compile(pattern);
		yr = Pattern.compile(yearPattern);
		dr = Pattern.compile(datePattern);
		dr2 = Pattern.compile(datePattern2);
		dr3 = Pattern.compile(datePattern3);
		
		res = new LinkedList<String>();
	}

	@Override
	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {
		Matcher m = r.matcher(page.getText());
		while (m.find()) {
			
			
			
			String dateS = m.group(1);
			Matcher ym = yr.matcher(dateS);
			//System.out.println(dateS);
			Matcher dm2 = dr2.matcher(dateS);
			if(dm2.find()){
				res.add(page.getTitle() + dm2.group(1));
				System.out.println(page.getTitle()+ " född " + dm2.group(1)+ " måndad " + dm2.group(2) + " dag " + dm2.group(3));
			}
			else if(ym.find()){
				//System.out.print(page.getTitle()+ " föddes " + ym.group(1));
				Matcher dm = dr.matcher(dateS);
				if(dm.find()){
					int manad = mandToDag(dm.group(2));
					if( manad > 0){
						res.add(page.getTitle() + m.group(1));
						System.out.print(" månad " + manad + " dag " + dm.group(1));
						
					}
				}
				System.out.println();
				
			}

			

		}
		if(num > 1000){
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
	
	private int mandToDag(String s){
		s = s.toLowerCase();
		if(s.equals("januari")){
			return 1;
		}
		else if(s.equals("februari")){
			return 2;
		}else if(s.equals("mars")){
			return 3;
		}else if(s.equals("april")){
			return 4;
		}else if(s.equals("maj")){
			return 5;
		}else if(s.equals("juni")){
			return 6;
		}else if(s.equals("juli")){
			return 7;
		}else if(s.equals("augusti")){
			return 8;
		}else if(s.equals("september")){
			return 9;
		}else if(s.equals("oktober")){
			return 10;
		}else if(s.equals("november")){
			return 11;
		}else if(s.equals("december")){
			return 12;
		}else{
			return -1;
		}
		
		
//		Januari
//		Februari
//		Mars
//		April
//		Maj
//		Juni
//		Juli
//		Augusti
//		September
//		Oktober
//		November
//		December
	}

	/*
	 * född födelseår födelsedatum
	 * 
	 * 
	 *[[5 oktober]] [[1785]]
	 *{{Ålder|1973|6|6}}
	 */

}
