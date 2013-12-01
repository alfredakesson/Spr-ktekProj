package ontologyMatching;

public class ToBeAShell {

	public static void main(String[] args) {
		String queryString = "SELECT ?type WHERE {"
				//+ "?v <http://scn.cs.lth.se/rawproperty/län> ?type."
				+"<http://dbpedia.org/ontology/Artist>" + " ?type " + "<http://dbpedia.org/ontology/PopulatedPlace> ." 
				//+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
		SesameDb db = new SesameDb();
		db.createDb();
		int i = 0; 
		for( String s:db.askSesame(queryString, "type", "type")){
			if(i % 2 == 0){
				System.out.print(s +"\t");
			}
			else{
				System.out.println(s);
			}
			i++;
		};
	}

}
/*





		String queryString = "SELECT ?v ?type WHERE {"
				+ "?v <http://scn.cs.lth.se/rawproperty/född> ?type."
				//+ " ?type <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Class> ."
				+ "}";
*/