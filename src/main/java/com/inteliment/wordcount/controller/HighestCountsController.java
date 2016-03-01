package main.java.com.inteliment.wordcount.controller;

import main.java.com.inteliment.wordcount.model.SearchResults;


import main.java.com.inteliment.wordcount.service.WordCountService;
 
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired; 

@RestController

@RequestMapping( "/counter-api/top")
/**
 * 
 * @author Jaya
 * Controller class for /top curl command to obtain the topmost frequency words
 */
public class HighestCountsController{
	@Autowired  
	WordCountService wordCountService;  
	@RequestMapping(value = "{howMany}", method = RequestMethod.GET, produces = "text/csv"  )
	public @ResponseBody String getTop(@PathVariable String howMany) {

		int count = 0;
		try {
			count = Integer.parseInt(howMany);
		}
		catch(NumberFormatException nfe) {
			count = 1;					//defaulting to topmost 1 item in the event of wrong input such as alphanumeric instead of numeric resulting in 
										//NumberFormatException; This is mentioned in the assumptions in README doc
		}
 
		String wordCount = wordCountService.getCounts(count);
		return wordCount;	    
	}
}

