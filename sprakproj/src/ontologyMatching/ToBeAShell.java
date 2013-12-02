package ontologyMatching;

import java.util.ArrayList;

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
		
		String[] queryVars = new String[3];
		queryVars[0] = "type1";
		queryVars[1] = "art";
		queryVars[2] = "value";
		
		String query2 = "select REDUCED ?type where{" +
				"<http://dbpedia.org/ontology/Person> ?type <http://dbpedia.org/ontology/Place> . " +
				"}";
		
		String[] queryVars2 = new String[1];
		queryVars2[0] = "type";
		

		System.out.println("### Query till sesame: ");
		//ArrayList<String[]> res = db.question(query, queryVars);
		db.questionSyso(query2, queryVars2);
		
	}

}
