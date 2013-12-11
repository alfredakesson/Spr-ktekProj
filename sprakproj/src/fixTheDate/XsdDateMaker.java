package fixTheDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XsdDateMaker {
	private Database db;
	private BufferedWriter bw;
//^^xsd:date
	//<http://www.w3.org/2001/XMLSchema#date>
	//"322"^^<http://www.w3.org/2001/XMLSchema#integer> 
	//"322"^^<http://www.w3.org/2001/XMLSchema#integer>
	
	//"bornDate"
	
	public static void main(String args[]){
		XsdDateMaker xsdDateMaker = new XsdDateMaker();
		xsdDateMaker.makeXSDfile("bornDate");
	}
	
	
	public XsdDateMaker(){
		db = Database.getInstance();
	}
	
	public void makeXSDfile(String table){
		
		File file = new File("./date_xsd.nt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		
		
		
		ResultSet rs = db.getTable(table);
		int numArt = 0;
		try {
			while (rs.next()) {
				numArt++;
				
				String property = "birthDate";
				String article = null;
				String value = null;

				try {
					article = rs.getString("art").replaceAll(" ", "_")
							.replaceAll("\"", "%22");
				} catch (SQLException e1) {
					e1.printStackTrace();
					continue;
				}
				if (article == null) {
					continue;
				}

				try {
					value = rs.getString("val");
					if(value.substring(5).equals("00-00")){
						//Year
						value = "\"" + value.substring(0, 4) + "\"" + "^^<http://www.w3.org/2001/XMLSchema#gYear>";
					}
					else if(value.substring(8).equals("00")){
						//Year-month
						value = "\"" + value.substring(0, 7) + "\"" + "^^<http://www.w3.org/2001/XMLSchema#gYearMonth>";
					}
					else{
						//Year-month-day
						value = "\"" + value + "\"" + "^^<http://www.w3.org/2001/XMLSchema#date>";
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					continue;
				}
				try {
					bw.append("<http://sv.dbpedia.org/resource/" + article
					+ "> <http://semantica.cs.lth.se/scns/date/"
					+ property + "> " + value + " . \n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(numArt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
