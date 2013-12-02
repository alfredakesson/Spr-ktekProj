package ontologyMatching;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openrdf.repository.RepositoryException;

public class CalculateEditDistancesTest {

	public static void main(String[] args) {
		ConnectToSchool db = new ConnectToSchool();
		try {
			db.connect();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		String query = "SELECT REDUCED ?type2"
				+	"WHERE{"
				+		"?art1 <http://scn.cs.lth.se/rawproperty/kommun> ?value1 ."
				+		"?art1 ?type2 ?value1 ."
				+		"?art2 <http://scn.cs.lth.se/rawproperty/kommun> ?value2 ."
				+		"?art2 ?type2 ?value2 ."
				+		"FILTER (?art1 != ?art2 && ?value1 != ?value2 &&  ?type2 != <http://scn.cs.lth.se/rawproperty/kommun> )."
				+	"}";
		
		String[] queryVars = new String[3];
		String query2 = "select REDUCED ?type where{" +
				"<http://dbpedia.org/ontology/Place> ?type <http://dbpedia.org/ontology/Place> . " +
				"}";
		
		String[] queryVars2 = new String[1];
		queryVars2[0] = "type";
		queryVars[0] = "type2";
		String comper = "kommun";
		List<String[]> allOptions  =  db.question(query2, queryVars2);
		ArrayList<ValueHolder> res = new ArrayList<ValueHolder>();		
		for (String[] strings : allOptions) {
			String value = strings[0].substring(33);
			Integer temp2 = EditDistance.computeLevenshteinDistance(comper, value);
			ValueHolder temp = new ValueHolder(temp2, value);
			res.add(temp);
		}
		Collections.sort(res);
		for (ValueHolder valueHolder : res) {
			System.out.println(valueHolder.aginst + "\t"+ valueHolder.editDistance);	
		}
		
	}
	

}
