
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class WordNet {
	// constructor takes the name of the two input files
	private LinearProbingHashST<String,Bag<Integer>> nouns = new LinearProbingHashST<String,Bag<Integer>>();
	private Digraph wn;
	private String[] synsets = new String[2];
	//private String[] gloss = new String[2];
	private SAP s;
	public WordNet(String synsets, String hypernyms){
		if (synsets == null || synsets == null) throw new NullPointerException();
		In synsetsIn = new In(synsets);
		int V = 0;
		for(String line = synsetsIn.readLine(); line != null; line = synsetsIn.readLine()){
			String[] fields = line.split(",", 3);
			int idx = Integer.parseInt(fields[0]);
			while (idx > this.synsets.length-1) {
				this.synsets = Arrays.copyOf(this.synsets, this.synsets.length*2);
				//this.gloss = Arrays.copyOf(this.gloss, this.gloss.length*2);
			}
			this.synsets[idx] = fields[1];
			//this.gloss[idx] = fields[2];
			String[] syns = fields[1].split(" ");
			for(int i = 0; i < syns.length; i++){
				if(nouns.contains(syns[i])){
					nouns.get(syns[i]).add(idx);
				}else{
					Bag<Integer> tmp = new Bag<Integer>();
					tmp.add(idx);
					nouns.put(syns[i], tmp);
				}
			}
			V++;
		}
		wn = new Digraph(V);
		In hypernymsIn = new In(hypernyms);
		for(String line = hypernymsIn.readLine(); line != null; line = hypernymsIn.readLine()){
			String[] fields = line.split(",");
			int v = Integer.parseInt(fields[0]);
			for (int i = 1; i < fields.length; i++) {
				int w = Integer.parseInt(fields[i]);
				wn.addEdge(v, w);
			}
		}
		s = new SAP(wn);
	}
	
	// returns all WordNet nouns
	public Iterable<String> nouns(){
		return nouns.keys();
	}
	
	// is the word a WordNet noun?
	public boolean isNoun(String word){
		if (word == null) throw new NullPointerException();
		return nouns.contains(word);
	}
	
	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new NullPointerException();
		return s.length(nouns.get(nounA), nouns.get(nounB));
	}
	
	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new NullPointerException();
		int ancestor = s.ancestor(nouns.get(nounA), nouns.get(nounB));		
		return synsets[ancestor];
	}
	
/*	private Bag<String> define(String noun){
		Bag<String> result = new Bag<String>();
		for(int i: nouns.get(noun)){
			result.add(gloss[i]);
		}
		return result;
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String synset = args[0];
		String hypernym = args[1];
		WordNet wn = new WordNet(synset, hypernym);
		StdOut.println(wn.isNoun("hello"));
		/*for(String gloss: wn.define("b")){
			StdOut.println(gloss);
		}*/
		for(String noun: wn.nouns()){
			StdOut.println(noun);
		}
		StdOut.println(wn.distance("d", "d"));
	}

}
