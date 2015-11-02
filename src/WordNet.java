
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.SET;
import java.util.Arrays;

public class WordNet {
	private class synset {
		private Bag<synset> hypernyms = new Bag<synset>();
		private SET<String> synset = new SET<String>();
		private String gloss;
		public synset(String[] synset, String gloss){
			this.gloss = gloss;
			for(String key: synset) this.synset.add(key);
		}
		public void addHypernym(synset hypernym){
			hypernyms.add(hypernym);
		}
	}
	private synset[] synsets = new synset[2];
	private int n = 0;
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms){
		In synsetsIn = new In(synsets);
		for(String line = synsetsIn.readLine(); line != null; line = synsetsIn.readLine()){
			if (!(n < this.synsets.length)) {
				this.synsets = Arrays.copyOf(this.synsets, this.synsets.length*2);
			}
			String[] fields = line.split(",", 1);
			this.synsets[n++] = new synset(fields[0].split(" "), fields[1]);
		}
		In hypernymsIn = new In(hypernyms);
		for(String line = hypernymsIn.readLine(); line != null; line = hypernymsIn.readLine()){
			String[] fields = line.split(",", 1);
			this.synsets[n++] = new synset(fields[0].split(" "), fields[1]);
		}
		
	}
	
	// returns all WordNet nouns
	public Iterable<String> nouns(){
		
	}
	
	// is the word a WordNet noun?
	public boolean isNoun(String word){
		
	}
	
	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){
		
	}
	
	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		
	}
	private synset parseSynset(String line){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
