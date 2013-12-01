package ontologyMatching;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		
		SesameDb sesameDb = new SesameDb();
		sesameDb.createDb();
		int numArt = 0;
		long propdur = 0;
		long valdur = 0;
		long artdur = 0;
		long insdur = 0;

		System.out.println("heap: " + Runtime.getRuntime().maxMemory() + " : "
				+ Runtime.getRuntime().freeMemory());

		String exist = sesameDb.existArticle("Stockholm");
		if (exist != null) {
			System.out.println("English article exist, url: \t" + exist);
		}

		DatabaseSQLite db = DatabaseSQLite.getInstance();
		ResultSet rs = db.getTable();

		String getProp = "\\[\\[(.+?)(\\]\\]|\\|)";
		Pattern objPattern = Pattern.compile(getProp);

		try {
			while (rs.next()) {
				numArt++;
				if (numArt % 10000 == 0) {
					sesameDb.closeDb();
					sesameDb.createDb();
					System.out.println("Num art: " + numArt);
					System.out.println("Dur prop: " + propdur);
					System.out.println("Val prop: " + valdur);
					System.out.println("Art prop: " + artdur);
					System.out.println("Ins prop: " + insdur);
					System.out.println("------------------------");
				}
				String property = null;
				String article = null;
				String value = null;
				
				long startTime = System.nanoTime();

				try {
					property = rs.getString("prop").replaceAll(" ", "_")
							.replaceAll("\"", "%22");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					System.out.println("ERROR1");
					e2.printStackTrace();
					continue;
				}

				long endTime = System.nanoTime();
				long duration = endTime - startTime;
				propdur+=duration;
				startTime = System.nanoTime();

				try {
					article = rs.getString("art").replaceAll(" ", "_")
							.replaceAll("\"", "%22");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("ERROR2");
					e1.printStackTrace();
					continue;
				}
				article = sesameDb.existArticle(article);
				if (article == null) {
					continue;
				}

				endTime = System.nanoTime();
				duration = endTime - startTime;
				artdur+=duration;
				startTime = System.nanoTime();

				try {
					value = rs.getString("val");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR3");
					e.printStackTrace();
					continue;
				}

				Matcher m = objPattern.matcher(value);
				endTime = System.nanoTime();
				duration = endTime - startTime;
				valdur+=duration;
				startTime = System.nanoTime();

				while (m.find()) {
					value = m.group(1);
					value = value.replaceAll(" ", "_").replaceAll("\"", "%22");
					value = sesameDb.existArticle(value);
					if (value != null) {
						 sesameDb.insertNewTypeTriple(property, article,
						 value);
					}
				}
				endTime = System.nanoTime();
				duration = endTime - startTime;
				insdur+=duration;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR4");
			e.printStackTrace();
		}
		sesameDb.closeDb();

	}

}

//
// File theFile = new
// File("../../instance_types_en.ttl");
// conn.add(theFile, "test", RDFFormat.TURTLE);
// conn.close();


/*
 * född föddplats = samma
 * 
 * född_plats är ofta person plats född_datum är ofta person datum
 * 
 * Vi kan bara kolla egenskaper som länkar från en entitet till en annan
 */
/*
 * 
 * String queryString = "SELECT ?type WHERE {" +
 * "<http://dbpedia.org/resource/Barack_Obama> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
 * //+
 * " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
 * + "}"; String variable = "type";
 * 
 * ArrayList<String> sesameRes = sesameDb.askSesame(queryString, variable);
 * for(String s : sesameRes){ System.out.println(s); }
 */
/*
 * System.out.println("****SUBJECT****");
 * System.out.println(rs.getString("Subject"));
 * System.out.println("****PREDICATE****"); String pred =
 * rs.getString("Predicate").replaceAll(" ", "_").replaceAll("\"", "%22");
 * String predExist = sesameDb.existArticle(pred);
 * System.out.println("predicate_swedish:\t" + pred);
 * System.out.println("predicate_english:\t" + predExist);
 * 
 * 
 * System.out.println("****OBJECT****"); String obj = rs.getString("Object");
 * while(m.find()){ obj = m.group(1); obj = obj.replaceAll(" ",
 * "_").replaceAll("\"", "%22"); String objExist = sesameDb.existArticle(obj);
 * 
 * System.out.println("object_svenska:\t" + obj);
 * System.out.println("object_english:\t" + objExist); }
 * System.out.println("##################################");
 */
