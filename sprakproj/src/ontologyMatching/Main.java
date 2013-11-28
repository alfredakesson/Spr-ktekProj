package ontologyMatching;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openrdf.repository.RepositoryException;

public class Main {

	public static void main(String[] args) throws RepositoryException {
		SesameDb sesameDb = new SesameDb();
		sesameDb.createDb();
		String exist = sesameDb.existArticle("Stockholm");
		if(exist != null){
			System.out.println("English article exist, url: \t" + exist);
		}
		
		
		String queryString = "SELECT ?type WHERE {"
				+ "<http://dbpedia.org/resource/Barack_Obama> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
				//+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		String variable = "type";
		
		ArrayList<String> sesameRes = sesameDb.askSesame(queryString, variable);
		for(String s : sesameRes){
			System.out.println(s);
		}
		
		
		RdfGlue glue = new RdfGlue();
		try{			
			glue.glue();
		}catch(Exception e){
			
		}
		
		
		DatabaseSQLite db = DatabaseSQLite.getInstance();
		ResultSet rs = db.getTable();
		
		String getProp = "\\[\\[(.+?)(\\]\\]|\\|)";
		Pattern objPattern = Pattern.compile(getProp);
		
		try{
		while(rs.next()){
	
			System.out.println("****SUBJECT****");
			System.out.println(rs.getString("Subject"));
			System.out.println("****PREDICATE****");
			String pred = rs.getString("Predicate").replaceAll(" ", "_").replaceAll("\"", "%22");
			String predExist = sesameDb.existArticle(pred);	
			System.out.println("predicate_swedish:\t" + pred);
			System.out.println("predicate_english:\t" + predExist);
			
			
			System.out.println("****OBJECT****");
			String obj = rs.getString("Object");
			Matcher m = objPattern.matcher(obj);
			while(m.find()){
				obj = m.group(1);
				obj = obj.replaceAll(" ", "_").replaceAll("\"", "%22");
				String objExist = sesameDb.existArticle(obj);
				
				System.out.println("object_svenska:\t" + obj);
				System.out.println("object_english:\t" + objExist);				
			}
			System.out.println("##################################");
			
		}
		}catch(Exception e){
			
		}
	}
	





}
//File theFile = new
//File("../../dbpedia_3.9.owl");
//conn.add(theFile, "test", RDFFormat.RDFXML);
//conn.close();


//
//File theFile = new
//File("../../instance_types_en.ttl");
//conn.add(theFile, "test", RDFFormat.TURTLE);
//conn.close();

//dbQ.addSameAsToDb();

/*
 * 	född föddplats = samma
 * 	
 * född_plats är ofta person plats
 * född_datum är ofta person datum 
 * 
 * Vi kan bara kolla egenskaper som länkar från en entitet till en annan
 * 
 */
