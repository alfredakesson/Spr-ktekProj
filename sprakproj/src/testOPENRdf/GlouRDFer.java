package testOPENRdf;

import java.io.File;
import java.io.IOException;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.nativerdf.NativeStore;

public class GlouRDFer {
	public static void main(String args[]) throws RepositoryException, RDFParseException, 
			IOException, MalformedQueryException, QueryEvaluationException {
		
		File dataDir = new File(".");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		repo.initialize();
		RepositoryConnection conn = repo.getConnection();
		
		DbPediaQuestion dbQ =new DbPediaQuestion(conn);
		
		String exist = dbQ.existArticle("Sverige");
		System.out.println(exist);
		String[] kolla = testOpenRDF.getTypes(exist, conn);
		for(String s: kolla){
			System.out.println(s);
		}
		
	}
}
