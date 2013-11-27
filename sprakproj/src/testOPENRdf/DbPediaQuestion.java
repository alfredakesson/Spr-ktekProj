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
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

public class DbPediaQuestion {
	private String beginNameArticle;
	private RepositoryConnection conn;
	
	
	public DbPediaQuestion(RepositoryConnection conn){
		this.beginNameArticle = "http://sv.dbpedia.org/resource/";
		this.conn = conn; 
	}
	
	public String existArticle(String article) throws RepositoryException, MalformedQueryException, QueryEvaluationException{
		String articleAddr = beginNameArticle+article;
		
		String queryString = "SELECT ?v WHERE " + "{"
				+ "<" + articleAddr + ">"
				+ "<http://www.w3.org/2002/07/owl#sameAs> ?v. "
				+ "FILTER (STRSTARTS(STR(?v), 'http://dbpedia.org'))"
				+ "}";
		
		
		
		
		TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryString);
		TupleQueryResult result = tupleQuery.evaluate();
		if(result.hasNext()){
			BindingSet bindingSet = result.next();
			Value valueOfY = bindingSet.getValue("v");
			return valueOfY.toString();
		}
		return null;
	}
	
	public void addSameAsToDb() throws RDFParseException, RepositoryException, IOException{
		File theFile = new
				 File("../../interlanguage_links_sv.nt");
				 conn.add(theFile, "test", RDFFormat.TURTLE);
				conn.close();
	}
	
	
	

}
