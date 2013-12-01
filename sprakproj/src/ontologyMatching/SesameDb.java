package ontologyMatching;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
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

public class SesameDb {
	private RepositoryConnection conn;
	private Repository repo;
	private String beginNameArticle;
	private int count;
	
	public SesameDb() {
		this.beginNameArticle = "http://sv.dbpedia.org/resource/";
		count = 0; 
	}

	public RepositoryConnection createDb() {
		File dataDir = new File(".");
		repo = new SailRepository(new NativeStore(dataDir));
		try {
			repo.initialize();
		} catch (RepositoryException e1) {
			System.out.println("ERROR20");
			e1.printStackTrace();
		}
		
		try {
			conn = repo.getConnection();	
			return conn;
			
		} catch (RepositoryException e1) {
			System.out.println("ERROR21");
			e1.printStackTrace();
		}
		return null;
	}
	
	public void insertNewTypeTriple(String property, String article, String value){
		try {
			String articlePropertiesList[] = getTypes(article);
			String valuePropertiesList[] = getTypes(value);
			insert_type_prop_lit(articlePropertiesList, property, valuePropertiesList);
			
			//Innan vi sätter in dom: 


			
		} catch (RepositoryException e) {
			System.out.println("ERROR22");
			e.printStackTrace();
		} catch (RDFParseException e) {
			System.out.println("ERROR23");
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			System.out.println("--------");
			System.out.println("Article:\t" + article);
			System.out.println("Property:\t" + property);
			System.out.println();	

			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			System.out.println("ERROR6");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR7");
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<String> askSesame(String queryString, String variable, String variable2){
		ArrayList<String> resultList = new ArrayList<String>();

		TupleQuery tupleQuery = null;
		try {
			tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
					queryString);
		} catch (RepositoryException e1) {
			System.out.println("ERROR8");
			e1.printStackTrace();
		} catch (MalformedQueryException e1) {
			System.out.println("ERROR9");
			e1.printStackTrace();
		}
		TupleQueryResult result = null;
		try {
			result = tupleQuery.evaluate();
		} catch (QueryEvaluationException e1) {
			System.out.println("ERROR10");
			e1.printStackTrace();
		}
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfY = bindingSet.getValue(variable);
				Value valueOfX = bindingSet.getValue(variable2);
				resultList.add(valueOfY.toString());
				resultList.add(valueOfX.toString());
			}
		} catch (QueryEvaluationException e1) {
			System.out.println("ERROR11");
			e1.printStackTrace();
		}
		return resultList;

	}

	private void insert_type_prop_lit(String[] typesOfSubject, String prop, String[] typesOfObj) throws RepositoryException {
		ValueFactory factory = repo.getValueFactory();
		for (String prop_val : typesOfObj) {
			for (String type : typesOfSubject) {
				URI type_URI = factory.createURI(type);
				URI prop_URI = factory
						.createURI("http://scn.cs.lth.se/rawproperty/" + prop);
				URI prop_value_URI = factory.createURI(prop_val);
				Statement type_prop_uri = factory.createStatement(type_URI,
						prop_URI, prop_value_URI);
				conn.add(type_prop_uri);
				if(count % 1000 == 0){
					System.out.println("sesame:\t" + count);					
				}
				count++;

			}
		}
	}

	public String existArticle(String article){
		String articleAddr = beginNameArticle+article;
		
		String queryString = "SELECT ?v WHERE " + "{"
				+ "<" + articleAddr + "> "
				+ "<http://www.w3.org/2002/07/owl#sameAs> ?v. "
				+ "FILTER (STRSTARTS(STR(?v), 'http://dbpedia.org'))"
				+ "}";

		TupleQuery tupleQuery = null;
		try {
			tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
					queryString);
		} catch (RepositoryException e) {
			System.out.println("ERROR15");
			e.printStackTrace();
			return null;
		} catch (MalformedQueryException e) {
			System.out.println("ERROR16");
			System.out.println("article is:\t" + article);
			e.printStackTrace();
			return null;
		}
		TupleQueryResult result = null;
		try {
			result = tupleQuery.evaluate();
		} catch (QueryEvaluationException e) {
			System.out.println("ERROR17");
			e.printStackTrace();
			return null;
		}
		try {
			if(result.hasNext()){
				BindingSet bindingSet = result.next();
				Value valueOfY = bindingSet.getValue("v");
				return valueOfY.toString();
			}
		} catch (QueryEvaluationException e) {
			System.out.println("ERROR18");
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public void addSameAsToDb() throws RDFParseException, RepositoryException, IOException{
		File theFile = new
				 File("../../interlanguage_links_sv.nt");
				 conn.add(theFile, "test", RDFFormat.TURTLE);
				conn.close();
	}
	
	private String[] getTypes(String entity)
			throws RepositoryException, RDFParseException, IOException,
			MalformedQueryException, QueryEvaluationException {

		String queryString = "SELECT ?type WHERE {"
				+ "<"
				+ entity
				+ "> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type."
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

		String[] resultList = new String[res.size()];
		return res.toArray(resultList);
	}

	public void insertManyTriples(String[] tmpListArt,
			String[] tmpListProp, String[] tmpListVal) {
		// TODO Auto-generated method stub
		
	}

}
