package ontologyMatching;

import org.openrdf.repository.RepositoryException;




public class ToBeAShell {

	public static void main(String[] args) {
		String queryString = "SELECT ?type WHERE {"
				//+ "?v <http://scn.cs.lth.se/rawproperty/län> ?type."
				+"<http://dbpedia.org/ontology/Artist>" + " ?type " + "<http://dbpedia.org/ontology/PopulatedPlace> ." 
				//+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		ConnectToSchool db = new ConnectToSchool();
		try {
			db.connect();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.question();
	}

}
/*





		String queryString = "SELECT ?v ?type WHERE {"
				+ "?v <http://scn.cs.lth.se/rawproperty/född> ?type."
				//+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
*/
