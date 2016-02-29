package main.java.com.inteliment.wordcount.dao;
import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;
import main.java.com.inteliment.wordcount.service.WordCountServiceImpl;

/**
 * Given a file of words, counts the word occurrences and
 * stores a Map of words and their occurrences and
 * stores another Map where the number of occurrences is the key(the value in this Map instance is a List of Strings since the count 100 for instance could happen for 2 or more words
 * rather than just 1 word 
 * This is a singleton class since it is always the same file
 * Assumptions:
 *        	1. If the file contents are modified, then a server restart is needed to produce correct output
 *         2. The file's words cannot contain special characters such as apostrophe etc since the split regex currently does not cater for special chars
 * @author Jaya
 *
 */
@Component
public class WordCountDao  {
	private static TreeMap<String,Integer> countWordsMap = new TreeMap<String,Integer>();							//Map with the word as the key
	public static TreeMap<Integer, List<String>> getMapSortedByOccurrences() {
		return mapSortedByOccurrences;
	}


	public static void setMapSortedByOccurrences(TreeMap<Integer, List<String>> mapSortedByOccurrences) {
		WordCountDao.mapSortedByOccurrences = mapSortedByOccurrences;
	}

	private static TreeMap<Integer, List<String>> mapSortedByOccurrences = new TreeMap< Integer, List<String>>();		//Map with the number of occurrences as the key; hence the words are in plural, hence the value = List of Strings 	

	public static Map<String, Integer> getCountWordsMap() {
		return countWordsMap;
	}


	public static void setCountWordsMap(TreeMap<String, Integer> countWordsMap) {
		WordCountDao.countWordsMap = countWordsMap;
	}


 	public WordCountDao(){ };

	//As a one off process, read the input file and parse it and populate the counts Maps if not done so yet
	public  void getCountsPopulated() {

		if( countWordsMap.isEmpty()) 	   			//if not already populated, populate counts now
			populateCount(); 
	}

 	//read the input text and populate the count maps, one Map with word as key and another Map as count as key and list of words as values
	public void  populateCount() {
 		
		try {
 			InputStream input = getClass().getClassLoader().getResourceAsStream("text.txt");
			if(input==null)
				return;

			Integer count = 0;
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader reader = new BufferedReader(isr);
			String line = reader.readLine();
			String[] words = null;
			while (line != null) {
				words = line.split("\\W+");
				for (String word : words) {
					word = word.toLowerCase();
					if (countWordsMap.containsKey(word)) {
						count =(Integer) countWordsMap.get(word) +1;		//increment the count
					}
					else {
						count =1;										//first occurrence of the word; initialise to 1
					}
					countWordsMap.put(word, count);
 				}
				line = reader.readLine();
			}	
		}	
		catch(Exception e) {
			System.err.println("exception " + e.getMessage());
		}

		constructTopItems();     //construct the Map which is ordered by number of occurrences of a word
	}

	/** go through the countWordsMAp
	 * which has word as the key and construct
	 * another map in which the key is the count and the value is the list of words that have that particular count
	 */
	public static void constructTopItems() {

  		Set<String> keys = countWordsMap.keySet();
 		Integer value = 0;
		Integer occurrenceMapKey = 0;
		List<String> currentValue = new ArrayList<String>();
		for (String key : keys) {
 			value = (Integer) countWordsMap.get(key);
 			//build the top items list
			occurrenceMapKey = value;											//This is the frequency of the word
			currentValue = mapSortedByOccurrences.get(occurrenceMapKey);		
			if (currentValue == null) {											//if this frequency has not yet occurred, then this is the first word for this freq
				currentValue = new ArrayList<String>();
				currentValue.add(key);
				mapSortedByOccurrences.put(occurrenceMapKey , currentValue);
			}
			else {																
				/**if this particular frequency has already occurred, then, add this word too to that list;
				For example, 100 times, already has a word attached called word1, if this word2 also occurs 100 times then add word2 also to it
				like key=100   value = <word1,word2>
				**/ 
				currentValue.add(key);
				mapSortedByOccurrences.put(occurrenceMapKey , currentValue);
			}	 
		}			   
	}

	public static void main(String args[]) {
		WordCountServiceImpl wcs = new WordCountServiceImpl();
		System.out.println(wcs.getCounts(10));
		System.out.println(wcs.getCounts(Integer.MAX_VALUE));
		List<String> words = new ArrayList(Arrays.asList("Duis","Sed","Donec","Augue","Pellentesque","123"));
	}	
}