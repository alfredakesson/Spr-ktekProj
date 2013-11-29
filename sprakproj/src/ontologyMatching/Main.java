package ontologyMatching;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {
		File file = new File("./theDb.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		SesameDb sesameDb = new SesameDb();
		sesameDb.createDb();
		int numArt = 0;

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
				if (numArt % 100 == 0) {
					System.out.println(numArt);
				}
				String property = null;
				String article = null;
				String value = null;

				try {
					property = rs.getString("prop").replaceAll(" ", "_")
							.replaceAll("\"", "%22");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					System.out.println("ERROR1");
					e2.printStackTrace();
					continue;
				}
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
				try {
					value = rs.getString("val");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR3");
					e.printStackTrace();
					continue;
				}
				Matcher m = objPattern.matcher(value);
				while (m.find()) {
					value = m.group(1);
					value = value.replaceAll(" ", "_").replaceAll("\"", "%22");
					value = sesameDb.existArticle(value);
					if (value != null) {
						bw.append("<" + article
								+ "> <http://scn.cs.lth.se/rawproperty/"
								+ property + "> <" + value + "> . \n");
						// sesameDb.insertNewTypeTriple(property, article,
						// value);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR4");
			e.printStackTrace();
		}
		bw.close();
	}

}

//
// File theFile = new
// File("../../instance_types_en.ttl");
// conn.add(theFile, "test", RDFFormat.TURTLE);
// conn.close();

// dbQ.addSameAsToDb();

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
