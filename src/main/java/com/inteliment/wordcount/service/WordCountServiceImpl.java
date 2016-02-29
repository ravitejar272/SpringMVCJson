package main.java.com.inteliment.wordcount.service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import main.java.com.inteliment.wordcount.dao.WordCountDao; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

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
public class WordCountServiceImpl implements WordCountService {
	@Autowired  
	WordCountDao wordCountDao;  

 	public WordCountServiceImpl(){ };


	//returns highest counts and the associated words; the number of items to be returned is given in topItems parameter
	//if topItems input arg is > the total number of words, then all the items are returned with the count frequency order
 	public String getCounts(Integer topItems ) {

		// calling it just for the effect, that is so as to get the Maps populated
 		
		wordCountDao.getCountsPopulated();

		int sofar = 0;
		StringBuffer resultBuffer = new StringBuffer();
		if(topItems < 1)
			return resultBuffer.toString();	//if topItems input arg is either 0 or negative, then return empty String

		Set<Integer> keys = wordCountDao.getMapSortedByOccurrences().descendingKeySet();	//with the frequency of words in descending order
	 	List<String> value = new ArrayList<String>();
	 
		for(Integer key : keys) {														//for each frequency,
			value = (List<String>)  wordCountDao.getMapSortedByOccurrences().get(key);	//get the list of words for that freq
			for(String word : value) {													//for each word in that freq
				resultBuffer.append(word + "|" + key + "\r\n");							//compose the word | key   buffer
				sofar++;
				if (sofar >= topItems) {													//less number of topmost words are sought than words present, then return the sought after number of words
					return resultBuffer.toString();
				}	
			}
		}
	}

 
 	/**
	 *  
	 * @param words
	 * @return count information of only the passed in words
	 */
	public   Map<String,Integer>[] getCounts(List<String> words) {
		//  calling it just for the effect, that is so as to get the Maps populated
        wordCountDao.getCountsPopulated();
        
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        int number = 0;
        int index = 0;
		
        for (String word : words) {
			HashMap<String,Integer> countMap = new HashMap<String,Integer>();
			word = word.toLowerCase();
			
			if (wordCountDao.getCountWordsMap().containsKey(word)) 
				number =(Integer) wordCountDao.getCountWordsMap().get(word);			 
			else 
				number = 0;
			
			countMap.put(word,number);
			list.add(countMap);		 
 		}
        HashMap<String,Integer>[] maps = list.toArray(new HashMap[list.size()]);
		return maps;
	}

 	public static void main(String args[]) {
		WordCountServiceImpl wcs = new WordCountServiceImpl();
		System.out.println(wcs.getCounts(10));
		System.out.println(wcs.getCounts(Integer.MAX_VALUE));
		List<String> words = new ArrayList(Arrays.asList("Duis","Sed","Donec","Augue","Pellentesque","123"));
  	}	
}