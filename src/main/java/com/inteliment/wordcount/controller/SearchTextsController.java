package main.java.com.inteliment.wordcount.controller;

import main.java.com.inteliment.wordcount. model.SearchResults;


import main.java.com.inteliment.wordcount.model.SearchCriteria;
import main.java.com.inteliment.wordcount.service.WordCount;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("counter-api/search")
public class SearchTextsController{
 
	@RequestMapping(  method = RequestMethod.POST )
	public @ResponseBody SearchResults showCounts( @RequestBody SearchCriteria searchCriteria) {
  	
	        List<String> wordsList = searchCriteria.getSearchText();                    //the search Curl command words input in json format gets into this method as SearchCriteria RequestBody input object. Jackson library enables reading JSON string and converting it to a Java object.  
  
	        SearchResults searchResults = new SearchResults();
	        //pass the input words to the WordCount Singleton class's method to obtain the counts of those words
	        String[] wordCounts = new WordCount().getInstance(wordsList,true);
 	  		searchResults.setCounts(wordCounts);													// set the obtained counts in the search results object
			return searchResults;                                                                            //and return the search results in the method's ResponseBody return parameter; jackson turns it into json representation
	 	}
	}