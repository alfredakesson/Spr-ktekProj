package testOPENRdf;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.LinkedList;
import org.openrdf.console.Connect;
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

	public static void main(String[] args) throws RepositoryException,
			RDFParseException, IOException, MalformedQueryException,
			QueryEvaluationException, SQLException {
		

		File dataDir = new File(".");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		repo.initialize();
		RepositoryConnection conn = repo.getConnection();
		String queryString = "SELECT ?type WHERE {"
				+ "<http://dbpedia.org/resource/Barack_Obama> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
				+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryString);
		TupleQueryResult result = tupleQuery.evaluate();
		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value valueOfY = bindingSet.getValue("type");
			System.out.println(valueOfY.toString());
		}

		DbPediaQuestion dbQ =new DbPediaQuestion(conn);

		String exist = dbQ.existArticle("Stockholm");
		if(exist != null){
			System.out.println(exist);
		}
		
		
		
		Database db = Database.getInstance();
		ResultSet rs = db.getTable();
		while(rs.next()){
			System.out.println(rs.getString("Subject") + "\t" + rs.getString("Predicate") + "\t" + rs.getString("Object") + "\t" );
		}

		
//		File theFile = new
//		 File("../../instance_types_en.ttl");
//		 conn.add(theFile, "test", RDFFormat.TURTLE);
//		conn.close();

//		DbPediaQuestion dbQ =new DbPediaQuestion(conn);
//		//dbQ.addSameAsToDb();
//		
//		String exist = dbQ.existArticle("Stockholm");
//		if(exist != null){
//			System.out.println(exist);
//		}
		
	}
	

	public static String[] getTypes(String entety,RepositoryConnection conn)throws RepositoryException,
		RDFParseException, IOException, MalformedQueryException,
		QueryEvaluationException {
		
		String queryString = "SELECT ?type WHERE {"
				+ "<"+entety+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
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
