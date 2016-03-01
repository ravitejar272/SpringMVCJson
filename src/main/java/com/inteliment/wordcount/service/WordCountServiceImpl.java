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
import java.util.Map.Entry;

import main.java.com.inteliment.wordcount.dao.WordCountDao; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

/**
 * @author Jaya
 * The service class which returns the 1. occurrences for a given list of words and 2. The topmost occurring N number of words
 */
@Component
public class WordCountServiceImpl implements WordCountService {
@Autowired  
WordCountDao wordCountDao;
	
	public WordCountServiceImpl(){ };


	//returns highest counts and the associated words; the number of items to be returned is given in topItems parameter
	//if topItems input arg is > the total number of words, then all the items are returned with the count frequency order
	//and if the topItems <=0, empty String is returned
	public String getCounts(Integer topItems ) {

 		int soFar = 0;
		StringBuffer resultBuffer = new StringBuffer();
		if(topItems < 1)
			return resultBuffer.toString();	//if topItems input arg is either 0 or negative, then return empty String

		List<String> countsList = wordCountDao.getCountsSortedByOccurrenceList();	//with the frequency of words in descending order
		
		for(String temp : countsList) {
  			resultBuffer.append(temp);		 										//construct a String by appending the topmost occurring list of words
			soFar++;
			if (soFar >= topItems) {													//less number of topmost words are sought than words present, then return the sought after number of words
 				return resultBuffer.toString();
			}	
		}
 
		return resultBuffer.toString();
	}


	/**
	 *  
	 * @param words
	 * @return count information of the given passed in words in terms of Array of Maps with String as the key and Integer as the value
	 */
	public   Map<String,Integer>[] getCounts(List<String> words) {
 
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		int number = 0;
		int index = 0;

		for (String word : words) {
			HashMap<String,Integer> countMap = new HashMap<String,Integer>();
			word = word.toLowerCase();

			if (wordCountDao.getCountWordsMap().containsKey(word)) 
				number =(Integer) wordCountDao.getCountWordsMap().get(word);			 
			else 
				number = 0;																//this word does not occur in the input file

			countMap.put(word,number);
			list.add(countMap);		 
 		}
		HashMap<String,Integer>[] maps = list.toArray(new HashMap[list.size()]);
		return maps;
	}
}
