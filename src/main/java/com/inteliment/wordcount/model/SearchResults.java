 package main.java.com.inteliment.wordcount.model;
 import java.util.Map;
/**
 * 
 * @author Jaya
 * Class encapsulating the search results
 * SearchResults class containing a Map of the words and their counts
 */
public class SearchResults {

	Map<String,Integer>[] counts;
 
	public Map<String,Integer>[] getCounts() {
		return counts;
	}
	public void setCounts(Map<String,Integer>[] counts) {
		this.counts = counts;
	}
 
	 
	public SearchResults() {
	}
}