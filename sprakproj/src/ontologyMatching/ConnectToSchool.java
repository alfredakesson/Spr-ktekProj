package ontologyMatching;


import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;


public class ConnectToSchool {
	private Repository repo;

	public void connect() throws RepositoryException{
		String sesameServer = "http://semantica.cs.lth.se/openrdf-sesame";
		String repositoryID = "scns_ontology_2";

		repo = new HTTPRepository(sesameServer, repositoryID);
		repo.initialize();
	}
	

	public void question(String queryString, String[] variable) {
		try {
			RepositoryConnection con = repo.getConnection();
			try {

				TupleQuery tupleQuery = con.prepareTupleQuery(
						QueryLanguage.SPARQL, queryString);
				TupleQueryResult result = tupleQuery.evaluate();
				try {
					Value val1, val2, val3, val4;
					while (result.hasNext()) {
						BindingSet bindingSet = result.next();
						switch (variable.length){
						case 1:	
							val1 = bindingSet.getValue(variable[0]);
							System.out.println(variable[0] + "\t" + val1);
							break;
						case 2:
							val1 = bindingSet.getValue(variable[0]);
							val2 = bindingSet.getValue(variable[1]);
							System.out.println(variable[0] + "\t" + val1);
							System.out.println(variable[1] + "\t" + val2);
							break; 
						case 3:
							val1 = bindingSet.getValue(variable[0]);
							val2 = bindingSet.getValue(variable[1]);
							val3 = bindingSet.getValue(variable[2]);
							System.out.println(variable[0] + "\t" + val1);
							System.out.println(variable[1] + "\t" + val2);
							System.out.println(variable[2] + "\t" + val3);
							break; 
						case 4:
							val1 = bindingSet.getValue(variable[0]);
							val2 = bindingSet.getValue(variable[1]);
							val3 = bindingSet.getValue(variable[2]);
							val4 = bindingSet.getValue(variable[3]);
							System.out.println(variable[0] + "\t" + val1);
							System.out.println(variable[1] + "\t" + val2);
							System.out.println(variable[2] + "\t" + val3);
							System.out.println(variable[3] + "\t" + val4);
							break; 
						}
						
					}
				}
				// finally {
				// result.close();
				catch (Exception e) {
					System.out.println("ERRROERRRR");
				}
			} finally {
				con.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("eeee");
		}
	}
}




