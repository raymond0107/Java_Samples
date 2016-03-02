/**
 * Prefix class
 *
 * Immutable class
 * This corresponds to the idea of a prefix in the problem description. 
 * That is, a sequence of words that are using as a basis to 
 * choose the next word to generate. 
 * Usually this sequence consists of the prefixLength previous words 
 * you have just generated (except for when you need to get a new initial prefix 
 * -- there was more about that in the section on markov text generation). 
 * When deciding on the Prefix methods, you should think in terms of 
 * what are the things we have to do with a prefix in this program.  
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Prefix {
	private int prefixLength;
	private ArrayList<String> list_Prefix;
	private ArrayList<String> sourceData;
	private ArrayList<String> list_successor;
	private HashMap<Prefix, ArrayList<String>> successor;
	private RandomTextGenerator generator;
	private Prefix prefix;

	public Prefix() {

	}
	public Prefix(ArrayList<String> list_Prefix, int prefixLength) {
		this.list_Prefix = list_Prefix;
		this.prefixLength = prefixLength;
	}

	public String listPrefix() {
		String ans = "";
		for (int i = 0; i < list_Prefix.size(); i++) {
			ans += list_Prefix.get(i) + " ";
		}
		return ans;
	}
	//overwrite hashCode.
	public int hashCode() {
		int sum = 0;
		for (int i = 0; i < list_Prefix.size(); i++) {
			sum += list_Prefix.get(i).length();
		}
		return sum;
	}

	//overwrite equals.
	public boolean equals(Object obj) {
		boolean flag = true;
		Prefix temp = (Prefix) obj;
		ArrayList<String> tar = temp.list_Prefix;
		if (list_Prefix.size() != tar.size()) {
			flag = false;
		}
		for (int i = 0; i < tar.size(); i++) {
			if (!tar.get(i).equals(list_Prefix.get(i))) {
				flag = false;
				break;
			}
		}
		return flag;
	}


}