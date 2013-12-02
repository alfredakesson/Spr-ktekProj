package ontologyMatching;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CalculateEditDistancesTest {

	public static void main(String[] args) {
		
		String comper = null;
		List<String[]> allOptions  = null;
		ArrayList<ValueHolder> res = new ArrayList<ValueHolder>();		
		for (String[] strings : allOptions) {
			String value = strings[0].substring(33);
			Integer temp2 = EditDistance.computeLevenshteinDistance(comper, value);
			ValueHolder temp = new ValueHolder(temp2, value);
			res.add(temp);
		}
		Collections.sort(res);
		
	}
	

}
