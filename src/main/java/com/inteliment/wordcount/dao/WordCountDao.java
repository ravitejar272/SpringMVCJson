package main.java.com.inteliment.wordcount.dao;
import java.io.BufferedReader;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import main.java.com.inteliment.wordcount.service.WordCountServiceImpl;

/**
 * Given a file of words, counts the word occurrences and
 * stores a Map of words and their occurrences and
 * Assumptions:
 *      	1. If the file contents are modified, then a server restart is needed to produce correct output
 *      2. Note: The regex used in the split command in this class is just for the sample file provided, this will not work for splitting words in an arbitrary file.  For example, hyphenated words and words with apostrophes will break this.
 * @author Jaya
 *
 */
@Component
public class WordCountDao  {
	private static TreeMap<String,Integer> countWordsMap = new TreeMap<String,Integer>();							//Map with the word as the key and the count as the value
 
	private static List<String> countsSortedByOccurrenceList = new ArrayList<String>();								//list of words with the frequency in descending order
 
	public static List<String> getCountsSortedByOccurrenceList() {
		return countsSortedByOccurrenceList;
	}


	public static void setCountsSortedByOccurrenceList(List<String> countsSortedByOccurrenceList) {
		WordCountDao.countsSortedByOccurrenceList = countsSortedByOccurrenceList;
	}


	public static Map<String, Integer> getCountWordsMap() {
		return countWordsMap;
	}


	public static void setCountWordsMap(TreeMap<String, Integer> countWordsMap) {
		WordCountDao.countWordsMap = countWordsMap;
	}


 	public WordCountDao()   throws Exception {
 		if(countWordsMap.isEmpty())
 			populateCount();																						//populate the count datastructures, such as the map and the list fields above
 	};

 
 	//read the input text and populate the count map and list, one Map with word as key and another List sorted with occurrences
	public void  populateCount() throws Exception  {
		InputStream input = getClass().getClassLoader().getResourceAsStream("text.txt");
		if(input==null)
			throw new Exception("Input file text.txt does not exist");
 
		Integer count = 0;
		try {
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
			throw new Exception("exception " + e.getMessage());
		}
	
  		constructTopItems();     //construct the List which is ordered by number of occurrences of a word
	}

	/** go through the countWordsMap
	 * which has word as the key and construct the list used for topmost items
 	 */
	public static void constructTopItems() {

		Set <Entry<String, Integer>> set = countWordsMap.entrySet();												//set containing Entries in the word count map
	    ArrayList<Entry<String, Integer>> tempList = new ArrayList<Entry<String, Integer>>(set);					//cast it to a list
	    
	    Collections.sort(tempList, new Comparator() {															//sort based on occurrences
		            @Override
		            public int compare(Object o1, Object o2) {
		            		Entry<String,Integer> e1 = (Entry<String,Integer>) o1;
		            		Entry<String,Integer> e2 = (Entry<String,Integer>) o2;
		                return (e2.getValue() > e1.getValue()) ? 1 : -1;
		            }
 		});		

	    for (Entry<String,Integer> temp : tempList) {
   			countsSortedByOccurrenceList.add(temp.getValue() + "|" + temp.getKey() + "\r\n");					//construct the list required for getting the topmost frequency words
  		} 
   	}
 }