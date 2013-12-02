package ontologyMatching;


public class ValueHolder implements Comparable<ValueHolder>{
	Integer editDistance;
	String aginst;
	
	public ValueHolder(int editDistance, String aginst) {
		super();
		this.editDistance = editDistance;
		this.aginst = aginst;
	}

	@Override
	public int compareTo(ValueHolder o) {
		return editDistance.compareTo(o.editDistance);
	}
	
}
