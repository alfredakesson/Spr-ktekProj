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

	private Pattern bornPattern;
	public LinkedList<String> res; 
	public int num = 0;
	private Pattern yearPattern;
	private Pattern datePattern;
	private Pattern datePattern2;
	private Pattern datePattern3;
	private Pattern bornInTextPattern;
	private Database db;

	public Startclass() {
		String stringBornpattern = "[f|F][ö|Ö][d|D].{1,15}\\s*=\\s*(.*)";
		String stringYearPattern = "(\\d{1,4})";// "\\[\\[\\s{0,3}(\\d{4})\\s{0,3}\\]\\]";
												// //"\\[\\[\\s{1,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\]\\]//\\s{0,3}\\[\\[(\\d{4})\\]\\]";
		String stringDatePattern = "(\\d{1,2})\\s{0,3}([a-zA-Z]{2,10})\\D{1,30}(\\d{1,4})"; // "\\[\\[\\s{0,3}(\\d{1,2})\\s{0,3}(\\w{2,10})\\s{0,3}\\]\\]";
		String stringDatePattern2 = "\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}";// "\\{\\{.{0,10}\\|\\s{0,3}(\\d{4})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\|\\s{0,3}(\\d{1,2})\\s{0,3}\\}\\}";
		String stringDatePattern3 = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		String stringBornInTextPattern = "född.{0,50}";

		bornPattern = Pattern.compile(stringBornpattern);
		yearPattern = Pattern.compile(stringYearPattern);
		datePattern = Pattern.compile(stringDatePattern);
		datePattern2 = Pattern.compile(stringDatePattern2);
		datePattern3 = Pattern.compile(stringDatePattern3);
		bornInTextPattern = Pattern.compile(stringBornInTextPattern);
		db = new Database();

		res = new LinkedList<String>();
	}

	@Override
	public void process(WikiArticle page, Siteinfo siteinfo)
			throws SAXException {
		Matcher m = bornPattern.matcher(page.getText());
		boolean added = false;
		while (m.find()) {
			String dateS = m.group(1);
			Matcher ym = yearPattern.matcher(dateS);
			// System.out.println(dateS);
			Matcher dm = datePattern.matcher(dateS);
			Matcher dm2 = datePattern2.matcher(dateS);
			Matcher dm3 = datePattern3.matcher(dateS);
			if (dm2.find()) {
				res.add(page.getTitle() + dm2.group(1));
				added = true;
				String date = dm2.group(1) + "-" + dm2.group(2) + "-"
						+ dm2.group(3);
				//System.out.println(page.getTitle().replaceAll(" ", "_")
					//	+ ",född," + date);
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						date);
				// System.out.println(page.getTitle()+ " född " + dm2.group(1)+
				// " måndad " + dm2.group(2) + " dag " + dm2.group(3));
			} else if (dm3.find()) {
				res.add(page.getTitle() + dm3.group(1));
				added = true;
				String date = dm3.group(1) + "-" + dm3.group(2) + "-"
						+ dm3.group(3);
				//System.out.println(page.getTitle().replaceAll(" ", "_")
						//+ ",född," + date);
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						date);
				// System.out.println(page.getTitle()+ " född " + dm3.group(1)+
				// " måndad " + dm3.group(2) + " dag " + dm3.group(3));
			} else if (dm.find()) {

				int manad = convertMonthStringToNbr(dm.group(2));
				if (manad > 0) {
					res.add(page.getTitle() + m.group(1));
					added = true;
					String date = dm.group(3) + "-" + manad + "-" + dm.group(1);
					//System.out.println(page.getTitle().replaceAll(" ", "_")
					//		+ ",född," + date);
					db.insertTriple(page.getTitle().replaceAll(" ", "_"),
							"född", date);
					// System.out.print(page.getTitle()+ " föddes " +
					// ym.group(1));
					// System.out.print(" månad " + manad + " dag " +
					// dm.group(1));
					// System.out.println();

				}

			} else if (ym.find()) {
				db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född",
						ym.group(1));
				//System.out.println("hej");

			}
			else{
				if(!dateS.equals("==")){
					System.out.println(dateS);					
				}
			}

		}
		// if(!added){
		// Matcher min = bornInTextPattern.matcher(page.getText());
		// if(min.find()){
		// String dateS = min.group(0);
		// Matcher ym = yearPattern.matcher(dateS);
		//
		// if(ym.find()){
		//
		// Matcher dm = datePattern.matcher(dateS);
		// if(dm.find()){
		// int manad = convertMonthStringToNbr(dm.group(2));
		// if( manad > 0){
		// res.add(page.getTitle());
		// String date = ym.group(1)+"-"+manad+"-"+ dm.group(1);
		// System.out.println(dateS);
		// System.out.println(page.getTitle().replaceAll(" ",
		// "_")+",född,"+date);
		// db.insertTriple(page.getTitle().replaceAll(" ", "_"), "född", date);
		// //System.out.print(page.getTitle()+ " föddes " + ym.group(1));
		// //System.out.print(" månad " + manad + " dag " + dm.group(1));
		// //System.out.println();
		//
		// }
		// }
		//
		//
		// }
		// }
		// }
//		if (num > 10000) {
//			throw new SAXException();
//		}
//		num++;

	}

	public static void main(String[] args) {
		String bz2Filename = "../../svwiki-20131101-pages-articles.xml.bz2";
		//System.out.println("hej");
		Startclass handler = new Startclass();
		try {
			WikiXMLParser wxp = new WikiXMLParser(bz2Filename, handler);
			wxp.parse();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(handler.res.size());
			// for (String theRes : handler.res) {
			// System.out.println(theRes);
			//
			// }
		}

	}

	private int convertMonthStringToNbr(String s) {
		s = s.toLowerCase();
		if (s.equals("januari")) {
			return 1;
		} else if (s.equals("februari")) {
			return 2;
		} else if (s.equals("mars")) {
			return 3;
		} else if (s.equals("april")) {
			return 4;
		} else if (s.equals("maj")) {
			return 5;
		} else if (s.equals("juni")) {
			return 6;
		} else if (s.equals("juli")) {
			return 7;
		} else if (s.equals("augusti")) {
			return 8;
		} else if (s.equals("september")) {
			return 9;
		} else if (s.equals("oktober")) {
			return 10;
		} else if (s.equals("november")) {
			return 11;
		} else if (s.equals("december")) {
			return 12;
		} else {
			return -1;
		}

		// Januari
		// Februari
		// Mars
		// April
		// Maj
		// Juni
		// Juli
		// Augusti
		// September
		// Oktober
		// November
		// December
	}

	/*
	 * född födelseår födelsedatum
	 * 
	 * 
	 * [[5 oktober]] [[1785]]{{Ålder|1973|6|6}}
	 */

}
