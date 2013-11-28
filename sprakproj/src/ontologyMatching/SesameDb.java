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
	
	public SesameDb() {
		this.beginNameArticle = "http://sv.dbpedia.org/resource/";
	}

	public RepositoryConnection createDb() {
		File dataDir = new File(".");
		repo = new SailRepository(new NativeStore(dataDir));
		try {
			repo.initialize();
		} catch (RepositoryException e1) {
			e1.printStackTrace();
		}
		
		try {
			conn = repo.getConnection();
			return conn;
		} catch (RepositoryException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	
	public ArrayList<String> askSesame(String queryString, String variable){
		ArrayList<String> resultList = new ArrayList<String>();

		TupleQuery tupleQuery = null;
		try {
			tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
					queryString);
		} catch (RepositoryException e1) {
			e1.printStackTrace();
		} catch (MalformedQueryException e1) {
			e1.printStackTrace();
		}
		TupleQueryResult result = null;
		try {
			result = tupleQuery.evaluate();
		} catch (QueryEvaluationException e1) {
			e1.printStackTrace();
		}
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfY = bindingSet.getValue(variable);
				resultList.add(valueOfY.toString());
			}
		} catch (QueryEvaluationException e1) {
			e1.printStackTrace();
		}
		return resultList;

	}

	public void insert_type_prop_lit(ArrayList<String> types, String prop, ArrayList<String> prop_vals) throws RepositoryException {
		ValueFactory factory = repo.getValueFactory();
		for (String prop_val : prop_vals) {
			for (String type : types) {
				URI type_URI = factory.createURI(type);
				URI prop_URI = factory
						.createURI("http://scn.cs.lth.se/rawproperty/" + prop);
				URI prop_value_URI = factory.createURI(prop_val);
				Statement type_prop_uri = factory.createStatement(type_URI,
						prop_URI, prop_value_URI);
				conn.add(type_prop_uri);
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
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			e.printStackTrace();
		}
		TupleQueryResult result = null;
		try {
			result = tupleQuery.evaluate();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		try {
			if(result.hasNext()){
				BindingSet bindingSet = result.next();
				Value valueOfY = bindingSet.getValue("v");
				return valueOfY.toString();
			}
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addSameAsToDb() throws RDFParseException, RepositoryException, IOException{
		File theFile = new
				 File("../../interlanguage_links_sv.nt");
				 conn.add(theFile, "test", RDFFormat.TURTLE);
				conn.close();
	}
	
	public String[] getTypes(String entity)
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

}
