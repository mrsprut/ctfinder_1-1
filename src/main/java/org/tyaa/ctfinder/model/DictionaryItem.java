package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class DictionaryItem {

	@Expose
	public String key;
	@Expose
	public String word;
	
	public DictionaryItem() {}

	public DictionaryItem(String key, String word) {
		super();
		this.key = key;
		this.word = word;
	}
}
