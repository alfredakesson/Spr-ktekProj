package testOPENRdf;

import java.io.File;
import java.io.IOException;

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
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.nativerdf.NativeStore;

public class testOpenRDF {

	public static void main(String[] args) throws RepositoryException,
			RDFParseException, IOException, MalformedQueryException,
			QueryEvaluationException {
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
//		 File theFile = new
//		 File("../../dbpedia_3.9.owl");
//		 conn.add(theFile, "test", RDFFormat.RDFXML);
//		conn.close();


//		
//		File theFile = new
//		 File("../../instance_types_en.ttl");
//		 conn.add(theFile, "test", RDFFormat.TURTLE);
//		conn.close();

		DbPediaQuestion dbQ =new DbPediaQuestion(conn);
		//dbQ.addSameAsToDb();
		
		String exist = dbQ.existArticle("Stockholm");
		if(exist != null){
			System.out.println(exist);
		}
		
	}

}



/*
 * 	född föddplats = samma
 * 	
 * född_plats är ofta person plats
 * född_datum är ofta person datum 
 * 
 * Vi kan bara kolla egenskaper som länkar från en entitet till en annan
 * 
 */
