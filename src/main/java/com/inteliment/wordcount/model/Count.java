package main.java.com.inteliment.wordcount.model;
 
public class Count {
	
	public Count(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}
 
    String word;
 
	int count;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
 
}
