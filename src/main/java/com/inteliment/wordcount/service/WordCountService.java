package main.java.com.inteliment.wordcount.service;
import java.util.Map;
import java.util.List;
/**
 * WordCountService interface which is implemented by WordCountServiceImpl class
 * @author Jaya
 *
 */
public interface WordCountService {
  	public String getCounts(Integer topItems );									//top most topItems number of words (occurrence wise)
  	public Map<String,Integer>[] getCounts(List<String> words);					//given the list of words, to return the map containing word and their counts
}