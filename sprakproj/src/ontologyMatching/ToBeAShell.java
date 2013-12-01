package ontologyMatching;

import org.openrdf.repository.RepositoryException;



public class ToBeAShell {

	public static void main(String[] args) {

		ConnectToSchool db = new ConnectToSchool();
		try {
			db.connect();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		String query = "select ?type1 ?art ?value where{ " +
				"?art ?type1 ?value ." +
				"}";
		
		String query2 = "select ?type where{" +
				"<http://dbpedia.org/ontology/Place> ?type <http://dbpedia.org/ontology/Place> . " +
				"}";
		
		
		String[] queryVars = new String[3];
		queryVars[0] = "type1";
		queryVars[1] = "art";
		queryVars[2] = "value";
		
		System.out.println("### Query till sesame: ");
		db.question(query, queryVars);
		
	}

}
