package ontologyMatching;


import java.util.ArrayList;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

public class ConnectToSchool {
	private Repository repo;
	

	public void connect() throws RepositoryException{
		String sesameServer = "http://semantica.cs.lth.se:8080/openrdf-sesame";
		String repositoryID = "scns_ontology_2";

		repo = new HTTPRepository(sesameServer, repositoryID);
		repo.initialize();

	}
	
	
	
	public static void main(String args[]){
		ConnectToSchool connect = new ConnectToSchool();
		
		try{
			
			connect.connect();

			
		} catch(Exception e){
			System.out.println("ERROR");
		}
	}

	public void question(){
		try {
			   RepositoryConnection con = repo.getConnection();
			   try {
				  String queryString = "select ?type where{ <http://dbpedia.org/ontology/Place> ?type <http://dbpedia.org/ontology/Place> . }";
				  TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);

				  TupleQueryResult result = tupleQuery.evaluate();
				  try {
						BindingSet bindingSet = result.next();
						Value valueOfX = bindingSet.getValue("type");
						//Value valueOfY = bindingSet.getValue("y");
System.out.println(valueOfX);
						// do something interesting with the values here...
				  }
				  finally {
				      result.close();
				  }
			   }
			   finally {
			      con.close();
			   }
			
		}catch(Exception e) {
			
		}
	}
}




