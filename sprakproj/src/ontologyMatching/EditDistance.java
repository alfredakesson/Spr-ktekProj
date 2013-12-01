package ontologyMatching;

public class EditDistance {

	public static void main(String[] args) {
		System.out.println("#### Edit Distance result:\n");
		printDistance("född", "död_datum");
		printDistance("född", "född_datum");
	}

	private static void printDistance(String string1, String string2) {
		System.out.println("String1: \t" +  string1);
		System.out.println("String2: \t"  + string2);
		System.out.println("Result: \t" + computeLevenshteinDistance(string1, string2));
		System.out.println("---------");
	}

	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}

	public static int computeLevenshteinDistance(String str1, String str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = minimum(
						distance[i - 1][j] + getDis(i-1,j),
						distance[i][j - 1] + getDis(i,j-1),
						distance[i - 1][j - 1]
								+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
										: getDis(i-1,j-1)));
//		for (int i = 0; i <= str1.length(); i++) {
//		    for (int j = 0; j <= str2.length(); j++) {
//		        System.out.print(distance[i][j] + "\t");
//		    }
//		    System.out.print("\n");
//		}
		return distance[str1.length()][str2.length()];
	}
	
	
	
	
	public static int getDis(int i, int j){
		int max = (i+j)/2;
		if(max >= 5){
			return 1;
		}
		return 2*(5-max);
	}
}
