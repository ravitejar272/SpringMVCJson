package main.java.com.inteliment.wordcount.model;

 import java.util.List;
 
public class SearchCriteria   {
 
  	private List<String> searchText;	 
 
	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}

	public SearchCriteria() {
		
	}
	public SearchCriteria(List<String> searchText) {
		super();
		this.searchText = searchText;
	}
}

 