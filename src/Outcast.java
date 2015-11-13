import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
	private WordNet wn;
	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		this.wn = wordnet;
	}
	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast
		String target = "";
		int maxdist = 0;
		int d = 0;
		for (int i = 0; i < nouns.length; i++) {
			d = 0;
			for (int j = 0; j < nouns.length; j++) {
				if (i != j) {
					d += wn.distance(nouns[i], nouns[j]);
				}
			}
			if (d > maxdist) {
				maxdist = d;
				target = nouns[i];
			}
		}
		return target;
	}
	public static void main(String[] args) {
		// see test client below
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }

	}
}
