package testOPENRdf;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.nativerdf.NativeStore;

public class testOpenRDF {

	public static void main(String[] args) {
		

		File dataDir = new File(".");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		try {
			repo.initialize();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RepositoryConnection conn = null;
		try {
			conn = repo.getConnection();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String queryString = "SELECT ?type WHERE {"
				+ "<http://dbpedia.org/resource/Barack_Obama> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
				+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		TupleQuery tupleQuery = null;
		try {
			tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
					queryString);
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedQueryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TupleQueryResult result = null;
		try {
			result = tupleQuery.evaluate();
		} catch (QueryEvaluationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfY = bindingSet.getValue("type");
				System.out.println(valueOfY.toString());
			}
		} catch (QueryEvaluationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DbPediaQuestion dbQ =new DbPediaQuestion(conn);

		String exist = dbQ.existArticle("Stockholm");
		if(exist != null){
			System.out.println(exist);
		}
		
		
		
		Database db = Database.getInstance();
		ResultSet rs = db.getTable();

		String getProp = "\\[\\[(.+?)(\\]\\]|\\|)";
		Pattern objPattern = Pattern.compile(getProp);
		
		try{
		while(rs.next()){
	
			//String pred = rs.getString("Predicate").replaceAll(" ", "_").replaceAll("\"", "%22");
			String obj = rs.getString("Object");
			Matcher m = objPattern.matcher(obj);
			if(m.find()){
				obj = m.group(0);
			}
			System.out.println(obj);
			//String predExist = dbQ.existArticle(pred);
		
			
			//System.out.println(predExist);

			//System.out.println(objExist);
			
		}
		}catch(Exception e){
			
		}
	}
	

	public static String[] getTypes(String entity,RepositoryConnection conn)throws RepositoryException,
		RDFParseException, IOException, MalformedQueryException,
		QueryEvaluationException {
		
		String queryString = "SELECT ?type WHERE {"
				+ "<"+entity+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
				+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryString);
		TupleQueryResult result = tupleQuery.evaluate();
		LinkedList<String> res = new LinkedList<String>(); 
		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value valueOfY = bindingSet.getValue("type");
			res.add(valueOfY.toString());
		}
		
		String[] kalle = new String[res.size()];
		return res.toArray(kalle);
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
