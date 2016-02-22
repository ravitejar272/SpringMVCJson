package main.java.com.inteliment.wordcount.service;

	import java.io.BufferedReader;

	import java.io.File;
	import java.io.FileReader;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;
	import java.util.Set;
	import java.util.TreeMap;
	import main.java.com.inteliment.wordcount.model.Count;
	 
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
	public class WordCountServiceImpl   {
		private static TreeMap<String,Integer> countWordsMap = new TreeMap<String,Integer>();								//Map with the word as the key
	 	private static TreeMap<Integer, List<String>> mapSortedByOccurrences = new TreeMap< Integer, List<String>>();		//Map with the number of occurrences as the key; hence the words are in plural, hence the value = List of Strings 	

		private static Map<String, Integer> getCountWordsMap() {
			return countWordsMap;
		}


		private static void setCountWordsMap(TreeMap<String, Integer> countWordsMap) {
			countWordsMap = countWordsMap;
		}

	 	   
		/* A private Constructor prevents any other 
		  * class from instantiating.
		  */
		private WordCountServiceImpl(){ };
		   
		  
		//returns the String representation of words and their counts
		public  static void getInstance( ) {

			if( countWordsMap.isEmpty()) 	   
				populateCount(); 
		}

		//returns highest counts and the associated words; the numbre of items to be returned is given in topItems parameter
		//if topItems input arg is > the total number of words, then all the items are returned with the count frequency order
		//if topItems input arg is Integer.MAX, then all of the items are returned with the count frequency order
		public  static String getInstance(Integer topItems ) {
			 
			// calling it just for the effect, that is so as to get the Maps populated
			 getInstance();
			   
			int sofar = 0;
			StringBuffer resultBuffer = new StringBuffer();
			Set<Integer> keys = mapSortedByOccurrences.descendingKeySet();
			Integer key = 0;
			List<String> value = new ArrayList<String>();
			String word = new String();
			if(topItems < 1)
				return resultBuffer.toString();	//if topItems input arg is either 0 or negative, then return empty String

			for (Iterator i = keys.iterator(); i.hasNext();) {
	 			key = (Integer) i.next();
	 			value = (List<String>)  mapSortedByOccurrences.get(key);
				for(int j =0; j < value.size(); j++) {
					word = value.get(j);
	 			    resultBuffer.append(word + "|" + key + "\r\n");
				    sofar++;
				    if (sofar >= topItems) {	
	 			    	return resultBuffer.toString();
				    }	
				}
			}
	 		return resultBuffer.toString();
	 		    
		}
		
		/**
		 *  
		 * @param words
		 * @return count information of only the passed in words
		 */
		public  static Count[] getInstance(List<String> words) {
			//  calling it just for the effect, that is so as to get the Maps populated
	        getInstance();

	 		int number = 0;
	 		Count count = null;
	 		//List<Count> counts = new ArrayList<Count>();
	 		Count[] counts = new Count[words.size()];
	 		int index = 0;
			for (String word : words) {
				word = word.toLowerCase();
				
				if (countWordsMap.containsKey(word)) 
					number =(Integer) countWordsMap.get(word);			 
				else 
					number = 0;
				
				counts[index++] = new Count(word,number);
			 
	 		}
			return counts;
		}
		
		
		/** 
		 * @param words
		 * @return count information of only the passed in words
		 */
		public static String[] getInstance(List<String> words,boolean stringForm) {
			
			// calling it just for the effect, that is so as to get the Maps populated
			getInstance();

	 		int number = 0;
	   		String[] counts = new String[words.size()];
	 		int index = 0;
			for (String word : words) {
				word = word.toLowerCase();			
				if (countWordsMap.containsKey(word)) 
					number =(Integer) countWordsMap.get(word);			 
				else 
					number = 0;
				
				counts[index++] = new String(word +":" +number);
	 		}
			return counts;
		}

		
		//read the input text and populate the count maps, one Map with word as key and another Map as count as key and list of words as values
		private static void  populateCount() {

			Integer count = 0;
			try {		
				File file = new File("text.txt");
				if (file != null) {
					BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
					String line = reader.readLine();
					String[] words = null;
					while (line != null) {
						words = line.split("\\W+");
						for (String word : words) {
							word = word.toLowerCase();
							if (countWordsMap.containsKey(word)) {
								count =(Integer) countWordsMap.get(word) +1;
							}
							else {
								count =1;
							}
							countWordsMap.put(word, count);
						}
						line = reader.readLine();
					}
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
		private static void constructTopItems() {

			/** go through the countWordsMAp
			 * which has word as the key and formulate
			 * another map in which the key is the count and the value is the list of words that have that particular count
			 */
			StringBuffer sb = new StringBuffer();
			Set<String> keys = countWordsMap.keySet();
			String key = new String();		
			Integer value = 0;
			Integer occurrenceMapKey = 0;
			List<String> currentValue = new ArrayList<String>();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				  key = (String) i.next();
				  value = (Integer) countWordsMap.get(key);
				  sb.append(key + " |" + value + ",");
				  //build the top items list
				  occurrenceMapKey = value;
				  currentValue = mapSortedByOccurrences.get(occurrenceMapKey);
	 			  if (currentValue == null) {
	 				  currentValue = new ArrayList<String>();
	 			      currentValue.add(key);
				      mapSortedByOccurrences.put(occurrenceMapKey , currentValue);
	 			  }
				  else {
					  currentValue.add(key);
					  mapSortedByOccurrences.put(occurrenceMapKey , currentValue);
	 			  }	 
			}			   
		}
	 	   
		public static void main(String args[]) {
			WordCountServiceImpl wcsi = new WordCountServiceImpl();
	   		System.out.println(wcsi.getInstance(10));
	  		System.out.println(wcsi.getInstance(Integer.MAX_VALUE));
	  	    List<String> words = new ArrayList(Arrays.asList("Duis","Sed","Donec","Augue","Pellentesque","123"));
	    	System.out.println(wcsi.getInstance(words));		
		}	
	}
