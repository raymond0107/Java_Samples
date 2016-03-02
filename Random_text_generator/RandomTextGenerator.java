/**
 * RandomTextGenerator class
 * 
 * This will contain most of the program logic and data structures, 
 * and will probably only have a few methods. 
 * You will create it with some form of the source text 
 * and possibly other arguments. Then it will have another method to 
 * generate random text from that source.
 */
import java.util.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

public class RandomTextGenerator {
	private String answer;
	private Prefix findPrefix;
	private int prefixLength;
	private Random generator;
	private ArrayList<String> sourceData;
	private ArrayList<String> initialPrefix;
	private ArrayList<String> list_Prefix;
	private	ArrayList<String> list_Successor;
	private HashMap<Prefix, ArrayList<String>> successor;
	private HashSet<String> successForList_Set;
	private HashMap<Prefix, HashSet<String>> successorForList;
	private Iterator<String> iter;

	public RandomTextGenerator(ArrayList<String> sourceData, int prefixLength) {
		this.sourceData = sourceData;
		this.prefixLength = prefixLength;
	}

	public ArrayList<String> generateInitialPrefix () {
		if (sourceData.size() == 0) {
			return initialPrefix;
		}
		generator = new Random();
		int start = generator.nextInt(sourceData.size() - prefixLength);
		initialPrefix = new ArrayList<>();
		for (int i = start; i < prefixLength + start; i++) {
			initialPrefix.add(sourceData.get(i));
		}
		return initialPrefix;
	}

	public void creatSuccessor(int prefixLength) {
		if (sourceData.size() == 0) {
			return;
		}
		successor = new HashMap<Prefix, ArrayList<String>>();
		successorForList = new HashMap<Prefix, HashSet<String>>();
		for (int i = 0; i < sourceData.size() - prefixLength; i++) {
			list_Prefix = new ArrayList<String>();
			list_Successor = new ArrayList<String>();
			successForList_Set = new HashSet<String>();
			for (int j = i; j < i + prefixLength; j++) {
				list_Prefix.add(sourceData.get(j));
			}	
			findPrefix = new Prefix(list_Prefix, prefixLength);
			if (successor.containsKey(findPrefix)) {
				list_Successor = successor.get(findPrefix);
				list_Successor.add(sourceData.get(i + prefixLength));
				successor.put(findPrefix, list_Successor);
				successForList_Set = successorForList.get(findPrefix);
				successForList_Set.add(sourceData.get(i + prefixLength));
				successorForList.put(findPrefix, successForList_Set);

			}
			else {
				list_Successor = new ArrayList<String>();
				list_Successor.add(sourceData.get(i + prefixLength));
				successor.put(findPrefix, list_Successor);
				successForList_Set = new HashSet<String>();
				successForList_Set.add(sourceData.get(i + prefixLength));
				successorForList.put(findPrefix, successForList_Set);
			}

		}
		return;
	}

	public String chooseSuccessor(Prefix prefix) {
		if (sourceData.size() == 0) {
			return "";
		}
		generator = new Random();
		if (successor.containsKey(prefix)) {
			list_Successor = successor.get(prefix);
			int size = list_Successor.size();
			if (size == 0) {
				answer = "";
			}
			else {
				answer = list_Successor.get(generator.nextInt(size));
			}
		}
		else {
			answer = "";
		}
		return answer;
	}

	public String listSuccessor(Prefix prefix) {
		if (sourceData.size() == 0) {
			return "";
		}
		answer = "";
		if (successorForList.containsKey(prefix)) {
			successForList_Set = successorForList.get(prefix);
			int size = successForList_Set.size();
			if (size == 0) {
				return answer;
			}
			else {
				iter = successForList_Set.iterator();
				while (iter.hasNext()) {
					String temp = iter.next();
					answer += temp + " ";
				}
			}
		}
		else {
			answer = "";
		}
		return answer;
	}
}












