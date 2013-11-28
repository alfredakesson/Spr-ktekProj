package ontologyMatching;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.collections.Factory;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.nativerdf.NativeStore;

public class RdfGlue {
	
	public RdfGlue(){
		
	}
	
	public void glue() throws RepositoryException,
			RDFParseException, IOException, MalformedQueryException,
			QueryEvaluationException, SQLException {

		File dataDir = new File(".");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		repo.initialize();
		RepositoryConnection conn = repo.getConnection();

		SesameDb sesameDb = new SesameDb();
		sesameDb.createDb();
		
		String exist = sesameDb.existArticle("Sverige");
		System.out.println(exist);
		String[] kolla = sesameDb.getTypes(exist);
		for (String s : kolla) {
			System.out.println(s);
		}
		DatabaseSQLite db = DatabaseSQLite.getInstance();
		ResultSet rs = db.getTable();
		int num = 0;
		while (rs.next()) {
			if (num % 1000 == 0) {
				System.out.println(num);
			}
			num++;
		}
		conn.close();

	}

	private static void inset_type_prop_lit(Repository repo,
			RepositoryConnection conn, String[] types, String prop,
			String[] prop_vals) throws RepositoryException {
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
}
